package demo.pratice.practiceproject;

import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Toast;

import com.trello.rxlifecycle.components.support.RxFragmentActivity;

import butterknife.ButterKnife;
import demo.pratice.practiceproject.service.Service;
import demo.pratice.practiceproject.service.ServiceManager;
import demo.pratice.practiceproject.utils.CheckLogoutOperator;
import demo.pratice.practiceproject.utils.RxAndroidUtil;
import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;


/**
 * Created by jwmeng on 2015/5/7.
 */
public class BaseActivity extends RxFragmentActivity {

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setRootView();
        ButterKnife.bind(this);
        initData();
        initWidget();
    }

    /**
     * override this method to set content view, remember call supper.setRootView
     */
    protected void setRootView() {
    }

    protected void initData() {
    }

    protected void initWidget() {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                onBackPressed();
                break;
            }
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void showToast(String content) {
        Toast toast = Toast.makeText(this, content, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    protected void showToast(int resId) {
        Toast toast = Toast.makeText(this, resId, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public void showProgressDialog(String message) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    public void showProgressDialog(int msgResId) {
        showProgressDialog(getString(msgResId));
    }

    public void hideProgressDialog() {
        try {
            if (progressDialog != null) {
                progressDialog.dismiss();
                progressDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected Service getService() {
        return ServiceManager.getInstance().getService();
    }

    protected <T> void subscribe(Observable<T> observable, Action1<T> onNext) {
        RxAndroidUtil.subscribe(this, toCheckoutLogoutObservable(observable), onNext, this::handleError);
    }

    protected <T> void subscribe(Observable<T> observable, Action1<T> onNext, Action0 onComplete) {
        RxAndroidUtil.subscribe(this, toCheckoutLogoutObservable(observable), onNext, this::handleError, onComplete);
    }

    protected <T> void subscribe(Observable<T> observable, Action1<T> onNext, Action1<Throwable> onError) {
        RxAndroidUtil.subscribe(this, toCheckoutLogoutObservable(observable), onNext, onError);
    }

    protected void handleError(Throwable t) {
        hideProgressDialog();
    }

    private <T> Observable<T> toCheckoutLogoutObservable(Observable<T> observable) {
        return observable.lift(new CheckLogoutOperator<>());
    }

}
