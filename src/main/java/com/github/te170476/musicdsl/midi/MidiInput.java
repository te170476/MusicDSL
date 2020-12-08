package com.github.te170476.musicdsl.midi;

import java.util.Objects;

public class MidiInput {
    public final int keyCode;
    public final int strength;
    public MidiInput(int keyCode, int strength) {
        this.keyCode = keyCode;
        this.strength = strength;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MidiInput midiInput = (MidiInput) o;
        return keyCode == midiInput.keyCode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(keyCode);
    }
}
