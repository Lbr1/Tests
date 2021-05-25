package test;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import test.newwindow.NewWindow;
import test.steps.ElementActions;

import java.util.List;

import static java.lang.Thread.sleep;

public class Test {

    private static WebDriver driver;
    private static NewWindow newWindow;

    private static ElementActions elementActions;

    @BeforeAll
    public static void initialTestConditions(){
        newWindow = new NewWindow();
        driver = newWindow.createNewInstanceBrowser();
        elementActions = new ElementActions(driver);
    }

    @org.junit.jupiter.api.Test
    public void testSubmitWithoutAcceptingAllRules() throws InterruptedException {

        driver.navigate().to("https://careers.xpand-it.com/?utm_source=website_xpand-it&utm_medium=banner_destaque_botao&utm_campaign=careers_website_launch");

        sleep(1000);

        elementActions.clickCANDIDATE();
        elementActions.clickCookie();

        sleep(3000);

        elementActions.clickListOpportunities();

        sleep(3000);

        elementActions.sendKeys(elementActions.nameInput(), "Luisinho Filipe");
        elementActions.sendKeys(elementActions.emailInput(), "xpander@xpand-it.com");
        elementActions.sendKeys(elementActions.phoneInput(), "964565555");
        elementActions.countryInput();

        elementActions.selectOffer();
        elementActions.selectUniversityByValue(elementActions.selectUniversity(), "UM - Universidade do Minho \t");

        sleep(3000);
        elementActions.sendCV();
        sleep(3000);
        elementActions.submit();

        sleep(3000);
        String errorMessage = elementActions.error();

        Assert.assertEquals("This field is required.", errorMessage);
    }
}
