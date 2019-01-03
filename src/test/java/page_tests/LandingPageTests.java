package page_tests;

import base.TestBase;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.LandingPage;

import java.net.MalformedURLException;

public class LandingPageTests {

    WebDriver driver;
    LandingPage landing;

    @Parameters({"browser"})
    @BeforeClass
    private void beforeClass(@Optional("chrome") String browser) throws MalformedURLException {
        TestBase base = new TestBase();
        driver = base.getDriver(browser);
        landing = new LandingPage(driver);
    }

    @Test
    public void openLoginPage() throws InterruptedException {
//        Thread.sleep(1000);
        landing.openLoginPage();
//        landing.selectPickUpLoc();
    }

}
