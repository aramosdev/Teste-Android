package br.com.aramosdev.testeandroid.moviedetail;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.aramosdev.testeandroid.R;
import br.com.aramosdev.testeandroid.core.BaseActivity;
import br.com.aramosdev.testeandroid.util.ImageUtil;
import butterknife.BindView;

/**
 * Created by Alberto.Ramos on 12/11/17.
 */

public class MovieDetailActivity extends BaseActivity implements MovieDetailContract.View {

    public static final String ID_MOVIE_EXTRA = "idMovieExtra";

    @BindView(R.id.refresh)
    protected SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.image)
    protected ImageView mMovieImage;
    @BindView(R.id.title)
    protected TextView mMovieTitle;
    @BindView(R.id.overview)
    protected TextView mOverview;
    @BindView(R.id.release_date)
    protected TextView mMovieReleaseDate;
    @BindView(R.id.vote)
    protected TextView mMovieVote;

    private MovieDetailContract.Interaction mPresenter;
    private long mIdMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        setActionBarTitle(getString(R.string.detail_title));
        setReturnButton();

        init();
    }

    private void init() {
        mPresenter = new MovieDetailPresenter(mApi, this);
        if(getIntent().getExtras() != null) {
            mIdMovie = getIntent().getExtras().getLong(ID_MOVIE_EXTRA);
            mPresenter.getMovieDetail(mIdMovie);
            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    mPresenter.getMovieDetail(mIdMovie);
                }
            });
        }
    }

    @Override
    public void showLoading() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void tryAgain() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.DialogTheme);
        builder.setTitle(R.string.ops_title);
        builder.setCancelable(false);
        builder.setMessage(R.string.error_message);

        builder.setPositiveButton(R.string.try_again, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mPresenter.getMovieDetail(mIdMovie);
            }
        });
        builder.setNegativeButton(R.string.cancel, null);

        builder.show();
    }

    @Override
    public void showTitleMovie(String title) {
        mMovieTitle.setText(title);
        mMovieTitle.setVisibility(View.VISIBLE);
        setActionBarTitle(title);
    }

    @Override
    public void showImageMovie(String posterPath) {
        ImageUtil.loadImageInto(posterPath, mMovieImage, getContext());
        mMovieImage.setVisibility(View.VISIBLE);
    }

    @Override
    public void showVoteMovie(String vote) {
        mMovieVote.setText(vote);
        mMovieVote.setVisibility(View.VISIBLE);
    }

    @Override
    public void showOverview(String overview) {
        mOverview.setText(overview);
        mOverview.setVisibility(View.VISIBLE);
        findViewById(R.id.overview_title).setVisibility(View.VISIBLE);
    }

    @Override
    public void showReleaseDateMovie(String releaseDate) {
        mMovieReleaseDate.setText(releaseDate);
        mMovieReleaseDate.setVisibility(View.VISIBLE);
    }

    @Override
    public Context getContext() {
        return this;
    }
}
