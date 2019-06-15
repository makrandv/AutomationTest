package flightCentreTests;

import HelperUtilities.HelperUtilities;
import modelPages.FlightListPage;
import modelPages.HomePage;
import modelPages.TripReviewPage;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertTrue;

//This class represents tests validating the acceptance criteria
public class SearchFlightTests extends BaseTests {

    /* This test validates below Acceptance Criteria
    1.	I should see the fares sorted ascending order (small to large)
    2.	I should be able to select the cheapest for onward and then backward
    3.	I should be able to get the total and it should be correct depending on the fares
     */
    @Test
    public void SearchAndSelectCheapestFlight() throws InterruptedException {

        //Launch Application
        driver.navigate().to(prop.getProperty("ApplicationURL"));
        HomePage homePage = new HomePage(driver);

        //Enter Search Criteria
        homePage.setTripType("return");
        homePage.setSourceDestination("Auckland","Wellington");
        homePage.setJourneyDates("25-Jun-2019","25-Aug-2019");
        //Click Search Button
        homePage.ClickSearchFlight();

        //Switching the driver to new opened tab
        HelperUtilities.SwitchDriverHandles(driver);

        //Flight List Page - Departing flight
        FlightListPage onWardflightLisPage = new  FlightListPage(driver);
        assertTrue(onWardflightLisPage.VerifyPageTitle("departing","Select Departing Flight"));

        //Sort Flight By - Cheapest Price
        // A.C. :1. I should see the fares sorted ascending order (small to large) - Verified
        assertTrue(onWardflightLisPage.VerifyFlightsSortedBy("Price (Cheapest)"));
        assertTrue(onWardflightLisPage.VerifyFlightListSortedByPrice());

        //Select Cheapest Flight from the list
        //A.C. : 2.	I should be able to select the cheapest for onward and then backward - Verified
        BigDecimal onWardFare = onWardflightLisPage.SelectFirstFlightReturnFinalPrice();

        //Flight List Page - Return flight
        FlightListPage returnflightLisPage = new  FlightListPage(driver);
        assertTrue(returnflightLisPage.VerifyPageTitle("returning","Select Return Flight"));

        //Sort Flight By - Cheapest Price
        // A.C. :1. I should see the fares sorted ascending order (small to large) - Verified
        assertTrue(returnflightLisPage.VerifyFlightsSortedBy("Price (Cheapest)"));
        assertTrue(returnflightLisPage.VerifyFlightListSortedByPrice());

        //Select Cheapest Flight from the list
        //A.C. : 2.	I should be able to select the cheapest for onward and then backward - Verified
        BigDecimal returnFare = returnflightLisPage.SelectFirstFlightReturnFinalPrice();

        //Trip Review Page
        TripReviewPage tripReviewPage = new  TripReviewPage(driver);
        assertTrue(tripReviewPage.VerifyPageTitle("Review trip"));

        //Compare Total Fare
        BigDecimal netFare = tripReviewPage.NetJourneyPrice();
        //A.C.: 3.I should be able to get the total and it should be correct depending on the fares - Verified
        assertTrue(netFare.compareTo(returnFare.add(onWardFare))==0);
    }

    /*
    This test validates below acceptance criteria :
    4.	The responses come back within 3.5 seconds (Page Load Time for each page under test is <= 3.5 Seconds)
     */
    @Test
    public void VerifyLoadTimeForWebPages() throws InterruptedException {

        driver.navigate().to(prop.getProperty("ApplicationURL"));
        // (HOME PAGE)
        // A.C. :The responses come back within 3.5 seconds
        //(Page Load Time for each page under test is <= 3.5 Seconds) - Verified
        assertTrue(HelperUtilities.WaitForPageToLoad(driver,3500));
        driver.manage().window().maximize();

        HomePage homePage = new HomePage(driver);
        //Enter Search Criteria
        homePage.setTripType("return");
        homePage.setSourceDestination("Auckland","Wellington");
        homePage.setJourneyDates("25-Jun-2019","25-Aug-2019");
        //Click Search Button
        homePage.ClickSearchFlight();

        //Switching the driver to new opened tab
        HelperUtilities.SwitchDriverHandles(driver);

        // (DEPARTING FLIGHT LIST PAGE)
        // A.C.:The responses come back within 3.5 seconds
        //(Page Load Time for each page under test is <= 3.5 Seconds) - Verified
        assertTrue(HelperUtilities.WaitForPageToLoad(driver,3500));

        FlightListPage onWardflightLisPage = new  FlightListPage(driver);
        assertTrue(onWardflightLisPage.VerifyPageTitle("departing","Select Departing Flight"));
        BigDecimal onWardFare = onWardflightLisPage.SelectFirstFlightReturnFinalPrice();

        // (RETURNING FLIGHT LIST PAGE)
        // A.C.: The responses come back within 3.5 seconds
        //(Page Load Time for each page under test is <= 3.5 Seconds) - Verified
        assertTrue(HelperUtilities.WaitForPageToLoad(driver,3500));

        FlightListPage returnflightLisPage = new  FlightListPage(driver);
        assertTrue(returnflightLisPage.VerifyPageTitle("returning","Select Return Flight"));
        BigDecimal returnFare = returnflightLisPage.SelectFirstFlightReturnFinalPrice();

        // (TRIP REVIEW PAGE)
        //  A.C.:The responses come back within 3.5 seconds
        // (Page Load Time for each page under test is <= 3.5 Seconds) - Verified
        assertTrue(HelperUtilities.WaitForPageToLoad(driver,3500));

        //Trip Review Page
        TripReviewPage tripReviewPage = new  TripReviewPage(driver);
        assertTrue(tripReviewPage.VerifyPageTitle("Review trip"));
    }

    /*
    This test verified below Acceptance Criteria :
    5.	I should get a proper message should the session expire
     */
    @Test
    public void VerifySessionExpiry() throws InterruptedException {

        //Launch Application
        driver.navigate().to(prop.getProperty("ApplicationURL"));

        HomePage homePage = new HomePage(driver);
        //Enter Search Criteria
        homePage.setTripType("return");
        homePage.setSourceDestination("Auckland","Wellington");
        homePage.setJourneyDates("25-Jul-2019","25-Aug-2019");
        //Click Search Button
        homePage.ClickSearchFlight();

        //Switching the driver to new opened tab
        HelperUtilities.SwitchDriverHandles(driver);

        //Flight List Page - Departing flight
        FlightListPage onWardflightLisPage = new  FlightListPage(driver);
        assertTrue(onWardflightLisPage.VerifyPageTitle("departing","Select Departing Flight"));

        //Making Session Idle for 500 miliseconds for Session to expire
        Thread.sleep(501);

        //(DEPARTING FLIGHT LIST PAGE)
        // A.C. : I should get a proper message should the session expire - Validated
        //Validate that Session Expire message appears after session expires
        assertTrue(onWardflightLisPage.VerifySessionExpiryMessage());

        //Click Refresh Results to Re-load the page
        onWardflightLisPage.ClickRefreshResultonSessionExpiry();

        onWardflightLisPage = new  FlightListPage(driver);
        assertTrue(onWardflightLisPage.VerifyPageTitle("departing","Select Departing Flight"));

        //Select Cheapest Flight from the list to proceed to next page
        onWardflightLisPage.SelectFirstFlightReturnFinalPrice();

        //Flight List Page - Return flight
        FlightListPage returnflightLisPage = new  FlightListPage(driver);
        assertTrue(returnflightLisPage.VerifyPageTitle("returning","Select Return Flight"));

        //Making Session Idle for 500 miliseconds
        Thread.sleep(501);

        //(RETURNING FLIGHT LIST PAGE)
        // A.C. : I should get a proper message should the session expire - Validated
        //Validate that Session Expire message appears after session expires
        assertTrue(returnflightLisPage.VerifySessionExpiryMessage());
        //Click Refresh Results to Re-load the page
        returnflightLisPage.ClickRefreshResultonSessionExpiry();

        returnflightLisPage = new  FlightListPage(driver);
        assertTrue(returnflightLisPage.VerifyPageTitle("returning","Select Return Flight"));

        //Select Cheapest Flight from the list
        returnflightLisPage.SelectFirstFlightReturnFinalPrice();

        //Trip Review Page
        TripReviewPage tripReviewPage = new  TripReviewPage(driver);
        assertTrue(tripReviewPage.VerifyPageTitle("Review trip"));

        //Making Session Idle for 500 miliseconds
        Thread.sleep(501);
        //(TRIP SUMMARY PAGE)
        // A.C. : I should get a proper message should the session expire - Validated
        //Validate that Session Expire message appears after session expires
        assertTrue(tripReviewPage.VerifySessionExpiryMessage());

        //Click Refresh Results to Re-load the page
        tripReviewPage.ClickRefreshResultonSessionExpiry();
    }
}
