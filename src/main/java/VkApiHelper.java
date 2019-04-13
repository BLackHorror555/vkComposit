import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.friends.responses.GetResponse;
import friends.CloseFriend;
import friends.Friend;
import friends.OpenFriend;
import utils.VkUtils;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class VkApiHelper {

    private VkApiClient vkApiClient;
    private UserActor userActor;

    public VkApiHelper() {
    }

    public void initClient() {
        TransportClient transportClient = HttpTransportClient.getInstance();
        vkApiClient = new VkApiClient(transportClient); }

    public void authenticate(int userId) throws Exception {
        userActor = new UserActor(userId, askEnterToken(getOAuthUrl()));
    }

    public List<Friend> getFriends() {
        List<Friend> friends = getFriendsOfUser(userActor.getId());
        for (Friend friend : friends) {
            friend.setFriends(getFriendsOfUser(friend.getId()));
            for (Friend friendOfFriend : friend.getFriends()) {
                friendOfFriend.setFriends(getFriendsOfUser(friend.getId()));
                for (Friend ff : friendOfFriend.getFriends()) {
                    if (ff instanceof OpenFriend) {
                        ((OpenFriend)ff).setNeedToVisualizeFriends(false);
                    }
                }
            }
        }
        return friends;
    }

    private ArrayList<Friend> getFriendsOfUser(int userId) {
        try {
            ArrayList<Friend> friends = new ArrayList<>();
            GetResponse getResponse = getFriendsResponse(userId, VkUtils.MAX_FRIENDS_COUNT);
            for (Integer id : getResponse.getItems()) {
                friends.add(createFriend(id));
            }
            return friends;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }

    }

    private Friend createFriend(int id) {
        GetResponse getResponse;
        try {
            getResponse = getFriendsResponse(id, 1);
            if (getResponse.getCount() == 0) {
                return new CloseFriend(id);
            } else {
                return new OpenFriend(id);
            }
        } catch (Exception e) {
            return new CloseFriend(id);
        }
    }

    private GetResponse getFriendsResponse(int userId, int count) throws Exception {
        Thread.sleep(VkUtils.REQUEST_DELAY_MS);
        return vkApiClient.friends().get(userActor)
                .userId(userId)
                .count(count)
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
