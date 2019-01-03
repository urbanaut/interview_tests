package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LandingPage {

    private WebDriver driver;

    public LandingPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(xpath = "//nav//li[@id='li_myaccount']")
    WebElement myAccountCbx;
    @FindBy(xpath = "//a[text()=' Login']")
    WebElement loginBtn;
    @FindBy(xpath = "//a[@title='Cars']")
    WebElement carsBtn;
    @FindBy(xpath = "//span[@class='select2-chosen']")
    WebElement pickUpLoc;



    public void openLoginPage() {
        myAccountCbx.click();
        loginBtn.click();
    }

    public void selectPickUpLoc() throws InterruptedException {
        carsBtn.click();
        Thread.sleep(1000);
        pickUpLoc.click();
    }
}
