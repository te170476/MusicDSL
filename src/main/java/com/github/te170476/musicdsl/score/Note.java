package com.github.te170476.musicdsl.score;

import com.github.te170476.musicdsl.Sound;
import com.github.te170476.musicdsl.score.note.INoteValue;
import com.github.te170476.musicdsl.sound.Waves;
import com.github.te170476.musicdsl.sound.generator.IWaveGenerator;
import com.github.te170476.musicdsl.sound.generator.WaveGenerator;

public class Note{
    public int pitch;
    public INoteValue rate;
    public Note(int pitch, INoteValue rate){
        this.pitch = pitch;
        this.rate = rate;
    }
    public Sound toSound(WaveGenerator generator, int bpm, int offset, IWaveGenerator waveGen) {
        var wholeNoteSec = 240.0 / bpm;
        var notePercentage = rate.toPercentage();
        var noteSec = wholeNoteSec * notePercentage;
        var noteLength = (int) (noteSec * generator.sampleRate);
        var wave = generator.generate(Waves.getHertz(pitch), noteLength, waveGen);
        return new Sound(wave, offset);
    }
}
