package com.github.te170476.musicdsl.score.note;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KeyTest {

    @Test
    void moved() {
        var relativeE = Keys.B.moved(Pitches.Fa);
        assertEquals(Keys.E, relativeE);
        assertEquals(Keys.E, relativeE.moved(Pitches.Fi).moved(Pitches.Fi));
    }

    @Test
    void toPitch() {
        assertEquals(Keys.B.toPitch(Keys.Ds), Pitches.Le);
        assertEquals(Keys.Ds.toPitch(Keys.B), Pitches.Le);
    }
}