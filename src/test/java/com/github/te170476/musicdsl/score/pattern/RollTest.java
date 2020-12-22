package com.github.te170476.musicdsl.score.pattern;

import com.github.te170476.Streams;
import com.github.te170476.musicdsl.Player;
import com.github.te170476.musicdsl.score.Tempo;
import com.github.te170476.musicdsl.score.note.AbsoluteNote;
import com.github.te170476.musicdsl.score.note.INoteValue;
import com.github.te170476.musicdsl.score.note.NoteValue;
import com.github.te170476.musicdsl.score.note.RelativeNote;
import com.github.te170476.musicdsl.score.signature.Keys;
import com.github.te170476.musicdsl.score.signature.Pitches;
import com.github.te170476.musicdsl.score.tone.AbsoluteTone;
import com.github.te170476.musicdsl.sound.Waves;
import com.github.te170476.musicdsl.sound.generator.WaveGenerator;
import org.junit.jupiter.api.Test;

import javax.sound.sampled.AudioFormat;
import java.util.List;
import java.util.stream.Collectors;

class RollTest {
    int sampleRate = 44100;
    AudioFormat format = new AudioFormat(sampleRate, 8, 1, true, false);
    WaveGenerator generator = new WaveGenerator(sampleRate);

    Tempo tempo = new Tempo(120);

    @Test
    void toSound() {
        var key = Keys.G;
        var rhythm1 = List.of(
                NoteValue.get(4),
                NoteValue.get(4),
                NoteValue.get(4),
                NoteValue.get(4),
                NoteValue.get(4),
                NoteValue.get(4),
                NoteValue.get(2)
        );
        var rhythm2 = List.of(
                NoteValue.get(2),
                NoteValue.get(2),
                NoteValue.get(2),
                NoteValue.get(2)
        );
        var rhythm3 = List.of(
                NoteValue.get(8),
                NoteValue.get(8),
                NoteValue.get(8),
                NoteValue.get(8),
                NoteValue.get(8),
                NoteValue.get(8),
                NoteValue.get(8),
                NoteValue.get(8),
                NoteValue.get(4),
                NoteValue.get(4),
                NoteValue.get(2)
        );
        var melody1 = List.of(
                Pitches.Do.toTone(),
                Pitches.Re.toTone(),
                Pitches.Mi.toTone(),
                Pitches.Fa.toTone(),
                Pitches.Mi.toTone(),
                Pitches.Re.toTone(),
                Pitches.Do.toTone()
        );
        var melody2 = List.of(
                Pitches.Mi.toTone(),
                Pitches.Fa.toTone(),
                Pitches.So.toTone(),
                Pitches.La.toTone(),
                Pitches.So.toTone(),
                Pitches.Fa.toTone(),
                Pitches.Mi.toTone()
        );
        var melody3 = List.of(
                Pitches.Do.toTone(),
                Pitches.Do.toTone(),
                Pitches.Do.toTone(),
                Pitches.Do.toTone()
        );
        var melody4 = List.of(
                Pitches.Do.toTone(),
                Pitches.Do.toTone(),
                Pitches.Re.toTone(),
                Pitches.Re.toTone(),
                Pitches.Mi.toTone(),
                Pitches.Mi.toTone(),
                Pitches.Fa.toTone(),
                Pitches.Fa.toTone(),
                Pitches.Mi.toTone(),
                Pitches.Re.toTone(),
                Pitches.Do.toTone()
        );
        var phrase1 = genLine(rhythm1, melody1);
        var phrase2 = genLine(rhythm1, melody2);
        var phrase3 = genLine(rhythm2, melody3);
        var phrase4 = genLine(rhythm3, melody4);
        var phrasesNotes =
                List.of(phrase1, phrase2, phrase3, phrase4).stream()
                        .map(Line::toRoll)
                        .map(Roll::toNote)
                        .collect(Collectors.toList());

        var phraseLine = new Line<>(phrasesNotes);
        var roll = phraseLine.toRoll();

        var notedPhrase = roll.toNote();
        var rhythm1FullLength = rhythm1.stream()
                .reduce(NoteValue.get(0), (it, sum)-> NoteValue.get(sum, it));
        var rounded = new Roll<>(List.of(
                new AbsoluteNote<>(NoteValue.get(0), notedPhrase),
                new AbsoluteNote<>(rhythm1FullLength, notedPhrase)
        ));

        var rootTone = new AbsoluteTone(key, 0);
        Player.open(format)
                .ifPresent(it-> it.playAndWait(rounded.toSound(rootTone, generator, tempo, NoteValue.get(0), Waves.sin).collect(Collectors.toList())));
    }

    <T> Line<T> genLine(List<INoteValue> rhythm, List<T> things) {
        var notes = Streams.zip(rhythm, things, RelativeNote::new)
                .collect(Collectors.toList());
        return new Line<>(notes);
    }
}