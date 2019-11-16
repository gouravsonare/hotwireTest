package com.aera.hotwire.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class Reporter {

    protected static ExtentHtmlReporter extentHtmlReporter;

    protected static ExtentReports extentReports;

    protected static ExtentTest extentTest;

    public static void setupReports(String ReportName, String className)
    {
        extentHtmlReporter = new ExtentHtmlReporter("./Reports"+ReportName+".html");
        extentReports = new ExtentReports();
        extentReports.attachReporter(extentHtmlReporter);
        extentTest = extentReports.createTest(className);
        org.testng.Reporter.log(className, true);
        System.out.println(className);
    }

    public static void flushReports()
    {
        extentReports.flush();
    }

    public static void logResult(Status status, String result)
    {
        try {
            extentTest.log(status, result);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void logThrowable(Status Status, Throwable Throw)
    {
        extentTest.log(Status, Throw);
    }
}