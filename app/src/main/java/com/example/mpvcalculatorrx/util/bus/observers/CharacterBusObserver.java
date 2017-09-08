package com.example.mpvcalculatorrx.util.bus.observers;

public abstract class CharacterBusObserver extends BusObserver<Character> {
    public CharacterBusObserver() {
        super(Character.class);
    }
}
