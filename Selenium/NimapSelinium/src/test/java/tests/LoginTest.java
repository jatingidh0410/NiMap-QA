package tests;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginTest {

    @DataProvider(name = "data")
    public Object[][] data() {
        return new Object[][] {

            {"jatingidh04@gmail.com", "[Anand]", false},
            {"jatingidh04@gmail.com", "Anand@283", true}
        };
    }

    @Test(dataProvider = "data")
    public void login(String username, String password, boolean expected) throws InterruptedException {

        WebDriver wd = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(wd, Duration.ofSeconds(15));

        wd.get("https://test.fieldforceconnect.com/auth/login");
        wd.manage().window().maximize();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.name("username"))).sendKeys(username);

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.name("password"))).sendKeys(password);

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@type='submit']"))).click();

        Thread.sleep(3000);

        boolean actual = wd.getCurrentUrl().contains("dashboard");

        System.out.println("Login: " + actual);

        Assert.assertEquals(actual, expected);

      //  wd.quit();
    }
}