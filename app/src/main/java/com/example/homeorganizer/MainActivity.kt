package com.example.homeorganizer
// These are the imports needed for the Firebase code
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Button


class MainActivity : ComponentActivity() {
    // This is the main activity for the app.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            HomeOrganizerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    FirebaseExpenseScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun FirebaseExpenseScreen(modifier: Modifier = Modifier) {
// This is the main screen for the app.
    var currentScreen by remember {
        mutableStateOf("home")}
    if (currentScreen == "rooms") {
        RoomsScreen(
            onBack = { currentScreen = "home" }
        )
        return
    }

    if (currentScreen == "tasks") {
        TasksScreen(
            onBack = { currentScreen = "home" }
        )
        return
    }

    var name by remember { mutableStateOf("Loading...") }
    var amount by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        val db = FirebaseFirestore.getInstance()

        db.collection("expenses")
            .limit(1)
            .get()
            .addOnSuccessListener { result ->

                if (!result.isEmpty) {
                    val document = result.documents[0]

                    name = document.getString("name") ?: "No name"
                    amount = document.get("amount")?.toString() ?: "No amount"
                    category = document.getString("category") ?: "No category"
                } else {
                    name = "No expenses found"
                }
            }
            .addOnFailureListener { exception ->
                errorMessage = exception.message ?: "Firebase error"
            }
    }

    Column(
        modifier = modifier
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
            modifier = Modifier.padding(bottom = 24.dp)
        )

        OrganizerCard(
            title = "🏠 Rooms",
            onClick = {
                currentScreen = "rooms"
            }
        )

        OrganizerCard(
            title = "✅ Tasks",
            onClick = {
                currentScreen = "tasks"
            }
        )

        OrganizerCard(
            title = "🔧 Maintenance",
            onClick = {
                // We can build this screen next
            }
        )

        OrganizerCard(
            title = "💰 Expenses",
            onClick = {
                // We can build this screen next
            }
        )
        Text(
            text = "Recent Expense",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(top = 24.dp, bottom = 8.dp)
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
@Composable
fun RoomsScreen(onBack: () -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        Button(onClick = onBack) {
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
@Composable
fun TasksScreen(onBack: () -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        Button(onClick = onBack) {
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