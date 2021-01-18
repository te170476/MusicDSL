package com.github.te170476.musicdsl.midi;

import com.github.te170476.musicdsl.sound.Converter;
import com.github.te170476.musicdsl.Player;
import com.github.te170476.musicdsl.Sound;
import com.github.te170476.musicdsl.sound.Waves;
import com.github.te170476.musicdsl.sound.generator.WaveGenerator;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Transmitter;
import javax.sound.sampled.*;
import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MidiInputter {
    private final CopyOnWriteArraySet<MidiInput> onPressMidiKeySet = new CopyOnWriteArraySet<>();
    private final AudioFormat format;

    public static Optional<MidiInputter> open(AudioFormat format, MidiDevice device) {
        try {
            device.open();
        } catch (MidiUnavailableException e) {
            return Optional.empty();
        }
        return getTransmitter(device)
                .map(it-> new MidiInputter(device, format, it));
    }
    private MidiInputter(MidiDevice device, AudioFormat format, Transmitter transmitter) {
        transmitter.setReceiver(new MidiInputReceiver(device.toString(), onPressMidiKeySet));
        this.format = format;
    }

    public static List<MidiDevice> getMidiDevices() {
        return Arrays.stream(MidiSystem.getMidiDeviceInfo())
                .map(MidiInputter::getMidiDeviceFrom)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(it-> getTransmitter(it).isPresent())
                .collect(Collectors.toList());
    }
    private static Optional<MidiDevice> getMidiDeviceFrom(MidiDevice.Info info) {
        try {
            return Optional.of(MidiSystem.getMidiDevice(info));
        } catch (MidiUnavailableException e) {
            return Optional.empty();
        }
    }
    private static Optional<Transmitter> getTransmitter(MidiDevice device) {
        try {
            return Optional.of(device.getTransmitter());
        } catch (MidiUnavailableException e) {
            return Optional.empty();
        }
    }

    public void play() {
        var sampleRate = (int) format.getSampleRate();
        var sampleSize = format.getSampleSizeInBits();
        var channelCount = format.getChannels();
        var bufferMagnification = 64;
        var bufferSize = sampleSize * channelCount * bufferMagnification;
        var generator = new WaveGenerator(sampleRate);
        var player = Player.open(format, bufferSize).get();

        var offsetMap = new HashMap<Integer, Integer>();
        while (true) {
            if(onPressMidiKeySet.size() <= 0) {
                offsetMap.clear();
                player.dataLine.drain();
                continue;
            }
            var waves =
                    onPressMidiKeySet.stream()
                            .map(midiInput-> {
                                var keyCode = midiInput.keyCode;
                                var strength = midiInput.strength;
                                if (!offsetMap.containsKey(keyCode))
                                    offsetMap.put(keyCode, 0);
                                var pitch = keyCode - 57;
                                var hertz = Waves.getHertz(pitch);
                                var offset = offsetMap.get(keyCode);
                                offsetMap.put(keyCode, offset + bufferSize);
                                return generator.generate(hertz, bufferSize, offset, Waves.triangle, strength);
                            });
            byte[] wave = new byte[]{};
            for (byte[] it : waves.collect(Collectors.toList())){
                wave = Converter.merge(wave, new Sound(it, 0));
            }
            player.play(wave);
        }
    }

    public static void main(String[] args) {
        IntStream.range(0, 128)
                .forEach(it->
                        System.out.println("strength: "+ it +" _ "+ 20 *  Math.log10(it / 127.0)));

        var devices = getMidiDevices();
        for (int index = 0; index < devices.size(); index++) {
            System.out.println(index +": "+ devices.get(index).getDeviceInfo());
        }
        System.out.println("plz select midi device index: ");
        Scanner scan  = new Scanner(System.in);
        int deviceIndex = scan.nextInt();
        var device = devices.get(deviceIndex);

        var sampleRate = 44100;
        var sampleSize = 8;
        var channelCount = 1;
        var format = new AudioFormat(sampleRate, sampleSize, channelCount, true, false);
        MidiInputter.open(format, device)
            .ifPresent(MidiInputter::play);
    }
}
