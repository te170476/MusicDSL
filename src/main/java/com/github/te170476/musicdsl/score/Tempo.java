package com.github.te170476.musicdsl.score;

import com.github.te170476.musicdsl.score.note.INoteValue;
import com.github.te170476.musicdsl.score.note.NoteValue;

public class Tempo {
    static final int secBpm = 60;
    INoteValue noteValue;
    int value;
    public Tempo(int tempo) {
        this(NoteValue.get(4), tempo);
    }
    public Tempo(INoteValue noteValue, int tempo) {
        this.noteValue = noteValue;
        this.value = tempo;
    }

    public double toSecParNoteValue() {
        return secBpm / (noteValue.toPercentage() * value);
    }
}
