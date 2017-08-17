package demo.pratice.practiceproject.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import cn.bmob.v3.Bmob;
import demo.pratice.practiceproject.MainActivity;
import demo.pratice.practiceproject.consts.AppConst;
import demo.pratice.practiceproject.consts.AppDataKey;
import demo.pratice.practiceproject.data.CommonDAO;

/**
 * Created by yb on 2017/8/15.
 */
public class EntryActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(this, AppConst.BMOB_APP_ID);
        String isLogin = CommonDAO.getInstance().getString(AppDataKey.IS_LOGIN.name());
        if (Boolean.TRUE.toString().equals(isLogin)) {

        } else {

        }
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
