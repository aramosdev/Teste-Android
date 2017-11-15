package br.com.aramosdev.testeandroid.model.api;

import br.com.aramosdev.testeandroid.model.movie.MovieDetailResponse;
import br.com.aramosdev.testeandroid.model.movie.MovieResponse;
import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Alberto.Ramos on 11/11/17.
 */

public interface Services {

    @GET("movie/upcoming")
    Flowable<MovieResponse> getMovies(@Query("page") int page);

    @GET("movie/{id}")
    Flowable<MovieDetailResponse> getMovieDetail(@Path("id") long idMovie);

    @GET("search/movie")
    Flowable<MovieResponse> searchMovies(@Query("query") String query, @Query("page") int page);
}
