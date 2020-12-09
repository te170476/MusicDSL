package com.github.te170476.musicdsl.score;

import com.github.te170476.musicdsl.score.note.INoteValue;
import com.github.te170476.musicdsl.score.note.NoteValue;

import java.util.ArrayList;
import java.util.List;

public class Line {
    public final List<Note> notes;
    public Line(List<Note> notes) {
        this.notes = notes;
    }

    public Roll toRoll(INoteValue initOffset) {
        List<Roll.RollNote> rollNotes = new ArrayList<>();
        INoteValue offset = initOffset;
        for (Note note : notes) {
            rollNotes.add(new Roll.RollNote(note, offset));
            offset = NoteValue.get(offset, note.noteValue);
        }
        return new Roll(rollNotes);
    }
}
