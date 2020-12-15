package com.github.te170476.musicdsl.score.signature;

import com.github.te170476.musicdsl.score.Line;
import com.github.te170476.musicdsl.score.note.INoteValue;

public class Tone {
    public Pitch pitch;
    public int octaveScalar;
    Tone(Pitch pitch, int octaveScalar) {
        this.pitch = pitch;
        this.octaveScalar = octaveScalar;
    }

    public Line.Note toNote(INoteValue noteValue) {
        return new Line.Note(noteValue, this);
    }

    public AbsoluteTone toAbsolute(AbsoluteTone root) {
        var key = pitch.toKey(root.key);
        var octaveInc = root.key.value > key.value ? 1 : 0;
        System.out.println(octaveInc);
        return new AbsoluteTone(key, root.octave + octaveScalar + octaveInc);
    }
}
