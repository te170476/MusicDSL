package com.github.te170476.musicdsl.score.note;

public class Pitch {
    public final int value;
    Pitch(int pitch) {
        this.value = pitch;
    }
    public Pitch getRelative(Pitch scalar) {
        return Pitches.get(value + scalar.value);
    }
    public Key toKey(Key root) {
        return root.getMovable(value);
    }

    @Override
    public String toString() {
        return "Pitch{" +
                "value=" + value +
                '}';
    }
}
