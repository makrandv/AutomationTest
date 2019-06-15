Software Requirements : 

OS : Windows 7 or later
Development Framework : Java 8 with (JDK 1.8 or later)

Application as IDE : Intellig 2018.3.2 

Scripting Language : Java
Testing Framework : Junit 5.0

Browser : Chorme
Driver : Web driver corresponding to browser used to be downloaded based on the browser version and path of the driver exe to be added to the "PATH" Environment Variable.


Test Strategy : 

Acceptance criteria mentioned are tested using the Junit test cases as below :
Junit Test : SearchAndSelectCheapestFlight
Acceptance Criteria covered :
1.	I should see the fares sorted descending (small to large)
2.	I should be able to select the cheapest for onward and then backward
3.	I should be able to get the total and it should be correct depending on the fares

Junit Test : VerifyLoadTimeForWebPages
Acceptance Criteria covered :
4. The responses come back within 3.5 seconds

Junit Test :VerifySessionExpiry
Acceptance Criteria covered :
5.	I should get a proper message should the session expire

Project Folder Structure :
1. flightCentreTests: This packages contains tests to be executed for the Application
	a. BaseTests.class : Class containing common test methods (e.g. setup and close test) used by other specific test classes.
	b. SearchFlightTests.class : Class containing the test cases for testing the scenarios mentioned in the Acceptance Criteria.

2. HelperUtilities : This packages contains generic functions not specific to any pages. 

3. ModelPages : This packages contains classes corresponding to each web page in the Application
	a. BasePage : Page with common controls and methods used in all the pages in the application.
	b. HomePage : Landing page of the application with search dialog.
	c. FlightListPage : Page representing the flight list after search criteria is applied.
	d. TripReviewPage : Page representing trip review summary showing fare total.
	
- config.Properties : This file defines configuration added to the project (e.g. Browser to be used for test , ApplicationURl)


Test Execution : 
	a. Open the project "~\lpsFilghtCentreAutomation" in Intellig.
	b. Build the project from Intellig using (Ctrl+F9)
	c. Right Click "lpsFilghtCentreAutomation" node from the right pane and select "Run 'All Tests'" menu item
	d. Test should run successfully and show the result

Attached "TestExecution.jpg" snapshot shows execution done , VerifySessionExpiry method is currently failing as exact session expire time is not known hence test is assuming expire time as 500ms.


