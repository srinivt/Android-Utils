package com.rosshambrick.android.utils;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

public abstract class BetterAsyncTaskLoader<T> extends AsyncTaskLoader<T> {
    private T mData;

    public BetterAsyncTaskLoader(Context context) {
        super(context);
    }

    @Override
    public void deliverResult(T data) {
        if (isReset()) {
            return;
        }

        mData = data;

        super.deliverResult(data);
    }

    @Override
    protected void onStartLoading() {
        if (mData != null) {
            deliverResult(mData);
        }

        if (takeContentChanged() || mData == null) {
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        mData = null;
    }

}