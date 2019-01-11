package non_page_tests;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
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

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static com.jayway.restassured.RestAssured.given;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

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
        // Set the base URI to 'https://reqres.in'
        RestAssured.baseURI = "https://reqres.in/";

        // 1. "/api/users/23" returns a 404 status code
        given().when()
                .get("api/users/23")
                .then().assertThat()
                .statusCode(404);

        // 2. "/api/unknown/2" returns a response where the year is equal to 2001
        given().when()
                .get("/api/unknown/2")
                .then().assertThat()
                .body("data.year", equalTo(2001));

        // 3. "/api/users" returns a 200 status code and the 3rd user from "/api/users" has the last name, "Wong"
        given().when()
                .get("api/users")
                .then().assertThat()
                .statusCode(200)
                .assertThat()
                .body("data[2].last_name", containsString("Wong"));

        // BONUS: Using JSONPath, assert that the first name from "api/users/1" is "George"
        Response response = RestAssured.given()
                .when()
                .get("api/users/1")
                .then()
                .assertThat()
                .statusCode(200)
                .extract().response();

        String json = response.asString();
        JsonPath jp = new JsonPath(json);
        Assert.assertEquals("George", jp.get("data.first_name"));
    }

    @Test
    public void postRequest() {
        RestAssured.baseURI = "https://reqres.in";
        given().contentType(ContentType.JSON)
                .body("{\"email\":\"peter@klaven\",\"password\":\"cityslicka\"}")
                .when().post("/api/login")
                .then().assertThat()
                .statusCode(200)
                .body("token", equalTo("QpwL5tke4Pnpja7X"));
    }

    @Test
    public void putRequest() {
        RestAssured.baseURI = "https://reqres.in";
        given().contentType(ContentType.JSON)
                .body("{\"name\":\"morpheus\",\"job\":\"zion resident\"}")
                .when().put("/api/users/2")
                .then().assertThat()
                .statusCode(200)
                .body("updatedAt", containsString("2019-01-08"));
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

    // End of REST API Tests //


    // Selenium UI Tests //
    @Test
    public void amazonTests() throws Exception {
        // 1. Navigate to https://amazon.com
        driver.navigate().to("https://www.amazon.com/");

        // 2. Change the search type to "Electronics"
        driver.findElement(By.id("searchDropdownBox")).click();
        Select allSelector = new Select(driver.findElement(By.id("searchDropdownBox")));
        allSelector.selectByValue("search-alias=electronics");  // OR allSelector.selectByVisibleText("Electronics");

        // 3. Search for "calculators"
        driver.findElement(By.id("twotabsearchtextbox")).sendKeys("calculators");
        driver.findElement(By.xpath("//input[@value='Go']")).click();

        // 4. Sort the results by "Average Customer Review"
        Select sortBy = new Select(driver.findElement(By.id("sort")));
        sortBy.selectByValue("review-rank"); // OR allSelector.selectByVisibleText("Avg. Customer Review");

        // 5. Filter results to only show calculators in the range of $300 to $350
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("leftNavContainer"))); // OR Thread.sleep(1000);
        driver.findElement(By.id("low-price")).sendKeys("300");
        driver.findElement(By.id("high-price")).sendKeys("350");
        driver.findElement(By.xpath("//span/input[@value='Go']")).click();

        // 6. Save all the search results into a List of WebElements
        List<WebElement> results = driver.findElements(By.xpath("//div[@id='resultsCol']//li[contains(@id,'result_')]"));

        // 7. Use a for-each loop to assert that each result title contains the word "calculator"
        for (WebElement result : results) {
            Assert.assertTrue(result.getText().toLowerCase().contains("calculator"));
        }
    }

    // End of Selenium UI Tests //


    // Simple Java Coding Tests //
    public static void main(String[] args) {
        // Find duplicates in a String array

        String[] strArray = {"abc", "def", "mno", "xyz", "pqr", "xyz", "def"};
        for (int i = 0; i < strArray.length-1; i++) {

            for (int j = i+1; j < strArray.length; j++) {
                if( (strArray[i].equals(strArray[j])) && (i != j) ) {
                    System.out.println("1) Duplicate Element is : "+strArray[j]);
                }
            }
        }

        // OR... //
        String[] myArray = {"abc", "def", "mno", "xyz", "pqr", "xyz", "def"};
        Set<String> set = new HashSet<>();
        for (String arrayElement : myArray) {
            if(!set.add(arrayElement)) {
                System.out.println("2) Duplicate Element is : "+arrayElement);
            }
        }
    }


    // Find 2nd largest number in number array //
//    static int secondLargest(int[] input) {
//        int firstLargest, secondLargest;
//        //Checking first two elements of input array
//        if(input[0] > input[1]) {
//            //If first element is greater than second element
//            firstLargest = input[0];
//            secondLargest = input[1];
//        }
//        else {
//            //If second element is greater than first element
//            firstLargest = input[1];
//            secondLargest = input[0];
//        }
//        //Checking remaining elements of input array
//        for (int i = 2; i < input.length; i++) {
//            if(input[i] > firstLargest) {
//                //If element at 'i' is greater than 'firstLargest'
//                secondLargest = firstLargest;
//                firstLargest = input[i];
//            } else if (input[i] < firstLargest && input[i] > secondLargest) {
//                //If element at 'i' is smaller than 'firstLargest' and greater than 'secondLargest'
//                secondLargest = input[i];
//            }
//        }
//        return secondLargest;
//    }

//    public static void main(String[] args) {
//        int[] nums = {1, 2, 3, 4, 5, 6, 7, 8, 9};
//        System.out.println(secondLargest(nums));
//    }


    // Fibonacci 1 //
//    public static int fibonacci(int number) {
//        if(number == 1 || number == 2){
//            return 1;
//        }
//        return fibonacci(number-1) + fibonacci(number -2);
//    }
//
//    // Fibonacci 2 //
//    public static int fibonacci2(int number) {
//        if (number == 1 || number == 2) {
//            return 1;
//        }
//
//        int fibo1 = 1, fibo2 = 1, fibonacci = 1;
//
//        for(int i= 3; i<= number; i++){
//            fibonacci = fibo1 + fibo2;
//            fibo1 = fibo2;
//            fibo2 = fibonacci;
//        }
//
//        return fibonacci;
//    }

    // End of Simple Java Coding Tests //

}
