package com.github.te170476.musicdsl.score.note;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PitchTest {
    @Test
    void getRelative() {
        var absoluteMi = Pitches.Mi;
        var relativeMi1 = Pitches.Do.getRelative(Pitches.Mi);
        assertEquals(absoluteMi, relativeMi1);
        var relativeMi2 = relativeMi1.getRelative(Pitches.Fi).getRelative(Pitches.Fi);
        assertEquals(absoluteMi, relativeMi2);
    }
}