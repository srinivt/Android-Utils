package com.rosshambrick.android.utils.masterdetail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import com.bignerdranch.android.criminalintent.R;

public abstract class MasterDetailActivity<T> extends FragmentActivity
        implements MasterFragment.Listener<T>, DetailFragment.Listener<T> {

    private static final String TAG = MasterDetailActivity.class.getSimpleName();

    protected MasterFragment<T> mMasterFragment;
    private boolean mLargeScreen;
    private FragmentManager mFragmentManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_detail);

        mFragmentManager = getSupportFragmentManager();

        mMasterFragment = (MasterFragment<T>) mFragmentManager.findFragmentById(R.id.masterFragmentContainer);

        if (mMasterFragment == null) {
            mMasterFragment = getMasterFragment();
            mFragmentManager.beginTransaction()
                    .add(R.id.masterFragmentContainer, mMasterFragment)
                    .commit();
        }

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();

        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        mLargeScreen = dpWidth > 650;

        Log.d(TAG, "Screen width: " + dpWidth);

        if (!mLargeScreen) {
            findViewById(R.id.detailFragmentContainer).setVisibility(View.GONE);
        }
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        if (fragment instanceof MasterFragment) {
            ((MasterFragment<T>)fragment).setListener(this);
        } else if (fragment instanceof DetailFragment) {
            ((DetailFragment<T>)fragment).setListener(this);
        }
    }

    abstract protected MasterFragment<T> getMasterFragment();

    abstract protected DetailFragment<T> getDetailFragment(T item);

    @Override
    public void onItemUpdated(T item) {
        mMasterFragment.onItemUpdated(item);
    }

    @Override
    public void onItemSelected(T item) {
        if (mLargeScreen) {
            FragmentTransaction ft = mFragmentManager.beginTransaction();

            DetailFragment oldDetailFragment = (DetailFragment) mFragmentManager.findFragmentById(R.id.detailFragmentContainer);
            if (oldDetailFragment != null) {
                ft.remove(oldDetailFragment);
            }

            if (item != null) {
                DetailFragment newDetailFragment = getDetailFragment(item);
                ft.add(R.id.detailFragmentContainer, newDetailFragment);
            }

            ft.commit();

        } else if (item != null) {
            mFragmentManager.beginTransaction()
                    .replace(R.id.masterFragmentContainer, getDetailFragment(item))
                    .addToBackStack(null)
                    .commit();
        }
    }

}
