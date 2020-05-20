import 'package:background_tts_stt/models/speech_result.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:stacked/stacked.dart';

import 'home_viewmodel.dart';

class HomeView extends StatelessWidget {

  @override
  Widget build(BuildContext context) {
    return ViewModelBuilder<HomeViewModel>.reactive(
      builder: (context, model, child) => Scaffold(
        body: Center(
          child: Text(model?.speechResult?.result ?? "No Results"),
        ),
      ),
      viewModelBuilder: () => HomeViewModel(),
    );
  }
}
