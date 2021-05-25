package test.steps;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import test.pageobjects.ElementIdentifier;

public class ElementActions {

    private ElementIdentifier elementIdentifier;
    private WebDriver driver;

    public ElementActions(WebDriver driverParameter)
    {
        //
        this.driver = driverParameter;
        // a class que identificaste os elementos
        this.elementIdentifier = new ElementIdentifier(this.driver);
    }

    public void clickCookie(){
        this.elementIdentifier.cookieClose().click();
    }

    public void clickListOpportunities(){
        this.elementIdentifier.WebElementListOF().get(0).click();
    }


    public void sendCV(){
        this.elementIdentifier.cvFile().sendKeys("C:\\Users\\LbrT2\\Desktop\\cv.pdf");
    }


    public void clickCANDIDATE(){
        this.elementIdentifier.anchorCandy().click();
    }


    public String error(){
        return this.elementIdentifier.getError().getAttribute("innerHTML");
    }

    public void submit(){
        WebElement aux = this.elementIdentifier.submit() ;

        if (aux.isDisplayed() && aux.isEnabled()) {
            aux.click();
        }
    }

    public WebElement nameInput(){
        return this.elementIdentifier.nameInput();
    }

    public WebElement emailInput(){
        return this.elementIdentifier.emailInsert();
    }

    public void countryInput(){
        this.elementIdentifier.countryConfirm().click();
    }

    public void selectOffer(){
        this.elementIdentifier.getOffer().selectByIndex(1);
    }

    public Select selectUniversity(){
        return this.elementIdentifier.getUniversity();
    }

    public void selectUniversityByValue(Select element, String words){
        element.selectByValue(words);
    }

    public WebElement phoneInput(){
        return this.elementIdentifier.phoneInsert();
    }

    public void sendKeys(WebElement element, String words){
        element.sendKeys(words);
    }


}
