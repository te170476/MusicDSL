package com.github.te170476.musicdsl.score.note;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class PitchesTest {

    @Test
    void get() {
        var zero = Pitches.get(0);
        var octaveUp = Pitches.get(Pitches.max);
        assertEquals(zero, octaveUp);
        var octaveDown = Pitches.get(Pitches.max * -1);
        assertEquals(zero, octaveDown);
    }
}