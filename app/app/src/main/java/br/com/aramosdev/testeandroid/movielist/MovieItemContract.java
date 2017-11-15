package br.com.aramosdev.testeandroid.movielist;

import android.content.Context;

import br.com.aramosdev.testeandroid.model.movie.Movie;

/**
 * Created by Alberto.Ramos on 11/11/17.
 */

public interface MovieItemContract {
    interface View {
        void showTitleMovie(String title);
        void showImageMovie(String posterPath);
        void showVoteMovie(String vote);
        void showReleaseDateMovie(String releaseDate);
        Context getContext();
    }
    interface Interaction {
        void handleMovie(Movie data);
    }
}
