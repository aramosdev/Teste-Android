package br.com.aramosdev.testeandroid.search;

import java.util.List;

import br.com.aramosdev.testeandroid.core.BaseContract;
import br.com.aramosdev.testeandroid.model.movie.Movie;
import br.com.aramosdev.testeandroid.model.movie.MovieResponse;

/**
 * Created by Alberto.Ramos on 12/11/17.
 */

public interface SearchContract {
    interface View extends BaseContract.BaseView {
        void responseSearch(List<Movie> movies);
        void tryAgain();
    }
    interface Interaction extends BaseContract.BaseInteraction<MovieResponse> {
        void searchMovies();
        void searchMovies(String query, int page);
    }
}
