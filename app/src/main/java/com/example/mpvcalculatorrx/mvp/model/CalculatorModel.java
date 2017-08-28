package com.example.mpvcalculatorrx.mvp.model;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class CalculatorModel implements MvpModel {
    public static final char OP_ADDITION = '+';
    public static final char OP_SUBTRACTION = '-';
    public static final char OP_MULTIPLICATION = '*';
    public static final char OP_DIVISION = '/';
    public static final char EQUALS = '=';

    private List<Long> values;
    private List<Character> operators;
    private long result;
    private String currentValue;

    public CalculatorModel() {
        values = new ArrayList<>();
        operators = new ArrayList<>();
        currentValue = "";
        result = 0;
    }

    private void doOperation(final char operator, final long a, final long b) {
        switch (operator) {
            case OP_ADDITION:
                result += a + b;
                break;

            case OP_SUBTRACTION:
                result += a - b;
                break;

            case OP_DIVISION:
                result += a / b;
                break;

            case OP_MULTIPLICATION:
                result += a * b;
                break;
        }
    }

    public void addDigit(final int digit) {
        boolean canAdd = false;
        if (digit == 0) {
            if (currentValue.startsWith(String.valueOf(OP_SUBTRACTION)) && currentValue.length() > 1
                    || !currentValue.isEmpty()) {
                canAdd = true;
            }
        } else {
            canAdd = true;
        }

        if (canAdd) {
            currentValue += String.valueOf(digit);
        }
    }

    public void addOperator(@NonNull final Character operator) {
        if (!currentValue.isEmpty()) {
            values.add(Long.parseLong(currentValue));
            operators.add(operator);
            currentValue = "";
        } else {
            if (operator != OP_SUBTRACTION) {
                operators.add(operator);
            } else {
                currentValue = String.valueOf(OP_SUBTRACTION);
            }
        }
    }

    public void reset() {
        values.clear();
        operators.clear();
        currentValue = "";
        result = 0;
    }

    public void calculate() {
        result = 0;

        if (!currentValue.isEmpty()) {
            if (!currentValue.equals(String.valueOf(OP_SUBTRACTION))) {
                values.add(Long.parseLong(currentValue));
            }
        }

        while (!operators.isEmpty()) {
            final char operator = operators.remove(0);

            if (values.size() > 1) {

                final long a = values.remove(0);
                final long b = values.remove(0);

                doOperation(operator, a, b);
            }
        }

        currentValue = String.valueOf(result);
    }

    public long getResult() {
        return result;
    }

    public String getDisplayText() {
        StringBuilder sb = new StringBuilder();
        for (int valIndex = 0, opIndex = 0; valIndex < values.size(); valIndex++) {
            sb.append(values.get(valIndex));
            if (operators.size() > opIndex) {
                sb.append(operators.get(opIndex));
                ++opIndex;
            }
            if (values.size() > ++valIndex) {
                sb.append(values.get(valIndex));
            }
        }
        if (!currentValue.isEmpty()) {
            sb.append(currentValue);
        }
        return sb.toString();
    }
}
