import 'dart:async';
import 'dart:convert';

import 'package:background_tts_stt/models/speech_result.dart';
import 'package:flutter/services.dart';

class SpeechListenerService {
  var _tag = "SpeechListenerService";
  var _channel = const MethodChannel('speech_listener_channel');
  var _eventChannel = EventChannel('speech_listener_stream');

  bool _running = false;
  SpeechResult _speechResultSaved = SpeechResult();

  Stream<SpeechResult> get speechResult => _speechListenerController.stream;

  StreamController<SpeechResult> _speechListenerController =
      StreamController<SpeechResult>();

  Future<String> get startSpeechListenService async {
    final String result = await _channel.invokeMethod('startService');
    print('[$_tag] Received: $result');
    return result;
  }

  Future<String> get stopSpeechListenService async {
    _stopSpeechListener();
    final String result = await _channel.invokeMethod('stopService');
    print('[$_tag] Received: $result');
    return result;
  }

  void getSpeechResults() async {
    _running = true;
    while (_running) {
      _eventChannel.receiveBroadcastStream().listen((dynamic event) {
        print('[$_tag] Received: $event');
        Map result = jsonDecode(event);
        _speechResultSaved = SpeechResult.fromJson(result);
        _speechListenerController.add(_speechResultSaved);
      }, onError: (dynamic error) {
        print('[$_tag] Received error: ${error.message}');
      });
      await Future.delayed(Duration(seconds: 5));
    }
  }

  void _stopSpeechListener() {
    _running = false;
  }
}
