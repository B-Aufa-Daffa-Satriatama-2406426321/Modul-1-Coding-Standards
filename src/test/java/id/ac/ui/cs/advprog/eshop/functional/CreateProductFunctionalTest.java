package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
class CreateProductFunctionalTest {

    @LocalServerPort
    private int serverPort;

    @Value("${app.baseUrl:http://localhost}")

    private String testBaseUrl;
    private String baseUrl;

    @BeforeEach
    void setUpTest() {
        baseUrl = String.format("%s:%d", testBaseUrl, serverPort);
    }

    void testH2_isCorrect(ChromeDriver driver){

        driver.get(baseUrl + "/product/list");
        String h2Text = driver.findElement(By.tagName("h2")).getText();
        assertEquals("Product' List", h2Text);
    }

    // Coba create product, lalu cek apakah muncul di list
    @Test
    void testCreateProductFlow_isCorrect(ChromeDriver driver) throws Exception {

        driver.get(baseUrl + "/product/list");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement createButton = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath("//a[@href='/product/create']")
            )
        );

        createButton.click();

        String pageTitle = driver.getTitle();
        assertEquals("Create New Product", pageTitle);

        driver.findElement(By.id("nameInput")).sendKeys("Sampo Cap Bambang");
        driver.findElement(By.id("quantityInput")).sendKeys("100");
        driver.findElement(By.id("submit")).click();

        driver.findElement(By.xpath("//td[text()='Sampo Cap Bambang']")).isDisplayed();
        driver.findElement(By.xpath("//td[text()='100']")).isDisplayed();

    }



}