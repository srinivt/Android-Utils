package com.rosshambrick.android.utils;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

public class L {
    //DEBUG
    public static void d(Object object, String message, Throwable e) {
        d(object.getClass().getName(), message, e);
    }

    public static void d(Object object, String message) {
        d(object.getClass().getName(), message);
    }

    public static void d(String tag, String message, Throwable e) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, message, e);
        }
    }

    public static void d(String tag, String message) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, message);
        }
    }

    //ERROR
    public static void e(Object object, String message, Throwable e) {
        Log.e(object.getClass().getName(), message, e);
        toastIfDebug(object, message);
    }

    public static void e(Object object, String message) {
        Log.e(object.getClass().getName(), message);
        toastIfDebug(object, message);
    }

    public static void e(Object object, Exception e) {
        e(object, e.getMessage(), e);
    }

    private static void toastIfDebug(Object object, String message) {
        if (BuildConfig.DEBUG) {
            if (object instanceof Context) {
                toastOnMainThread((Context) object, message);
            }
            if (object instanceof android.support.v4.app.Fragment) {
                toastOnMainThread(((android.support.v4.app.Fragment) object).getActivity(), message);
            }
            if (object instanceof android.app.Fragment && Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                toastOnMainThread(((android.app.Fragment) object).getActivity(), message);
            }
            if (object instanceof android.support.v4.content.Loader) {
                toastOnMainThread(((android.support.v4.content.Loader) object).getContext(), message);
            }
            if (object instanceof android.content.Loader && Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                toastOnMainThread(((android.content.Loader) object).getContext(), message);
            }
        }
    }

    private static void toastOnMainThread(final Context context, final String message) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            }
        });
    }

    //INFO
    public static void i(Object object, String message) {
        Log.i(object.getClass().getName(), message);
    }

    public static void i(Object object, String message, Throwable e) {
        Log.i(object.getClass().getName(), message, e);
    }

    //WARN
    public static void w(Object object, String message) {
        Log.w(object.getClass().getName(), message);
    }

    public static void w(Object object, String message, Throwable e) {
        Log.w(object.getClass().getName(), message, e);
    }

}