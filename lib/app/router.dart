import 'package:auto_route/auto_route_annotations.dart';
import 'package:background_tts_stt/ui/views/home/home_view.dart';
import 'package:background_tts_stt/ui/views/startup/startup_view.dart';

@MaterialAutoRouter()
class $Router {
  @initial
  StartupView startupViewRoute;

  HomeView homeViewRoute;
}
