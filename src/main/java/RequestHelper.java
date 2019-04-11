import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiAccessException;
import com.vk.api.sdk.objects.friends.responses.GetResponse;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class RequestHelper {

    private VkApiClient vkApiClient;
    private UserActor userActor;

    public RequestHelper(VkApiClient vkApiClient) {
        this.vkApiClient = vkApiClient;
    }

    public void authenticate(int userId) throws Exception {
        userActor = new UserActor(userId, askEnterToken(getOAuthUrl()));
    }

    public ArrayList<Friend> getUserFriendsTwoLevelDepth() throws Exception {
        GetResponse getResponse = getFriendsResponse(userActor.getId());
        ArrayList<Friend> friendsOne = new ArrayList<>();
        Friend friendOneToAdd = new Friend();

        for (int i = 0; i < getResponse.getItems().size(); i++) {
            ArrayList<Friend> friendsTwo = new ArrayList<>();
            Friend friendTwoToAdd = new Friend();
            GetResponse friendsTwoResponse;
            try {
                friendsTwoResponse = getFriendsResponse(getResponse.getItems().get(i));
            } catch (Exception ex) {
                continue;
            }

            for (int j = 0; j < friendsTwoResponse.getItems().size(); j++) {
                Friend lastFriend = new Friend();
                lastFriend.setNeedToVisualizeFriends(false);
                GetResponse anotherFriends;
                try {
                    anotherFriends = getFriendsResponse(friendsTwoResponse.getItems().get(j));
                } catch (Exception ex) {
                    continue;
                }
                boolean isFriends;
                try {
                    isFriends = getFriendsResponse(anotherFriends.getItems().get(j)).getCount() != 0;
                } catch (Exception ex) {
                    isFriends = false;
                }
                if (isFriends) {
                    lastFriend.setFriends(new ArrayList<>());
                }

                friendsTwo.add(lastFriend);
            }
            friendTwoToAdd.setFriends(friendsTwo);
        }
        friendOneToAdd.setFriends(friendsOne);
        friendsOne.add(friendOneToAdd);

        return friendsOne;
    }

    private GetResponse getFriendsResponse(int userId) throws Exception {
        Thread.sleep(400);
        return vkApiClient.friends().get(userActor)
                .userId(userId)
                .count(2)
                .execute();
    }

    private String getOAuthUrl() {
        return "https://oauth.vk.com/authorize?client_id=" + VkUtils.APP_ID
                + "&display=page&redirect_uri=" + VkUtils.REDIRECT_URI
                + "&scope=friends&response_type=token&v=5.95";
    }

    private String askEnterToken(String link) throws IOException, URISyntaxException {
        Desktop.getDesktop().browse(new URI(link));
        return JOptionPane.showInputDialog("Please input access_token param from browser: ");
    }
}
