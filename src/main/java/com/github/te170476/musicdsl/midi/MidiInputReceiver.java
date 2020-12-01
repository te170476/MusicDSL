package com.github.te170476.musicdsl.midi;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import java.util.Set;

public class MidiInputReceiver implements Receiver {
    public final String name;
    private final Set<Integer> onPressKeySet;
    public MidiInputReceiver(String name, Set<Integer> onPressKeySet){
        this.name = name;
        this.onPressKeySet = onPressKeySet;
        System.out.println("midi connect");
    }

    @Override
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
    }
    public void onPress(int key) {
        onPressKeySet.add(key);
    }
    public void onRelease(int key) {
        onPressKeySet.remove(key);
    }

    @Override
    public void close(){}
}
