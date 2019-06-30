package modelPages;

import HelperUtilities.HelperUtilities;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver)
    {
        super(driver);
    }

    /*
    Get all Search Flight Dialog on Home Page
     */
    private WebElement getSearchDialog()
    {
        return driver.findElement(By.cssSelector(".pane-react-search"));
    }

    /*
    Set the Trip Type in the Search Flight Dialog
     */
    public void setTripType(String tripType)
    {
        WebElement tripTypeRadioGroup = this.getSearchDialog().findElement(By.className("trip-type__group"));
        tripTypeRadioGroup.findElement(By.xpath(".//input[@value="+"'"+tripType+"'"+"]")).click();
    }

    /*
    Set Source and Destination of Travel
     */
    public void setSourceDestination(String source, String destination)
    {
        WebElement searchDialog = this.getSearchDialog();
        searchDialog.findElement(By.name("expoint")).clear();
        searchDialog.findElement(By.name("expoint")).sendKeys(source);
        searchDialog.findElements(By.className("destination-autocomplete__item")).get(0).click();
        searchDialog.findElement(By.name("destination")).clear();
        searchDialog.findElement(By.name("destination")).sendKeys(destination);
        searchDialog.findElements(By.className("destination-autocomplete__item")).get(0).click();
    }

    /*
    Get displayed date in the Return date field on the page
     */
    public String getDisplayedReturnDate() throws ParseException {
        String returnDate = driver.findElement(By.name("arriveDate")).getAttribute("value");
        SimpleDateFormat formatter1 = new SimpleDateFormat("MMM dd, yyyy");
        Date date = formatter1.parse(returnDate);
        SimpleDateFormat formatter2 = new SimpleDateFormat("dd-MMM-yyyy");
        String returnFormattedDate = formatter2.format(date);
        return returnFormattedDate;
    }

    /*
    Set the Journey Dates
     */
    public void setJourneyDates(String onWardDate,String returnDate) throws ParseException, InterruptedException {
        WebElement searchDialog = this.getSearchDialog();
        String displayedReturnDate = getDisplayedReturnDate();
        WebDriverWait wait = new WebDriverWait(driver,5000);
        wait.until(ExpectedConditions.elementToBeClickable(searchDialog.findElement(By.name("departDate"))));
        searchDialog.findElement(By.name("departDate")).click();

        //Get Present Date
        DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
        LocalDate localDate = LocalDate.now();
        String currentDate = FORMATTER.format(localDate);


        //Set the date from the DatePicker Control
        setJourneryDate("depart",currentDate,onWardDate,displayedReturnDate);
        setJourneryDate("return",onWardDate,returnDate,displayedReturnDate);
    }

    /*
        Click on Search Flight Button
     */
    public void ClickSearchFlight(){

        WebDriverWait wait = new WebDriverWait(driver,5000);
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.cssSelector(".undefined__button"))));

        WebElement searchFlightButton = driver.findElement(By.cssSelector(".undefined__button"));
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", searchFlightButton);

        searchFlightButton.click();

    }

    /*
    Method to set the Date from the Date Picker control based on the Journey (Depart or Return)
     */
    private void setJourneryDate(String journeyType,String currentDate ,String enteredDate,String returnDateDisplayed) throws InterruptedException {

        int backWardMonthDiff=0,monthDiff=0,forwardMonthDiff=0;

        WebElement dateArrow;

        //Check if the calendar displayed shows current month or next month
        if(journeyType.equalsIgnoreCase("return") && (int)HelperUtilities.CalculateDateDiff(enteredDate,returnDateDisplayed) >0)
        {
            backWardMonthDiff = (int)HelperUtilities.CalculateDateDiff(enteredDate,returnDateDisplayed);
        }
        else
        {
            forwardMonthDiff = (int)HelperUtilities.CalculateDateDiff(currentDate,enteredDate);
        }

        WebElement datePickerDialog = driver.findElement(By.xpath(".//div[contains(@class,"+"'trip-dates__"+journeyType+"-date-dialog')]"));

        //Move backwards if the return date is in current month however the calendar is displayed for next m
        if(backWardMonthDiff>0)
        {
            dateArrow = datePickerDialog.findElements(By.tagName("button")).get(0);
            monthDiff = backWardMonthDiff;
        }
        else
        {
            dateArrow = datePickerDialog.findElements(By.tagName("button")).get(1);
            monthDiff = forwardMonthDiff;
        }

        //Traverse through the month till the excepted month calendar appears
        for(int iMon=0;iMon < monthDiff;iMon++)
        {
            dateArrow.click();
        }

        //Select Date from the month
        String travelDay = enteredDate.split("-")[0];
        WebElement onWardDay = datePickerDialog.findElement(By.xpath(".//button/span[contains(text(),"+travelDay+")]"));
        WebDriverWait wait = new WebDriverWait(driver,5000);
        wait.until(ExpectedConditions.elementToBeClickable(onWardDay));
        Thread.sleep(500);
        onWardDay.click();
    }
}
