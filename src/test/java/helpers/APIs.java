package helpers;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.InputStream;

public class APIs {

    /**
     *
     * https://api.slack.com/messaging/webhooks
     * https://digitalai.slack.com/services/B02JYPV4MEK?added=1
     *
     */

    protected HttpResponse<JsonNode> responseJson = null;
    protected HttpResponse<String> responseString = null;
    protected HttpResponse<InputStream> responseInputStream = null;

    public String cloudUrlAndApiEndPoint() {
        return new PropertiesReader().getProperty("seetest.cloudUrl") + new PropertiesReader().getProperty("seetest.devices.apiEndPoint");
    }

    public String accessKey() {
        return new PropertiesReader().getProperty("seetest.accessKey");
    }

    public String accessKeyCleanupUser() {
        return new PropertiesReader().getProperty("seetest.cleanupUser.accessKey");
    }

    public String cloudUrl() {
        return new PropertiesReader().getProperty("seetest.cloudUrl");
    }

//    public void sendSlackMessage(String message) {
//        responseString = Unirest.post(new PropertiesReader().getProperty("slack.webhookUrl"))
//                .header("content-type", "application/json")
//                .body("{\r\n    \"text\": \"" + message + "\"\r\n}")
//                .asString();
//
//        if (responseString.getStatus() == 200) {
//            System.out.println("HTTP Status: " + responseString.getStatus());
//            System.out.println("HTTP Body: " + responseString.getBody());
//            System.out.println("Successfully sent Slack Message: " + message);
//        } else {
//            System.err.println("HTTP Status: " + responseString.getStatus());
//            System.err.println("HTTP Body: " + responseString.getBody());
//            System.err.println("Failed to send Message");
//        }
//    }

    public String getDeviceId(String serialNumber) {
        responseJson = Unirest.get(cloudUrlAndApiEndPoint() + "?query=@serialnumber='" + serialNumber + "'")
                .header("Authorization", "Bearer " + accessKey())
                .header("content-type", "application/json")
                .asJson();

        System.out.println(responseJson.getStatus());
        System.out.println(responseJson.getBody());
        System.out.println("====================");

        JSONArray array = responseJson.getBody().getArray();
        JSONArray data = array.getJSONObject(0).getJSONArray("data");

        String[] newArray = data.toString().split(",");

        String deviceId = newArray[0].replace("[{\"id\":", "").replace("\"", "").trim();
        return deviceId;
    }

    public void getDeviceTags(String deviceId) {
        // GET /api/v1/devices/{id}/tags
        responseJson = Unirest.get(cloudUrlAndApiEndPoint() + "/" + deviceId + "/tags")
                .header("Authorization", "Bearer " + accessKey())
                .header("content-type", "application/json")
                .asJson();

        System.out.println(responseJson.getStatus());
        System.out.println(responseJson.getBody());
    }

    public void addDeviceTag(String deviceId, String tagValue) {
        // PUT /api/v1/devices/{id}/tags/{tag_value}
        responseJson = Unirest.put(cloudUrlAndApiEndPoint() + "/" + deviceId + "/tags/" + tagValue)
                .header("Authorization", "Bearer " + accessKey())
                .header("content-type", "application/json")
                .asJson();

        System.out.println(responseJson.getStatus());
        System.out.println(responseJson.getBody());
    }

    public void removeDeviceTag(String deviceId, String tagValue) {
        // DELETE /api/v1/devices/{id}/tags/{tag_value}
        responseJson = Unirest.delete(cloudUrlAndApiEndPoint() + "/" + deviceId + "/tags/" + tagValue)
                .header("Authorization", "Bearer " + accessKey())
                .header("content-type", "application/json")
                .asJson();

        System.out.println(responseJson.getStatus());
        System.out.println(responseJson.getBody());
    }

    public void removeAllDeviceTags(String deviceId) {
        // DELETE /api/v1/devices/{id}/tags
        responseJson = Unirest.delete(cloudUrlAndApiEndPoint() + "/" + deviceId + "/tags")
                .header("Authorization", "Bearer " + accessKey())
                .header("content-type", "application/json")
                .asJson();

        System.out.println(responseJson.getStatus());
        System.out.println(responseJson.getBody());
    }

    public void finishCleanupState(String uid, String status) {
        HttpPost post = new HttpPost(cloudUrl() + "/api/v1/cleanup-finish?deviceId=" + uid + "&status=" + status);
        post.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessKeyCleanupUser());

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(post)) {
        } catch (Exception ignore){ }
    }

}
