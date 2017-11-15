package br.com.aramosdev.testeandroid.home;

import java.util.List;

import br.com.aramosdev.testeandroid.core.BaseContract;
import br.com.aramosdev.testeandroid.model.movie.Movie;
import br.com.aramosdev.testeandroid.model.movie.MovieResponse;

/**
 * Created by Alberto.Ramos on 11/11/17.
 */

public interface HomeContract {
    interface View extends BaseContract.BaseView {
        void tryAgain();
        void fillMovieList(List<Movie> moviesVoteAverage);
    }

    interface Interaction extends BaseContract.BaseInteraction<MovieResponse> {
        void getMovies(int page);
        void getMovies();
    }
}
