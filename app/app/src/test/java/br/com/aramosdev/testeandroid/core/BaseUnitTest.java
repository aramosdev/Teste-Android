package br.com.aramosdev.testeandroid.core;

import android.content.Context;

import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.reactivestreams.Subscriber;

import br.com.aramosdev.testeandroid.model.api.ApiSubscriber;
import br.com.aramosdev.testeandroid.model.api.RestClient;
import br.com.aramosdev.testeandroid.model.api.Services;
import io.reactivex.Flowable;

import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.doReturn;


/**
 * Created by Alberto.Ramos on 12/11/17.
 */

public abstract class BaseUnitTest<T> {

    @Mock
    protected ApiSubscriber mMockedSubscriber;

    @Mock
    public RestClient mApi;

    @Mock
    protected Services mServices;

    @Mock
    protected Context mContext;

    protected boolean mIsResponseError = false;

    protected abstract BaseContract.BaseInteraction<T> getPresenter();

    protected abstract T getFullResponse();

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);

        doReturn(mServices).when(mApi).getServices();
        setupMockedResponse(getFullResponse());
    }

    protected void setupMockedResponse(T response) {
        Exception error = new Exception();
        Flowable<T> mockedFlowable;
        if (response == null) {
            mockedFlowable = new Flowable<T>() {
                @Override
                protected void subscribeActual(Subscriber<? super T> s) {
                    if (mIsResponseError) s.onError(null);
                    else s.onNext(null);
                }
            };
        } else {
            mockedFlowable = Flowable.just(response);
        }
        if (mIsResponseError) mockedFlowable = Flowable.error(error);

        mMockedSubscriber = new MockedSubscriber(mockedFlowable, getPresenter());

        doReturn(mMockedSubscriber).when(mApi).buildRequest((Flowable) any(), (BaseContract.BaseInteraction) any());
    }

}