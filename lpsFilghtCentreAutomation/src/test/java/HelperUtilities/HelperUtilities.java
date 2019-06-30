package HelperUtilities;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

//This are common function or methods used by the product/pages during execution
public class HelperUtilities {

    //Calculate month difference between given dates
    public static long CalculateDateDiff(String startDate, String endDate) {
        /**-------------------------Date----------------------------*/
        LocalDate sDate = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("dd-MMM-yyyy")).withDayOfMonth(1);
        LocalDate eDate = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("dd-MMM-yyyy")).withDayOfMonth(1);
        long dateDiff = ChronoUnit.MONTHS.between(sDate, eDate);
        return dateDiff;
    }

    //This method verifies if the page loading for a given page is less or equal to specified timings
    public static boolean WaitForPageToLoad(WebDriver driver,int timeOutInmiliSeconds) throws InterruptedException {
        boolean loadFlag = true;
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String jsCommand = "return document.readyState";

        // Validate readyState before doing any waits
        if (js.executeScript(jsCommand).toString().equals("complete")) {
            return loadFlag;
        }
           System.out.println("Page Still loading....");

           Thread.sleep(timeOutInmiliSeconds);

            if (js.executeScript(jsCommand).toString().equals("complete")) {
                loadFlag = true;
            } else {
                loadFlag = false;
            }
        return loadFlag;
    }

    //This function switches the control of webdriver to the newly opened browser window (tab)
    public static void SwitchDriverHandles(WebDriver driver)
    {
        String currentWindowHandle = driver.getWindowHandle();

        //Get the list of all window handles
        ArrayList<String> windowHandles = new ArrayList<String>(driver.getWindowHandles());

        for (String window:windowHandles){
            //if it contains the current window we want to eliminate that from switchTo();
            if (window != currentWindowHandle){
                //Now switchTo new Tab.
                driver.switchTo().window(window);
            }
        }
    }

    //This method converts given string into BigDecimal
    public static BigDecimal ConvertToDecimal(String numberString)
    {
        BigDecimal convertedDecimal = new BigDecimal(numberString.replace("$","").replaceAll("\\n",""));
        return convertedDecimal;
    }
}
