package com.github.te170476.musicdsl.score.signature;

import com.github.te170476.musicdsl.score.tone.AbsoluteTone;

public class Key {
    public final int value;
    Key(int key) {
        this.value = key;
    }

    public Key moved(Pitch scalar) {
        return Keys.get(value + scalar.value);
    }
    public Pitch toPitch(Key root) {
        return Pitches.get(Math.abs(value - root.value));
    }

    public AbsoluteTone toTone(int octave) {
        return new AbsoluteTone(this, octave);
    }

    @Override
    public String toString() {
        return "Key{" +
                "value=" + value +
                '}';
    }
}
