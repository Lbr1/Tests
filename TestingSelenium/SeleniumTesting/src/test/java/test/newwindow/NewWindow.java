package test.newwindow;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.HashMap;
import java.util.logging.Level;

public class NewWindow {

    // webdriver a instancia do browser
    public WebDriver createNewInstanceBrowser(){
        // versao do browser -> download chrome driver
        // https://chromedriver.storage.googleapis.com/index.html?path=90.0.4430.24/

        //Criar uma nova janela do chrome
        // para que pasta vao os downloads
        // browser para testes de automação

        // chromePath caminho chromedriver
        // pasta do projeto -> independete do projeto
        // get canonic path file.getCanonicalPath()

        String chromePath = "C:\\Users\\LbrT2\\Desktop\\BDD\\SeleniumTesting\\drivers\\chromedriver.exe";

        System.setProperty("webdriver.chrome.driver", chromePath);
        HashMap<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("safebrowsing.enabled", "true");
        chromePrefs.put("download.prompt_for_download", "false");
        ChromeOptions options = new ChromeOptions();

        options.addArguments("--disable-extensions");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-default-apps");
        options.addArguments("--enable-automation");
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-gpu");
        options.addArguments("--safebrowsing-disable-download-protection");
        options.setExperimentalOption("useAutomationExtension", false);
        options.setExperimentalOption("prefs", chromePrefs);
        DesiredCapabilities caps = DesiredCapabilities.chrome();
        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
        options.setCapability("goog:loggingPrefs", logPrefs);
        logPrefs.enable(LogType.BROWSER, Level.ALL);

        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        return driver;
    }
}
