package helpers;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class PropertiesReader {

    public PropertiesReader() {}

    public String getProperty(String property) {
        Properties props = System.getProperties();

        try {
            if (getOperatingSystem().contains("Win")) {
                props.load(new FileInputStream(new File(System.getProperty("user.dir") + "\\src\\test\\resources\\config.properties")));
            } else if (getOperatingSystem().contains("Mac")) {
                props.load(new FileInputStream(new File(System.getProperty("user.dir") + "/src/test/resources/config.properties")));
            }

            if (props.getProperty("seetest.accessKey").isEmpty()) {
                throw new Exception("SeeTest Cloud Access Key not found. Please look in resources/config.properties.");
            }

            if (props.getProperty("seetest.cloudUrl").isEmpty()) {
                throw new Exception("SeeTest Cloud URL not found. Please look in resources/config.properties.");
            }

            if (props.getProperty("seetest.devices.apiEndPoint").isEmpty()) {
                throw new Exception("SeeTest Cloud Devices End Point not found. Please look in resources/config.properties.");
            }

            if (props.getProperty("slack.webhookUrl").isEmpty()) {
                throw new Exception("Slack Webhook URL not found. Please look in resources/config.properties.");
            }

        } catch(Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return System.getProperty(property);
    }

    private String getOperatingSystem() {
        String os = System.getProperty("os.name");
        return os;
    }

}
