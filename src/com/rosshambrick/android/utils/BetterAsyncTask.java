package com.rosshambrick.android.utils;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.widget.Toast;

public abstract class BetterAsyncTask<TProgress, TResult> extends AsyncTask<Void, TProgress, Result<TResult>> {
    public static final String TAG = "BetterAsyncTask";
    private Fragment mFragment;
    protected AlertDialog mDialog;

    public BetterAsyncTask(Fragment fragment) {
        mFragment = fragment;
    }

    @Override
    protected void onPreExecute() {
        mDialog = new AlertDialog.Builder(mFragment.getActivity()).setMessage("Please wait...").show();
    }

    @Override
    protected Result<TResult> doInBackground(Void... params) {
        TResult result = null;
        Throwable error = null;
        try {
            result = doBackgroundWork();
        } catch (Throwable e) {
            error = e;
        }

        return new Result<TResult>(result, error);
    }

    protected abstract TResult doBackgroundWork() throws Throwable;

    @Override
    protected void onPostExecute(Result<TResult> taskResult) {
        if (taskResult.getError() != null) {
            onError(taskResult.getError());
        } else {
            onSuccess(taskResult.getResult());
        }
        onFinished();
    }

    void onSuccess(TResult result) {
        //do nothing by default
    }

    void onError(Throwable error) {
        Toast.makeText(mFragment.getActivity(), "Task error: " + error.getMessage(), Toast.LENGTH_LONG).show();
    }

    void onFinished() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

}


class Result<T> {
    private Throwable mError;
    private T mResult;

    public Result(T result, Throwable error) {
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
