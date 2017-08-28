package com.example.mpvcalculatorrx.util.bus.observers;

public abstract class BooleanBusObserver extends BusObserver<Boolean> {
    public BooleanBusObserver() {
        super(Boolean.class);
    }
}