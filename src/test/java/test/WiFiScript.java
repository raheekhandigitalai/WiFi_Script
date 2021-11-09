package test;

import helpers.APIs;
import helpers.PropertiesReader;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class WiFiScript {

    private IOSDriver<IOSElement> driver = null;
    private DesiredCapabilities dc = new DesiredCapabilities();
    private APIs api = null;

    private String wifiName = "UKCustomers_ALL";
    private String certName = "mitmproxy";

    private String goodTagValue = "Good_Device";
    private String badTagValue = "Bad_Device";

    private String uid = System.getenv("deviceID");
    private String os = System.getenv("deviceOS");
    private String deviceName = System.getenv("deviceName");
    private String osVersion = System.getenv("osVersion");
    private String deviceModel = System.getenv("deviceModel");
    private String deviceManufacturer = System.getenv("deviceManufacturer");
    private String deviceCategory = System.getenv("deviceCategory");
    private String username = System.getenv("username");
    private String userProject = System.getenv("userProject");

    private String status = "failed";

    private String UDID = "011adb5554652d475d6a4f325877a48ccc384e75";

    @BeforeMethod
    public void setUp(Method method) throws MalformedURLException {
        api = new APIs();
        dc.setCapability("testName", method.getName());
        dc.setCapability("accessKey", new PropertiesReader().getProperty("seetest.cleanupUser.accessKey"));
//        dc.setCapability("deviceQuery", "@serialnumber='" + uid + "'");
        dc.setCapability("releaseDevice", false);
        dc.setCapability("udid", UDID);
        driver = new IOSDriver<>(new URL(new PropertiesReader().getProperty("seetest.cloudUrl") + "/wd/hub"), dc);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
//        api.finishCleanupState(uid, status);
        api.finishCleanupState(UDID, status);
        System.out.println("Report URL: " + driver.getCapabilities().getCapability("reportUrl"));
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void check_wifi_state() throws InterruptedException {
        // Get Device Serial Number using Desired Capabilities
        String deviceId = api.getDeviceId((String) driver.getCapabilities().getCapability("udid"));

        // Launch Settings, last parameter "true" defines the Settings App will launch a fresh instance, to ensure we are always on the front page
        driver.executeScript("seetest:client.launch(\"com.apple.Preferences\", \"false\", \"true\")");

        // Wait for WiFi object to be clickable, and click once found
        new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@text='Wi-Fi' and @knownSuperClass='UITableViewLabel']"))).click();

        String wifiLabel = null;
        // Different iOS Models / Versions had different identifiers for finding the WiFi text, hence handling as try / catch
        try {
            wifiLabel = new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='selected']//parent::*/following-sibling::*[@knownSuperClass='UILabel']"))).getText().trim();
        } catch (Exception e) {
            wifiLabel = new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='checkmark']//parent::*/following-sibling::*[@knownSuperClass='UILabel']"))).getText().trim();
        }

        // Check if WiFi Label contains the desired WiFi name
        if (wifiLabel.contains(wifiName)) {
            System.out.println("Contains WiFi Name");
            api.removeAllDeviceTags(deviceId);
            api.addDeviceTag(deviceId, goodTagValue);
        } else {
            // Check Cert
            System.out.println("Does Not Contain WiFi Name");

            // Launch Settings, last parameter "true" defines the Settings App will launch a fresh instance, to ensure we are always on the front page
            driver.executeScript("seetest:client.launch(\"com.apple.Preferences\", \"false\", \"true\")");

            // Swipe down and click on "General"
            driver.executeScript("seetest:client.swipeWhileNotFound(\"DOWN\", 500, 1000, \"NATIVE\", \"//XCUIElementTypeCell[@id='General' and @onScreen='true']\", 0, 1000, 2, true)");

            // Swipe down and click on "Profiles"
            driver.executeScript("seetest:client.swipeWhileNotFound(\"DOWN\", 500, 1000, \"NATIVE\", \"//*[@id='ManagedConfigurationList' and @onScreen='true']\", 0, 1000, 2, true)");

            // Check if we are on the Profiles page
            if (new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@accessibilityLabel='CONFIGURATION PROFILE']"))).isDisplayed()) {
                // Define a list of elements based on certs present in the Profiles page
                List<IOSElement> profiles = driver.findElements(By.xpath("//XCUIElementTypeCell"));

                // Iterate through each profile to verify whether the desired Cert exists
                for (IOSElement profile : profiles) {
                    if (profile.getText().contains(certName)) {
                        System.out.println("Hello 1");
                    } else {
                        System.out.println("Hello 2");
                    }
                }

                api.removeAllDeviceTags(deviceId);
                api.addDeviceTag(deviceId, badTagValue);
//                api.sendSlackMessage("Device Not Good");
            } else {
                System.out.println("Did not get into Certs page");
            }

        }
        status = "passed";
    }

}
