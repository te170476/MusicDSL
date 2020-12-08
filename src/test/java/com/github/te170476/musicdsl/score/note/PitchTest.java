package com.github.te170476.musicdsl.score.note;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PitchTest {

    @Test
    void moved() {
        var relativeMi = Pitches.Ti.moved(Pitches.Fa);
        assertEquals(Pitches.Mi, relativeMi);
        assertEquals(Pitches.Mi, relativeMi.moved(Pitches.Fi).moved(Pitches.Fi));
    }

    @Test
    void toKey() {
        assertEquals(Keys.E, Pitches.Ti.toKey(Keys.F));
        assertEquals(Keys.Af, Pitches.Mi.toKey(Keys.E));
    }
}