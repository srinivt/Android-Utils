package com.rosshambrick.android.utils;

import android.os.AsyncTask;

public abstract class BetterAsyncTask<TProgress, TResult> extends AsyncTask<Object, TProgress, AsyncTaskResult<TResult>> {
    private AsyncTaskListener mListener = null;

    public BetterAsyncTask(AsyncTaskListener<TResult> listener) {
        mListener = listener;
    }

    @Override
    protected AsyncTaskResult<TResult> doInBackground(Object... params) {
        TResult tResult = null;
        Throwable error = null;
        try {
            tResult = doBackgroundWork(params);
        } catch (Throwable e) {
            error = e;
        }

        if (error == null && tResult == null) {
            error = new RuntimeException("Result from background work was null");
        }
        return new AsyncTaskResult<TResult>(tResult, error);
    }

    protected abstract TResult doBackgroundWork(Object... params) throws Throwable;

    @Override
    protected void onPostExecute(AsyncTaskResult<TResult> taskResult) {
        if (mListener != null) {
            if (taskResult.getError() != null) {
                mListener.onError(taskResult.getError());
            } else {
                mListener.onSuccess(taskResult.getResult());
            }
        }
        mListener.onFinished();
    }

    public interface AsyncTaskListener<T> {
        void onSuccess(T result);

        void onError(Throwable error);

        void onFinished();
    }

}

class AsyncTaskResult<T> {
    private Throwable mError;
    private T mResult;

    public AsyncTaskResult(T result, Throwable error) {
        mResult = result;
        mError = error;
    }

    public Throwable getError() {
        return mError;
    }

    public void setError(Throwable error) {
        mError = error;
    }

    public T getResult() {
        return mResult;
    }

    public void setResult(T result) {
        mResult = result;
    }

}