package br.com.aramosdev.testeandroid.core;

import br.com.aramosdev.testeandroid.model.api.ApiSubscriber;
import io.reactivex.Flowable;

/**
 * Created by Alberto.Ramos on 12/11/17.
 */

public class MockedSubscriber extends ApiSubscriber {

    protected final Flowable mObservableMock;
    private BaseContract.BaseInteraction interactionMock;

    public MockedSubscriber(Flowable observable, BaseContract.BaseInteraction interaction) {
        super(observable, interaction);
        this.mObservableMock = observable;
        this.interactionMock = interaction;
    }

    @Override
    public void execute() {
        mObservableMock.subscribe(this);
    }

    @Override
    public void onComplete() {
        super.onComplete();

        cancel();
    }
}