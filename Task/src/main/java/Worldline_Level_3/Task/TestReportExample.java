package Worldline_Level_3.Task;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

@SuppressWarnings("deprecation")
public class TestReportExample {

    public static void main(String[] args) {
        // Start ExtentReports
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("test-output/test-report.html");
        ExtentReports extent = new ExtentReports();
        extent.attachReporter(htmlReporter);

        // Create Test
        ExtentTest test = extent.createTest("Indigo flight Booking Automation", "A person wants to check for the one-way flights from Bengaluru to \r\n"
        		+ "Lucknow for 5 passengers (3 Adults and 2 children) on Indigo \r\n"
        		+ "portal for the current date. Automate the same using \r\n"
        		+ "(https://www.goindigo.in/?linkNav=home_header ) portal.");

        // Log Status
        test.log(Status.INFO, "Starting test case");
        test.log(Status.PASS, "Test Passed");

        // Flush report
        extent.flush();
    }
}
