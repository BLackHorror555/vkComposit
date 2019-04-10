import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.UserAuthResponse;
import com.vk.api.sdk.objects.friends.FriendsList;
import com.vk.api.sdk.objects.friends.responses.GetResponse;
import com.vk.api.sdk.queries.friends.FriendsGetQuery;

public class RequestHelper {

    private VkApiClient vkApiClient;
    private String host;
    private UserActor userActor;

    public RequestHelper(VkApiClient vkApiClient, String host) {
        this.vkApiClient = vkApiClient;
        this.host = host;
    }

    public void authenticate() throws ClientException, ApiException {
        UserAuthResponse authResponse = vkApiClient.oauth()
                .userAuthorizationCodeFlow(VkUtils.APP_ID, VkUtils.CLIENT_SECRET, getRedirectUri(), "code")
                .execute();

        userActor = new UserActor(authResponse.getUserId(), authResponse.getAccessToken());
    }

    public void getFriends() throws ClientException, ApiException {
        GetResponse getResponse = vkApiClient.friends().get(userActor).execute();
        FriendsList

    }

    private String getOAuthUrl() {
        return "https://oauth.vk.com/authorize?client_id=" + VkUtils.APP_ID + "&display=page&redirect_uri="
                + getRedirectUri() + "&scope=friends&response_type=code";
    }

    private String getRedirectUri() {
        return host + "/callback";
    }
}
