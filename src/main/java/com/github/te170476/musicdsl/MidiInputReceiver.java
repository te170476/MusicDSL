package com.github.te170476.musicdsl;
import javax.sound.midi.*;
import javax.sound.midi.Receiver;
import javax.sound.sampled.AudioFormat;
import java.util.Arrays;
import java.util.List;

public class MidiInputReceiver implements Receiver {
    public String name;
    public MidiInputReceiver(String name){
        this.name = name;
        System.out.println("midi connect");
    }

    public void send(MidiMessage message, long timeStamp){
        int sampleRate = 44100;
        AudioFormat format = new AudioFormat(sampleRate, 8, 1, true, false);
        var player = new Player(format);
        var generator = new Generator(sampleRate);


        if(message instanceof ShortMessage){
            ShortMessage sm = ((ShortMessage)message);
            if(sm.getCommand() == 128){
                return;
            }

            System.out.println(sm.getCommand());
            System.out.println(sm.getData1());
            System.out.println(sm.getData2());
            List<Sound> sounds = Arrays.asList(
                    new Sound(generator.sin(generator.getHertz(sm.getData1()-57), 0.25), 0)
            );
            player.play(sounds);
        }
        System.out.println("midi received");
    }

    public void close(){}

}
