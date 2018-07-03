package pl.bladekp.dbbenchmark;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class E2ETest {

    @Test
    public void testFlow() {
        ChromeDriver driver = new ChromeDriver();
        driver.get("http://localhost:8080/benchmark");
        WebElement sqlText = driver.findElement(By.id("sql"));
        sqlText.sendKeys("select * from town");
        WebElement mqlText = driver.findElement(By.id("mql"));
        mqlText.sendKeys("db.town.find({})");
        WebElement count = driver.findElement(By.id("count"));
        count.sendKeys("1");
        WebElement submit = driver.findElement(By.id("submit"));
        submit.click();
        Assert.assertEquals(driver.findElements(By.id("report-table")).size(), 1);
        Assert.assertEquals(driver.findElements(By.id("H2")).size(), 1);
        Assert.assertEquals(driver.findElements(By.id("MONGO")).size(), 1);
        Assert.assertEquals(driver.findElements(By.id("SQLSERVER")).size(), 0);
    }
}
