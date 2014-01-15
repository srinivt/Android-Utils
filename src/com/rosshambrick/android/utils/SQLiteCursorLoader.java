package com.rosshambrick.android.utils;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.AsyncTaskLoader;

public abstract class SQLiteCursorLoader<TCursor extends Cursor> extends AsyncTaskLoader<TCursor> {
	
	private TCursor mCursor;

	public SQLiteCursorLoader(Context context) {
		super(context);
	}

    @Override
    protected void onStartLoading() {
        if (mCursor != null) {
            deliverResult(mCursor);
        }

        if (takeContentChanged() || mCursor == null) {
            forceLoad();
        }
    }

	@Override
	public void deliverResult(TCursor cursor) {
		TCursor oldCursor = mCursor;
		mCursor = cursor;

		if (isStarted()) {
			super.deliverResult(cursor);
		}

		if (oldCursor != null && oldCursor != cursor && oldCursor.isClosed()) {
			oldCursor.close();
		}
	}

	@Override
	protected void onStopLoading() {
		cancelLoad();
	}

	@Override
	public void onCanceled(Cursor data) {
		if (data != null && !data.isClosed()) {
			data.close();
		}
	}

	@Override
	protected void onReset() {
		super.onReset();
		
		onStopLoading();
		
		if (mCursor != null && !mCursor.isClosed()) {
			mCursor.close();
		}
		mCursor = null;
	}
}
