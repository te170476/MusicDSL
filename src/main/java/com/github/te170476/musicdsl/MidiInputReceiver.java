package com.github.te170476.musicdsl;
import com.github.te170476.musicdsl.sound.generator.WaveGenerator;

import javax.sound.midi.*;
import javax.sound.midi.Receiver;
import javax.sound.sampled.*;
import java.util.Optional;
import java.util.Set;

public class MidiInputReceiver implements Receiver {
    public final String name;
    private final Set<Integer> onPressKeySet;
    private final int sampleRate;
    private final WaveGenerator generator;
    private final SourceDataLine dataLine;
    public MidiInputReceiver(String name, Set<Integer> onPressKeySet){
        this.name = name;
        this.onPressKeySet = onPressKeySet;
        this.sampleRate = 44100;
        this.generator = new WaveGenerator(sampleRate);
        AudioFormat format = new AudioFormat(sampleRate, 8, 1, true, false);
        this.dataLine = createSourceDataLine(format).get();
        System.out.println("midi connect");
    }

    public void send(MidiMessage message, long timeStamp){
        if(!(message instanceof ShortMessage)) return;

        ShortMessage shortMessage = ((ShortMessage)message);
        System.out.println(shortMessage.getCommand());
        System.out.println(shortMessage.getData1());
        System.out.println(shortMessage.getData2());
        var keyNumber = shortMessage.getData1();
        switch (shortMessage.getCommand()) {
            case 144:
                onPress(keyNumber);
                break;
            case 128:
                onRelease(keyNumber);
                break;
            default:
                break;
        }
        System.out.println("midi received");
//            List<Sound> sounds = Arrays.asList(
//                    new Sound(generator.generate(Waves.getHertz(shortMessage.getData1()-57), sampleRate / 4, Waves.sin), 0)
//            );
//            player.play(sounds);
    }
    public void onPress(int key) {
        onPressKeySet.add(key);
    }
    public void onRelease(int key) {
        onPressKeySet.remove(key);
    }

    public void close(){}

    private Optional<SourceDataLine> createSourceDataLine(AudioFormat format) {
        try {
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
            if (!(AudioSystem.isLineSupported(info)))
                return Optional.empty();
            return Optional.of((SourceDataLine) AudioSystem.getLine(info));
        } catch (LineUnavailableException ignored) {}
        return Optional.empty();
    }

}
