package com.github.te170476.musicdsl.score.note;

public class RelativeNote<T> {
    public final INoteValue noteValue;
    public final T thing;
    public RelativeNote(INoteValue noteValue, T thing) {
        this.noteValue = noteValue;
        this.thing = thing;
    }
}
