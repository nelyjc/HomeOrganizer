package com.example.homeorganizer
import android.os.Bundle


import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.homeorganizer.ui.theme.HomeOrganizerTheme
import com.google.firebase.firestore.FirebaseFirestore

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.DisposableEffect
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale


class MainActivity : ComponentActivity() {

    // Starts the HomeOrganizer application.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
// Displays expense information and allows the user to add,
// update, delete, and retrieve data from Firebase Firestore.
data class ExpenseItem(
    val id: String = "",
    val name: String = "",
    val amount: Double = 0.0,
    val category: String = "",
    val date: Timestamp? = null,
    val notes: String = ""
)
// Displays all expenses stored in Firebase Firestore.
// Allows the user to create, read, update, and delete expenses.
// Displays all expenses stored in Firebase Firestore.
// Allows the user to add, read, update, and delete expenses.
@Composable
fun FirebaseExpenseScreen(
    onBack: () -> Unit
) {
//Listener to update firebase
    val db = FirebaseFirestore.getInstance()
    val expensesCollection = db.collection("expenses")

    var name by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }

    var selectedDocumentId by remember {
        mutableStateOf<String?>(null)
    }

    var expenses by remember {
        mutableStateOf<List<ExpenseItem>>(emptyList())
    }

    var statusMessage by remember {
        mutableStateOf("Loading expenses...")
    }

    // Listen for all expenses stored in Firestore.
    DisposableEffect(Unit) {

        val listener = expensesCollection
            .addSnapshotListener { snapshot, error ->

                if (error != null) {

                    statusMessage =
                        "Firebase error: ${error.message}"

                    return@addSnapshotListener
                }

                if (snapshot != null) {

                    expenses = snapshot.documents.map { document ->

                        // Safely read amount.
                        // This works with both old String amounts
                        // and new Double amounts.
                        val expenseAmount =
                            when (val value = document.get("amount")) {

                                is Number -> value.toDouble()

                                is String ->
                                    value.toDoubleOrNull() ?: 0.0

                                else -> 0.0
                            }

                        // Safely read the Firestore date.
                        val expenseDate =
                            when (val value = document.get("date")) {

                                is Timestamp -> value

                                is java.util.Date ->
                                    Timestamp(value)

                                else -> null
                            }

                        ExpenseItem(
                            id = document.id,
                            name =
                                document.getString("name") ?: "",
                            amount = expenseAmount,
                            category =
                                document.getString("category") ?: "",
                            date = expenseDate,
                            notes =
                                document.getString("notes") ?: ""
                        )
                    }
                        .sortedByDescending {
                            it.date?.seconds ?: 0
                        }

                    statusMessage =
                        "${expenses.size} expense(s) loaded"
                }
            }

        onDispose {
            listener.remove()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
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
            modifier = Modifier.padding(top = 20.dp)
        )

        Text(
            text = "Manage your home expenses",
            modifier = Modifier.padding(
                top = 8.dp,
                bottom = 20.dp
            )
        )

        // NAME
        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
            },
            label = {
                Text("Expense Name")
            },
            modifier = Modifier.fillMaxWidth()
        )

        // AMOUNT
        OutlinedTextField(
            value = amount,
            onValueChange = {
                amount = it
            },
            label = {
                Text("Amount")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
        )

        // CATEGORY
        OutlinedTextField(
            value = category,
            onValueChange = {
                category = it
            },
            label = {
                Text("Category")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
        )

        // NOTES
        OutlinedTextField(
            value = notes,
            onValueChange = {
                notes = it
            },
            label = {
                Text("Notes")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
        )

        Text(
            text = "Date: automatically saved when added",
            modifier = Modifier.padding(top = 10.dp)
        )

        // -------------------------
        // ADD EXPENSE
        // -------------------------

        Button(
            onClick = {

                val amountNumber =
                    amount.toDoubleOrNull()

                if (name.isBlank()) {

                    statusMessage =
                        "Enter an expense name"

                } else if (amountNumber == null) {

                    statusMessage =
                        "Enter a valid amount"

                } else {

                    val expense = hashMapOf(
                        "name" to name,
                        "amount" to amountNumber,
                        "category" to category,
                        "date" to Timestamp.now(),
                        "notes" to notes
                    )

                    // add() automatically creates
                    // a random Firestore document ID.
                    expensesCollection
                        .add(expense)
                        .addOnSuccessListener { document ->

                            statusMessage =
                                "Expense added"

                            name = ""
                            amount = ""
                            category = ""
                            notes = ""

                            selectedDocumentId = null
                        }
                        .addOnFailureListener { error ->

                            statusMessage =
                                "Could not add expense: ${error.message}"
                        }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
        ) {

            Text("Add Expense")
        }

        // -------------------------
        // UPDATE EXPENSE
        // -------------------------

        Button(
            onClick = {

                val documentId =
                    selectedDocumentId

                val amountNumber =
                    amount.toDoubleOrNull()

                if (documentId == null) {

                    statusMessage =
                        "Tap an expense below first"

                } else if (amountNumber == null) {

                    statusMessage =
                        "Enter a valid amount"

                } else {

                    expensesCollection
                        .document(documentId)
                        .update(
                            mapOf(
                                "name" to name,
                                "amount" to amountNumber,
                                "category" to category,
                                "notes" to notes
                            )
                        )
                        .addOnSuccessListener {

                            statusMessage =
                                "Expense updated"

                            name = ""
                            amount = ""
                            category = ""
                            notes = ""

                            selectedDocumentId = null
                        }
                        .addOnFailureListener { error ->

                            statusMessage =
                                "Could not update: ${error.message}"
                        }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {

            Text("Update Selected Expense")
        }

        // -------------------------
        // DELETE EXPENSE
        // -------------------------

        Button(
            onClick = {

                val documentId =
                    selectedDocumentId

                if (documentId == null) {

                    statusMessage =
                        "Tap an expense below first"

                } else {

                    expensesCollection
                        .document(documentId)
                        .delete()
                        .addOnSuccessListener {

                            statusMessage =
                                "Expense deleted"

                            name = ""
                            amount = ""
                            category = ""
                            notes = ""

                            selectedDocumentId = null
                        }
                        .addOnFailureListener { error ->

                            statusMessage =
                                "Could not delete: ${error.message}"
                        }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {

            Text("Delete Selected Expense")
        }

        Text(
            text = statusMessage,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 20.dp)
        )

        Spacer(
            modifier = Modifier.height(30.dp)
        )

        Text(
            text = "Saved Expenses",
            style = MaterialTheme.typography.headlineSmall
        )

        Text(
            text = "Tap an expense to select it.",
            modifier = Modifier.padding(
                top = 5.dp,
                bottom = 10.dp
            )
        )

        // -------------------------
        // SHOW ALL EXPENSES
        // -------------------------

        expenses.forEach { expense ->

            Card(
                onClick = {

                    selectedDocumentId =
                        expense.id

                    name =
                        expense.name

                    amount =
                        expense.amount.toString()

                    category =
                        expense.category

                    notes =
                        expense.notes

                    statusMessage =
                        "Selected: ${expense.name}"
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
            ) {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text(
                        text = expense.name,
                        style =
                            MaterialTheme.typography.titleMedium
                    )

                    Text(
                        text = "Amount: $${expense.amount}",
                        modifier =
                            Modifier.padding(top = 6.dp)
                    )

                    Text(
                        text =
                            "Category: ${expense.category}"
                    )

                    val formattedDate =
                        expense.date
                            ?.toDate()
                            ?.let { date ->

                                SimpleDateFormat(
                                    "MMMM d, yyyy h:mm a",
                                    Locale.getDefault()
                                ).format(date)

                            } ?: "No date"

                    Text(
                        text =
                            "Date: $formattedDate"
                    )

                    Text(
                        text =
                            "Notes: ${expense.notes}"
                    )

                    Text(
                        text =
                            "Document ID: ${expense.id}",
                        style =
                            MaterialTheme.typography.bodySmall,
                        modifier =
                            Modifier.padding(top = 8.dp)
                    )
                }
            }
        }

        Spacer(
            modifier = Modifier.height(40.dp)
        )
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