# VoiceAssistant
Voice assistant with OpenAi
The program is designed for voice communication with OpenAi models such as chatGPT-3

The voice assistant uses the English model (vosk-model-small-en-us-0.15) from Vosk to recognize spoken words. For a list of models, see https://www.voicerss.org/.
The answer uses the gpt-3.5-turbo model from OpenAi - https://platform.openai.com/ after this api from Voice RSS - https://www.voicerss.org/api/ voices the answer coming

How to use
Before startup you need to add your API key from OpenAi (https://platform.openai.com/) and API key from Voice RSS (https://www.voicerss.org/api/) to startup parameters. Once launched, the program will listen to everything you say and send recognized sentences to the OpenAi model for processing. It will not react to pissing, and will stop its execution if it hears "stop".
After sending a message, the OpenAi model will take some time to respond, up to 20 seconds. If it fails to respond within this time an automatic "Sorry, there wasn't enough time to respond. Try again." and you can ask the question again.
