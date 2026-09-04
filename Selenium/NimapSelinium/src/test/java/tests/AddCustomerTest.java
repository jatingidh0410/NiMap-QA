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

public class AddCustomerTest {

    @DataProvider(name = "customerData")
    public Object[][] customerData() {
        return new Object[][] {
            {"Test Customer 01", "12345", "9876543210", "testcustomer01@gmail.com"}
        };
    }

    @Test(dataProvider = "customerData")
    public void addCustomer(String customerName, String refNo,
                            String mobile, String email) throws InterruptedException {

        WebDriver wd = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(wd, Duration.ofSeconds(15));

        wd.manage().window().maximize();

        wd.get("https://test.fieldforceconnect.com/auth/login");

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.name("username"))).sendKeys("jatingidh04@gmail.com");

        wd.findElement(By.name("password")).sendKeys("Anand@283");

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@type='submit']"))).click();

        wait.until(ExpectedConditions.urlContains("dashboard"));

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//span[normalize-space()='My Customers']"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//span[normalize-space()='My Customer']"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[normalize-space()='Manage']"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//span[normalize-space()='New Customer']"))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.name("LeadName"))).sendKeys(customerName);

        wd.findElement(By.name("RefNo")).sendKeys(refNo);

        wd.findElement(By.name("PersonName")).sendKeys("Test Person");

        wd.findElement(By.name("ContactNo")).sendKeys(mobile);

        wd.findElement(By.name("Email")).sendKeys(email);

        wd.findElement(By.name("PersonLocation")).sendKeys("Navi Mumbai");

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[.//span[normalize-space()='Save']]"))).click();

        Thread.sleep(2000);

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@placeholder='Search...']"))).sendKeys(customerName);

        Thread.sleep(2000);

        boolean actual = wd.getPageSource().contains(customerName);

        System.out.println("Customer Added: " + actual);

        Assert.assertTrue(actual);

        wd.quit();
    }
}