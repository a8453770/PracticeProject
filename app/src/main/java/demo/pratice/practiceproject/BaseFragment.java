package demo.pratice.practiceproject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Toast;

import butterknife.ButterKnife;
import demo.pratice.practiceproject.service.Service;
import demo.pratice.practiceproject.service.ServiceManager;
import demo.pratice.practiceproject.utils.RxAndroidUtil;
import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;


/**
 * Created by jwmeng on 11/5/15.
 */
public abstract class BaseFragment extends Fragment {

    protected BaseActivity activity;
    protected View rootView;
    protected boolean needInitWidget;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //防止Tab切换重新创建Fragment
        if (rootView == null) {
            rootView = inflater.inflate(getLayout(), container, false);
            ButterKnife.bind(this, rootView);
            needInitWidget = true;
        } else {
            ViewParent parent = rootView.getParent();
            if (parent != null && rootView instanceof ViewGroup) {
                ((ViewGroup) rootView.getParent()).removeView(rootView);
            }
            needInitWidget = false;
        }
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = (BaseActivity) getActivity();
        if (needInitWidget) {
            initData();
            initWidget();
        }
    }

    protected void initData() {
    }

    protected void initWidget() {
    }

    protected abstract int getLayout();

    protected void showToast(String content) {
        Toast toast = Toast.makeText(getContext(), content, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    protected void showToast(int resId) {
        Toast toast = Toast.makeText(getContext(), resId, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    protected Service getService() {
        return ServiceManager.getInstance().getService();
    }

    protected <T> void subscribe(Observable<T> observable, Action1<T> onNext) {
        RxAndroidUtil.subscribe(activity, activity.toCheckoutLogoutObservable(observable), onNext, activity::handleError);
    }

    protected <T> void subscribe(Observable<T> observable, Action1<T> onNext, Action0 onComplete) {
        RxAndroidUtil.subscribe(activity, activity.toCheckoutLogoutObservable(observable), onNext, activity::handleError, onComplete);
    }

    protected <T> void subscribe(Observable<T> observable, Action1<T> onNext, Action1<Throwable> onError, Action0 onComplete) {
        RxAndroidUtil.subscribe(activity, activity.toCheckoutLogoutObservable(observable), onNext, onError, onComplete);
    }

}
