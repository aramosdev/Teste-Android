package br.com.aramosdev.testeandroid.movielist;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;

import java.util.List;

import br.com.aramosdev.testeandroid.core.BaseActivity;
import br.com.aramosdev.testeandroid.core.BaseRecyclerAdapter;
import br.com.aramosdev.testeandroid.core.ViewWrapper;
import br.com.aramosdev.testeandroid.model.movie.Movie;

/**
 * Created by Alberto.Ramos on 11/11/17.
 */

public class MovieListAdapter extends BaseRecyclerAdapter<Movie, MovieItemView> {

    private final static int FADE_DURATION = 1000; // in milliseconds
    private OnItemClick mOnItemClick;

    public MovieListAdapter(BaseActivity mActivity) {
        super(mActivity);
    }

    @Override
    protected MovieItemView onCreateItemView(ViewGroup parent, int viewType) {
        return new MovieItemView(mActivity);
    }

    @Override
    public void onBindViewHolder(ViewWrapper<Movie, MovieItemView> holder, final int position) {
        super.onBindViewHolder(holder, position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClick != null) mOnItemClick.onItemClicked(mItems.get(position));
            }
        });

        setAnimation(holder.itemView);
    }

    @Override
    public void onViewDetachedFromWindow(ViewWrapper<Movie, MovieItemView> holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
    }

    public void addMovies(List<Movie> movies) {
        mItems.addAll(movies);
        notifyDataSetChanged();
    }

    private void setAnimation(View viewToAnimate) {
        viewToAnimate.clearAnimation();
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(FADE_DURATION);
        viewToAnimate.startAnimation(anim);
    }

    public void setOnItemClick(OnItemClick onItemClick) {
        this.mOnItemClick = onItemClick;
    }

    public interface OnItemClick {
        void onItemClicked(Movie movie);
    }
}
