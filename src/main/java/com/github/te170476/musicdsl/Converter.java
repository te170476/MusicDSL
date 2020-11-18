package com.github.te170476.musicdsl;

public class Converter {
    public static byte[] merge(byte[] wave1, Sound sound){
        byte[] wave2 = sound.wave;
        int offset = sound.offset;
        long frameLength = Math.max(wave1.length, wave2.length + offset);
        byte[] out = new byte[(int) frameLength];
        for (int index=0; index<out.length; index++){
            boolean wave1Exists = index < wave1.length;
            boolean wave2Exists = index < (wave2.length + offset) && index > offset;
            byte byte1 = wave1Exists ? wave1[index] : 0;
            byte byte2 = wave2Exists ? wave2[index - offset] : 0;
            int merged = byte1 + byte2;
            if (wave1Exists && wave2Exists)
                merged = merged >> 1;
            out[index] = (byte) merged;
        }
        return out;
    }
}
