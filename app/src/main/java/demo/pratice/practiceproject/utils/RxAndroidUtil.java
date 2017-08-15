package demo.pratice.practiceproject.utils;

import demo.pratice.practiceproject.BaseActivity;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


/**
 * Created by Jeff on 2016/3/8.
 */
public class RxAndroidUtil {

    private final static Observable.Transformer<Observable, Observable> schedulersTransformer =
            observable -> observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());

    /**
     * Add subscribe scheduler and observe scheduler for observable
     * @see <a href="http://blog.danlew.net/2015/03/02/dont-break-the-chain/">Don't break the chain</a>
     */
    @SuppressWarnings("unchecked")
    public static  <T> Observable.Transformer<T, T> applySchedulers() {
        return (Observable.Transformer<T, T>) schedulersTransformer;
    }

    public static <T> void subscribe(BaseActivity activity,
                                     Observable<T> observable,
                                     Action1<T> onNext) {
        observable.compose(activity.bindToLifecycle())
                .compose(applySchedulers())
                .subscribe(onNext);
    }

    public static <T> void subscribe(BaseActivity activity,
                                     Observable<T> observable,
                                     Action1<T> onNext,
                                     Action1<Throwable> onError) {
        observable.compose(activity.bindToLifecycle())
                .compose(applySchedulers())
                .subscribe(onNext, onError);
    }

    public static <T> void subscribe(BaseActivity activity,
                                     Observable<T> observable,
                                     Action1<T> onNext,
                                     Action1<Throwable> onError,
                                     Action0 onComplete) {
        observable.compose(activity.bindToLifecycle())
                .compose(applySchedulers())
                .subscribe(onNext, onError, onComplete);
    }
}
