package com.github.te170476.musicdsl.score;

import com.github.te170476.musicdsl.Player;
import com.github.te170476.musicdsl.score.note.NoteValue;
import com.github.te170476.musicdsl.score.signature.*;
import com.github.te170476.musicdsl.score.tone.AbsoluteTone;
import com.github.te170476.musicdsl.sound.Waves;
import com.github.te170476.musicdsl.sound.generator.WaveGenerator;
import org.junit.jupiter.api.Test;

import javax.sound.sampled.AudioFormat;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

class RollTest {
    int sampleRate = 44100;
    AudioFormat format = new AudioFormat(sampleRate, 8, 1, true, false);
    WaveGenerator generator = new WaveGenerator(sampleRate);
    Player player = Player.open(format).get();

    Tempo tempo = new Tempo(120);

    @Test
    void toSound() {
        var tones = Pitches.Mi.toCode(Pitches.major).toTones();
        var notes = tones.stream()
                .map(it-> it.toNote(NoteValue.get(4)))
                .collect(Collectors.toList());
        var line = new Line(notes);
        var roll = line.toRoll(NoteValue.get(0));
        IntStream.range(0, 12)
                .forEach(index->{
                    var rootTone = new AbsoluteTone(Keys.get(index * 7), 0);
                    player.playAndWait(roll.toSound(rootTone, generator, tempo, NoteValue.get(0), Waves.sin));
                });
    }
}