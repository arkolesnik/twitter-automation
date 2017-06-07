package selenium_helper.driver_utils;

import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;
import java.util.Map;

public class Utils {

    public static void setOptionsForChrome(ChromeOptions options) {
        options.addArguments("chrome.switches", "--disable-extensions --disable-extensions-file-access-check " +
                "--disable-extensions-http-throttling --disable-infobars --enable-automation --start-maximized");
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("password_manager_enabled", false);
        options.setExperimentalOption("prefs", prefs);
    }
}
