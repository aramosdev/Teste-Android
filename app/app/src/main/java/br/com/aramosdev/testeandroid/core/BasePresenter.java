package br.com.aramosdev.testeandroid.core;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import br.com.aramosdev.testeandroid.model.api.ApiSubscriber;
import br.com.aramosdev.testeandroid.model.api.RestClient;
import io.reactivex.Flowable;


/**
 * Created by Alberto.Ramos on 11/11/17.
 */

public abstract class BasePresenter<T, V extends BaseContract.BaseView>
        implements BaseContract.BaseInteraction<T> {

    protected RestClient mApi;
    private ApiSubscriber mRequest;
    protected V mView;

    public BasePresenter(RestClient api, V view) {
        mApi = api;
        mView = view;
    }

    public void execute(Flowable<T> observable) {
        mRequest = mApi.buildRequest(observable, this);
        mRequest.execute();
    }

    @Override
    public Type genericType(){
        ParameterizedType parameterizedType = (ParameterizedType) getClass()
                .getGenericSuperclass();

        return parameterizedType.getActualTypeArguments()[0];
    }

}
