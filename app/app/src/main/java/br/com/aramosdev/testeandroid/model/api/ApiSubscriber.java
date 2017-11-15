package br.com.aramosdev.testeandroid.model.api;

import br.com.aramosdev.testeandroid.core.BaseContract;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DefaultSubscriber;

/**
 * Created by Alberto.Ramos on 11/11/17.
 */

public class ApiSubscriber<T> extends DefaultSubscriber<T> {

    private final Flowable<T> mObservable;
    private BaseContract.BaseInteraction<T> mInteraction;
    private boolean mOnNextCalled;

    public ApiSubscriber(Flowable<T> observable, BaseContract.BaseInteraction<T> interaction) {
        mObservable = observable;
        mInteraction = interaction;
    }

    public void execute() {
        mObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.newThread())
                .subscribe(this);
        mOnNextCalled = false;
    }

    @Override
    public void onNext(T value) {
        mOnNextCalled = true;
        mInteraction.handleResponse(value);
    }

    @Override
    public void onError(Throwable t) {
        mInteraction.handleResponseError(null);
    }

    @Override
    public void onComplete() {
        if (!mOnNextCalled) {
            mInteraction.handleResponse(null);
        }
    }
}
