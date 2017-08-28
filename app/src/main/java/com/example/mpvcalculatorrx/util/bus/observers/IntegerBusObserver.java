package com.example.mpvcalculatorrx.util.bus.observers;

public abstract class IntegerBusObserver extends BusObserver<Integer> {
    public IntegerBusObserver() {
        super(Integer.class);
    }
}