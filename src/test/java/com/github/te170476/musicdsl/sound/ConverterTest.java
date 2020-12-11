package com.github.te170476.musicdsl.sound;

import com.github.te170476.musicdsl.Player;
import com.github.te170476.musicdsl.Sound;
import com.github.te170476.musicdsl.sound.generator.IWaveGenerator;
import com.github.te170476.musicdsl.sound.generator.Parameters;
import com.github.te170476.musicdsl.sound.generator.WaveGenerator;
import org.junit.jupiter.api.Test;

import javax.sound.sampled.AudioFormat;
import java.util.Arrays;
import java.util.List;

class ConverterTest {
    int sampleRate = 44100;
    AudioFormat format = new AudioFormat(sampleRate, 8, 1, true, false);
    WaveGenerator generator = new WaveGenerator(sampleRate);
    Player player = Player.open(format).get();

    @Test
    void merge() {
        IWaveGenerator sinAndSquare = parameters -> {
            double sinResult = Waves.sin.apply(parameters);
            var octaveDowned = new Parameters(parameters.sampleRate, parameters.timeIndex, parameters.hertz / 2);
            double squareResult = Waves.square.apply(octaveDowned);
            return Converter.merge(sinResult,squareResult);
        };
        List<Sound> sounds = Arrays.asList(
                new Sound(generator.generate(Waves.getHertz(12), sampleRate * 1, sinAndSquare), 0),
                new Sound(generator.generate(Waves.getHertz(12), sampleRate * 1, Waves.sin), sampleRate*2),
                new Sound(generator.generate(Waves.getHertz(-0), sampleRate * 1, Waves.square), sampleRate*2)
        );
        player.playAndWait(sounds);
    }
}