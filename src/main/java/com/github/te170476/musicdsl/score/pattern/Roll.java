package com.github.te170476.musicdsl.score.pattern;

import com.github.te170476.musicdsl.score.note.AbsoluteNote;
import com.github.te170476.musicdsl.score.note.INoteValue;
import com.github.te170476.musicdsl.score.note.NoteValue;
import com.github.te170476.musicdsl.score.note.RelativeNote;
import com.github.te170476.musicdsl.sound.Soundable;

import java.util.Comparator;
import java.util.List;

public class Roll<T> {
    public final List<AbsoluteNote<T>> notes;
    public Roll(List<AbsoluteNote<T>> notes) {
        this.notes = notes;
    }

    public RelativeNote<Roll<T>> toNote() {
        return new RelativeNote<>(noteValue(), this);
    }

    INoteValue noteValue() {
        return notes.stream()
                .map(AbsoluteNote::fullNoteValue)
                .max(Comparator.comparingDouble(INoteValue::toPercentage))
                .orElse(NoteValue.get(0));
    }
}
