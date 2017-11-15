package br.com.aramosdev.testeandroid.search;

import br.com.aramosdev.testeandroid.core.BasePresenter;
import br.com.aramosdev.testeandroid.model.api.RestClient;
import br.com.aramosdev.testeandroid.model.movie.MovieResponse;
import br.com.aramosdev.testeandroid.util.TextUtils;

/**
 * Created by Alberto.Ramos on 12/11/17.
 */

public class SearchPresenter extends BasePresenter<MovieResponse, SearchContract.View>
        implements SearchContract.Interaction {

    private String mCurrentQuery;
    private int mCurrentPage;

    public SearchPresenter(RestClient api, SearchContract.View view) {
        super(api, view);
    }

    @Override
    public void searchMovies() {
        mView.showLoading();
        if (!TextUtils.isNullOrEmpty(mCurrentQuery)) {
            execute(mApi.getServices().searchMovies(mCurrentQuery, mCurrentPage));
        } else execute(mApi.getServices().getMovies(mCurrentPage));
    }

    @Override
    public void searchMovies(String query, int page) {
        mView.showLoading();
        mCurrentPage = page;
        mCurrentQuery = query;
        if (!TextUtils.isNullOrEmpty(mCurrentQuery)) {
            execute(mApi.getServices().searchMovies(mCurrentQuery, mCurrentPage));
        } else execute(mApi.getServices().getMovies(mCurrentPage));
    }

    @Override
    public void handleResponse(MovieResponse response) {
        mView.hideLoading();
        if (response == null || TextUtils.isEmptyOrNull(response.getMovies())) {
            mView.tryAgain();
            return;
        }
        mView.responseSearch(response.getMovies());
    }

    @Override
    public void handleResponseError(MovieResponse response) {
        mView.hideLoading();
        mView.tryAgain();
    }
}
