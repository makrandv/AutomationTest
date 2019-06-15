package flightCentreTests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

//This class represents the common setup and exit setup used by actual test before and after execution.
public class BaseTests {

    protected WebDriver driver;
    protected Properties prop;

    @BeforeEach
    public void SetupTests() throws IOException {

        //Reading the execution Parameters from config file
        InputStream input = new FileInputStream("src/main/resources/config.properties");
        prop = new Properties();
        // load a properties file
        prop.load(input);

        String browserType = prop.getProperty("Browser");
        switch (browserType){
            case "Chrome" :
            {
                driver = new ChromeDriver();
                break;
            }
            case "FireFox" :
            {
                driver = new FirefoxDriver();
                break;
            }
            case "IE" :
            {
                System.setProperty("webdriver.ie.driver","<Path to IEDriver>");
                driver = new InternetExplorerDriver();
                break;
            }
            default:
            {
                driver = new ChromeDriver();
                break;
            }
        }
        driver.manage().timeouts().implicitlyWait(Long.parseLong(prop.getProperty("ImplicitWait")),TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @AfterEach
    public void CloseTests()
    {
        driver.quit();
    }
}
