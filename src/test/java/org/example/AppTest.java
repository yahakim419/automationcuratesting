package org.example;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.*;


import java.util.Calendar;
import java.util.TimeZone;


/**
 * Unit test for simple App.
 */
public class AppTest 
{
    WebDriver driver;

    String username="John Doe";
    String password="ThisIsNotAPassword";

    private String today;

   //Get Current Day
    private String currentDay(){
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

        int todayInt = calendar.get(Calendar.DAY_OF_MONTH);
        //System.out.println(todayInt);

        String todayStr = Integer.toString(todayInt);
        System.out.println(todayStr);

        return todayStr;
    }

    @BeforeMethod
    public void setupBrowser(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://katalon-demo-cura.herokuapp.com/");
    }

    @Test
    public void testHome() {

        Boolean actualTitle =  driver.getTitle().contains("CURA Healthcare Service");
        Assert.assertEquals(actualTitle,Boolean.TRUE);
    }

    @Test
    public void loginTest() throws InterruptedException {

        driver.findElement(By.id("btn-make-appointment")).click();
        Thread.sleep(5000);
        driver.findElement(By.id("txt-username")).sendKeys(username);
        driver.findElement(By.id("txt-password")).sendKeys(password);
        driver.findElement(By.id("btn-login")).click();
        Thread.sleep(5000);

        //Fill the appointment form
        Select select = new Select(driver.findElement(By.id("combo_facility")));

        select.selectByValue("Seoul CURA Healthcare Center");

        driver.findElement(By.className("checkbox-inline")).click();
        driver.findElement(By.xpath("//body//label[3]")).click();

        //Open date
        driver.findElement(By.id("txt_visit_date")).click();

        //now date
        today = currentDay();

        driver.findElement(By.xpath("//td[contains(text(),'"+today+"')]")).click();
        Thread.sleep(5000);

        driver.findElement(By.id("btn-book-appointment")).click();
        Thread.sleep(5000);

        Boolean actualResult = driver.findElement(By.tagName("h2")).getText().contains("Appointment Confirmation");
        Assert.assertEquals(actualResult, Boolean.TRUE);


        //Boolean actualValue = driver.findElement(By.xpath("//h2[contains(text(),'Make Appointment')]")).getText().contains("Make Appointment");



        //Assert.assertEquals(actualValue,Boolean.TRUE);

    }

//    @Test()
//    public void makeAppointment(){
////        driver.findElement(By.id("combo_facility"))
//
//
//    }

    @AfterMethod
    public void finishTest(){
        driver.quit();
    }
}
