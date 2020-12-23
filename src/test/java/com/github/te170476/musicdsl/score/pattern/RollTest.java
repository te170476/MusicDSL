package com.github.te170476.musicdsl.score.pattern;

import com.github.te170476.Streams;
import com.github.te170476.musicdsl.Player;
import com.github.te170476.musicdsl.score.Tempo;
import com.github.te170476.musicdsl.score.note.AbsoluteNote;
import com.github.te170476.musicdsl.score.note.INoteValue;
import com.github.te170476.musicdsl.score.note.NoteValue;
import com.github.te170476.musicdsl.score.note.RelativeNote;
import com.github.te170476.musicdsl.score.signature.Keys;
import com.github.te170476.musicdsl.score.signature.Pitch;
import com.github.te170476.musicdsl.score.signature.Pitches;
import com.github.te170476.musicdsl.score.tone.AbsoluteTone;
import com.github.te170476.musicdsl.score.tone.Tone;
import com.github.te170476.musicdsl.sound.Waves;
import com.github.te170476.musicdsl.sound.generator.WaveGenerator;
import org.junit.jupiter.api.Test;

import javax.sound.sampled.AudioFormat;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class RollTest {
    int sampleRate = 44100;
    AudioFormat format = new AudioFormat(sampleRate, 8, 1, true, false);
    WaveGenerator generator = new WaveGenerator(sampleRate);

    Tempo tempo = new Tempo(120);

    @Test
    void toSound() {
        var key = Keys.G;
        var rhythm1n1 = List.of(
                NoteValue.get(4),
                NoteValue.get(4),
                NoteValue.get(4),
                NoteValue.get(4)
        );
        var rhythm1n2 = List.of(
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
        var melody1n1n1 = List.of(
                Pitches.Do.toTone(),
                Pitches.Re.toTone(),
                Pitches.Mi.toTone(),
                Pitches.Fa.toTone()
        );
        var melody1n2n1 = List.of(
                Pitches.Mi.toTone(),
                Pitches.Re.toTone(),
                Pitches.Do.toTone()
        );
        var melody1n1n2 = List.of(
                Pitches.Mi.toTone(),
                Pitches.Fa.toTone(),
                Pitches.So.toTone(),
                Pitches.La.toTone()
        );
        var melody1n2n2 = List.of(
                Pitches.So.toTone(),
                Pitches.Fa.toTone(),
                Pitches.Mi.toTone()
        );
        var melody2 = List.of(
                Pitches.Do.toTone(),
                Pitches.Do.toTone(),
                Pitches.Do.toTone(),
                Pitches.Do.toTone()
        );
        Function<RelativeNote<Tone>, Stream<RelativeNote<Tone>>> toDouble = note-> {
            var halfNoteValue = note.noteValue.setBase(2);
            var halfNote = new RelativeNote<>(halfNoteValue, note.thing);
            return Stream.of(halfNote, halfNote);
        };
        var phrase1 = new Line<>(List.of(
                genLine(rhythm1n1, melody1n1n1).toRoll().toNote(),
                genLine(rhythm1n2, melody1n2n1).toRoll().toNote()
        ));
        var phrase2 = new Line<>(List.of(
                genLine(rhythm1n1, melody1n1n2).toRoll().toNote(),
                genLine(rhythm1n2, melody1n2n2).toRoll().toNote()
        ));
        var phrase3 = new Line<>(List.of(
                genLine(rhythm2, melody2).toRoll().toNote()
        ));
        var phrase4 = new Line<>(List.of(
                new Line<>(genLine(rhythm1n1, melody1n1n1).notes.stream().flatMap(toDouble).collect(Collectors.toList())).toRoll().toNote(),
                genLine(rhythm1n2, melody1n2n1).toRoll().toNote()
        ));

        var phraseLine = new Line<>(
                List.of(phrase1, phrase2, phrase3, phrase4).stream()
                        .map(Line::toRoll)
                        .map(Roll::toNote)
                        .collect(Collectors.toList())
        );
        var roll = phraseLine.toRoll();

        var rounded = new Roll<>(List.of(
                new AbsoluteNote<>(NoteValue.get(0), roll.toNote()),
                new AbsoluteNote<>(phrase1.noteValue(), roll.toNote())
        ));

        var rootTone = new AbsoluteTone(key, 0);
        Player.open(format)
                .ifPresent(it-> it.playAndWait(rounded.toSound(rootTone, generator, tempo, NoteValue.get(0), Waves.square).collect(Collectors.toList())));
    }

    <T> Line<T> genLine(List<INoteValue> rhythm, List<T> things) {
        var notes = Streams.zip(rhythm, things, RelativeNote::new)
                .collect(Collectors.toList());
        return new Line<>(notes);
    }
}