package demo.pratice.practiceproject.service;


import java.util.concurrent.TimeUnit;

import demo.pratice.practiceproject.BuildConfig;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yb on 2017/8/15.
 */

public class ServiceManager {
    private static final String END_POINT = "https://dev.emuzi-tech.com/HippoStart_V3/";
    private static ServiceManager ourInstance = new ServiceManager();
    private Service service;

    public static ServiceManager getInstance() {
        return ourInstance;
    }

    private ServiceManager() {}

    public void init() {
        service = buildService();
    }

    private Service buildService() {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);

        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder()
                .cookieJar(CookiesManager.getInstance().getCookiesJar())
                .addInterceptor(loggingInterceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .retryOnConnectionFailure(false);

        return new Retrofit.Builder()
                .baseUrl(END_POINT)
                .client(clientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
                .create(Service.class);
    }

    public Service getService() {
        return service;
    }

//    private class HeaderInterceptor implements Interceptor {
//
//        @Override
//        public Response intercept(Chain chain) throws IOException {
//            Request request = chain.request().newBuilder()
//                    .addHeader("version", BuildConfig.VERSION_NAME)
//                    .build();
//            return chain.proceed(request);
//        }
//    }
 }
