package br.com.aramosdev.testeandroid.home;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;

import java.lang.reflect.Type;
import java.util.List;

import br.com.aramosdev.testeandroid.core.BaseUnitTest;
import br.com.aramosdev.testeandroid.core.UnitTestUtil;
import br.com.aramosdev.testeandroid.core.BaseContract;
import br.com.aramosdev.testeandroid.model.movie.Movie;
import br.com.aramosdev.testeandroid.model.movie.MovieResponse;
import br.com.aramosdev.testeandroid.util.ArrayUtils;

import static br.com.aramosdev.testeandroid.home.HomePresenter.VOTE_AVERAGE;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * Created by Alberto.Ramos on 12/11/17.
 */

public class HomePresenterTest extends BaseUnitTest<MovieResponse> {

    @Mock
    private HomeContract.View mView;

    private HomeContract.Interaction mPresenter;
    private MovieResponse mResponse;

    @Override
    public void setup() {
        super.setup();
    }

    @Test
    public void testGetMovies_error() throws Exception {
        mIsResponseError = true;
        setupMockedResponse(mResponse);
        mPresenter.getMovies(1);
        verify(mView).showLoading();
        verify(mView).hideLoading();
        verify(mView).tryAgain();
        verify(mView, never()).fillMovieList((List<Movie>) any());
    }

    @Test
    public void testGetMovies_responseNull() throws Exception {
        setupMockedResponse(null);
        mPresenter.getMovies(1);
        verify(mView).showLoading();
        verify(mView).hideLoading();
        verify(mView).tryAgain();
        verify(mView, never()).fillMovieList((List<Movie>) any());
    }

    @Test
    public void testGetMovies_responseEmpty() throws Exception {
        setupMockedResponse(new MovieResponse());
        mPresenter.getMovies();
        verify(mView).showLoading();
        verify(mView).hideLoading();
        verify(mView).tryAgain();
        verify(mView, never()).fillMovieList((List<Movie>) any());
    }

    @Test
    public void testGetMovies_success() throws Exception {
        List<Movie> movies = ArrayUtils.getMoviesVoteAverage(mResponse.getMovies(), VOTE_AVERAGE);
        mPresenter.getMovies();
        verify(mView).showLoading();
        verify(mView).hideLoading();
        verify(mView).fillMovieList(eq(movies));

        Assert.assertEquals(15, movies.size());

        verify(mView, never()).tryAgain();
    }

    @Override
    protected BaseContract.BaseInteraction<MovieResponse> getPresenter() {
        mPresenter = new HomePresenter(mApi, mView);
        return mPresenter;
    }

    @Override
    protected MovieResponse getFullResponse() {
        Type type = getPresenter().genericType();
        mResponse = UnitTestUtil.getInstance().readFile(type, "movie_response.json");
        return mResponse;
    }
}
