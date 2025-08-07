Valora - Android Portfolio Project
A simple, clean, and modern currency converter application for Android, built to showcase modern Android development skills. This project serves as a practical example of implementing cutting-edge technologies and best practices in Android development.

✨ Features
Real-time Currency Conversion: Fetches the latest exchange rates from a free, reliable API.

Minimal & Glassmorphism UI: A user-friendly, galaxy-themed interface built entirely with Jetpack Compose.

Offline Caching: Uses Room to cache the latest rates, allowing the app to function without an internet connection.

Searchable Currency List: A modern bottom sheet allows users to easily find and select currencies.

Robust Network Handling: Intelligently detects online/offline status and provides clear user feedback.

🛠️ Tech Stack & Architecture
This project follows the MVVM (Model-View-ViewModel) design pattern and incorporates principles of Clean Architecture. This separation of concerns makes the app scalable, maintainable, and testable.

UI Layer:

Jetpack Compose: For building the entire UI declaratively, including custom themes and animations.

ViewModel: To manage UI-related data and state in a lifecycle-conscious way.

Domain Layer:

UseCases (Interactors): Encapsulates specific business logic (e.g., GetConversionRatesUseCase). This layer is independent of the UI and Data layers.

Data Layer:

Repository Pattern: To abstract the data sources (network and local database).

Retrofit: For type-safe HTTP calls to the currency exchange API.

Room: For persisting data and providing a robust offline cache.

Hilt (Dagger): For dependency injection, making the code more modular and easier to manage.

Kotlin Coroutines & Flow: For managing asynchronous operations and streams of data.

🚀 How to Build
Get an API Key: This project uses the ExchangeRate-API. Sign up for a free API key.

Add your API Key: Open the local.properties file in the root of your project and add your API key:

EXCHANGE_RATE_API_KEY="YOUR_API_KEY_HERE"

Build the project: Open the project in Android Studio and run it on an emulator or a physical device.

📂 Project Structure
com.example.valora
├── core                #Application Class
├── data
│   ├── remote             # Retrofit API interface
│   ├── local           # Room Database, DAO, and Entity
│   ├── di              # Hilt modules for the data layer
│   ├── dto             # Data Transfer Objects for API responses
│   └── repository      # Repository implementation
|   |__ util            # Utility classes for data handling
│
├── domain
│   ├── di              # Hilt modules for the domain layer
│   ├── model           # Clean data models for the app
│   ├── repository      # Abstract repository interfaces
│   └── usecase         # Business logic interactors
│
├── presentation
│   ├── ui              # Jetpack Compose UI
│   │   ├── components  # Reusable UI components
│   │   └── theme       # Custom theme and colors
│   └── viewmodel       # ViewModels for the UI
│
└── util                # Utility classes (Resource, ConnectivityObserver)

🤝 Contributing
Contributions, issues, and feature requests are welcome! Feel free to check the issues page.
alireza_moradbakhti@outlook.com
T.me/ashenheart1

📄 License
This project is licensed under the MIT License - see the LICENSE.md file for details.