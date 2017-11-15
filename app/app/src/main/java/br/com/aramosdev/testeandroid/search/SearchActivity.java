package br.com.aramosdev.testeandroid.search;

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
 * Created by Alberto.Ramos on 13/11/17.
 */

public class SearchActivity extends BaseActivity implements SearchContract.View {

    public static final String SEARCH_QUERY_EXTRA = "searchQuery";

    @BindView(R.id.refresh)
    public SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.movie_list)
    public RecyclerView mMovieList;

    private MovieListAdapter mAdapter;
    private String mQuery;
    private SearchContract.Interaction mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movie);
        showSearch();
        setReturnButton();

        init();
    }

    private void init() {
        mPresenter = new SearchPresenter(mApi, this);
        mAdapter = new MovieListAdapter(this);
        if(getIntent().getExtras() != null) {
            mQuery = getIntent().getExtras().getString(SEARCH_QUERY_EXTRA);
            mSearchText.setText(mQuery);
            mPresenter.searchMovies(mQuery, 1);
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);

        mMovieList.setLayoutManager(layoutManager);
        mMovieList.addOnScrollListener(new EndlessRecyclerScrollListener(layoutManager) {
            @Override
            protected void onLoadMore(int currentPage) {
                mPresenter.searchMovies(mQuery, currentPage);
            }
        });
        mMovieList.setAdapter(mAdapter);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAdapter.clearItems();
                mPresenter.searchMovies(mQuery, 1);
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
    public void responseSearch(List<Movie> movies) {
        mAdapter.setOnItemClick(new MovieListAdapter.OnItemClick() {
            @Override
            public void onItemClicked(Movie movie) {
                Intent intent = new Intent(SearchActivity.this,
                        MovieDetailActivity.class);
                intent.putExtra(MovieDetailActivity.ID_MOVIE_EXTRA, movie.getId());
                startActivity(intent);
                overridePendingTransition(R.anim.transition_in_up, R.anim.transition_none);
            }
        });
        mAdapter.addMovies(movies);
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
                mPresenter.searchMovies();
            }
        });
        builder.setNegativeButton(R.string.cancel, null);

        builder.show();
    }
}
