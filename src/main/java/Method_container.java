import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;

@Listeners(Listener.class)
public class Method_container {
    static Logger log = LogManager.getLogger(Method_container.class);
    static WebDriver driver;
    static int new_client_id;
    static String FirstName;
    static String LastName;
    static String PinCode;
    static String deposit;
    static String withdraw;
    static ExtentTest test;
    static ExtentReports extent;
    static int number=1;
    @BeforeTest
    public static ExtentHtmlReporter getHtmlReporter() throws Exception
    {
        ExtentHtmlReporter htmlReporter=new ExtentHtmlReporter("extent.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        test = extent.createTest("ExtentMaker", "description");

        return htmlReporter;
    }
    public static void read_data() throws Exception
    {
        String line = "";
        String splitBy = ",";
        ArrayList<String> lst = new ArrayList<String>();
        test.log(Status.INFO,"Accessing the excel file to get user data");
        BufferedReader br = new BufferedReader(new FileReader("s/bank_manager_login_add_cus.csv"));
        String[] data = new String[0];
        while ((line = br.readLine()) != null) {
            data = line.split(splitBy);
            FirstName = data[0];
            LastName = data[1];
            PinCode = data[2];
            deposit=data[3];
            withdraw=data[4];
        }
        log.info("Required user data is fetched from the file");
        test.pass("All the required user data are accessed from the file and stored in the local variable");

    }
    public static void initialSetup() throws Exception
    {
        //Loading CSV
        System.out.println("Q:A :- Open Website");
        test.log(Status.INFO,"Starting of test cases Q:A :- Open Website");
        log.info("Q:A :- Open Website");
        read_data();
        System.setProperty("webdriver.chrome.driver", "chromedriver2");
        test.pass("Web driver is initialized successfully");
        driver = new ChromeDriver();
        driver.get("https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login");
        Thread.sleep(1000);
        driver.manage().window().maximize();
        log.info("Website is opened and window is Maximized");
        test.pass("Web pages is opened and maximized");

    }
    public static void add_new_cus() throws Exception{
        test.info("Q:B :- Add New Customer:- "+FirstName+" "+LastName);
        System.out.println("Q:B :- Add New Customer:- "+FirstName+" "+LastName);
        log.info("Q:B :- Add New Customer:- "+FirstName+" "+LastName);
        //Clicked "bank manager login"
        Thread.sleep(2000);
        WebElement btn = driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[1]/div[2]/button"));
        btn.click();
        Thread.sleep(2000);
        //Clicked "add customer"
        driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[1]/button[1]")).click();
        Thread.sleep(2000);
        // Feeding values fetched from Excel
        WebElement txt = driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[2]/div/div/form/div[1]/input"));

        txt.sendKeys(FirstName);
        Thread.sleep(2000);
        driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[2]/div/div/form/div[2]/input")).sendKeys(LastName);


        driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[2]/div/div/form/div[3]/input")).sendKeys(PinCode);
        Thread.sleep(2000);
        //Submit
        driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[2]/div/div/form/button")).click();
        Thread.sleep(2000);
        //New account Created
        Alert alert = driver.switchTo().alert();
        String alertMessage = driver.switchTo().alert().getText();
        alert.accept();
        String[] al_t = alertMessage.split(" ");
        new_client_id = Integer.parseInt(al_t[al_t.length - 1].substring(1));
        test.pass("Successfully added new customer details with ID"+ new_client_id);
        log.info("Successfully added new customer details with ID"+ new_client_id);
        System.out.println("Successfully added new customer details with ID"+ new_client_id);
    }
    public static void open_acc_new_cus() throws Exception{
        //Click "Open Account"
        test.info("Q:C :- Open Account of"+FirstName);
        System.out.println("Q:C :- Open Account of"+FirstName);
        log.info("Q:C :- Open Account of"+FirstName);
        driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[1]/button[2]")).click();
        Thread.sleep(2000);
        // Select user just registered by ID
        WebElement drop = driver.findElement(By.xpath("//*[@id=\"userSelect\"]"));
        Select select = new Select(drop);
        Thread.sleep(2000);
        select.selectByIndex(new_client_id);

        WebElement drop1 = driver.findElement(By.xpath("//*[@id=\"currency\"]"));
        Select select1 = new Select(drop1);
        Thread.sleep(2000);
        select1.selectByValue("Rupee");
        Thread.sleep(2000);
        driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[2]/div/div/form/button")).click();
        driver.switchTo().alert().accept();
        Thread.sleep(2000);
        test.pass("Account opening successfull" +FirstName);
        log.info("Account opening successfull" +FirstName);
        System.out.println("Account opening successfull" +FirstName);

    }
    public  static void login() throws Exception{
        // Click "Home
        test.info("Q:D :- Logging in as" +FirstName);
        System.out.println("Q:D :- Logging in as" +FirstName);
        log.info("Q:D :- Logging in as" +FirstName);
        driver.findElement(By.xpath("/html/body/div/div/div[1]/button[1]")).click();
        Thread.sleep(2000);

        //clicking "customer login section"
        driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[1]/div[1]/button")).click();
        Thread.sleep(2000);

        //Searching for new user in the dropdown by ID
        WebElement drop3 = driver.findElement(By.xpath("//*[@id=\"userSelect\"]"));
        Select select2 = new Select(drop3);
        select2.selectByIndex(new_client_id);
        Thread.sleep(2000);

        driver.findElement(By.xpath("/html/body/div/div/div[2]/div/form/button")).click();
        Thread.sleep(2000);
        //Loggedin as new User
        test.pass("Logged in as" +FirstName);
        log.debug("Logged in as" +FirstName);
        System.out.println("Logged in as" +FirstName);

    }
    public  static void deposit_test() throws Exception{
        // Fetching Current Balance before deposit
        test.info("Q:E :- Depositting" +deposit);
        System.out.println("Q:E :- Depositting" +deposit);
        log.info("Q:E :- Depositting" +deposit);
        int cur_bal= Integer.parseInt(driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[2]/strong[2]")).getText());
        Thread.sleep(2000);
        System.out.println(cur_bal);
        // Click on "deposit"
        driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[3]/button[2]")).click();
        Thread.sleep(2000);
        // Pass amt fetched from excel
        driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[4]/div/form/div/input")).sendKeys(deposit);
        driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[4]/div/form/button")).click();
        Thread.sleep(2000);
        int bal_after_deposit= Integer.parseInt(driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[2]/strong[2]")).getText());
        // If deposit is Consistent then Successfully
        if (bal_after_deposit-cur_bal==Integer.parseInt(deposit)){
            test.pass("Concurrency maintained"+driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[4]/div/span")).getText());
            log.info("Concurrency maintained");
            System.out.println("Concurrency maintained"+driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[4]/div/span")).getText());}
        else{
            test.fail("Concurrency not-maintained");
            log.error("Concurrency not-maintained");
            System.out.println("Concurrency not-maintained");
        }
    }
    public  static void withdrawl_test() throws Exception{
        test.info("Q:F :- Withdrawing" + withdraw);
        System.out.println("Q:F :- Withdrawing" + withdraw);
        log.info("Q:F :- Withdrawing" + withdraw);
        int cur_bal= Integer.parseInt(driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[2]/strong[2]")).getText());
        Thread.sleep(2000);
        // Click withdraw
        driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[3]/button[3]")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[4]/div/form/div/input")).sendKeys(withdraw);
        driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[4]/div/form/button")).click();
        Thread.sleep(2000);
        int bal_after_withdraw= Integer.parseInt(driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[2]/strong[2]")).getText());
        // If withdrawal is Consistent then Successfully
        if (cur_bal-bal_after_withdraw==Integer.parseInt(withdraw)){
            test.pass("Concurrency maintained" +driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[4]/div/span")).getText());
            log.info("Concurrency maintained" +driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[4]/div/span")).getText());
            System.out.println("Concurrency maintained" +driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[4]/div/span")).getText());}
        else{
            test.fail("Concurrency not-maintained");
            log.error("Concurrency not-maintained");
        }
    }
    public  static void withdraw_exception() throws Exception{

        //Trying to withdraw more than available balance

        int cur_bal= Integer.parseInt(driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[2]/strong[2]")).getText());
        test.info("Q:G :- Withdrawing more than available" + cur_bal+10);
        System.out.println("Q:G :- Withdrawing more than available" + cur_bal+10);
        log.info("Q:G :- Withdrawing more than available" + cur_bal+10);
        Thread.sleep(2000);
        System.out.println(cur_bal);
        driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[3]/button[3]")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[4]/div/form/div/input")).sendKeys(Integer.toString(cur_bal+10));
        driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[4]/div/form/button")).click();
        Thread.sleep(2000);
        test.fail(driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[4]/div/span")).getText());
        log.error(driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[4]/div/span")).getText());
        test.fail("details", MediaEntityBuilder.createScreenCaptureFromPath("screenshot.jpg").build());
        System.out.println(driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div[4]/div/span")).getText());
        throw new Exception("***You can not withdraw amount more than the balance***");
    }
    public void getScreenShotPass() throws IOException
    {
        String filename="PassScreenshot"+""+number+".jpg";
        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File src = takesScreenshot.getScreenshotAs(OutputType.FILE);
        File des = new File(System.getProperty("user.dir")+"/PassedScreenshot/"+filename);
        FileUtils.copyFile(src,des);
        log.info("Screenshot action is performed for pass condition");
        number+=1;
    }
    public void getScreenShotFail() throws IOException
    {
        String filename="FailedScreenshot"+""+number+".jpg";
        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File src = takesScreenshot.getScreenshotAs(OutputType.FILE);
        File des = new File(System.getProperty("user.dir")+"/FailedScreenshot/"+filename);
        FileUtils.copyFile(src,des);
        log.info("Screenshot option is performed for fail condition");
        number+=1;
    }
    public static WebDriver closing()
    {
        test.info("Browser is closed");
        log.info("Browser is getting closed");
        driver.close();
        extent.flush();
        return null;
    }

}
