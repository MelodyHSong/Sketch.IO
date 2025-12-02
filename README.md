# ☆ Sketch.IO Mobile Codebase ☆

> "I'm on a date with Kotlin now, baby~"

Welcome to the internal source directory for **Sketch.IO**! This repository contains the complete source code for a robust Android mobile drawing application developed for the SICI 4185 course (Introducción a la Programación de Dispositivos Móviles).

The application functions as a digital canvas, allowing users to draw freehand, customize brush settings (color and width), save their artwork using modern APIs, and print/erase the canvas.

## ☆ What's Inside?

Here is a breakdown of the core files and folders you'll find in the `app/src/main/` directory:

| File/Folder | Purpose | Key Features |
| :--- | :--- | :--- |
| **`java/`** | **The Brains.** Contains all primary **Kotlin** logic files. | Implements drawing lifecycle, dialog communication, persistence, and printing. |
| &nbsp;&nbsp;&nbsp;`MainActivity.kt` | **Activity Host.** Controls the main screen, manages the Toolbar setup, and handles permission delegation. | `saveDrawing()`, `setSupportActionBar()`. |
| &nbsp;&nbsp;&nbsp;`MainActivityFragment.kt` | **Core Controller.** Initializes `DrawingView`, handles menu clicks, executes `saveDrawing()`, and uses the Fragment Result API. | `onMenuItemSelected()`, `setupConfirmationListener()`. |
| &nbsp;&nbsp;&nbsp;`DrawingView.kt` | **Custom Canvas.** The core drawing surface. | `setBrushColor()`, `setBrushSize()`, `clear()`, `getDrawingBitmap()`. |
| &nbsp;&nbsp;&nbsp;`AboutActivity.kt` | **Developer Info.** Displays specialized developer card and handles external link navigation (Ko-fi, GitHub). | `setupLinkListeners()`, `openUrl()`. |
| &nbsp;&nbsp;&nbsp;`ConfirmationDialogFragment.kt` | **UX Manager.** Shows confirmation dialog before executing the erase action. | `newInstance()`, Fragment Result API. |
| **`res/`** | **The Assets.** Contains all resources referenced by the code and layouts. | Divided into `layout/`, `values/`, `drawable/`, and `menu/`. |
| &nbsp;&nbsp;&nbsp;`menu/` | **Toolbar Menu.** Defines the nested **"More"** menu structure for Save, Print, and Delete actions. | `action_more` nested menu structure. |
| &nbsp;&nbsp;&nbsp;`values/` | **Strings & Colors.** Holds localization, theme colors, and custom colors (e.g., `about_background`). | `strings.xml`, `colors.xml`, `themes.xml`. |

---

## ☆ Changelog

This log tracks key feature additions and structural changes across the development cycles of the Sketch Application.

| Version | Date | Changes |
| :--- | :--- | :--- |
| **V1.0.0REL** | 2025-12-01 | **[PRODUCTION RELEASE] Codebase Finalization & Stability.** This version confirms the Sketch App is complete, stable, and production-ready. Includes the definitive fix for the cross-device black background saving bug, full adherence to all PDF method names and API structures, and final UI cleanup. |
| **V1.1.4a** | 2025-12-01 | *Superseded by V1.0.0r.* Solved the critical black background saving bug; planned for rotation support enhancement. |
| **V1.1.3a** | 2025-11-30 | **Critical Theme & Save Fixes:** Fixed theme-compliance issues for all menu and button icons, changing their color to Light Neon Pink. Corrected `DrawingView.kt`'s save logic to ensure the white background is captured properly on physical devices. |
| **V1.1.2a** | 2025-11-30 | Implemented external navigation links (Ko-fi and GitHub) in the About Activity screen. |
| **V1.1.0a** | 2025-11-30 | **Initial Feature Implementation & Code Standardization.** Completed all core assignment requirements, including the Print feature, modern saving logic (API 29+), nested menu structure, and finalized all custom dialogs. |

## ☆ Implemented Functionality & Features

The application fully implements the core requirements across the entire assignment (Parts I, II, & III):

* **Drawing/Customization:** Full control over brush **Color** and **Width** using visually enhanced dialogs.
* **Saving:** Implemented using modern **Android Q+ MediaStore API** for permission-less saving to the device's gallery.
* **Printing:** Integrated using the **`androidx.print:print`** library (`PrintHelper`).
* **UX/Safety:** **Confirmation dialog** is required before erasing the canvas.
* **Structure:** Uses a `MaterialToolbar` and `Fragment Result API` for clean, modern communication.

## ☆ Requirements

To compile and run this mobile application, ensure your development environment is set up with:

* **Android Studio** (Latest version recommended)
* **Kotlin Development Kit (KDK)**
* **Android SDK** (API 28+ required for `minSdk`)
* **Dependencies:** `androidx.print:print:1.0.0` and Material Components.

## ☆ Coding Style & Attribution

All Kotlin code across my projects follows a specific stylish header and clean structure. Please keep these headers intact if you modify or redistribute the source files.

Here is an example of the file header format:

```kotlin
/*
☆
☆ Author: ☆ MelodyHSong ☆
☆ Language: Kotlin
☆ File Name: Example.kt
☆ Date: 2025-11-30
☆
*/
```
---

## ☆ License

This code is licensed under the **MIT License**!

A credit to **MelodyHSong** is always appreciated.

---
*Kotlin, I love that. - MelodyHSong*
