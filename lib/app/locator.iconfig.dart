// GENERATED CODE - DO NOT MODIFY BY HAND

// **************************************************************************
// InjectableConfigGenerator
// **************************************************************************

import 'package:background_tts_stt/services/third_party_services_module.dart';
import 'package:stacked_services/stacked_services.dart';
import 'package:background_tts_stt/services/speech_listener_service.dart';
import 'package:background_tts_stt/services/text_to_speech_service.dart';
import 'package:get_it/get_it.dart';

void $initGetIt(GetIt g, {String environment}) {
  final thirdPartyServicesModule = _$ThirdPartyServicesModule();
  g.registerLazySingleton<DialogService>(
      () => thirdPartyServicesModule.dialogService);
  g.registerLazySingleton<NavigationService>(
      () => thirdPartyServicesModule.navigationService);
  g.registerLazySingleton<SpeechListenerService>(
      () => thirdPartyServicesModule.speechService);
  g.registerLazySingleton<TextToSpeechService>(
      () => thirdPartyServicesModule.ttsService);
}

class _$ThirdPartyServicesModule extends ThirdPartyServicesModule {
  @override
  DialogService get dialogService => DialogService();
  @override
  NavigationService get navigationService => NavigationService();
  @override
  SpeechListenerService get speechService => SpeechListenerService();
  @override
  TextToSpeechService get ttsService => TextToSpeechService();
}
