package non_page_tests;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ResponseBody;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static com.jayway.restassured.RestAssured.given;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.Matchers.containsString;

public class CodingInterviewTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeClass
    public void init() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(20, SECONDS);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, 15);
    }


    // REST API Tests //
    @Test
    public void testRestApi() {
        RestAssured.baseURI = "https://reqres.in/";
        Response response = RestAssured.given()
                .when()
                .get("api/users/1")
                .then()
                .assertThat()
                .statusCode(200)
                .extract().response();

        System.out.println(response.asString());

        String json = response.asString();
        JsonPath jp = new JsonPath(json);

        System.out.println(jp.get("data.first_name"));

        Assert.assertEquals("George", jp.get("data.first_name"));
    }

    @Test
    public void testRecipeApi() {
        RestAssured.baseURI ="http://www.recipepuppy.com/api/";
        Response response = RestAssured.given()
                .when()
                .get("?i=banana&p=4");

        ResponseBody body = response.getBody();
        String bodyJson = body.asString();
        List<String> ingredients = JsonPath.from(bodyJson).get("results.ingredients");

        for (String ingredient : ingredients) {
            Assert.assertTrue(ingredient.contains("banana"));
        }

        String responseBody = response.asString();
        JsonPath jp = new JsonPath(responseBody);

        String recipe = jp.get("results[1].title");
        Assert.assertTrue(recipe.contains("Strawberry Banana Frappe"));

        List<String> results = jp.get("results");
        Assert.assertEquals(results.size(),10);
    }

    @Test
    public void testRecipeApi2() {
        RestAssured.baseURI ="http://www.recipepuppy.com/api/";
        given()
                .when().get("?i=banana&p=4")
                .then()
                .assertThat()
                .body("results[2].title", containsString("Butter"));
    }


    // Selenium UI Tests //
    @Test
    public void clothesShopTests() {
        driver.navigate().to("http://automationpractice.com/index.php");
        String body = driver.getPageSource();
        System.out.println(body);
        WebElement shirts = driver.findElement(By.xpath("(//a[text()='T-shirts'])[2]"));
        WebDriverWait wait= new WebDriverWait(driver,15);
        wait.until(ExpectedConditions.elementToBeClickable(shirts));
        shirts.click();
        driver.findElement(By.id("selectProductSort")).click();
    }

    @Test
    public void amazonTests() throws InterruptedException {
        driver.navigate().to("https://www.amazon.com/");

        driver.findElement(By.id("searchDropdownBox")).click();

        Select allSelector = new Select(driver.findElement(By.id("searchDropdownBox")));
        allSelector.selectByValue("search-alias=electronics");

        driver.findElement(By.id("twotabsearchtextbox")).sendKeys("calculator");
        driver.findElement(By.xpath("//input[@value='Go']")).click();

        Select sortBy = new Select(driver.findElement(By.id("sort")));
        sortBy.selectByValue("review-rank");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("leftNavContainer")));
        driver.findElement(By.id("low-price")).sendKeys("300");
        driver.findElement(By.id("high-price")).sendKeys("350");
        driver.findElement(By.xpath("//span/input[@value='Go']")).click();

        List<WebElement> results = driver.findElements(By.xpath("//div[@id='resultsCol']//li[contains(@id,'result_')]"));
        Assert.assertEquals(results.size(),24);

        for (WebElement result : results) {
            System.out.println(result.getText());
            Assert.assertTrue(result.getText().toLowerCase().contains("calculator"));
        }
    }

    public static void main(String args[]) {

        // loop for 100 times
        int n = 100;

        for (int i=1; i<=n; i++) {

            // number divisible by 15(divisible by
            // both 3 & 5), print 'FizzBuzz' in
            // place of the number
            if (i%15==0)
                System.out.print("FizzBuzz"+" ");

            // number divisible by 5, print 'Buzz'
            // in place of the number
            else if (i%5==0)
                System.out.print("Buzz"+" ");

            // number divisible by 3, print 'Fizz'
            // in place of the number
            else if (i%3==0)
                System.out.print("Fizz"+" ");

            // print the numbers
            else
                System.out.print(i+" ");
        }
    }


}
