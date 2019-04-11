import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.friends.responses.GetResponse;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class RequestHelper {

    private VkApiClient vkApiClient;
    private UserActor userActor;

    public RequestHelper(VkApiClient vkApiClient) {
        this.vkApiClient = vkApiClient;
    }

    public void authenticate(int userId) throws Exception {
        userActor = new UserActor(userId, askEnterToken(getOAuthUrl()));
    }

    public void getFriends() throws ClientException, ApiException {
        GetResponse getResponse = vkApiClient.friends().get(userActor).execute();
        System.out.println(getResponse);
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
