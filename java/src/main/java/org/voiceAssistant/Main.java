package org.voiceAssistant;

import org.voiceAssistant.ai.VoiceAssistant;
import org.voiceAssistant.ai.VoiceAssistantInterface;

public class Main {

    public static void main(String[] args) throws Exception {

        VoiceAssistantInterface voiceAssistant = new VoiceAssistant(args[0], args[1]);
        voiceAssistant.start();
    }
}
