package com.example.mpvcalculatorrx.mvp.presenter;

import android.support.annotation.NonNull;

import com.example.mpvcalculatorrx.mvp.model.MvpModel;
import com.example.mpvcalculatorrx.mvp.view.MvpView;

public class MvpPresenter<M extends MvpModel, V extends MvpView> {
    protected M model;
    protected V view;

    public MvpPresenter(@NonNull M model, @NonNull V view) {
        this.model = model;
        this.view = view;
    }
}
