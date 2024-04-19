# Flexi-Store-KMP
![Flexi-Store-KMP](https://github.com/KhubaibKhan4/Flexi-Store-KMP/blob/master/assests/screenshots/banner.png)

## Flexi Store KMP is an Ecommerce Platform that is Developed using Compose Multiplatform.
Flexi Store KMP is a Compose Multiplatform Project that uses the Custom Ktor Server as a Backend & Supports Android, iOS, Web & Desktop. Basically, It's an Amazon Clone & Will contains all the features in future including the Seller Central too where users can register and sell their products to earn.

* - User can Buy or Sell Products.
* - User can Make their own Store.
* - Can Review The Product Images, Videos & Old ratings.
* - Can Chat with Seller.

* (Work is still in progress)

Are you generous enought? 
<a href="https://www.buymeacoffee.com/khubaibkhan"><img src="https://img.buymeacoffee.com/button-api/?text=Buy me a coffee&emoji=&slug=khubaibkhan&button_colour=FFDD00&font_colour=000000&font_family=Cookie&outline_colour=000000&coffee_colour=ffffff" /></a>

## Screen Shots

| ![Screenshot 1](https://github.com/KhubaibKhan4/Flexi-Store-KMP/blob/master/assests/screenshots/1.png) | ![Screenshot 2](https://github.com/KhubaibKhan4/Flexi-Store-KMP/blob/master/assests/screenshots/2.png) | ![Screenshot 3](https://github.com/KhubaibKhan4/Flexi-Store-KMP/blob/master/assests/screenshots/3.png) | ![Screenshot 4](https://github.com/KhubaibKhan4/Flexi-Store-KMP/blob/master/assests/screenshots/4.png) | ![Screenshot 5](https://github.com/KhubaibKhan4/Flexi-Store-KMP/blob/master/assests/screenshots/5.png) | ![Screenshot 6](https://github.com/KhubaibKhan4/Flexi-Store-KMP/blob/master/assests/screenshots/6.png) | ![Screenshot 7](https://github.com/KhubaibKhan4/Flexi-Store-KMP/blob/master/assests/screenshots/7.png) | ![Screenshot 8](https://github.com/KhubaibKhan4/Flexi-Store-KMP/blob/master/assests/screenshots/8.png) | ![Screenshot 9](https://github.com/KhubaibKhan4/Flexi-Store-KMP/blob/master/assests/screenshots/9.png) |
| --- | --- | --- | --- | --- | --- | --- | --- | --- |
| ![Screenshot 10](https://github.com/KhubaibKhan4/Flexi-Store-KMP/blob/master/assests/screenshots/10.png) | ![Screenshot 11](https://github.com/KhubaibKhan4/Flexi-Store-KMP/blob/master/assests/screenshots/11.png) | ![Screenshot 12](https://github.com/KhubaibKhan4/Flexi-Store-KMP/blob/master/assests/screenshots/12.png) | ![Screenshot 13](https://github.com/KhubaibKhan4/Flexi-Store-KMP/blob/master/assests/screenshots/13.png) | ![Screenshot 14](https://github.com/KhubaibKhan4/Flexi-Store-KMP/blob/master/assests/screenshots/14.png) | ![Screenshot 15](https://github.com/KhubaibKhan4/Flexi-Store-KMP/blob/master/assests/screenshots/15.png) | ![Screenshot 16](https://github.com/KhubaibKhan4/Flexi-Store-KMP/blob/master/assests/screenshots/16.png) | ![Screenshot 17](https://github.com/KhubaibKhan4/Flexi-Store-KMP/blob/master/assests/screenshots/17.png) | ![Screenshot 18](https://github.com/KhubaibKhan4/Flexi-Store-KMP/blob/master/assests/screenshots/18.png) |


## Desktop Screen Shots

| ![Screenshot 1](https://github.com/KhubaibKhan4/Flexi-Store-KMP/blob/master/assests/screenshots/30.png) | 
| --- |
| ![Screenshot 2](https://github.com/KhubaibKhan4/Flexi-Store-KMP/blob/master/assests/screenshots/31.png) | 
| ![Screenshot 3](https://github.com/KhubaibKhan4/Flexi-Store-KMP/blob/master/assests/screenshots/32.png) |
| ![Screenshot 4](https://github.com/KhubaibKhan4/Flexi-Store-KMP/blob/master/assests/screenshots/33.png) |
| ![Screenshot 5](https://github.com/KhubaibKhan4/Flexi-Store-KMP/blob/master/assests/screenshots/34.png) | 
| ![Screenshot 6](https://github.com/KhubaibKhan4/Flexi-Store-KMP/blob/master/assests/screenshots/35.png) | 
| ![Screenshot 7](https://github.com/KhubaibKhan4/Flexi-Store-KMP/blob/master/assests/screenshots/36.png) | 
| ![Screenshot 8](https://github.com/KhubaibKhan4/Flexi-Store-KMP/blob/master/assests/screenshots/37.png) | 
| ![Screenshot 9](https://github.com/KhubaibKhan4/Flexi-Store-KMP/blob/master/assests/screenshots/38.png) | 
| ![Screenshot 10](https://github.com/KhubaibKhan4/Flexi-Store-KMP/blob/master/assests/screenshots/39.png) | 
| ![Screenshot 11](https://github.com/KhubaibKhan4/Flexi-Store-KMP/blob/master/assests/screenshots/40.png) | 
| ![Screenshot 12](https://github.com/KhubaibKhan4/Flexi-Store-KMP/blob/master/assests/screenshots/41.png) | 
| ![Screenshot 13](https://github.com/KhubaibKhan4/Flexi-Store-KMP/blob/master/assests/screenshots/42.png) | 
| ![Screenshot 14](https://github.com/KhubaibKhan4/Flexi-Store-KMP/blob/master/assests/screenshots/43.png) | 

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

