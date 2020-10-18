## CryptoExchange
App for checking crypto currency exchange rates!

<img src="documentation/materials/screenshot_list.jpg" width = 180>

<img src="documentation/materials/screenshot_details.jpg" width = 180>


## Overview
This is an Android mobile app project that:
* shows a list of cryptocurrencies and their details
* uses MVVM architecture and Rx programming


## Architecture
<img src="documentation/materials/crypto-diagram.jpg" width = 480>

* **Model** layer is in charge of storing and accessing data
* **ViewModel** is the core of the app which interacts both with the UI and the Data layer
* **Repository** and **DataSource** are an abstraction in charge of empowering the separation of concerns
* **CryptoCurrencyActivity** is as lightweight as possible
* **Fragments** are in charge of binding data and xml and passing events to the ViewModels
* **Dagger-Hilt** is the library used for compile-time safe dependency injection
* **RxKotlin** and **RxAndroid** are used for multithreading and error handling
* **Navigation** with **SafeArgs** is used for navigation between Fragments
* **View binding** and **Data binding** implemented for instant reactive UI changes
* CryptoExchange adapts well to edge cases like and supports **screen rotation**

<img src="documentation/materials/Skocko-land.jpg" height = 180>

## Potential Enhancements
* add charts in the DetailFragment screen 
* implement local Room caching
* implement testing
* add more comprehensive error handling
* tests on multiple devices (tested on Samsung A10 and Pixel 2)


## Other
* Kotlin and MVVM knowledge base: [Codelabs](https://codelabs.developers.google.com/android-kotlin-fundamentals/)
* Dagger-Hilt documentation: [Dagger-Hilt](https://dagger.dev/hilt/)
* RxKotlin and Rx: [Reactivex.io](http://reactivex.io/)