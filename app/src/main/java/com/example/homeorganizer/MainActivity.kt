package com.example.homeorganizer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.homeorganizer.ui.theme.HomeOrganizerTheme
import com.google.firebase.firestore.FirebaseFirestore


class MainActivity : ComponentActivity() {

    // Starts the HomeOrganizer application.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            HomeOrganizerTheme {
                HomeOrganizerApp()
            }
        }
    }
}


// Controls which screen is currently displayed in the application.
@Composable
fun HomeOrganizerApp() {
    // The current screen displayed in the application.

    var currentScreen by remember {
        mutableStateOf("home")
    }

    when (currentScreen) {

        "home" -> HomeScreen(
            onRoomsClick = {
                currentScreen = "rooms"
            },
            onTasksClick = {
                currentScreen = "tasks"
            },
            onMaintenanceClick = {
                currentScreen = "maintenance"
            },
            onExpensesClick = {
                currentScreen = "expenses"
            }
        )

        "rooms" -> RoomsScreen(
            onBack = {
                currentScreen = "home"
            }
        )

        "tasks" -> TasksScreen(
            onBack = {
                currentScreen = "home"
            }
        )

        "maintenance" -> MaintenanceScreen(
            onBack = {
                currentScreen = "home"
            }
        )

        "expenses" -> FirebaseExpenseScreen(
            onBack = {
                currentScreen = "home"
            }
        )
    }
}


// Displays the main HomeOrganizer home screen.
@Composable
fun HomeScreen(
    onRoomsClick: () -> Unit,
    onTasksClick: () -> Unit,
    onMaintenanceClick: () -> Unit,
    onExpensesClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        Text(
            text = "HomeOrganizer",
            style = MaterialTheme.typography.headlineLarge
        )

        Text(
            text = "Everything for your home in one place",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(
                top = 8.dp,
                bottom = 24.dp
            )
        )

        OrganizerCard(
            title = "🏠 Rooms",
            onClick = onRoomsClick
        )

        OrganizerCard(
            title = "✅ Tasks",
            onClick = onTasksClick
        )

        OrganizerCard(
            title = "🔧 Maintenance",
            onClick = onMaintenanceClick
        )

        OrganizerCard(
            title = "💰 Expenses",
            onClick = onExpensesClick
        )
    }
}


// Creates a clickable card used on the Home screen.
@Composable
fun OrganizerCard(
    title: String,
    onClick: () -> Unit
) {

    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {

        Text(
            text = title,
            modifier = Modifier.padding(20.dp),
            style = MaterialTheme.typography.titleMedium
        )
    }
}


// Displays expense information retrieved from Firebase Firestore.
@Composable
fun FirebaseExpenseScreen(
    onBack: () -> Unit
) {

    var name by remember {
        mutableStateOf("Loading...")
    }

    var amount by remember {
        mutableStateOf("")
    }

    var category by remember {
        mutableStateOf("")
    }

    var errorMessage by remember {
        mutableStateOf("")
    }

    // Retrieves the first expense stored in Firebase Firestore.
    LaunchedEffect(Unit) {

        val db = FirebaseFirestore.getInstance()

        db.collection("expenses")
            .limit(1)
            .get()
            .addOnSuccessListener { result ->

                if (!result.isEmpty) {

                    val document = result.documents[0]

                    name =
                        document.getString("name")
                            ?: "No name"

                    amount =
                        document.get("amount")
                            ?.toString()
                            ?: "No amount"

                    category =
                        document.getString("category")
                            ?: "No category"

                } else {

                    name = "No expenses found"
                }
            }
            .addOnFailureListener { exception ->

                errorMessage =
                    exception.message
                        ?: "Firebase error"
            }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        Button(
            onClick = onBack
        ) {
            Text("Back")
        }

        Text(
            text = "Expenses",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(top = 24.dp)
        )

        Text(
            text = "Expense stored in Firebase",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(
                top = 8.dp,
                bottom = 20.dp
            )
        )

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                Text(
                    text = name,
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = "Amount: $$amount",
                    modifier = Modifier.padding(top = 8.dp)
                )

                Text(
                    text = "Category: $category",
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }

        if (errorMessage.isNotEmpty()) {

            Text(
                text = "Error: $errorMessage",
                color = Color.Red,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}


// Displays the list of rooms in the home.
@Composable
fun RoomsScreen(
    onBack: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        Button(
            onClick = onBack
        ) {
            Text("Back")
        }

        Text(
            text = "Rooms",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(top = 24.dp)
        )

        Text(
            text = "Kitchen",
            modifier = Modifier.padding(top = 20.dp)
        )

        Text("Living Room")

        Text("Bedroom")

        Text("Bathroom")
    }
}


// Displays household tasks.
@Composable
fun TasksScreen(
    onBack: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        Button(
            onClick = onBack
        ) {
            Text("Back")
        }

        Text(
            text = "Tasks",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(top = 24.dp)
        )

        Text(
            text = "Clean kitchen",
            modifier = Modifier.padding(top = 20.dp)
        )

        Text("Change air filter")

        Text("Organize garage")
    }
}


// Displays home maintenance information.
@Composable
fun MaintenanceScreen(
    onBack: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        Button(
            onClick = onBack
        ) {
            Text("Back")
        }

        Text(
            text = "Maintenance",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(top = 24.dp)
        )

        Text(
            text = "Home maintenance items will appear here.",
            modifier = Modifier.padding(top = 20.dp)
        )
    }
}