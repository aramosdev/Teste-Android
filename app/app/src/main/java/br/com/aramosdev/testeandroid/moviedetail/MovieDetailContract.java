package br.com.aramosdev.testeandroid.moviedetail;

import br.com.aramosdev.testeandroid.core.BaseContract;
import br.com.aramosdev.testeandroid.model.movie.MovieDetailResponse;

/**
 * Created by Alberto.Ramos on 12/11/17.
 */

public interface MovieDetailContract {
    interface View extends BaseContract.BaseView {
        void tryAgain();
        void showTitleMovie(String title);
        void showOverview(String overview);
        void showImageMovie(String posterPath);
        void showReleaseDateMovie(String releaseDate);
        void showVoteMovie(String vote);
    }

    interface Interaction extends BaseContract.BaseInteraction<MovieDetailResponse> {
        void getMovieDetail(long idMovie);
    }
}
