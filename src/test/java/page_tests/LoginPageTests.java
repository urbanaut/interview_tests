package page_tests;

import base.TestBase;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.LoginPage;

import java.net.MalformedURLException;

public class LoginPageTests {

    WebDriver driver;
    LoginPage login;

    @Parameters({"browser"})
    @BeforeClass
    private void beforeClass(@Optional("chrome") String browser) throws MalformedURLException {
        TestBase base = new TestBase();
        driver = base.getDriver(browser);
        driver.get("https://www.phptravels.net/login");

        login = new LoginPage(driver);


    }

    @Test
    public void login() {
        login.loginToApp();
    }
}
