package com.mansur.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    private final WebDriver driver;

    private final By usernameField = By.id("user-name");
    private final By passwordField = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By errorMessage = By.cssSelector("[data-test='error']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public InventoryPage loginAs(String username, String password) {
        attemptLogin(username, password);

        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.waitUntilLoaded();

        return inventoryPage;
    }

    public void attemptLogin(String username, String password) {

        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(usernameField)
        ).sendKeys(username);

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(passwordField)
        ).sendKeys(password);

        wait.until(
                ExpectedConditions.elementToBeClickable(loginButton)
        ).click();
    }

    public String getErrorMessage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).getText();
    }



}