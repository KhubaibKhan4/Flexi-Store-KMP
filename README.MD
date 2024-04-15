# Flexi-Store-KMP
## Flexi Store KMP is an Ecommerce Platform that is Developed using Compose Multiplatform.
Flexi Store KMP is a Compose Multiplatform Project that uses the Custom Ktor Server as a Backend & Supports Android, iOS, Web & Desktop. Basically, It's an Amazon Clone & Will contains all the features in future including the Seller Central too where users can register and sell their products to earn.

* - User can Buy or Sell Products.
* - User can Make their own Store.
* - Can Review The Product Images, Videos & Old ratings.
* - Can Chat with Seller.

* (Work is still in progress)


## Before running!
 - check your system with [KDoctor](https://github.com/Kotlin/kdoctor)
 - install JDK 17 or higher on your machine
 - add `local.properties` file to the project root and set a path to Android SDK there

### Android
To run the application on android device/emulator:  
 - open project in Android Studio and run imported android run configuration

To build the application bundle:
 - run `./gradlew :composeApp:assembleDebug`
 - find `.apk` file in `composeApp/build/outputs/apk/debug/composeApp-debug.apk`
Run android simulator UI tests: `./gradlew :composeApp:pixel5Check`

### Desktop
Run the desktop application: `./gradlew :composeApp:run`
Run desktop UI tests: `./gradlew :composeApp:jvmTest`

### iOS
To run the application on iPhone device/simulator:
 - Open `iosApp/iosApp.xcproject` in Xcode and run standard configuration
 - Or use [Kotlin Multiplatform Mobile plugin](https://plugins.jetbrains.com/plugin/14936-kotlin-multiplatform-mobile) for Android Studio
Run iOS simulator UI tests: `./gradlew :composeApp:iosSimulatorArm64Test`

### Experimental Browser (JS)
Run the browser application: `./gradlew :composeApp:jsBrowserDevelopmentRun --continue`
Run browser UI tests: `./gradlew :composeApp:jsBrowserTest`

