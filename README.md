# Minori Mobile App 🎨🏺

Minori is a beautifully designed, premium mobile marketplace application for art lovers and independent creators. It connects buyers with independent studios selling hand-thrown ceramics, woven textiles, oil paintings, woodwork, and glassware. 

The application is built entirely using **Android Jetpack Compose**, **Kotlin**, and **Dagger Hilt**, following modern Clean Architecture, MVVM/MVI patterns, and high-fidelity custom Canvas drawings.

---

## ✨ Design Aesthetic & Theme

Minori features a curated, warm, and organic **bohemian brand identity** designed to feel like an artistic editorial space:
- **Backgrounds**: Soft, organic warm white/sand (`SandCream` - `#FEFBF7`)
- **Accents & Primary Elements**: Burnt terracotta rust (`Terracotta` - `#C2410C` in Light Mode, `SoftTerracotta` - `#FDBA74` in Dark Mode)
- **Secondary Details**: Earthy Sage Olive Green (`SageGreen` - `#606C38`)
- **Typography**: Elegant Serif fonts for headers and clean Sans-Serif fonts for readability.
- **Adaptive Themes**: Seamless transitions between organic Light Cream and Obsidian Warm Slate (`#1C1917`) Dark themes.

---

## 🚀 Key Features & UI Screens

### 1. 🌀 Animated Splash Screen
- **Procedural Monogram Logo**: Renders Pebble 1 (Terracotta), Pebble 2 (Clay), and a Sage Leaf stem procedurally using Compose `Canvas` with multi-wash opacity overlay. No raster image artifacts.
- **Kinetic Typography Animation**: Letters slide together as letter-spacing spring-settles from `0.3.em` to `0.04.em`.
- **Staggered Spring Physics**: Bounce-scale and slide-up transitions on the brand monogram.

### 2. 📖 Onboarding Flow
- Three editorial onboarding pages featuring handcrafted illustrations outlining the platform's vision:
  - *Discover Unique Crafts*
  - *Support Local Artisans*
  - *Acquire Handcrafted Art*
- Dot indicator progress animations and standard `Next`, `Prev`, and `Skip` navigation triggers.

### 3. 🔐 Secure Authentication Suite
- **Custom Components**: Clean, reusable `MinoriTextField` featuring input validation error boundaries (red border outline) and keyboard actions.
- **Procedural Social Sign-in**: Pixel-perfect circular buttons for Google, Apple, and Facebook drawn dynamically using Compose `Canvas` shapes and vector paths.
- **Screen Flow**:
  - **Login**: Credential checking, custom password visibility mask toggling, and links to signup/reset options.
  - **Signup**: Input validation (names, emails, passwords) and confirm-password matching.
  - **Forgot Password**: Password reset submission and instructions with circular back navigation.

### 4. 🏺 Rich Home Screen & Dashboard
- **Top Bar**: Drawer button, small procedurally rendered brand monogram, and custom user avatar outline.
- **Search & Filter Suite**: Rounded search field with microphone outline icon and custom sort/filter popup triggers.
- **Featured Categories**: Horizontal category list (Ceramics, Paintings, Fiber Art, Woodwork, Glassware) displaying custom hand-drawn Canvas illustrations inside circular highlights.
- **Swipeable Promo Pager**: Carousel pager showing rotating studio discounts (e.g. 50-40% OFF Ceramics) with slide dots.
- **Deal of the Day**: Header bar featuring a live ticking countdown timer leading into a horizontal product grid (bowl, pitcher details, rating stars, price tags, and discounts).
- **Special Offers & Collections**: High-fidelity promotional banners displaying hand-woven tapestries, wood chests, and decanter details.
- **Bottom Navigation**: Sticky navigation bar with a prominent, floating circular Shopping Cart button in the center.

---

## 🏛️ Architecture Details

The codebase adheres to clean Android architecture principles:
- **MVI/MVVM Pattern**: ViewModels manage UI State and emit single-shot Side Effects (e.g., navigation events) to Composables.
- **Generic Base ViewModel**: Exposes `BaseViewModel<State, Effect>` to handle unified states, asynchronous loading triggers (`isLoading`), error messages (`errorMessage`), and state update operations.
- **Modular Packaging**:
  - `core/navigation`: Custom Screen routes and NavHost controllers.
  - `core/base`: Shared base classes.
  - `mainui/authentication`: Auth components and Login/Signup/ForgotPassword screens.
  - `mainui/homescreen`: Product grids and dashboard structures.
  - `mainui/onboardingscreen` & `mainui/splashscreen`: Welcome animations and onboarding sequences.

---

## 🛠️ Build & Installation

### Prerequisites
- Android Studio Ladybug (2024.2.1) or higher
- JDK 17
- Android SDK 34

### Compilation
Build the application and verify Kotlin compile checks via Gradle:
```powershell
# Windows PowerShell
.\gradlew compileDebugKotlin

# Linux / macOS Terminal
./gradlew compileDebugKotlin
```

### Running the App
Deploy the debug build to an active device or emulator:
```powershell
.\gradlew installDebug
```
