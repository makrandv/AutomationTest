package modelPages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.math.BigDecimal;
import java.util.List;

//This class represents the Trip summary page
public class TripReviewPage extends BasePage {

    public TripReviewPage(WebDriver driver)
    {
        super(driver);
    }

    /*
    Verify the Header of the page displayed
    */
    public boolean VerifyPageTitle(String pageTitle)
    {
        WebDriverWait wait = new WebDriverWait(driver,10000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".test-reviewScreen")));
        WebElement pageHeader = driver.findElement(By.cssSelector(".test-reviewScreen")).findElement(By.tagName("h1"));
        return pageHeader.getText().contains(pageTitle);
    }

    /*
    Get the Net Journey price (Depart & Return) excluding the taxes applied
     */
    public BigDecimal NetJourneyPrice()
    {
        //Need to wait till the price summary controls appear and clickable
        WebDriverWait wait = new WebDriverWait(driver,10000);
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.className("test-priceSummarySection"))));

        //Click on Price summary and get the bifracation of the total price.
        WebElement ele = driver.findElement(By.className("test-priceSummarySection"));
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", ele);

        //Get Total fare
        BigDecimal totalTaxFees=new BigDecimal(0.00);
        WebElement totalPriceElement = driver.findElement(By.cssSelector(".test-totalPrice"));
        BigDecimal totalPrice = new BigDecimal(totalPriceElement.findElement(By.className("test-price")).getText().replace("$",""));

        //Extract fees applied on Fare (e.g. Taxes)
        List<WebElement> taxFees = driver.findElements(By.className("test-priceSummaryFeeItemPrice"));
        for(WebElement taxFee : taxFees)
        {
            totalTaxFees = totalTaxFees.add(new BigDecimal(taxFee.findElement(By.className("test-price")).getText().replace("$","")));
        }

        //Return Net fare excluding the taxes
        return totalPrice.subtract(totalTaxFees);
    }
}
