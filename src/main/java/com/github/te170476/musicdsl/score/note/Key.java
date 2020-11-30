package com.github.te170476.musicdsl.score.note;

public class Key {
    public final int value;
    Key(int key) {
        this.value = key;
    }

    public Key getMovable(int scalar) {
        return Keys.get(value + scalar);
    }
}
