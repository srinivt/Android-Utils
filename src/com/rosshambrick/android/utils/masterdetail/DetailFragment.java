package com.rosshambrick.android.utils.masterdetail;

import android.support.v4.app.Fragment;

public abstract class DetailFragment<T> extends Fragment {
    public interface Listener<T> {
        void onItemUpdated(T crime);
    }

    private DetailFragment.Listener<T> mListener;

    public void setListener(Listener<T> listener) {
        mListener = listener;
    }

    protected void onItemUpdated(T item) {
        mListener.onItemUpdated(item);
    }

}
