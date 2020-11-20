package com.github.te170476.musicdsl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sound.sampled.AudioFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class GeneratorTest {
    int sampleRate = 44100;
    AudioFormat format = new AudioFormat(sampleRate, 8, 1, true, false);
    Generator generator = new Generator(sampleRate);
    Player player = new Player(format);

    List<Sound> sounds = Collections.emptyList();
    int playSecond = 2;

    @BeforeEach
    void beforeEach() {
        sounds.clear();
    }

    @Test
    void sin() {
        sounds = Arrays.asList(
                new Sound(generator.sin(261.626, playSecond), 0 * sampleRate),
                new Sound(generator.sin(329.628, playSecond), 0 * sampleRate),
                new Sound(generator.sin(391.995, playSecond), 0 * sampleRate)
        );
        player.playAndWait(sounds);
    }

    @Test
    void saw() {
        sounds = Arrays.asList(
                new Sound(generator.saw(261.626, playSecond), 0 * sampleRate),
                new Sound(generator.saw(329.628, playSecond), 0 * sampleRate),
                new Sound(generator.saw(391.995, playSecond), 0 * sampleRate)
        );
        player.playAndWait(sounds);
    }

    @Test
    void square() {
        sounds = Arrays.asList(
                new Sound(generator.square(261.626, playSecond), 0 * sampleRate),
                new Sound(generator.square(329.628, playSecond), 0 * sampleRate),
                new Sound(generator.square(391.995, playSecond), 0 * sampleRate)
        );
        player.playAndWait(sounds);
    }
}