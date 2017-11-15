package br.com.aramosdev.testeandroid.home;

import java.util.List;

import br.com.aramosdev.testeandroid.core.BasePresenter;
import br.com.aramosdev.testeandroid.model.api.RestClient;
import br.com.aramosdev.testeandroid.model.movie.Movie;
import br.com.aramosdev.testeandroid.model.movie.MovieResponse;
import br.com.aramosdev.testeandroid.util.ArrayUtils;
import br.com.aramosdev.testeandroid.util.TextUtils;

/**
 * Created by Alberto.Ramos on 11/11/17.
 */

public class HomePresenter extends BasePresenter<MovieResponse, HomeContract.View>
        implements HomeContract.Interaction {

    public static final int VOTE_AVERAGE = 5;

    private int mCurrentPage = 1;

    public HomePresenter(RestClient api, HomeContract.View view) {
        super(api, view);
    }

    @Override
    public void getMovies(int page) {
        mView.showLoading();
        mCurrentPage = page;
        execute(mApi.getServices().getMovies(mCurrentPage));
    }

    @Override
    public void getMovies() {
        mView.showLoading();
        execute(mApi.getServices().getMovies(mCurrentPage));
    }

    @Override
    public void handleResponse(MovieResponse response) {
        mView.hideLoading();
        if (response == null || TextUtils.isEmptyOrNull(response.getMovies())) {
            mView.tryAgain();
            return;
        }

        List<Movie> movies = ArrayUtils.getMoviesVoteAverage(response.getMovies(), VOTE_AVERAGE);
        mView.fillMovieList(movies);
    }

    @Override
    public void handleResponseError(MovieResponse response) {
        mView.hideLoading();
        mView.tryAgain();
    }

}
