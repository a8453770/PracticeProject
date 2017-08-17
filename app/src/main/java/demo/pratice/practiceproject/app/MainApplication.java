package demo.pratice.practiceproject.app;

import android.app.Application;


/**
 * Created by yb on 2017/8/15.
 */

public class MainApplication extends Application {

    private static MainApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

    }

    public static MainApplication getInstance() {
        return instance;
    }

}
