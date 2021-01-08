package com.github.te170476.musicdsl.score.note;

import com.github.te170476.musicdsl.score.Tempo;

public interface INoteValue {
    double toPercentage();
    default INoteValue setBase(int base) {
        return setBase(NoteValue.Value.get(base));
    }
    default INoteValue setBase(INoteValue base) {
        return new NoteValue.HasBase(base, this);
    }
    default double toSecond(Tempo tempo) {
        return toPercentage() * tempo.toSecParNoteValue();
    }
}
