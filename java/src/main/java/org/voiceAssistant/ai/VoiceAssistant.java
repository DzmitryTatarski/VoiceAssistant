package org.voiceAssistant.ai;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import org.voiceAssistant.recogizer.Recogniser;
import org.voiceAssistant.recogizer.RecognizerInterface;
import org.voiceAssistant.speaker.Speaker;
import org.voiceAssistant.speaker.SpeakerInterface;

import javax.sound.sampled.*;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class VoiceAssistant implements VoiceAssistantInterface {

    OpenAiService service;
    AudioFormat format = new AudioFormat(16000f, 16, 1, true, false);
    DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
    TargetDataLine microphone;
    List<ChatMessage> messages = new ArrayList<>();
    ChatCompletionRequest completionRequest = ChatCompletionRequest.builder()
            .messages(messages)
            .model("gpt-3.5-turbo")
            .build();
    String voicerssApiKey;
    String input;
    SpeakerInterface speaker = new Speaker();

    int numBytesRead;
    int CHUNK_SIZE = 4096;
    int bytesRead = 0;

    byte[] b = new byte[4096];

    RecognizerInterface recognizer = new Recogniser();

    public VoiceAssistant(String OpanAiApiKey, String voicerssApiKey) throws IOException {
        service = new OpenAiService(OpanAiApiKey, Duration.ofSeconds(20));
        this.voicerssApiKey = voicerssApiKey;
    }

    public void start() throws LineUnavailableException{
        microphone = (TargetDataLine) AudioSystem.getLine(info);
        microphone.open(format);
        microphone.start();

        while(!recognizer.isShouldStop() && bytesRead <= 100000000){
            bytesRead = 0;
            numBytesRead = microphone.read(b, 0, CHUNK_SIZE);

            bytesRead += numBytesRead;
            input = recognizer.recognize(microphone, b, numBytesRead);
            if (!input.equals("")){
                messages.add(new ChatMessage("user", input));
                try {
                    service.createChatCompletion(completionRequest)
                            .getChoices()
                            .forEach(choice -> {speaker.speak(choice.getMessage().getContent(), voicerssApiKey);
                                System.out.println(choice.getMessage().getContent());});
                }catch (Exception e){
                    System.out.println("Sorry, there wasn't enough time to respond. Try again.");
                    Speaker.voiceFile(Recogniser.class.getClassLoader()
                            .getResource("errorAnswer.wav")
                            .getPath().substring(1).replace("/", "\\"));
                }
            }
        }
        microphone.close();
    }
}
