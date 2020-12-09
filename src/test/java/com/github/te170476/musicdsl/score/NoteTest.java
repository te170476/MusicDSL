package com.github.te170476.musicdsl.score;

import com.github.te170476.musicdsl.Player;
import com.github.te170476.musicdsl.Sound;
import com.github.te170476.musicdsl.score.note.INoteValue;
import com.github.te170476.musicdsl.score.note.NoteValue;
import com.github.te170476.musicdsl.sound.Waves;
import com.github.te170476.musicdsl.sound.generator.WaveGenerator;
import org.junit.jupiter.api.Test;

import javax.sound.sampled.AudioFormat;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class NoteTest {
    int sampleRate = 44100;
    AudioFormat format = new AudioFormat(sampleRate, 8, 1, true, false);
    WaveGenerator generator = new WaveGenerator(sampleRate);
    Player player = Player.open(format).get();

    Tempo tempo = new Tempo(120);

    @Test
    void toSound() {
        var notes = IntStream.range(-12, 49)
                .mapToObj(pitch-> new Note(pitch, NoteValue.get(4)))
                .collect(Collectors.toList());
        List<Sound> sounds = new ArrayList<>();
        INoteValue offset = NoteValue.get(4);
        for (Note note : notes) {
            var sound = note.toSound(generator, tempo, offset, Waves.sin);
            offset = NoteValue.get(offset, note.noteValue);
            sounds.add(sound);
        }
        player.playAndWait(sounds);
    }
}