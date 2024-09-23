# SkyForcaster - Your Weather Forecast App

## 1. Overview
SkyForcaster is a modern weather forecasting app that provides real-time weather updates and a 5-day forecast. The app allows users to search for cities, view detailed weather data, and save favorite locations for easy access. Built with **Android Jetpack Compose** and **Kotlin**, it emphasizes simplicity, efficiency, and user-friendly navigation.

## 2. User Stories & App Architecture 🌟 

Before development, I carefully crafted user stories to guide the app’s functionality, focusing on a smooth user experience. Below is a visual representation of our architecture and screen flow:

### User Stories, Architecture & Screen Flow

<img width="1680" alt="app_architecture" src="https://github.com/user-attachments/assets/ca64f34f-c207-4b16-9b50-682c1646f55e">

*(Above: Visual architecture diagram and screen flow including user navigation between home, search, and details pages.)*

https://app.eraser.io/workspace/uVbwx7ycfVLUX3ygdicJ?origin=share

### Unit Testing
Comprehensive unit tests were implemented to ensure app stability and reliable functionality.

<img width="1680" alt="Screenshot 2024-09-23 at 11 03 13 PM" src="https://github.com/user-attachments/assets/fa399850-1830-4889-bda3-391ab6d6ca56">


## 3. Features
- **Real-time Weather Data**: Displays live weather updates for any city.
- **5-Day Forecast**: A detailed forecast for the next five days, updated every 3 hours.
- **Favorites Management**: Save and manage favorite cities for quick access.
- **Search Feature**: Instantly search for cities and view their detailed weather conditions.
- **Detailed Weather View**: Get in-depth weather details like temperature, wind speed, and humidity.
- **Seamless Navigation**: Navigate between different app screens using the Android Navigation Component.

## 4. Setup and Installation

### Prerequisites
- **Android Studio Bumblebee or later**
- **Kotlin**
- **Gradle**: (Handled by Android Studio)
- **OpenWeatherMap API Key**

### Steps
1. **Clone the repository**:
    ```bash
    git clone https://github.com/yourusername/skyforcaster.git
    ```
2. **Open the project** in Android Studio:
    - Select "Open an existing project" and choose the cloned project folder.
3. **Configure API Key**:
    - In `Constant.kt`, replace the placeholder with your API key:
    ```properties
    apiKey=your_api_key_here
    ```
4. **Build & Run**:
    - Sync Gradle and press `Shift + F10` to run the app on an emulator or physical device.

## 5. Screenshots 📸

### Home Screen
<img width="405" alt="Screenshot 2024-09-24 at 1 41 11 AM" src="https://github.com/user-attachments/assets/454178a0-b8f4-4420-ae71-cd445c1ccd44">


### Search and Favorites
<img width="405" alt="home_page" src="https://github.com/user-attachments/assets/d1893f9f-a001-4bdc-95b1-0eeede0e92d7">


### Detailed Weather View
<img width="405" alt="details_page" src="https://github.com/user-attachments/assets/191c1a71-2af7-4b79-b87b-1e9c0c8f28a2">


## 6. Key Components
- **WeatherViewModel**: Handles state management and API calls for weather data.
- **Jetpack Compose**: Used for the entire UI, enabling a declarative approach.
- **Navigation Component**: Ensures smooth transitions between app screens.

## 7. API Integration
The app integrates with the **OpenWeatherMap API** for real-time data. Make sure to include your API key as instructed above.

## 8. Contribution
We welcome contributions! Feel free to submit a pull request or report an issue. Let’s build a better weather app together!

---

SkyForcaster is your reliable companion for weather updates, designed for ease of use and accurate forecasting. 🌤️
