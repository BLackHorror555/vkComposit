import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.httpclient.HttpTransportClient;

public class Application {

    public static void main(String[] args) {
        TransportClient transportClient = HttpTransportClient.getInstance();
        VkApiClient vk = new VkApiClient(transportClient);
        RequestHelper requestHelper = new RequestHelper(vk);
        try {
            requestHelper.authenticate(183184707);
            requestHelper.getFriends();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
