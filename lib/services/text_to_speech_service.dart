import 'package:flutter_tts/flutter_tts.dart';

class TextToSpeechService {
  var _tag = "TextToSpeechService";

  FlutterTts flutterTts = FlutterTts()
    ..setLanguage("en-US")
    ..setSpeechRate(1.0)
    ..setVolume(1.0);

  Future speak(String words) async {
    await flutterTts.speak(words);
  }

  Future stopSpeaking() async {
    await flutterTts.stop();
  }
}
