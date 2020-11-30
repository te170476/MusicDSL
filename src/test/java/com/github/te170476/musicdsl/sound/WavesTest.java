package com.github.te170476.musicdsl.sound;

import com.github.te170476.musicdsl.Player;
import com.github.te170476.musicdsl.Sound;
import com.github.te170476.musicdsl.sound.generator.IWaveGenerator;
import com.github.te170476.musicdsl.sound.generator.WaveGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sound.sampled.AudioFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WavesTest {
    int sampleRate = 44100;
    AudioFormat format = new AudioFormat(sampleRate, 8, 1, true, false);
    WaveGenerator generator = new WaveGenerator(sampleRate);
    Player player = new Player(format);

    List<Sound> sounds = Collections.emptyList();
    int playLength = sampleRate * 2;

    @BeforeEach
    void beforeEach() {
        sounds.clear();
    }

    @Test
    void sin() {
        play(Waves.sin);
    }

    @Test
    void saw() {
        play(Waves.saw);
    }

    @Test
    void square() {
        play(Waves.square);
    }

    @Test
    void triangle() {
        play(Waves.triangle);
    }

    void play(IWaveGenerator waveGen) {
        sounds = Arrays.asList(
                new Sound(generator.generate(Waves.getHertz(-9), playLength, waveGen), 0),
                new Sound(generator.generate(Waves.getHertz(-5), playLength, waveGen), 0),
                new Sound(generator.generate(Waves.getHertz(-2), playLength, waveGen), 0)
        );
        player.playAndWait(sounds);
    }
}