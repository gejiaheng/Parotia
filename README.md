# Parotia (WIP 🔧️)

## Intro
This is an Android app made for [Unsplash](https://unsplash.com). It incorporates some best practices and modern architecture to Android development. 

Please don't try to publish this app on your behalf, as replicating core user experience of Unsplash including making an unofficial Unsplash for Android app violates the [guideline](https://help.unsplash.com/en/articles/2511257-guideline-replicating-unsplash).

## Android Development
This app is intended to be a playground for modern Android development. It
- Entirely written in [Kotlin](https://kotlinlang.org).
- Uses [Kotlin Coroutines](https://kotlinlang.org/docs/reference/coroutines/coroutines-guide.html) to handle asynchoronous programming.
- Uses [Hilt](https://dagger.dev/hilt/) for easier dependency injection.
- Uses some of the [Architecture Components](https://developer.android.com/topic/libraries/architecture/) which are listed below.
- Inplements a MVVM architecture.
- Inplements a Clean architecture.

It has the following notable dependencies
- Material Design Components
- ConstraintLayout
- Databinding
- Hilt
- Navigation
- Lifecycle
- ViewModel
- LiveData
- Coroutines

## Build
You have to have **access key** and **secret key** from Unsplash to build the project. Please apply for your keys on [Unsplash Developers](https://unsplash.com/developers).

To avoid committing keys to the repo, put your keys in your local global gradle.properties file which should be located at *~/.gradle/gradle.properties* on Mac. You can put the keys in the project's gradle.properties file as long as you don't commit them.
```
unsplash_access_key=xxxxx
unsplash_secret_key=xxxxx
```

## Test

## License
    Copyright 2020 Jiaheng Ge (gejiaheng@gmail.com)

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
