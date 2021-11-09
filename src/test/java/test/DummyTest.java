package test;

import helpers.APIs;
import helpers.PropertiesReader;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import org.aspectj.lang.annotation.After;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

public class DummyTest {

    IOSDriver driver = null;


    @BeforeMethod
    public void setUp(Method method) {
        System.out.println("Hello System");
    }

    @Test
    public void testing_01() {
        System.out.println(uid);
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

//    public void getCrumbInformation() {
//        responseJson = Unirest.get("https://e8f5-69-160-252-231.ngrok.io/crumbIssuer/api/json")
//                .basicAuth("rahee", "Surrahee22")
//                .asJson();
//
//        JSONArray array = responseJson.getBody().getArray();
//        String crumb = array.getJSONObject(0).getString("crumb");
//        String crumbRequestField = array.getJSONObject(0).getString("crumbRequestField");
//    }

//    @AfterMethod(alwaysRun = true)
//    public void tearDown() {
//        api.finishCleanupState(uid, status);
//    }

}
