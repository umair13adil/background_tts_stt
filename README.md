# background_tts_stt
An app that runs Text-to-Speech & Speech-to-Text in background.

This app uses Stacked Architecture for MVVM & State Management.
This architecture is provided by [FilledStacks](https://www.filledstacks.com/post/flutter-and-provider-architecture-using-stacked/)

# Dependencies:

- stacked
- auto_route
- get_it
- injectable
- flutter_tts

## IMPORTANT: 
After checkout, run this command in terminal to auto generate files:

 ```yaml
  flutter pub run build_runner build
```

or in case of build conflicts

 ```yaml
flutter packages pub run build_runner build --delete-conflicting-outputs
```

# Speech to Text Configuration

Speech to text is configured in Android module. Speech listening is dependent on these two values:

1. Stop Listening Delay [Seconds before service should stop listening]
2. Transition Delay [Seconds before service should start listening again]

These can be configured inside Android module:

```kotlin
    class MainApplication : FlutterApplication(){
        override fun onCreate() {
            super.onCreate()
            Speech.init(this, packageName, 10000L, 1200L)
        }
    }
```

# Speech to Text Library Credit

This project uses *Speech-Recognizer* library developed by [sachinvarma](https://github.com/sachinvarma/Speech-Recognizer) for background speech listening services.


## Getting Started

This project is a starting point for a Flutter application.

A few resources to get you started if this is your first Flutter project:

- [Lab: Write your first Flutter app](https://flutter.dev/docs/get-started/codelab)
- [Cookbook: Useful Flutter samples](https://flutter.dev/docs/cookbook)

For help getting started with Flutter, view our
[online documentation](https://flutter.dev/docs), which offers tutorials,
samples, guidance on mobile development, and a full API reference.
