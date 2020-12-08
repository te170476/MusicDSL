package com.github.te170476.musicdsl.midi;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import java.util.Set;

public class MidiInputReceiver implements Receiver {

    public final String name;
    private final Set<MidiInput> onPressKeySet;
    public MidiInputReceiver(String name, Set<MidiInput> onPressKeySet){
        this.name = name;
        this.onPressKeySet = onPressKeySet;
        System.out.println("midi connect");
    }

    @Override
    public void send(MidiMessage message, long timeStamp){
        if(!(message instanceof ShortMessage)) return;

        ShortMessage shortMessage = ((ShortMessage)message);
        System.out.println(shortMessage.getCommand());
        var command = shortMessage.getCommand();
        var keyCode = shortMessage.getData1();
        var strength = shortMessage.getData2();
        System.out.println(keyCode);
        System.out.println(strength);
        var input = new MidiInput(keyCode, strength);
        switch (command) {
            case 144:
                onPress(input);
                break;
            case 128:
                onRelease(input);
                break;
            default:
                break;
        }
        System.out.println("midi received");
    }
    public void onPress(MidiInput input) {
        onPressKeySet.add(input);
    }
    public void onRelease(MidiInput input) {
        onPressKeySet.remove(input);
    }

    @Override
    public void close(){}
}
