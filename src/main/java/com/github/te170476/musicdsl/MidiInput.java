package com.github.te170476.musicdsl;

import javax.sound.sampled.*;
import java.lang.Math;
import java.io.*;

import java.util.ArrayList;
import javax.sound.midi.*;
import java.util.Arrays;
import java.util.Scanner;

public class MidiInput{
    static final int SAMPLE_RATE=44100;
    static byte[] data = new byte[4];

//-----------------------------------------------

    public static void main(String[] args) throws MidiUnavailableException {
        var devices = MidiSystem.getMidiDeviceInfo();
        for (int i = 0; i < devices.length; i++) {
            System.out.println(i +": "+ devices[i]);
        }

        System.out.println("plz select midi device index: ");
        Scanner scan  = new Scanner(System.in);
        int DEVICE_IN = scan.nextInt();

        MidiDevice.Info device_info = devices[DEVICE_IN];
        MidiDevice device_input = MidiSystem.getMidiDevice(device_info);

        try{
            Transmitter trans = device_input.getTransmitter();

            trans.setReceiver(new MidiInputReceiver(device_info.toString()));

            device_input.open();
        }catch (MidiUnavailableException e){
            System.err.println(e.getMessage());
            System.exit(0);
        }

        try{
            AudioFormat fmt = new AudioFormat(SAMPLE_RATE, 8 , 1, true, false);
            SourceDataLine audio = (SourceDataLine)AudioSystem.getSourceDataLine(fmt);
                    audio.open(fmt);
                    audio.start();

                    while(true){
                        for(int i=0; i<4; i++){

                        }
                        audio.write(data, 0, 4);

                    }
        }catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }
    }
}
