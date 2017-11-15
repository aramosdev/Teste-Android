package br.com.aramosdev.testeandroid.movielist;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import br.com.aramosdev.testeandroid.R;
import br.com.aramosdev.testeandroid.core.ViewWrapper;
import br.com.aramosdev.testeandroid.model.movie.Movie;
import br.com.aramosdev.testeandroid.util.ImageUtil;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alberto.Ramos on 11/11/17.
 */

public class MovieItemView extends RelativeLayout implements ViewWrapper.Binder<Movie>,
        MovieItemContract.View {

    @BindView(R.id.movie_image)
    protected ImageView mMovieImage;
    @BindView(R.id.movie_title)
    protected TextView mMovieTitle;
    @BindView(R.id.movie_release_date)
    protected TextView mMovieReleaseDate;
    @BindView(R.id.movie_vote)
    protected TextView mMovieVote;

    private MovieItemContract.Interaction mPresenter;

    public MovieItemView(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_movie_item, this); // your layout with <merge> as the root tag
        ButterKnife.bind(this);
        mPresenter = new MovieItemPresenter(this);
    }

    @Override
    public void bind(Movie data) {
        mMovieTitle.setVisibility(INVISIBLE);
        mMovieImage.setVisibility(INVISIBLE);
        mMovieVote.setVisibility(INVISIBLE);
        mMovieReleaseDate.setVisibility(INVISIBLE);
        mPresenter.handleMovie(data);
    }

    @Override
    public void showTitleMovie(String title) {
        mMovieTitle.setText(title);
        mMovieTitle.setVisibility(VISIBLE);
    }

    @Override
    public void showImageMovie(String posterPath) {
        ImageUtil.loadImageInto(posterPath, mMovieImage, getContext());
        mMovieImage.setVisibility(VISIBLE);
    }

    @Override
    public void showVoteMovie(String vote) {
        mMovieVote.setText(vote);
        mMovieVote.setVisibility(VISIBLE);
    }

    @Override
    public void showReleaseDateMovie(String releaseDate) {
        mMovieReleaseDate.setText(releaseDate);
        mMovieReleaseDate.setVisibility(VISIBLE);
    }
}
