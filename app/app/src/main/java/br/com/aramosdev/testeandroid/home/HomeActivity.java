package br.com.aramosdev.testeandroid.home;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import br.com.aramosdev.testeandroid.R;
import br.com.aramosdev.testeandroid.core.BaseActivity;
import br.com.aramosdev.testeandroid.model.movie.Movie;
import br.com.aramosdev.testeandroid.moviedetail.MovieDetailActivity;
import br.com.aramosdev.testeandroid.movielist.MovieListAdapter;
import br.com.aramosdev.testeandroid.util.EndlessRecyclerScrollListener;
import butterknife.BindView;

/**
 * Created by Alberto.Ramos on 11/11/17.
 */

public class HomeActivity extends BaseActivity implements HomeContract.View {

    @BindView(R.id.refresh)
    public SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.movie_list)
    public RecyclerView mMovieList;

    private HomeContract.Interaction mPresenter;
    private MovieListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        showSearch();

        init();
    }

    protected void init() {
        mPresenter = new HomePresenter(mApi, this);
        mAdapter = new MovieListAdapter(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);

        mMovieList.setLayoutManager(layoutManager);
        mMovieList.addOnScrollListener(new EndlessRecyclerScrollListener(layoutManager) {
            @Override
            protected void onLoadMore(int currentPage) {
                mPresenter.getMovies(currentPage);
            }
        });
        mMovieList.setAdapter(mAdapter);

        mPresenter.getMovies(1);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadHome();
            }
        });
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
    public Context getContext() {
        return this;
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
                mPresenter.getMovies();
            }
        });
        builder.setNegativeButton(R.string.cancel, null);

        builder.show();
    }

    @Override
    public void fillMovieList(List<Movie> moviesVoteAverage) {
        mAdapter.setOnItemClick(new MovieListAdapter.OnItemClick() {
            @Override
            public void onItemClicked(Movie movie) {
                Intent intent = new Intent(HomeActivity.this,
                        MovieDetailActivity.class);
                intent.putExtra(MovieDetailActivity.ID_MOVIE_EXTRA, movie.getId());
                startActivity(intent);
                overridePendingTransition(R.anim.transition_in_up, R.anim.transition_none);
            }
        });
        mAdapter.addMovies(moviesVoteAverage);
    }

    private void loadHome() {
        mAdapter.clearItems();
        mPresenter.getMovies(1);
    }
}
