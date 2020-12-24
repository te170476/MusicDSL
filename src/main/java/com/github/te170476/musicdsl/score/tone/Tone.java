package com.github.te170476.musicdsl.score.tone;

import com.github.te170476.musicdsl.score.signature.Pitch;

public class Tone {
    public Pitch pitch;
    public int octaveScalar;
    public Tone(Pitch pitch, int octaveScalar) {
        this.pitch = pitch;
        this.octaveScalar = octaveScalar;
    }

    public AbsoluteTone toAbsolute(AbsoluteTone root) {
        var key = pitch.toKey(root.key);
        var octaveInc = root.key.value > key.value ? 1 : 0;
        return new AbsoluteTone(key, root.octave + octaveScalar + octaveInc);
    }
}
