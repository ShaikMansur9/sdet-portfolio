package com.mansur.listeners;

import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestExecutionListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println();
        System.out.println("TEST STARTED: " + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("TEST NAME: " + result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("TEST NAME: " + result.getName());

        Throwable failure = result.getThrowable();

        if (failure != null) {
            System.out.println("FAILURE REASON: " + failure.getMessage());
        }

    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("TEST NAME: " + result.getName());
    }
}