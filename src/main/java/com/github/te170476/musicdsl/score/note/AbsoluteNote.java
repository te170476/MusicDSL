package com.github.te170476.musicdsl.score.note;

public class AbsoluteNote<T> {
    public final INoteValue offset;
    public final RelativeNote<T> relative;
    public AbsoluteNote(INoteValue offset, RelativeNote<T> relativeNote) {
        this.offset = offset;
        this.relative = relativeNote;
    }

    public INoteValue fullNoteValue() {
        return NoteValue.get(offset, relative.noteValue);
    }
}
