package com.github.te170476.musicdsl.score.note;

import com.github.te170476.musicdsl.Sound;
import com.github.te170476.musicdsl.score.Tempo;
import com.github.te170476.musicdsl.score.tone.AbsoluteTone;
import com.github.te170476.musicdsl.sound.Soundable;
import com.github.te170476.musicdsl.sound.generator.IWaveGenerator;
import com.github.te170476.musicdsl.sound.generator.WaveGenerator;

import java.util.stream.Stream;

public class AbsoluteNote<T> implements Soundable {
    public final INoteValue offset;
    public final RelativeNote<T> relative;
    public AbsoluteNote(INoteValue offset, RelativeNote<T> relativeNote) {
        this.offset = offset;
        this.relative = relativeNote;
    }

    public INoteValue fullNoteValue() {
        return NoteValue.get(offset, relative.noteValue);
    }

    @Override
    public Stream<Sound> toSound(AbsoluteTone root, WaveGenerator generator, Tempo tempo, INoteValue initOffset, IWaveGenerator waveGen) {
        var offset = NoteValue.get(initOffset, this.offset);
        return relative.toSound(root, generator, tempo, offset, waveGen);
    }
}
