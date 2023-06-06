package org.voiceAssistant.recogizer;

import org.vosk.Model;
import org.vosk.Recognizer;

import javax.sound.sampled.TargetDataLine;
import java.io.IOException;

public class Recogniser implements RecognizerInterface{

    String input;
    String path = Recogniser.class.getClassLoader().getResource("models/vosk-model-small-en-us-0.15").getPath().substring(1).replace("/", "\\");
    boolean shouldStop = false;

    Model model = new Model(path);
    Recognizer recognizer = new Recognizer(model, 16000);

    public Recogniser() throws IOException {
    }

    public String recognize(TargetDataLine microphone, byte[] b, int numBytesRead) {

        if(recognizer.acceptWaveForm(b, numBytesRead)){
            input = wordFormat(recognizer.getFinalResult());
            System.out.println(input);
            switch (input){
                case "stop": stop(); break;
                case "": break;
                default: return input;
            }
        }
        return "";
    }


    @Override
    public boolean isShouldStop() {
        return shouldStop;
    }

    private void stop(){
        shouldStop = true;
    }

    private String wordFormat(String word){
        return word.substring(14, word.length() - 3);
    }
}
