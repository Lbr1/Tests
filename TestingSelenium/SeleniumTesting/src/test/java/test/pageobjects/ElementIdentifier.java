package test.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class ElementIdentifier {

    private WebDriver driver;

    //Craicao do objecto
    public ElementIdentifier(WebDriver driverParameter){
        driver = driverParameter;
    }

    // Locator
    public static final By BUTTON_CANDIDATE_CLICK = By.id("slider-10-slide-18-layer-14");

    public static final By BUTTON_LIST_OPPORTUNITIES = By.cssSelector("div[class='pgafu-medium-12 pgafu-columns filtr-item']");


    // FORM
    public static final By BUTTON_NAME_INSERT = By.id("dhvc_form_control_nome");

    public static final By BUTTON_EMAIL_INSERT = By.id("dhvc_form_control_email");

    public static final By BUTTON_PHONE_INSERT = By.id("dhvc_form_control_telefone");

    public static final By BUTTON_COOKIE_CLICK = By.id("cookie_action_close_header");

    public static final By BUTTON_CV_FILE_INSERT = By.id("dhvc_form_control_cvfile");

    public static final By BUTTON_PORTUGAL_YES = By.xpath("//label[contains(@for,'dhvc_form_control_sim')]/i");

    public static final By ERROR_MESSAGE = By.xpath("//div[@class='dhvc-form-checkbox']//span[@class='dhvc-form-error']");

    public static final By SUBMIT = By.xpath("//button[@class='button dhvc-form-submit']");





    public static final By BUTTON_UNIVERSITY_INSERT = By.id("dhvc_form_control_Universidade");

    public static final By BUTTON_OFFER_INSERT = By.id("dhvc_form_control_como");

    //create a webElement object
    public WebElement anchorCandy(){
        return driver.findElement(BUTTON_CANDIDATE_CLICK);
    }

    //create a List of webElements object
    public List<WebElement> WebElementListOF(){
        return driver.findElements(BUTTON_LIST_OPPORTUNITIES);
    }

    public WebElement nameInput(){
        return driver.findElement(BUTTON_NAME_INSERT);
    }

    public WebElement emailInsert(){
        return driver.findElement(BUTTON_EMAIL_INSERT);
    }

    public WebElement phoneInsert(){
        return driver.findElement(BUTTON_PHONE_INSERT);
    }

    public WebElement cookieClose(){
        return driver.findElement(BUTTON_COOKIE_CLICK);
    }

    public WebElement cvFile(){
        return driver.findElement(BUTTON_CV_FILE_INSERT);
    }

    public WebElement countryConfirm(){
        return driver.findElement(BUTTON_PORTUGAL_YES);
    }

    public Select getOffer(){
        return new Select(driver.findElement(BUTTON_OFFER_INSERT));
    }

    public Select getUniversity(){
        return new Select(driver.findElement(BUTTON_UNIVERSITY_INSERT));
    }

    public WebElement getError(){
        return driver.findElement(ERROR_MESSAGE);
    }

    public WebElement submit(){
        return  driver.findElement(SUBMIT);
    }






    
}
