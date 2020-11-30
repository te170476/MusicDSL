package com.github.te170476.musicdsl.score.note;

public class Pitch {
    private final int value;
    Pitch(int pitch) {
        this.value = pitch;
    }
    public Key toKey(Key root) {
        return root.getMovable(value);
    }
}
