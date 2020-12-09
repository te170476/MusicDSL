package com.github.te170476.musicdsl.score;

import com.github.te170476.musicdsl.Player;
import com.github.te170476.musicdsl.score.note.NoteValue;
import com.github.te170476.musicdsl.sound.Waves;
import com.github.te170476.musicdsl.sound.generator.WaveGenerator;
import org.junit.jupiter.api.Test;

import javax.sound.sampled.AudioFormat;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class RollTest {
    int sampleRate = 44100;
    AudioFormat format = new AudioFormat(sampleRate, 8, 1, true, false);
    WaveGenerator generator = new WaveGenerator(sampleRate);
    Player player = Player.open(format).get();

    Tempo tempo = new Tempo(120);

    @Test
    void toSound() {
        var line = new Line(
                IntStream.range(0, 25)
                        .mapToObj(index-> new Note(index, NoteValue.get(16)))
                        .collect(Collectors.toList())
        );
        var roll = line.toRoll(NoteValue.get(32));
        player.playAndWait(roll.toSound(generator, tempo, NoteValue.get(32), Waves.sin));
    }
}