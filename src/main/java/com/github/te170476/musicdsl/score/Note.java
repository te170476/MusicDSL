package com.github.te170476.musicdsl.score;

import com.github.te170476.musicdsl.Sound;
import com.github.te170476.musicdsl.sound.Waves;
import com.github.te170476.musicdsl.sound.generator.IWaveGenerator;
import com.github.te170476.musicdsl.sound.generator.WaveGenerator;

public class Note{
    public int pitch;
    public int rate;
    public Note(int pitch, int rate){
        this.pitch = pitch;
        this.rate = rate;
    }
    public Sound toSound(WaveGenerator generator, int bpm, int offset, IWaveGenerator waveGen) {
        var quarterNoteSec = 60.0 / bpm;
        var noteRate = 4.0 / rate;
        var noteSec = quarterNoteSec * noteRate;
        var noteLength = (int) (noteSec * generator.sampleRate);
        var wave = generator.generate(Waves.getHertz(pitch), noteLength, waveGen);
        return new Sound(wave, offset);
    }
}
