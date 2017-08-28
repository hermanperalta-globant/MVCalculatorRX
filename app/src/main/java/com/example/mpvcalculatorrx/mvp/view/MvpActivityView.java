package com.example.mpvcalculatorrx.mvp.view;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.ref.WeakReference;

import butterknife.ButterKnife;

public class MvpActivityView implements MvpView {
    private WeakReference<Activity> activityRef;

    public MvpActivityView(@NonNull final Activity activity) {
        activityRef = new WeakReference<>(activity);
        ButterKnife.bind(this, activity);
    }

    @Nullable
    public Activity getActivity() {
        return activityRef.get();
    }

    @Nullable
    public Context getContext() {
        return getActivity();
    }

    @Nullable
    public FragmentManager getFragmentManager() {
        Activity activity = getActivity();
        return (activity != null) ? activity.getFragmentManager() : null;
    }
}
