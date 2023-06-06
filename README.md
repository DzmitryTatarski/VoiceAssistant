# VoiceAssistant
Voice assistant with OpenAi
The program is designed for voice communication with OpenAi models such as chatGPT-3

  The voice assistant uses the English model (vosk-model-small-en-us-0.15) from Vosk to recognize spoken words. For a list of models, see https://www.voicerss.org/.
The answer uses the gpt-3.5-turbo model from OpenAi - https://platform.openai.com/ after this api from Voice RSS - https://www.voicerss.org/api/ voices the answer coming

# How to use:

  Before start it is necessary to add to start parameters its API key from OpenAi (https://platform.openai.com/) and the API key from Voice RSS (https://www.voicerss.org/api/). Also you may need to add the library "voicerss_tts.jar" which is in "src/main/resources/voicerss_tts_java/voicerss_tts.jar" in project structure. 
  Once launched, the program will listen to everything you say and send the recognized sentences to the OpenAi model for processing. It will not react to pissing, and will stop its execution if it hears "stop".
  After sending a message, the OpenAi model will take some time to respond, up to 20 seconds. If it fails to respond within this time an automatic "Sorry, there wasn't enough time to respond. Try again." and you can ask the question again.
