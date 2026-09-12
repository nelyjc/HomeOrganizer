# Overview

HomeOrganizer is an Android app I created to help keep home information organized in one place. I wanted to use this project to learn more about Kotlin, Android Studio, and how to connect an app to a cloud database.

The app connects to Google Firebase Cloud Firestore. Right now, it can read expense information from the database and display it in the app. The app also has sections for Rooms, Tasks, Maintenance, and Expenses.

To use the app, you start on the HomeOrganizer home screen and choose the section you want to open. You can view rooms, tasks, maintenance, or expenses. The Expenses screen connects to Firebase and shows expense information stored in Firestore. You can also use the Back button to return to the home screen.

My purpose for creating this app was to learn how a mobile app can connect to a cloud database and use information stored online. I also wanted to build something that could be useful for managing a home and that I can keep improving in the future.

[Software Demo Video](PASTE-YOUR-YOUTUBE-VIDEO-LINK-HERE)

# Cloud Database

I am using **Google Firebase Cloud Firestore** for the cloud database.

Firestore stores information using collections and documents. For HomeOrganizer, I created collections for:

* expenses
* homes
* maintenance
* tasks

The `expenses` collection stores information such as:

* name
* amount
* category
* date
* notes

My app currently connects to the `expenses` collection and reads expense information from Firebase to display on the Expenses screen.

# Development Environment

I used **Android Studio** to build the app and **Firebase Console** to create and manage the Firestore database.

The app is written in **Kotlin** and uses **Jetpack Compose** for the user interface.

Some of the tools and technologies I used are:

* Kotlin
* Android Studio
* Jetpack Compose
* Firebase Cloud Firestore
* Gradle
* Git
* GitHub

# Useful Websites

These websites helped me while working on the project:

* [Firebase Documentation](https://firebase.google.com/docs)
* [Cloud Firestore Documentation](https://firebase.google.com/docs/firestore)
* [Android Developers](https://developer.android.com/)
* [Kotlin Documentation](https://kotlinlang.org/docs/home.html)

# Future Work

Some things I would like to add or improve later are:

* Connect the Rooms, Tasks, and Maintenance sections to Firebase.
* Let users add, edit, and delete information from the app.
* Add Firebase Authentication so users can have their own accounts.
* Make the app look better and improve the navigation.
* Add more detailed maintenance and expense tracking.
