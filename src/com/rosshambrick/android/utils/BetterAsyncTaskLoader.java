package com.rosshambrick.android.utils;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

public abstract class BetterAsyncTaskLoader<T> extends AsyncTaskLoader<T> {
    private T mData;

    public BetterAsyncTaskLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

}