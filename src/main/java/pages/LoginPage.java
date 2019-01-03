package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    WebDriver driver;

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(name = "username")
    WebElement userTbx;
    @FindBy(name = "password")
    WebElement passTbx;
    @FindBy(xpath = "//button[text()='Login']")
    WebElement loginBtn;

    public void loginToApp() {
        String user = "user@phptravels.com";
        String pass = "demouser";

        userTbx.sendKeys(user);
        passTbx.sendKeys(pass);
        loginBtn.click();
    }
}
