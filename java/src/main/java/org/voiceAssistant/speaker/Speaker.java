package org.voiceAssistant.speaker;

import com.voicerss.tts.*;
import com.voicerss.tts.AudioFormat;

import javax.sound.sampled.*;
import java.io.*;

public class Speaker implements SpeakerInterface{

    public void speak(String sentence, String apiKey){
        VoiceProvider tts = new VoiceProvider(apiKey);

        VoiceParameters params = new VoiceParameters(sentence, Languages.English_GreatBritain);
        params.setCodec(AudioCodec.WAV);
        params.setFormat(AudioFormat.Format_44KHZ.AF_44khz_16bit_stereo);
        params.setBase64(false);
        params.setSSML(false);
        params.setRate(0);

        byte[] voice = new byte[0];
        try {
            voice = tts.speech(params);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(voice);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bis);

            javax.sound.sampled.AudioFormat audioFormat = audioInputStream.getFormat();
            SourceDataLine line = AudioSystem.getSourceDataLine(audioFormat);
            line.open(audioFormat);
            line.start();

            int bufferSize = 4096;
            byte[] buffer = new byte[bufferSize];
            int bytesRead = 0;

            while ((bytesRead = audioInputStream.read(buffer, 0, buffer.length)) != -1) {
                line.write(buffer, 0, bytesRead);
            }
            line.drain();
            line.stop();
            line.close();
            audioInputStream.close();

        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }

    public static void voiceFile(String filePath){
        try {
            File audioFile = new File(filePath);

            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);

            javax.sound.sampled.AudioFormat audioFormat = audioInputStream.getFormat();

            DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
            SourceDataLine sourceLine = (SourceDataLine) AudioSystem.getLine(info);
            sourceLine.open(audioFormat);

            sourceLine.start();

            byte[] buffer = new byte[4096];
            int bytesRead = 0;

            while ((bytesRead = audioInputStream.read(buffer)) != -1) {
                sourceLine.write(buffer, 0, bytesRead);
            }

            sourceLine.drain();
            sourceLine.close();

        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
