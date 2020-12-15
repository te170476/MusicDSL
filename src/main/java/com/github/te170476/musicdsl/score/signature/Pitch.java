package com.github.te170476.musicdsl.score.signature;

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

    public Tone toTone() {
        return this.setOctave(0);
    }
    public Tone setOctave(int octaveScalar) {
        return new Tone(this, octaveScalar);
    }

    @Override
    public String toString() {
        return "Pitch{" +
                "value=" + value +
                '}';
    }
}
