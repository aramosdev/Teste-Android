package br.com.aramosdev.testeandroid.moviedetail;

import br.com.aramosdev.testeandroid.R;
import br.com.aramosdev.testeandroid.core.BasePresenter;
import br.com.aramosdev.testeandroid.model.api.RestClient;
import br.com.aramosdev.testeandroid.model.movie.MovieDetailResponse;
import br.com.aramosdev.testeandroid.util.DateUtils;
import br.com.aramosdev.testeandroid.util.TextUtils;

/**
 * Created by Alberto.Ramos on 12/11/17.
 */

public class MovieDetailPresenter extends BasePresenter<MovieDetailResponse, MovieDetailContract.View>
        implements MovieDetailContract.Interaction {


    public MovieDetailPresenter(RestClient api, MovieDetailContract.View view) {
        super(api, view);
    }

    @Override
    public void getMovieDetail(long idMovie) {
        mView.showLoading();
        execute(mApi.getServices().getMovieDetail(idMovie));
    }

    @Override
    public void handleResponse(MovieDetailResponse response) {
        mView.hideLoading();
        if (response == null) {
            mView.tryAgain();
            return;
        }

        if (!TextUtils.isNullOrEmpty(response.getTitle())) {
            mView.showTitleMovie(response.getTitle());
        }

        if (!TextUtils.isNullOrEmpty(response.getOverview())) {
            mView.showOverview(response.getOverview());
        }

        if (!TextUtils.isNullOrEmpty(response.getPosterPath())) {
            mView.showImageMovie(response.getPosterPath());
        }

        if (!TextUtils.isNullOrEmpty(response.getReleaseDate())) {
            String date = DateUtils.getFormattedDate(response.getReleaseDate());
            String releaseDate = mView.getContext().getString(R.string.release_date_movie, date);
            mView.showReleaseDateMovie(releaseDate);
        }

        int voteAverage = (int) response.getVoteAverage();
        String vote = mView.getContext().getString(R.string.vote_movie, voteAverage);
        mView.showVoteMovie(vote);
    }

    @Override
    public void handleResponseError(MovieDetailResponse response) {
        mView.hideLoading();
        mView.tryAgain();
    }
}
