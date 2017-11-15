package br.com.aramosdev.testeandroid.search;

import org.junit.Test;
import org.mockito.Mock;

import java.lang.reflect.Type;
import java.util.List;

import br.com.aramosdev.testeandroid.core.BaseContract;
import br.com.aramosdev.testeandroid.core.BaseUnitTest;
import br.com.aramosdev.testeandroid.core.UnitTestUtil;
import br.com.aramosdev.testeandroid.model.movie.Movie;
import br.com.aramosdev.testeandroid.model.movie.MovieResponse;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by Alberto.Ramos on 12/11/17.
 */

public class SearchPresenterTest extends BaseUnitTest<MovieResponse> {

    @Mock
    private SearchContract.View mView;

    private SearchContract.Interaction mPresenter;
    private MovieResponse mResponse;
    private String mQuery;
    private int mPage;

    @Override
    public void setup() {
        super.setup();
        mQuery = "test";
        mPage = 1;
    }

    @Test
    public void testSearchMovies_queryNull() throws Exception {
        setupMockedResponse(null);
        mPresenter.searchMovies(null, mPage);
        verify(mView).showLoading();
        verify(mView).hideLoading();
        verify(mView).tryAgain();
        verify(mView, never()).responseSearch((List<Movie>) any());
    }

    @Test
    public void testSearchMovies_error() throws Exception {
        mIsResponseError = true;
        setupMockedResponse(null);
        mPresenter.searchMovies(mQuery, mPage);
        verify(mView).showLoading();
        verify(mView).hideLoading();
        verify(mView).tryAgain();
        verify(mView, never()).responseSearch((List<Movie>) any());
    }

    @Test
    public void testSearchMovies_responseNull() throws Exception {
        setupMockedResponse(null);
        mPresenter.searchMovies(mQuery, mPage);
        verify(mView).showLoading();
        verify(mView).hideLoading();
        verify(mView).tryAgain();
        verify(mView, never()).responseSearch((List<Movie>) any());
    }

    @Test
    public void testSearchMovies_responseEmpty() throws Exception {
        setupMockedResponse(new MovieResponse());
        mPresenter.searchMovies();
        verify(mView).showLoading();
        verify(mView).hideLoading();
        verify(mView).tryAgain();
        verify(mView, never()).responseSearch((List<Movie>) any());
    }

    @Test
    public void testSearchMovies_tryAgainSuccess() throws Exception {
        mIsResponseError = true;
        setupMockedResponse(mResponse);
        mPresenter.searchMovies(mQuery, mPage);
        mIsResponseError = false;
        setupMockedResponse(mResponse);
        mPresenter.searchMovies();
        verify(mView, times(2)).showLoading();
        verify(mView, times(2)).hideLoading();
        verify(mView).tryAgain();
        verify(mView).responseSearch(eq(mResponse.getMovies()));
    }

    @Test
    public void testSearchMovies_success() throws Exception {
        mPresenter.searchMovies(mQuery, mPage);
        verify(mView).showLoading();
        verify(mView).hideLoading();
        verify(mView).responseSearch(eq(mResponse.getMovies()));

        verify(mView, never()).tryAgain();
    }

    @Override
    protected BaseContract.BaseInteraction<MovieResponse> getPresenter() {
        if (mPresenter == null) mPresenter = new SearchPresenter(mApi, mView);
        return mPresenter;
    }

    @Override
    protected MovieResponse getFullResponse() {
        Type type = getPresenter().genericType();
        mResponse = UnitTestUtil.getInstance().readFile(type, "movie_response.json");
        return mResponse;
    }
}
