package com.github.te170476.musicdsl.score.note;

public class Pitch {
    public final int value;
    Pitch(int pitch) {
        this.value = pitch;
    }
    public Pitch moved(Pitch scalar) {
        return Pitches.get(value + scalar.value);
    }
    public Key toKey(Key root) {
        return root.moved(this);
    }

    @Override
    public String toString() {
        return "Pitch{" +
                "value=" + value +
                '}';
    }
}
