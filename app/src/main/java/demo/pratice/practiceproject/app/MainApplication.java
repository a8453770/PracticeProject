package demo.pratice.practiceproject.app;

import android.app.Application;

import java.util.Locale;

import demo.pratice.practiceproject.service.ServiceManager;


/**
 * Created by yb on 2017/8/15.
 */

public class MainApplication extends Application {

    private static MainApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        ServiceManager.getInstance().init();
    }

    public static MainApplication getInstance() {
        return instance;
    }

    public String getLanguage() {
        String language = Locale.getDefault().getLanguage();
        if (!"zh".equals(language)) {
            language = "en";
        }
        return language;
    }
}
