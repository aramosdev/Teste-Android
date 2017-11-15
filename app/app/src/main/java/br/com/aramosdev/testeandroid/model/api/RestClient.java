package br.com.aramosdev.testeandroid.model.api;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;

import br.com.aramosdev.testeandroid.R;
import br.com.aramosdev.testeandroid.core.BaseContract;
import io.reactivex.Flowable;
import okhttp3.Cache;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Alberto.Ramos on 11/11/17.
 */

public class RestClient implements Interceptor {

    private Context mContext;
    private Services mServices;

    public RestClient(Context context) {
        mContext = context;
        init(mContext.getString(R.string.base_url));
    }

    private void init(String baseUrl) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        int cacheSize = 10 * 1024 * 1024;
        Cache cache = new Cache(mContext.getCacheDir(), cacheSize);

        httpClient
                .cache(cache)
                .interceptors()
                .add(this);

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        httpClient.interceptors().add(logging);

        Gson gson = new GsonBuilder().create();

        Retrofit.Builder builder = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(baseUrl)
                .client(httpClient.build());

        mServices = builder.build().create(Services.class);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        HttpUrl originalHttpUrl = original.url();

        HttpUrl url = originalHttpUrl.newBuilder()
                .addQueryParameter("api_key", mContext.getString(R.string.api_key))
                .build();

        Request.Builder requestBuilder = original.newBuilder()
                .url(url);

        Request request = requestBuilder.build();
        return chain.proceed(request);
    }

    public Services getServices() {
        return mServices;
    }

    public <T> ApiSubscriber buildRequest(Flowable<T> observable, BaseContract.BaseInteraction<T> interaction) {
        return new ApiSubscriber<>(observable, interaction);
    }
}
