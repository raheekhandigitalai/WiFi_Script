package test;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import java.net.MalformedURLException;
import java.net.URL;

import io.appium.java_client.remote.MobileCapabilityType;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class WebHookTest {

    private String accessKey = "eyJ4cC51IjoyLCJ4cC5wIjoxLCJ4cC5tIjoiTVRVMk1Ea3pNemM0TURJM013IiwiYWxnIjoiSFMyNTYifQ.eyJleHAiOjE5MDIzMDk5OTQsImlzcyI6ImNvbS5leHBlcml0ZXN0In0.2MlUpOV5QR731X-fSl6KDbIscQQtW01YmNneWZb14cI";
    private IOSDriver<IOSElement> driver = null;
    private DesiredCapabilities dc = new DesiredCapabilities();
    private String status = "failed";
    private String uid = System.getenv("deviceID");

    @BeforeMethod
    public void setUp() throws MalformedURLException {
        System.out.println(System.getenv());
        dc.setCapability("testName", "Eribank iOS - from WebHook");
        dc.setCapability("accessKey", accessKey);
        dc.setCapability("deviceQuery", "@serialnumber='" + uid + "'");
        dc.setCapability("releaseDevice", false);
//        dc.setCapability(MobileCapabilityType.APP, "cloud:com.experitest.ExperiBank");
        dc.setCapability(IOSMobileCapabilityType.BUNDLE_ID, "com.apple.Preferences");
        driver = new IOSDriver<>(new URL("https://uscloud.experitest.com/wd/hub"), dc);
    }

    @Test
    public void quickStartiOSNativeDemo() throws InterruptedException {
        System.out.println(uid);
//        driver.findElement(By.xpath("//*[@id='usernameTextField']")).sendKeys("company");
//        driver.findElement(By.xpath("//*[@id='passwordTextField']")).sendKeys("company");
//        driver.findElement(By.xpath("//*[@id='loginButton']")).click();
//        driver.executeScript("seetest:client.launch(\"com.apple.Preferences\", \"false\", \"true\")");
//        driver.executeScript("seetest:client.launch(\"com.apple.Preferences\", \"false\", \"true\")");
        Thread.sleep(5000);
        status = "passed";
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
//        System.out.println("Report URL: "+ driver.getCapabilities().getCapability("reportUrl"));
        driver.quit();
        sendResponseToCloud();
    }

    private void sendResponseToCloud() {
        System.out.println("================ STATUS: " + status);
        HttpPost post = new HttpPost("https://uscloud.experitest.com/api/v1/cleanup-finish?deviceId=" + uid + "&status=" + status);
        post.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessKey);
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
                CloseableHttpResponse response = httpClient.execute(post)) {
        } catch (Exception ignore){ }
    }

}