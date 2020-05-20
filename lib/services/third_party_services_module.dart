import 'package:background_tts_stt/services/speech_listener_service.dart';
import 'package:background_tts_stt/services/text_to_speech_service.dart';
import 'package:injectable/injectable.dart';
import 'package:stacked_services/stacked_services.dart';

@module
abstract class ThirdPartyServicesModule {
  @lazySingleton
  NavigationService get navigationService;

  @lazySingleton
  DialogService get dialogService;

  @lazySingleton
  SpeechListenerService get speechService;

  @lazySingleton
  TextToSpeechService get ttsService;
}
