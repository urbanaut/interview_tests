package base;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static java.util.concurrent.TimeUnit.SECONDS;
import static utils.GetProperties.*;

public class TestBase {

    public WebDriver driver;

    @Parameters({"browser"})
    @BeforeSuite
    public void init(@Optional("chrome") String browser) {
        try {
            setDriver(browser);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public WebDriver getDriver(String browser) throws MalformedURLException {
        setDriver(browser);
        return driver;
    }

    private void setDriver(String browser) throws MalformedURLException {
        switch(browser) {
            case "android":
                driver = initAppiumDriver();
                break;
            case "chrome":
                driver = initChromeDriver();
                break;
            case "firefox":
                driver = initFirefoxDriver();
                break;
            case "edge":
                driver = initEdgeDriver();
                break;
            case "ie":
                driver = initIEDriver();
                break;
            case "mobile":
                driver = initMobileDriver();
                break;
            case "opera":
                driver = initOperaDriver();
                break;
            default:
                driver = initChromeDriver();
        }
    }

    private WebDriver initChromeDriver() {
        System.out.println("Launching Chrome browser...");
        System.out.println("Using driver path: " + CHROME_DRIVER_PATH);
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--start-maximized");
        options.addArguments("chrome.switches","--disable-extensions"); //Removes popup reminder for disabling extensions
        options.setBinary(CHROME_BINARY);
        options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT);

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        options.setExperimentalOption("prefs", prefs);

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(20, SECONDS);
        driver.navigate().to(BASE_URL);
        return driver;
    }

    private WebDriver initFirefoxDriver() {
        System.out.println("Launching Firefox browser...");
        System.out.println("Using driver path: " + FIREFOX_DRIVER_PATH);
        System.setProperty("webdriver.gecko.driver", FIREFOX_DRIVER_PATH);

        FirefoxOptions options = new FirefoxOptions();
        options.setLogLevel(FirefoxDriverLogLevel.WARN);

        driver = new FirefoxDriver(options);
        driver.manage().timeouts().implicitlyWait(10, SECONDS);
        driver.navigate().to(BASE_URL);
        return driver;
    }

    private WebDriver initEdgeDriver() {
        System.out.println("Launching Edge browser...");
        System.out.println("Using driver path: " + MS_DRIVER_PATH);
        System.setProperty("webdriver.edge.driver", MS_DRIVER_PATH);

        driver = new EdgeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, SECONDS);
        driver.navigate().to(BASE_URL);
        return driver;
    }

    private WebDriver initIEDriver() {
        System.out.println("Launching Internet Explorer browser...");
        System.out.println("Using driver path: " + IE_DRIVER_PATH);
        System.setProperty("webdriver.ie.driver", IE_DRIVER_PATH);

        InternetExplorerOptions options = new InternetExplorerOptions();
        options.getCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION);
        options.ignoreZoomSettings();

        driver = new InternetExplorerDriver(options);
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, SECONDS);
        driver.navigate().to(BASE_URL);
        return driver;
    }

    private WebDriver initOperaDriver() {
        System.out.println("Launching Opera browser...");
        System.out.println("Using driver path: " + OPERA_DRIVER_PATH);
        System.setProperty("webdriver.opera.driver", OPERA_DRIVER_PATH);

        OperaOptions options = new OperaOptions();
        options.setBinary(OPERA_BINARY);

        driver = new OperaDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, SECONDS);
        driver.navigate().to(BASE_URL);
        return driver;
    }

    private WebDriver initAppiumDriver() throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, "7.0");
        caps.setCapability(MobileCapabilityType.DEVICE_NAME, "UIPTest");
        caps.setCapability(MobileCapabilityType.BROWSER_NAME, "Chrome");

        URL url = new URL("http://127.0.0.1:4723/wd/hub");
        driver = new AppiumDriver(url, caps);
        return driver;
    }

    private WebDriver initMobileDriver() {
        System.out.println("Launching Chrome mobile emulation browser...");
        System.out.println("Using driver path: " + CHROME_DRIVER_PATH);
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);

        Map<String, Object> deviceMetrics = new HashMap<>();
        deviceMetrics.put("width", DEVICE_WIDTH);
        deviceMetrics.put("height", DEVICE_HEIGHT);
        deviceMetrics.put("pixelRatio", DEVICE_PIXEL_RATIO);

        Map<String, Object> mobileEmu = new HashMap<>();
        mobileEmu.put("deviceMetrics", deviceMetrics);
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setExperimentalOption("mobileEmulation", mobileEmu);

        driver = new ChromeDriver(chromeOptions);
        return driver;
    }


    //    @AfterSuite
    public void tearDown() {
        driver.quit();
    }
}
