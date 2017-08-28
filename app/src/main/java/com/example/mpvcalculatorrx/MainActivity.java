package com.example.mpvcalculatorrx;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.mpvcalculatorrx.mvp.model.CalculatorModel;
import com.example.mpvcalculatorrx.mvp.presenter.CalculatorPresenter;
import com.example.mpvcalculatorrx.mvp.view.CalculatorView;

public class MainActivity extends AppCompatActivity {

    private CalculatorPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new CalculatorPresenter(new CalculatorModel(), new CalculatorView(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.register();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.unregister();
    }
}
