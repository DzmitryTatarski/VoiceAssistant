package org.voiceAssistant.recogizer;

import javax.sound.sampled.TargetDataLine;

public interface RecognizerInterface {

    String recognize(TargetDataLine microphone, byte[] b, int numBytesRead);

    boolean isShouldStop();
}
