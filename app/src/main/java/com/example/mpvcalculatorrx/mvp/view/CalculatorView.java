package com.example.mpvcalculatorrx.mvp.view;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.example.mpvcalculatorrx.R;
import com.example.mpvcalculatorrx.mvp.model.CalculatorModel;
import com.example.mpvcalculatorrx.util.bus.RxBus;
import com.example.mpvcalculatorrx.util.bus.observers.ResetButtonObserver;

import butterknife.BindView;
import butterknife.OnClick;

public class CalculatorView extends MvpActivityView {

    @BindView(R.id.text_display) protected TextView textDisplayView;

    public CalculatorView(@NonNull Activity activity) {
        super(activity);
    }

    public void setDisplayValue(@NonNull final String value) {
        textDisplayView.setText(value);
    }

    @OnClick({R.id.button_0, R.id.button_1, R.id.button_2, R.id.button_3, R.id.button_4, R.id.button_5, R.id.button_6, R.id.button_7, R.id.button_8, R.id.button_9})
    protected void digitButtonPressed(@NonNull final View button) {
        int digit;
        switch (button.getId()) {
            case R.id.button_0:
                digit = 0;
                break;

            case R.id.button_1:
                digit = 1;
                break;

            case R.id.button_2:
                digit = 2;
                break;

            case R.id.button_3:
                digit = 3;
                break;

            case R.id.button_4:
                digit = 4;
                break;

            case R.id.button_5:
                digit = 5;
                break;

            case R.id.button_6:
                digit = 6;
                break;

            case R.id.button_7:
                digit = 7;
                break;

            case R.id.button_8:
                digit = 8;
                break;

            case R.id.button_9:
                digit = 9;
                break;

            default:
                digit = -1;
        }

        if (digit != -1) {
            digitButtonPressed(digit);
        }
    }

    @OnClick({R.id.button_division, R.id.button_multiplication, R.id.button_addition, R.id.button_subtraction})
    protected void operatorButtonPressed(@NonNull final View button) {
        Character operator;
        switch (button.getId()) {
            case R.id.button_division:
                operator = CalculatorModel.OP_DIVISION;
                break;

            case R.id.button_multiplication:
                operator = CalculatorModel.OP_MULTIPLICATION;
                break;

            case R.id.button_addition:
                operator = CalculatorModel.OP_ADDITION;
                break;

            case R.id.button_subtraction:
                operator = CalculatorModel.OP_SUBTRACTION;
                break;

            default:
                operator = null;
        }
        if (operator != null) {
            operatorButtonPressed(operator);
        }
    }

    @OnClick({R.id.button_equals, R.id.button_reset})
    protected void functionButtonPressed(@NonNull final View button) {
        switch (button.getId()) {
            case R.id.button_equals:
                equalsButtonPressed();
                break;

            case R.id.button_reset:
                resetButtonPressed();
                break;
        }
    }

    public void digitButtonPressed(final int digit) {
        RxBus.post(digit);
    }

    public void operatorButtonPressed(@NonNull final Character operator) {
        RxBus.post(operator);
    }

    public void equalsButtonPressed() {
        RxBus.post(CalculatorModel.EQUALS);
    }

    public void resetButtonPressed() {
        RxBus.post(new ResetButtonObserver.ResetButtonPressed());
    }
}
