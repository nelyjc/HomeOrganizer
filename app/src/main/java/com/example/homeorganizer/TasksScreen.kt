package com.example.homeorganizer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore


// Represents one task stored in Firebase.
data class tasks(
    val id: String = "",
    val title: String = "",
    val completed: Boolean = false,
    val dueDate: Timestamp? = null,
    val notes: String = "",
    val priority: String = ""
)


// Displays household tasks from Firebase.
@Composable
fun TasksScreen() {

    // Connect to Firebase Firestore.
    val db = FirebaseFirestore.getInstance()

    // Stores the tasks that are pulled from Firebase.
    var tasks by remember {
        mutableStateOf<List<tasks>>(emptyList())
    }

    // Stores what the user types into the new task box.
    var newTask by remember {
        mutableStateOf("")
    }

    // Listen for changes in the Firebase tasks collection.
    DisposableEffect(Unit) {

        val listener = db.collection("tasks")
            .addSnapshotListener { snapshot, error ->

                // Stop if Firebase returns an error.
                if (error != null) {
                    return@addSnapshotListener
                }

                // Convert each Firebase document into a Task object.
                if (snapshot != null) {

                    tasks = snapshot.documents.map { document ->

                        tasks(
                            id = document.id,

                            // Firebase currently uses "Title" with a capital T.
                            title = document.getString("Title") ?: "",

                            completed =
                                document.getBoolean("completed") ?: false,

                            dueDate =
                                document.getTimestamp("dueDate"),

                            notes =
                                document.getString("notes") ?: "",

                            priority =
                                document.getString("priority") ?: ""
                        )
                    }
                }
            }

        // Remove the Firebase listener when the screen closes.
        onDispose {
            listener.remove()
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Tasks",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )


        // Display every task stored in Firebase.
        tasks.forEach { task ->

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    // Checkbox shows whether the task is complete.
                    Checkbox(
                        checked = task.completed,

                        onCheckedChange = { checked ->

                            // Update the completed value in Firebase.
                            db.collection("tasks")
                                .document(task.id)
                                .update(
                                    "completed",
                                    checked
                                )
                        }
                    )


                    Column(
                        modifier = Modifier
                            .padding(start = 8.dp)
                    ) {

                        // Task title.
                        Text(
                            text = task.title,
                            style = MaterialTheme.typography.titleMedium
                        )

                        // Show notes if the task has notes.
                        if (task.notes.isNotBlank()) {

                            Text(
                                text = task.notes
                            )
                        }

                        // Show task priority.
                        if (task.priority.isNotBlank()) {

                            Text(
                                text = "Priority: ${task.priority}"
                            )
                        }
                    }
                }
            }
        }


        // Push the Add Task section toward the bottom.
        Spacer(
            modifier = Modifier.weight(1f)
        )


        // User types a new task here.
        OutlinedTextField(
            value = newTask,

            onValueChange = {
                newTask = it
            },

            label = {
                Text("New Task")
            },

            modifier = Modifier.fillMaxWidth()
        )


        Spacer(
            modifier = Modifier.height(8.dp)
        )


        // Adds the task to Firebase.
        Button(
            onClick = {

                if (newTask.isNotBlank()) {

                    val taskData = hashMapOf(

                        // Match your existing Firebase field.
                        "Title" to newTask,

                        "completed" to false,

                        // New tasks start with blank notes.
                        "notes" to "",

                        // Default priority for now.
                        "priority" to "Normal",

                        // Uses the current time for the date.
                        "dueDate" to FieldValue.serverTimestamp()
                    )


                    // Firebase automatically creates the document ID.
                    db.collection("tasks")
                        .add(taskData)


                    // Clear the text box after adding the task.
                    newTask = ""
                }
            },

            modifier = Modifier.fillMaxWidth()
        ) {

            Text("Add Task")
        }
    }
}