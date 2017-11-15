package br.com.aramosdev.testeandroid.util;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Alberto.Ramos on 11/11/17.
 */

public abstract class EndlessRecyclerScrollListener extends RecyclerView.OnScrollListener {

    private int mPreviousTotal = 1;
    private boolean mLoading = true;
    private int mCurrentPage = 1;

    private LinearLayoutManager mLinearLayoutManager;

    protected EndlessRecyclerScrollListener(LinearLayoutManager linearLayoutManager) {
        this.mLinearLayoutManager = linearLayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int visibleItemCount = recyclerView.getChildCount();
        int totalItemCount = mLinearLayoutManager.getItemCount();
        int firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();

        if (mPreviousTotal > totalItemCount) reset();

        if (mLoading) {
            if (totalItemCount > mPreviousTotal) {
                mLoading = false;
                mPreviousTotal = totalItemCount;
            }
        }
        int visibleThreshold = 5;
        if (!mLoading && (totalItemCount - visibleItemCount)
                <= (firstVisibleItem + visibleThreshold)) {
            mCurrentPage++;
            onLoadMore(mCurrentPage);
            mLoading = true;
        }
    }

    private void reset(){
        mPreviousTotal = 1;
        mCurrentPage = 1;
        mLoading = true;
    }

    protected abstract void onLoadMore(int currentPage);
}
