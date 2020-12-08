package com.github.te170476.musicdsl.score.note;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KeysTest {

    @Test
    void get() {
        var zero = Keys.get(0);
        var octaveUp = Keys.get(Keys.max);
        assertEquals(zero, octaveUp);
        var octaveDown = Keys.get(Keys.max * -1);
        assertEquals(zero, octaveDown);
    }
}