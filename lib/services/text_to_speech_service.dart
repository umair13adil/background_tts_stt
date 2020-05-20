import 'package:flutter_tts/flutter_tts.dart';

class TextToSpeechService {
  var _tag = "TextToSpeechService";
  FlutterTts _flutterTts;

  TextToSpeechService() {
    print("[$_tag] Text-to-Speech initialized.");

    _flutterTts = FlutterTts()
      ..setLanguage("en-US")
      ..setSpeechRate(1.0)
      ..setVolume(1.0);
  }

  Future speak(String words) async {
    await _flutterTts
        ?.setVolume(1.0)
        ?.then((value) => _flutterTts?.speak(words));
  }

  Future stopSpeaking() async {
    await _flutterTts?.stop();
  }
}
