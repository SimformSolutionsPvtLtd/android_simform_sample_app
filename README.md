<a href="https://www.simform.com/"><img src="https://github.com/SimformSolutionsPvtLtd/SSToastMessage/blob/master/simformBanner.png"></a>
# Sample Android App

[![Kotlin Version](https://img.shields.io/badge/Kotlin-v1.8.0-blue.svg)](https://kotlinlang.org)
[![Platform](https://img.shields.io/badge/Platform-Android-green.svg?style=flat)](https://www.android.com/)
[![API](https://img.shields.io/badge/API-24%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=24)

### The sample app uses Github's GraphQL APIs to query the Simform's Github repositories and list them using compose views in the Android application.

## Tech stack

* [Android SDK][android-home]

* [Jetpack Compose][jetpack-compose]

* [GraphQL][graphql-home]

* [Apollo GraphQL][apollo-graphql-home]

* [Github GraphQL][github-graphql-api]

* [Pagination][github-pagination]

## Architecture

* MVVM

## Building the sample

First, clone the repo:

```bash
git clone https://github.com/SimformSolutionsPvtLtd/android_simform_sample_app
```

### Android Studio (Android Studio Electric Eel | 2022.1.1 - Recommended)

* Open Android Studio and select `File -> Open...` or from the Android Launcher select `Oepn` and navigate to the root directory of cloned repo and click `open`
* GitHub PAT
    * Create [GitHub PAT](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/creating-a-personal-access-token) with `public_repo` access
    * Put the PAT in project level `local.properties` with key `GITHUB_TOKEN` as below format
        * `GITHUB_TOKEN="TOKEN"`
* A Gradle sync should start, but you can force a sync and build the 'app' module as needed.

## Running the Sample App

Connect an Android device to your development machine or use android emulator provided by Andorid studio

### Android Studio

* Select the device you wish to run the app on and hit run

### Output screenshots

| Light mode | Dark mode |
|--|--|
| ![alt text](https://raw.githubusercontent.com/SimformSolutionsPvtLtd/android_simform_sample_app/master/readmeresources/outputLightMode.png) | ![alt text](https://raw.githubusercontent.com/SimformSolutionsPvtLtd/android_simform_sample_app/master/readmeresources/outputDarkMode.png) |

[//]: # (These are reference links used in the body of this note and get stripped out when the markdown processor does its job. Thanks SO - http://stackoverflow.com/questions/4823468/store-comments-in-markdown-syntax)

   [apollo-graphql-home]:                           <https://www.apollographql.com/>
   [android-home]:                                  <https://www.android.com/>
   [graphql-home]:                                  <https://graphql.org/>
   [github-graphql-api]:                            <https://docs.github.com/en/graphql>
   [jetpack-compose]:                               <https://developer.android.com/jetpack/compose>
   [github-pagination]:                             <https://medium.com/simform-engineering/list-view-with-pagination-using-jetpack-compose-e131174eac8e>
