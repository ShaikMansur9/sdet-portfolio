package com.mansur.tests;

import com.mansur.listeners.TestExecutionListener;
import com.mansur.pages.InventoryPage;
import com.mansur.pages.LoginPage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(TestExecutionListener.class)
public class LoginTest {

    private WebDriver driver;
    private LoginPage loginPage;

    @BeforeMethod
    public void setUp() {

        driver = new ChromeDriver();

        driver.manage().window().maximize();

        driver.get("https://www.saucedemo.com/");

        loginPage = new LoginPage(driver);
    }

    @Test
    public void validUserCanLogIn() {

        InventoryPage inventoryPage =
                loginPage.loginAs("standard_user", "secret_sauce");

        Assert.assertEquals(
                inventoryPage.getTitle(),
                "Products",
                "Expected the Products page after a valid login"
        );
    }

    @Test
    public void invalidPasswordShowsError() {

        loginPage.attemptLogin(
                "standard_user",
                "wrong_password"
        );

        String actualErrorMessage = loginPage.getErrorMessage();

        Assert.assertTrue(
                actualErrorMessage.contains("do not match"),
                "Expected an error message for an incorrect password"
        );
    }

    @Test
    public void lockedOutUserCannotLogIn() {

        loginPage.attemptLogin(
                "locked_out_user",
                "secret_sauce"
        );

        String actualErrorMessage = loginPage.getErrorMessage();

        Assert.assertTrue(
                actualErrorMessage.contains("locked out"),
                "Expected an error message for a locked-out user"
        );
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {

        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}