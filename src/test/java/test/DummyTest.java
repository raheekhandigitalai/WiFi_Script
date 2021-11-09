package test;

import helpers.APIs;
import helpers.PropertiesReader;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

public class DummyTest {

    private APIs api = null;

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

    @BeforeMethod
    public void setUp(Method method) {
        System.out.println("Hello System");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        api.finishCleanupState(uid, status);
    }

}
