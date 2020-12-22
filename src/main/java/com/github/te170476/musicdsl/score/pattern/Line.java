package com.github.te170476.musicdsl.score.pattern;

import com.github.te170476.Streams;
import com.github.te170476.musicdsl.score.note.AbsoluteNote;
import com.github.te170476.musicdsl.score.note.INoteValue;
import com.github.te170476.musicdsl.score.note.NoteValue;
import com.github.te170476.musicdsl.score.note.RelativeNote;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Line<T> {
    public final List<RelativeNote<T>> notes;
    public Line(List<RelativeNote<T>> notes) {
        this.notes = notes;
    }

    public RelativeNote<Line<T>> toNote() {
        return new RelativeNote<>(noteValue(), this);
    }

    INoteValue noteValue() {
        return notes.stream()
                .map(it-> it.noteValue)
                .reduce(NoteValue.get(0), (it, sum) -> NoteValue.get(sum, it));
    }

    public List<AbsoluteNote<T>> toNotes() {
        return Streams.reducingMap(notes.stream(), NoteValue.get(0),
                (note, sum)-> NoteValue.get(sum, note.noteValue),
                (note, offset)-> new AbsoluteNote<>(offset, note)
        ).collect(Collectors.toList());
    }
    public Roll<T> toRoll() {
        return new Roll<>(toNotes());
    }
}
