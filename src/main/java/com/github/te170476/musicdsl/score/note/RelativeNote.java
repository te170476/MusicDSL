package com.github.te170476.musicdsl.score.note;

import com.github.te170476.musicdsl.Sound;
import com.github.te170476.musicdsl.score.Tempo;
import com.github.te170476.musicdsl.score.tone.AbsoluteTone;
import com.github.te170476.musicdsl.score.tone.Tone;
import com.github.te170476.musicdsl.sound.Soundable;
import com.github.te170476.musicdsl.sound.Waves;
import com.github.te170476.musicdsl.sound.generator.IWaveGenerator;
import com.github.te170476.musicdsl.sound.generator.WaveGenerator;

import java.util.stream.Stream;

public class RelativeNote<T> implements Soundable {
    public final INoteValue noteValue;
    public final T thing;
    public RelativeNote(INoteValue noteValue, T thing) {
        this.noteValue = noteValue;
        this.thing = thing;
    }

    @Override
    public Stream<Sound> toSound(AbsoluteTone root, WaveGenerator generator, Tempo tempo, INoteValue initOffset, IWaveGenerator waveGen) {
        if (thing instanceof Tone) {
            var absoluteTone = ((Tone) thing).toAbsolute(root);
            var offsetSec = tempo.toSecParNoteValue() * initOffset.toPercentage();
            var offsetLength = (int)(offsetSec * generator.sampleRate);
            var noteSec = tempo.toSecParNoteValue() * noteValue.toPercentage();
            var noteLength = (int) (noteSec * generator.sampleRate);
            var wave = generator.generate(Waves.getHertz(absoluteTone.getIntPitch()), noteLength, waveGen);
            return Stream.of(new Sound(wave, offsetLength));
        }
        if (thing instanceof Soundable) {
            return ((Soundable) thing).toSound(root, generator, tempo, initOffset, waveGen);
        }
        return Stream.empty();
    }
}
