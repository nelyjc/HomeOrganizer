# HomeOrganizer
## Description

HomeOrganizer is an Android application designed to help users organize and manage information related to their home. The application uses Firebase Firestore as a cloud database to store and retrieve information.

The goal of this project was to learn how to connect an Android application to a cloud database and work with data stored online.

HomeOrganizer can be expanded to manage information such as:

* Rooms
* Tasks
* Expenses
* Maintenance
* Household items

## Purpose

I created HomeOrganizer because I wanted to build an application that could eventually help families keep important household information organized in one place.

For this module, my main focus was learning how to use Firebase Firestore with an Android application. I learned how to connect my project to Firebase, create collections and documents, and retrieve information from the database.

## Software Demo Video



In this video, I demonstrate the application, show the Firebase Firestore database, and explain some of the code used to connect the Android application to the database.

## Development Environment

The following tools were used to create this project:

* Android Studio
* Kotlin
* Jetpack Compose
* Firebase
* Firebase Firestore
* Gradle
* Git
* GitHub

## Useful Websites

The following resources were helpful while developing this project:

* [Firebase Documentation](https://firebase.google.com/docs)
* [Cloud Firestore Documentation](https://firebase.google.com/docs/firestore)
* [Android Developers](https://developer.android.com/)
* [Kotlin Documentation](https://kotlinlang.org/docs/home.html)

## Cloud Database

This application uses **Firebase Cloud Firestore**.

The Firestore database contains collections that can be used to organize different types of home information.

Some of the collections created for this project include:

### expenses

Stores information about household expenses.

Example fields:

* `name`
* `amount`
* `category`
* `date`
* `notes`

## What I Learned

During this project, I learned how to:

* Create a Firebase project.
* Connect Firebase to an Android Studio project.
* Add Firebase dependencies to a Gradle project.
* Create a Cloud Firestore database.
* Create Firestore collections and documents.
* Store different types of information in Firestore.
* Retrieve information from Firestore using Kotlin.
* Display information from a cloud database in an Android application.
* Troubleshoot Firebase and Gradle configuration problems.

One of the most important things I learned was how an Android application can communicate with a database that is hosted in the cloud instead of storing all of the information directly on the device.

## Future Work

There are several features I would like to add to HomeOrganizer in the future:

* Add Firebase Authentication.
* Allow each user to have their own account.
* Create, edit, and delete tasks directly from the app.
* Add rooms and organize household items by room.
* Create containers inside rooms for better organization.
* Add maintenance reminders.
* Add expense tracking and reports.
* Improve the user interface.
* Add secure Firestore rules so users can only access their own information.

## Author

Nely Crespin

Software Development Student

## GitHub Repository

https://github.com/nelyjc/HomeOrganizer.git
