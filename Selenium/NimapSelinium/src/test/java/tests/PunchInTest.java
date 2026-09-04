package tests;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PunchInTest {

    @Test
    public void punchIn() {

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
                By.xpath("//a[@href='/attendance']"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@aria-label='Add New Claims']"))).click();

        By dateFields = By.xpath(
                "//div[contains(@class,'MuiPickersSectionList-sectionsContainer')]");

        List<WebElement> dates = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(dateFields));

        dates.get(0).click();

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@role='gridcell' and @aria-current='date']"))).click();

        dates = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(dateFields));

        dates.get(1).click();

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@role='gridcell' and @aria-current='date']"))).click();

        By timeFields = By.xpath("//input[@placeholder='HH:MM']");

        wd.findElements(timeFields).get(0).click();

        setTime(wait, "10", "03", "AM");

        wd.findElements(timeFields).get(1).click();

        setTime(wait, "10", "59", "AM");

        WebElement reason = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.name("reason")));

        reason.clear();

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[.//span[normalize-space()='Save']]"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@id='mui-tab-0']"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//img[@aria-label='Accept']"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[@href='/']"))).click();

        wait.until(ExpectedConditions.urlContains("dashboard"));

        String punchedIn = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h6[normalize-space()='Punched In']/preceding-sibling::p"))).getText();

        System.out.println("Punched In: " + punchedIn);

        Assert.assertEquals(punchedIn, "1 / 1");

        wd.quit();
    }

    private void setTime(WebDriverWait wait, String hourValue,
                         String minuteValue, String periodValue) {

        By hourInput = By.xpath("//input[@placeholder='HH'][@maxlength='2']");
        By minuteInput = By.xpath("//input[@placeholder='MM'][@maxlength='2']");

        WebElement hour = wait.until(driver -> {
            for (WebElement element : driver.findElements(hourInput)) {
                if (element.isDisplayed()) {
                    return element;
                }
            }
            return null;
        });

        WebElement minute = wait.until(driver -> {
            for (WebElement element : driver.findElements(minuteInput)) {
                if (element.isDisplayed()) {
                    return element;
                }
            }
            return null;
        });

        hour.click();
        hour.sendKeys(Keys.CONTROL, "a");
        hour.sendKeys(hourValue);

        minute.click();
        minute.sendKeys(Keys.CONTROL, "a");
        minute.sendKeys(minuteValue);

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[normalize-space()='" + periodValue + "']"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[normalize-space()='Done']"))).click();
    }
}