package br.com.aramosdev.testeandroid.movielist;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.aramosdev.testeandroid.R;
import br.com.aramosdev.testeandroid.core.UnitTestUtil;
import br.com.aramosdev.testeandroid.model.movie.Movie;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Alberto.Ramos on 12/11/17.
 */

public class MovieItemPresenterTest {

    @Mock
    private MovieItemContract.View mView;
    @Mock
    private Context mContext;

    private MovieItemContract.Interaction mPresenter;
    private Movie mResponse;
    private String mReleaseDateResponse;
    private String mVote;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        when(mView.getContext()).thenReturn(mContext);
        mPresenter = new MovieItemPresenter(mView);
        mResponse = UnitTestUtil.getInstance().readFile(Movie.class, "movie.json");

        mReleaseDateResponse = "data de lançamento - 12/06/2019";
        mVote = "nota 7";

        when(mContext.getString(eq(R.string.release_date_movie),anyString()))
                .thenReturn(mReleaseDateResponse);
        when(mContext.getString(eq(R.string.vote_movie),anyInt())).thenReturn(mVote);
    }

    @Test
    public void testHandleMovie_movieNull() throws Exception {
        mPresenter.handleMovie(null);
        verify(mView, never()).showImageMovie(anyString());
        verify(mView, never()).showTitleMovie(anyString());
        verify(mView, never()).showReleaseDateMovie(anyString());
        verify(mView, never()).showVoteMovie(anyString());
    }

    @Test
    public void testHandleMovie_imagesNull() throws Exception {
        mResponse.setPosterPath(null);
        mPresenter.handleMovie(mResponse);
        verify(mView, never()).showImageMovie(anyString());
        verify(mView).showTitleMovie(eq(mResponse.getTitle()));
        verify(mView).showReleaseDateMovie(eq(mReleaseDateResponse));
        verify(mView).showVoteMovie(eq(mVote));
    }

    @Test
    public void testHandleMovie_titleNull() throws Exception {
        mResponse.setTitle(null);
        mPresenter.handleMovie(mResponse);
        verify(mView, never()).showTitleMovie(anyString());
        verify(mView).showImageMovie(eq(mResponse.getPosterPath()));
        verify(mView).showReleaseDateMovie(eq(mReleaseDateResponse));
        verify(mView).showVoteMovie(eq(mVote));
    }

    @Test
    public void testHandleMovie_releaseDateNull() throws Exception {
        mResponse.setReleaseDate(null);
        mPresenter.handleMovie(mResponse);
        verify(mView, never()).showReleaseDateMovie(anyString());
        verify(mView).showTitleMovie(eq(mResponse.getTitle()));
        verify(mView).showImageMovie(eq(mResponse.getPosterPath()));
        verify(mView).showVoteMovie(eq(mVote));
    }
}
