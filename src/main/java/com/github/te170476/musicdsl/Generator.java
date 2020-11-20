package com.github.te170476.musicdsl;

public class Generator {
    double pitchReference = 440.0;
    int amplitude = 127;
    int sampleRate;
    public Generator(int sampleRate) {
        this.sampleRate = sampleRate;
    }

    public double getHertz(int pitch) {
        return pitchReference * Math.pow(2, pitch / 12.0);
    }
    public byte[] sin(double hertz, double second){
        int length = (int)(sampleRate * second);
        byte[] wave = new byte[length];
        double byteParAmplitude = sampleRate / hertz;
        for(int index = 0; index < wave.length; index++){
            wave[index] = (byte) (amplitude * Math.sin((index / byteParAmplitude) * Math.PI * 2.0));
        }
        return wave;
    }
    public byte[] saw(double hertz, double second){
        int length = (int)(sampleRate * second);
        byte[] wave = new byte[length];
        double byteParAmplitude = sampleRate / hertz;
        for(int index = 0; index < wave.length; index++){
            wave[index] = (byte) ((amplitude * 2) * ((index / byteParAmplitude) % 1.0));
        }
        return wave;
    }
    public byte[] square(double hertz, double second){
        int length = (int)(sampleRate * second);
        byte[] wave = new byte[length];
        double byteParAmplitude = sampleRate / hertz;
        for(int index = 0; index < wave.length; index++){
            int count = (int) (index / (byteParAmplitude / 2));
            if (count % 2 == 0)
                wave[index] = Byte.MAX_VALUE;
            else
                wave[index] = Byte.MIN_VALUE;
        }
        return wave;
    }
}
