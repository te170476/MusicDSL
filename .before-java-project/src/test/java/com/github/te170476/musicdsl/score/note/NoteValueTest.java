package com.github.te170476.musicdsl.score.note;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NoteValueTest {
    INoteValue whole = NoteValue.get(1);
    INoteValue triplet = NoteValue.get(3).setBase(whole);
    INoteValue nonuplet = NoteValue.get(9).setBase(whole);

    @Test
    void relativeEqualsAbsolute() {
        var relativeNonuplet = NoteValue.get(3).setBase(triplet);
        var absoluteNonuplet = nonuplet;
        assertEquals(
                relativeNonuplet.toPercentage(),
                absoluteNonuplet.toPercentage()
        );

        var relativeTriplet = NoteValue.get(nonuplet, nonuplet, nonuplet);
        var absoluteTriplet = triplet;
        assertEquals(
                relativeTriplet.toPercentage(),
                absoluteTriplet.toPercentage()
        );
    }
}
