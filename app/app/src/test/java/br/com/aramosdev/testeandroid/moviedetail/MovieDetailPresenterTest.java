package br.com.aramosdev.testeandroid.moviedetail;

import org.junit.Test;
import org.mockito.Mock;

import java.lang.reflect.Type;

import br.com.aramosdev.testeandroid.R;
import br.com.aramosdev.testeandroid.core.BaseUnitTest;
import br.com.aramosdev.testeandroid.core.UnitTestUtil;
import br.com.aramosdev.testeandroid.model.movie.MovieDetailResponse;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Alberto.Ramos on 12/11/17.
 */

public class MovieDetailPresenterTest extends BaseUnitTest<MovieDetailResponse> {

    @Mock
    private MovieDetailContract.View mView;

    private MovieDetailContract.Interaction mPresenter;
    private MovieDetailResponse mResponse;
    private String mReleaseDateResponse;
    private String mVote;

    @Override
    public void setup() {
        super.setup();
        when(mView.getContext()).thenReturn(mContext);
        mReleaseDateResponse = "data de lançamento - 12/06/2019";
        mVote = "nota 7";

        when(mContext.getString(eq(R.string.release_date_movie),anyString()))
                .thenReturn(mReleaseDateResponse);
        when(mContext.getString(eq(R.string.vote_movie),anyInt())).thenReturn(mVote);
    }

    @Test
    public void testGetMovieDetail_error() throws Exception {
        mIsResponseError = true;
        setupMockedResponse(mResponse);
        mPresenter.getMovieDetail(1);
        verify(mView).showLoading();
        verify(mView).hideLoading();
        verify(mView).tryAgain();
        verify(mView, never()).showImageMovie(anyString());
        verify(mView, never()).showTitleMovie(anyString());
        verify(mView, never()).showReleaseDateMovie(anyString());
        verify(mView, never()).showOverview(anyString());
        verify(mView, never()).showVoteMovie(anyString());
    }

    @Test
    public void testGetMovieDetail_responseNull() throws Exception {
        setupMockedResponse(null);
        mPresenter.getMovieDetail(1);
        verify(mView).showLoading();
        verify(mView).hideLoading();
        verify(mView).tryAgain();
        verify(mView, never()).showImageMovie(anyString());
        verify(mView, never()).showTitleMovie(anyString());
        verify(mView, never()).showReleaseDateMovie(anyString());
        verify(mView, never()).showOverview(anyString());
        verify(mView, never()).showVoteMovie(anyString());
    }

    @Test
    public void testGetMovieDetail_imagesNull() throws Exception {
        mResponse.setPosterPath(null);
        mPresenter.getMovieDetail(1);
        verify(mView).showLoading();
        verify(mView).hideLoading();
        verify(mView).showTitleMovie(eq(mResponse.getTitle()));
        verify(mView).showOverview(eq(mResponse.getOverview()));
        verify(mView).showReleaseDateMovie(eq(mReleaseDateResponse));
        verify(mView).showVoteMovie(eq(mVote));

        verify(mView, never()).showImageMovie(anyString());
    }

    @Test
    public void testHandleMovie_titleNull() throws Exception {
        mResponse.setTitle(null);
        mPresenter.getMovieDetail(1);
        verify(mView).showLoading();
        verify(mView).hideLoading();
        verify(mView).showImageMovie(eq(mResponse.getPosterPath()));
        verify(mView).showReleaseDateMovie(eq(mReleaseDateResponse));
        verify(mView).showVoteMovie(eq(mVote));
        verify(mView).showOverview(eq(mResponse.getOverview()));

        verify(mView, never()).showTitleMovie(anyString());
    }

    @Test
    public void testHandleMovie_releaseDateNull() throws Exception {
        mResponse.setReleaseDate(null);

        mPresenter.getMovieDetail(1);
        verify(mView).showLoading();
        verify(mView).hideLoading();
        verify(mView).showOverview(eq(mResponse.getOverview()));
        verify(mView).showTitleMovie(eq(mResponse.getTitle()));
        verify(mView).showImageMovie(eq(mResponse.getPosterPath()));
        verify(mView).showVoteMovie(eq(mVote));

        verify(mView, never()).showReleaseDateMovie(anyString());
    }

    @Test
    public void testHandleMovie_overviewNull() throws Exception {
        mResponse.setOverview(null);

        mPresenter.getMovieDetail(1);
        verify(mView).showLoading();
        verify(mView).hideLoading();
        verify(mView).showTitleMovie(eq(mResponse.getTitle()));
        verify(mView).showImageMovie(eq(mResponse.getPosterPath()));
        verify(mView).showVoteMovie(eq(mVote));
        verify(mView).showReleaseDateMovie(eq(mReleaseDateResponse));

        verify(mView, never()).showOverview(eq(mResponse.getOverview()));
    }

    @Override
    protected MovieDetailContract.Interaction getPresenter() {
        mPresenter = new MovieDetailPresenter(mApi, mView);
        return mPresenter;
    }

    @Override
    protected MovieDetailResponse getFullResponse() {
        Type type = getPresenter().genericType();
        mResponse = UnitTestUtil.getInstance().readFile(type, "movie_detail.json");
        return mResponse;
    }
}
