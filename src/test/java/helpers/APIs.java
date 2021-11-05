package helpers;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;

import java.io.InputStream;

public class APIs {

    /**
     *
     * https://api.slack.com/messaging/webhooks
     * https://digitalai.slack.com/services/B02JYPV4MEK?added=1
     *
     */

    HttpResponse<JsonNode> responseJson = null;
    HttpResponse<String> responseString = null;
    HttpResponse<InputStream> responseInputStream = null;

    public String cloudUrlAndApiEndPoint() {
        return new PropertiesReader().getProperty("seetest.cloudUrl") + new PropertiesReader().getProperty("seetest.devices.apiEndPoint");
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
                .header("Authorization", "Bearer " + new PropertiesReader().getProperty("seetest.accessKey"))
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
                .header("Authorization", "Bearer eyJ4cC51Ijo3MzU0MjQsInhwLnAiOjIsInhwLm0iOiJNVFUzT0RZd016ZzFOek16TVEiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE4OTM5NjM4NTcsImlzcyI6ImNvbS5leHBlcml0ZXN0In0.GP0hK0o0j2WEKt-J0aXsVbu1tmt-PhWUryqluokszJk")
                .header("content-type", "application/json")
                .asJson();

        System.out.println(responseJson.getStatus());
        System.out.println(responseJson.getBody());
    }

    public void addDeviceTag(String deviceId, String tagValue) {
        // PUT /api/v1/devices/{id}/tags/{tag_value}
        responseJson = Unirest.put(cloudUrlAndApiEndPoint() + "/" + deviceId + "/tags/" + tagValue)
                .header("Authorization", "Bearer eyJ4cC51Ijo3MzU0MjQsInhwLnAiOjIsInhwLm0iOiJNVFUzT0RZd016ZzFOek16TVEiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE4OTM5NjM4NTcsImlzcyI6ImNvbS5leHBlcml0ZXN0In0.GP0hK0o0j2WEKt-J0aXsVbu1tmt-PhWUryqluokszJk")
                .header("content-type", "application/json")
                .asJson();

        System.out.println(responseJson.getStatus());
        System.out.println(responseJson.getBody());
    }

    public void removeDeviceTag(String deviceId, String tagValue) {
        // DELETE /api/v1/devices/{id}/tags/{tag_value}
        responseJson = Unirest.delete(cloudUrlAndApiEndPoint() + "/" + deviceId + "/tags/" + tagValue)
                .header("Authorization", "Bearer eyJ4cC51Ijo3MzU0MjQsInhwLnAiOjIsInhwLm0iOiJNVFUzT0RZd016ZzFOek16TVEiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE4OTM5NjM4NTcsImlzcyI6ImNvbS5leHBlcml0ZXN0In0.GP0hK0o0j2WEKt-J0aXsVbu1tmt-PhWUryqluokszJk")
                .header("content-type", "application/json")
                .asJson();

        System.out.println(responseJson.getStatus());
        System.out.println(responseJson.getBody());
    }

    public void removeAllDeviceTags(String deviceId) {
        // DELETE /api/v1/devices/{id}/tags
        responseJson = Unirest.delete(cloudUrlAndApiEndPoint() + "/" + deviceId + "/tags")
                .header("Authorization", "Bearer eyJ4cC51Ijo3MzU0MjQsInhwLnAiOjIsInhwLm0iOiJNVFUzT0RZd016ZzFOek16TVEiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE4OTM5NjM4NTcsImlzcyI6ImNvbS5leHBlcml0ZXN0In0.GP0hK0o0j2WEKt-J0aXsVbu1tmt-PhWUryqluokszJk")
                .header("content-type", "application/json")
                .asJson();

        System.out.println(responseJson.getStatus());
        System.out.println(responseJson.getBody());
    }

}
