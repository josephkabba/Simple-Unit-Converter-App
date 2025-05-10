# Simple Unit Converter App

A clean, intuitive Android application that allows users to convert between different units of temperature, length, and mass.

## Features

- Convert between various units:
    - Temperature: Celsius, Fahrenheit, Kelvin
    - Length: Meters, Feet, Inches
    - Mass: Kilograms, Pounds, Ounces
- Two-way conversion support
- Intuitive UI with clear input and output displays
- Input validation
- Supports landscape and portrait orientations

## Technical Implementation

- **Platform**: Android (minimum SDK 24)
- **Language**: Kotlin
- **Architecture**:
    - Clean Architecture with MVVM pattern
    - Separation of concerns (domain, data, presentation layers)
- **UI**: Jetpack Compose
- **Unit Testing**: JUnit for business logic

## Project Structure

```
com.example.simpleunitconverterapp/
├── domain/
│   ├── model/
│   │   ├── UnitType.kt
│   │   ├── ConversionRequest.kt
│   │   └── ConversionResult.kt
│   ├── repository/
│   │   └── ConversionRepository.kt
│   └── usecase/
│       └── ConvertUnitUseCase.kt
├── data/
│   └── repository/
│       └── ConversionRepositoryImpl.kt
├── di/
│   └── AppContainer.kt
└── presentation/
    ├── MainActivity.kt
    ├── viewmodel/
    │   └── ConverterViewModel.kt
    ├── screens/
    │   └── ConverterScreen.kt
    ├── components/
    │   └── UnitDropdown.kt
    └── theme/
        ├── Color.kt
        ├── Theme.kt
        └── Type.kt
```

## Future Improvements

If I had more time to complete this project, I would focus on:

1. **Complete unit and UI testing** - Finish writing unit tests for the repository, use cases, and ViewModel with proper mocking, and add UI tests for the Compose screens.

2. **Improved error handling** - Add more robust error handling and user feedback for edge cases.

3. **Additional unit types** - Expand the app to include other conversion types like volume, currency, time, etc.

4. **Save recent conversions** - Add a history feature to save and recall recent conversions.

5. **Custom themes** - Add light/dark mode support and user-selectable themes.

6. **Offline first architecture** - Implement local storage with Room database to save recent conversions and user preferences.

7. **Widget support** - Create a home screen widget for quick conversions.

8. **Accessibility improvements** - Ensure the app is fully accessible with TalkBack support and content descriptions.

## AI Usage

I used AI assistance for parts of this project:

1. **UI implementation with Jetpack Compose** - I leveraged AI to create the UI components as these tend to be very time-consuming to build from scratch, especially without detailed design specifications.

2. **Implementation classes for conversion logic** - The conversion algorithms are straightforward but require precision, so I used AI to generate these efficiently.

I wrote the architecture structure and ViewModel logic myself to ensure core functionality works correctly and follows best practices. This approach allowed me to focus on the application architecture and business logic while streamlining the more boilerplate aspects of development.