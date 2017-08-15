package demo.pratice.practiceproject.utils;

import android.accounts.NetworkErrorException;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by yb on 2017/8/15.
 */
public class CheckLogoutOperator<T> implements Observable.Operator<T, T> {
    @Override
    public Subscriber<? super T> call(Subscriber<? super T> subscriber) {
        return new Subscriber<T>() {
            @Override
            public void onCompleted() {
                subscriber.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                if (subscriber != null) {
                    if (e instanceof UnknownHostException) {
                        subscriber.onError(new UnknownHostException());
                    } else if (e instanceof HttpException) {
                        subscriber.onError(e);
                    } else {
                        subscriber.onError(e);
                    }
                    subscriber.unsubscribe();
                }
            }

            @Override
            public void onNext(T t) {
                if (subscriber != null) {
                  if(t instanceof NetworkErrorException) {
                        subscriber.onError(new NetworkErrorException());
                        subscriber.unsubscribe();
                    } else if (t instanceof SocketTimeoutException) {
                        subscriber.onError(new SocketTimeoutException());
                        subscriber.unsubscribe();
                    } else {
                        subscriber.onNext(t);
                    }
                }
            }
        };
    }
}
