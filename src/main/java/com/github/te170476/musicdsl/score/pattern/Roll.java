package com.github.te170476.musicdsl.score.pattern;

import com.github.te170476.musicdsl.Sound;
import com.github.te170476.musicdsl.score.Tempo;
import com.github.te170476.musicdsl.score.note.AbsoluteNote;
import com.github.te170476.musicdsl.score.note.INoteValue;
import com.github.te170476.musicdsl.score.note.NoteValue;
import com.github.te170476.musicdsl.score.note.RelativeNote;
import com.github.te170476.musicdsl.score.tone.AbsoluteTone;
import com.github.te170476.musicdsl.sound.Soundable;
import com.github.te170476.musicdsl.sound.generator.IWaveGenerator;
import com.github.te170476.musicdsl.sound.generator.WaveGenerator;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class Roll<T> implements Soundable {
    public final List<AbsoluteNote<T>> notes;
    public Roll(List<AbsoluteNote<T>> notes) {
        this.notes = notes;
    }

    public RelativeNote<Roll<T>> toNote() {
        return new RelativeNote<>(noteValue(), this);
    }

    INoteValue noteValue() {
        return notes.stream()
                .map(AbsoluteNote::fullNoteValue)
                .max(Comparator.comparingDouble(INoteValue::toPercentage))
                .orElse(NoteValue.get(0));
    }

    @Override
    public Stream<Sound> toSound(AbsoluteTone root, WaveGenerator generator, Tempo tempo, INoteValue initOffset, IWaveGenerator waveGen) {
        return notes.stream()
                .flatMap(it-> it.toSound(root, generator, tempo, initOffset, waveGen));
    }
}
