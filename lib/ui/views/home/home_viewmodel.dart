import 'dart:async';
import 'package:background_tts_stt/app/locator.dart';
import 'package:background_tts_stt/models/speech_result.dart';
import 'package:background_tts_stt/services/speech_listener_service.dart';
import 'package:stacked/stacked.dart';

class HomeViewModel extends StreamViewModel<SpeechResult> {
  String _tag = 'HomeViewModel';
  String _title = 'HomeView';

  String get title => _title;

  SpeechResult get speechResult => data;

  var _service = locator<SpeechListenerService>();

  @override
  void initialise() {
    print("[$_tag] initialise");
    _service.startSpeechListenService;
    _service.getSpeechResults();
    super.initialise();
  }

  @override
  void onSubscribed() {
    print("[$_tag] onSubscribed");
    super.onSubscribed();
  }

  @override
  void onError(error) {
    print("[$_tag] onError: $error");
    super.onError(error);
  }

  @override
  void onCancel() {
    _service.stopSpeechListenService;
    print("[$_tag] onCancel");
    super.onCancel();
  }

  @override
  void onData(SpeechResult data) {
    print("[$_tag] onData: ${data.toString()}");
    super.onData(data);
  }

  @override
  Stream<SpeechResult> get stream => _service.speechResult;
}
