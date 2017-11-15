package br.com.aramosdev.testeandroid.movielist;

import br.com.aramosdev.testeandroid.R;
import br.com.aramosdev.testeandroid.model.movie.Movie;
import br.com.aramosdev.testeandroid.util.DateUtils;
import br.com.aramosdev.testeandroid.util.TextUtils;

/**
 * Created by Alberto.Ramos on 11/11/17.
 */

public class MovieItemPresenter implements MovieItemContract.Interaction {
    private MovieItemContract.View mView;

    public MovieItemPresenter(MovieItemContract.View view) {
        mView = view;
    }

    @Override
    public void handleMovie(Movie data) {
        if (data == null) return;

        if (!TextUtils.isNullOrEmpty(data.getTitle())) {
            mView.showTitleMovie(data.getTitle());
        }

        if (!TextUtils.isNullOrEmpty(data.getPosterPath())) {
            mView.showImageMovie(data.getPosterPath());
        }

        if (!TextUtils.isNullOrEmpty(data.getReleaseDate())) {
            String date = DateUtils.getFormattedDate(data.getReleaseDate());
            String releaseDate = mView.getContext().getString(R.string.release_date_movie, date);
            mView.showReleaseDateMovie(releaseDate);
        }

        int voteAverage = (int) data.getVoteAverage();
        String vote = mView.getContext().getString(R.string.vote_movie, voteAverage);
        mView.showVoteMovie(vote);

    }
}
