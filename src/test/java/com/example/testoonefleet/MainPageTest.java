package com.example.testoonefleet;

import dev.failsafe.internal.util.Assert;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.util.concurrent.TimeUnit;
import java.time.Duration;

public class MainPageTest {
    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        // Fix the issue https://github.com/SeleniumHQ/selenium/issues/11750
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://oonefleet.com/");

    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    @DisplayName("[N] Авторизация неверный пароль")
    public void authorization_wrongPassword() {
        WebElement signInButton = driver.findElement(By.cssSelector("#__next > div:nth-child(1) > div > div > div:nth-child(2) > button:nth-child(1)"));
        signInButton.click();

        WebElement emailField = driver.findElement(By.xpath("//*[@name=\"email\"]"));
        emailField.sendKeys("ilia.poplavskii@oone.world");

        WebElement passwordField = driver.findElement(By.xpath("//*[@name=\"password\"]"));
        passwordField.sendKeys("qwerty12345");
        passwordField.submit();

        WebElement validation = driver.findElement(By.xpath("//*[@id=\"__next\"]/div[1]/div[1]/div/main/form/div[2]/div/div[1]/div/label"));
        assertEquals("Incorrect email or password", validation.getAttribute("class"), "Password wrong");
    }

    @Test
    @DisplayName("Авторизация пользователя")
    @Timeout(5)

    public void authorization() {

        WebElement signInButton = driver.findElement(By.cssSelector("#__next > div:nth-child(1) > div > div > div:nth-child(2) > button:nth-child(1)"));
        signInButton.click();

        WebElement emailField = driver.findElement(By.xpath("//*[@name=\"email\"]"));
        emailField.sendKeys("ilia.poplavskii@oone.world");

        WebElement passwordField = driver.findElement(By.xpath("//*[@name=\"password\"]"));
        passwordField.sendKeys("Ilia2309!");
        passwordField.submit();

        //Тут должен быть код для Sleep в 5 секунд
        String url = driver.getCurrentUrl();

        assertEquals("https://oonefleet.com/dashboard", url, "Dashboard открылся");
    }

}
