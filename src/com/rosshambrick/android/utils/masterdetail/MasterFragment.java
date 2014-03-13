package com.rosshambrick.android.utils.masterdetail;

import android.support.v4.app.ListFragment;

public abstract class MasterFragment<T> extends ListFragment {
    public interface Listener<T> {
        void onItemSelected(T item);
    }

    protected Listener<T> mListener;

    public void setListener(Listener<T> listener) {
        mListener = listener;
    }

    abstract public void onItemUpdated(T item);

    protected void onItemSelected(T item) {
        mListener.onItemSelected(item);
    }

}
