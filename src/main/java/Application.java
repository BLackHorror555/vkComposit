import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.httpclient.HttpTransportClient;

import java.util.ArrayList;

public class Application {

    public static void main(String[] args) {
        TransportClient transportClient = HttpTransportClient.getInstance();
        VkApiClient vk = new VkApiClient(transportClient);
        RequestHelper requestHelper = new RequestHelper(vk);

        try {
            requestHelper.authenticate(183184707);
            ArrayList<Friend> friends = requestHelper.getUserFriendsTwoLevelDepth();

            for (Friend friend : friends) {
                friend.draw(3);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
