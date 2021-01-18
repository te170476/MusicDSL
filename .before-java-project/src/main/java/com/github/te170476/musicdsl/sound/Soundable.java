package com.github.te170476.musicdsl.sound;

import com.github.te170476.musicdsl.Sound;
import com.github.te170476.musicdsl.score.Tempo;
import com.github.te170476.musicdsl.score.note.INoteValue;
import com.github.te170476.musicdsl.score.tone.AbsoluteTone;
import com.github.te170476.musicdsl.sound.generator.IWaveGenerator;
import com.github.te170476.musicdsl.sound.generator.WaveGenerator;

import java.util.stream.Stream;

public interface Soundable {
    Stream<Sound> toSound(AbsoluteTone root, WaveGenerator generator, Tempo tempo, INoteValue initOffset, IWaveGenerator waveGen);
}
