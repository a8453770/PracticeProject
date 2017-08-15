package demo.pratice.practiceproject.service;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import demo.pratice.practiceproject.app.MainApplication;

/**
 * Created by yb on 2017/8/15.
 */

public class CookiesManager {
    private static CookiesManager ourInstance = new CookiesManager();
    private static ClearableCookieJar clearableCookieJar;
    private static SharedPrefsCookiePersistor sharedPrefsCookiePersistor;

    public static CookiesManager getInstance() {
        return ourInstance;
    }

    private CookiesManager() {
        if (sharedPrefsCookiePersistor == null) {
            sharedPrefsCookiePersistor = new SharedPrefsCookiePersistor(MainApplication.getInstance().getApplicationContext());
        }
        if (clearableCookieJar == null) {
            clearableCookieJar = new PersistentCookieJar(new SetCookieCache(), sharedPrefsCookiePersistor);
        }
    }

    public ClearableCookieJar getCookiesJar() {
        return clearableCookieJar;
    }

    public SharedPrefsCookiePersistor getSharedPrefsCookiePersistor() {
        return sharedPrefsCookiePersistor;
    }
}
