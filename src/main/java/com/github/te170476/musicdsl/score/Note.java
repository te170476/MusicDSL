package com.github.te170476.musicdsl.score;

import com.github.te170476.musicdsl.Sound;
import com.github.te170476.musicdsl.score.note.INoteValue;
import com.github.te170476.musicdsl.sound.Waves;
import com.github.te170476.musicdsl.sound.generator.IWaveGenerator;
import com.github.te170476.musicdsl.sound.generator.WaveGenerator;

public class Note{
    public int pitch;
    public INoteValue noteValue;
    public Note(int pitch, INoteValue noteValue){
        this.pitch = pitch;
        this.noteValue = noteValue;
    }
    public Sound toSound(WaveGenerator generator, Tempo tempo, INoteValue offset, IWaveGenerator waveGen) {
        var offsetSec = tempo.toSecParNoteValue() * offset.toPercentage();
        var offsetLength = (int)(offsetSec * generator.sampleRate);
        var noteSec = tempo.toSecParNoteValue() * noteValue.toPercentage();
        var noteLength = (int) (noteSec * generator.sampleRate);
        var wave = generator.generate(Waves.getHertz(pitch), noteLength, waveGen);
        return new Sound(wave, offsetLength);
    }
}
