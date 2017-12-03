package com.lukuvinkkikirjasto;

import com.lukuvinkkikirjasto.Main;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        loader = SpringBootContextLoader.class,
        classes = Main.class
)
@SpringBootTest
public class Stepdefs {

    int port = 8090;


    static {
        ChromeDriverManager.getInstance().setup();
    }
    WebDriver driver = new ChromeDriver();
    String baseUrl = "http://localhost:" + port + "/add";


    @Given("^page is opened")
    public void open_page() throws Throwable {
        driver.get(baseUrl);
    }



    @When("^name \"([^\"]*)\" and tag \"([^\"]*)\" are entered$")
    public void login_enter_credentials(String name, String tag) throws Throwable {
        WebElement element = driver.findElement(By.name("bookName"));
        element.sendKeys(name);
        element = driver.findElement(By.name("tags"));
        element.sendKeys(tag);
        element = driver.findElement(By.id("submit-book"));

        element.submit();
    }


    @Then("^a new book with the name \"([^\"]*)\" is added")
    public void containsName(String name) throws Throwable {
        assertTrue(driver.getPageSource().contains(name));
    }
}
