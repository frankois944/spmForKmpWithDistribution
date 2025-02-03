# spmForKmp For KMP : a distribution example

This repository shows a way to distribute KMP library for iOS / Android / ... using the plugin [smpForKmp](https://github.com/frankois944/spm4Kmp).

The distributed library contains a compose multiplatform component for iOS and Android, each component has a native video Player.

For each :
- iOS : [KSPlayer](https://github.com/kingslay/KSPlayer)
- Android : [Exoplayer](https://github.com/google/ExoPlayer)
  
The main issue here is that the end user of this library **needs to add KSPlayer Swift Package to his Xcode project**, but that's all; it's only used for the linking step of the Apple platform.

How to Test:

Run on the repository root the Gradle task for a local deployment of the library.

`./gradlew publishToMavenLocal --no-configuration-cache`

Then open with your editor the Sample directory for testing the library on iOS and Android.
