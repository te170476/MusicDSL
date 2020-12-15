package com.github.te170476.musicdsl.score.tone;

import com.github.te170476.musicdsl.score.signature.Key;
import com.github.te170476.musicdsl.score.signature.Keys;

public class AbsoluteTone {
    public Key key;
    public int octave;
    public AbsoluteTone(Key key, int octave) {
        this.key = key;
        this.octave = octave;
    }
    public int getIntPitch() {
        return key.value + (octave * Keys.max) - Keys.A.value;
    }
}
