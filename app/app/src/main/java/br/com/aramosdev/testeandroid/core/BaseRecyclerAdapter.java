package br.com.aramosdev.testeandroid.core;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alberto.Ramos on 11/11/17.
 */

public abstract class BaseRecyclerAdapter<T, V extends View & ViewWrapper.Binder<T>>
        extends RecyclerView.Adapter<ViewWrapper<T, V>> {

    protected List<T> mItems = new ArrayList<>();
    protected BaseActivity mActivity;

    public BaseRecyclerAdapter(BaseActivity mActivity) {
        this.mActivity = mActivity;
    }

    public void setItems(List<T> items) {
        this.mItems = items;
        notifyDataSetChanged();
    }

    public List<T> getItems() {
        return mItems;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public final ViewWrapper<T, V> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewWrapper<>(onCreateItemView(parent, viewType));
    }

    @Override
    public void onBindViewHolder(ViewWrapper<T, V> viewHolder, int position) {
        viewHolder.getView().bind(mItems.get(position));
    }

    protected abstract V onCreateItemView(ViewGroup parent, int viewType);

    public void clearItems() {
        mItems = new ArrayList<>();
        notifyDataSetChanged();
    }
}
