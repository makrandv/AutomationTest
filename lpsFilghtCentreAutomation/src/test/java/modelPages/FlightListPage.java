package modelPages;

import HelperUtilities.HelperUtilities;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.math.BigDecimal;
import java.util.List;

//This class represents the list of flights matching the searched criteria
public class FlightListPage extends BasePage{

    public FlightListPage(WebDriver driver)
    {
        super(driver);
    }

    /*
    Verify the Header of the page displayed
     */
    public boolean VerifyPageTitle(String flightType,String pageTitle)
    {
        WebDriverWait wait = new WebDriverWait(driver,10000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//div[contains(@class,"+"'"+"test-"+flightType+"FlightsScreen"+"')]")));
        WebElement pageHeader = driver.findElement(By.cssSelector(".heading-0-78"));
        return pageHeader.getText().contains(pageTitle);
    }

    /*
    Verifying if the sort criteria is applied correctly (e.g. default is "Price (Cheapest)"
     */
    public boolean VerifyFlightsSortedBy(String sortOrderApplied)
    {
        WebElement sortOrderField = driver.findElement(By.cssSelector(".test-flightSortButton"))
                                    .findElement(By.cssSelector(".undefined"));
        return sortOrderField.getText().contains(sortOrderApplied);
    }

    /*
    Verifying if the Flights listed are sorted as per Price is Ascending order
     */
    public boolean VerifyFlightListSortedByPrice()
    {
        boolean sortVerified=true;

        List<WebElement> flightList = driver.findElements(By.cssSelector(".test-accordionItem"));
        for(int iFlightCount=0;iFlightCount<=flightList.size()-2;iFlightCount++)
        {
            String currentFlightPrice = flightList.get(iFlightCount).findElement(By.cssSelector(".test-price")).getText().replace("$","");
            String nextFlightPrice = flightList.get(iFlightCount+1).findElement(By.cssSelector(".test-price")).getText().replace("$","");

            if((HelperUtilities.ConvertToDecimal(currentFlightPrice)
                    .compareTo(HelperUtilities.ConvertToDecimal(nextFlightPrice))) > 1)
            {
                sortVerified = false;
                break;
            }
        }

        return sortVerified;
    }

    /*
    Select First Flight from the Flight list and return its Price
     */
    public BigDecimal SelectFirstFlightReturnFinalPrice() throws InterruptedException {

        List<WebElement> filghtList = driver.findElements(By.cssSelector(".test-accordionItem"));
        WebElement flightSelected = filghtList.get(0);
        Thread.sleep(500);
        flightSelected.click();

        //Get the Price for the flight
        String priceForSelectedFlight = flightSelected.findElements(By.cssSelector(".test-price")).get(0).getText();
        BigDecimal finalPrice = HelperUtilities.ConvertToDecimal(priceForSelectedFlight);

        //Click on the Select button for the selected flight
        WebElement selectFlightButton = flightSelected.findElement(By.cssSelector(".test-fareTypeFooterButton"));
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", selectFlightButton);

        return finalPrice;
    }
}
