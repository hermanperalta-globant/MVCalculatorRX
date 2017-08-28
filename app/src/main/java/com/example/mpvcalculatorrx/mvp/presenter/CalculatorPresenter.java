package com.example.mpvcalculatorrx.mvp.presenter;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.example.mpvcalculatorrx.mvp.model.CalculatorModel;
import com.example.mpvcalculatorrx.mvp.view.CalculatorView;
import com.example.mpvcalculatorrx.util.bus.RxBus;
import com.example.mpvcalculatorrx.util.bus.observers.CharacterBusObserver;
import com.example.mpvcalculatorrx.util.bus.observers.IntegerBusObserver;
import com.example.mpvcalculatorrx.util.bus.observers.ResetButtonObserver;

public class CalculatorPresenter extends MvpPresenter<CalculatorModel, CalculatorView> {
    public CalculatorPresenter(CalculatorModel model, CalculatorView view) {
        super(model, view);
    }

    public void register() {
        Activity activity = view.getActivity();
        if (activity != null) {
            RxBus.subscribe(activity, new IntegerBusObserver() {
                @Override
                public void onEvent(Integer value) {
                    onDigitPressed(value);
                }
            });

            RxBus.subscribe(activity, new CharacterBusObserver() {
                @Override
                public void onEvent(Character value) {

                    if (value == CalculatorModel.EQUALS) {
                        onEqualsButtonPressed();
                    } else {
                        onOperatorPressed(value);
                    }
                }
            });

            RxBus.subscribe(activity, new ResetButtonObserver() {
                @Override
                public void onEvent(ResetButtonPressed value) {
                    onResetButtonPressed();
                }
            });
        }
    }

    public void unregister() {
        Activity activity = view.getActivity();
        if (activity != null) {
            RxBus.clear(activity);
        }
    }

    public void onDigitPressed(final int digit) {
        model.addDigit(digit);
        view.setDisplayValue(model.getDisplayText());
    }

    public void onOperatorPressed(@NonNull final Character operator) {
        model.addOperator(operator);
        view.setDisplayValue(model.getDisplayText());
    }

    public void onEqualsButtonPressed() {
        model.calculate();
        view.setDisplayValue(String.valueOf(model.getResult()));
    }

    public void onResetButtonPressed() {
        model.reset();
        view.setDisplayValue(model.getDisplayText());
    }
}
