package com.example.homeorganizer

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


// Rooms page
// Shows all the rooms and lets the user add a new room
@Composable
fun RoomsScreen(
    onBack: () -> Unit,
    onRoomClick: (String) -> Unit
) {

    // Stores what the user types for the new room
    var newRoomName by remember {
        mutableStateOf("")
    }

    // Rooms that show when the app starts
    var rooms by remember {
        mutableStateOf(
            listOf(
                "Kitchen",
                "Living Room",
                "Bedroom",
                "Bathroom"
            )
        )
    }

    // Main layout for the rooms page
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {

        // Back button
        Button(
            onClick = onBack
        ) {
            Text("← Back")
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Page title
        Text(
            text = "Rooms",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF2F4F3E)
        )

        // Description under the title
        Text(
            text = "Organize your home by room",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray,
            modifier = Modifier.padding(
                top = 6.dp,
                bottom = 24.dp
            )
        )

        // Title above the room cards
        Text(
            text = "My Rooms",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Makes a card for every room in the list
        rooms.forEach { room ->

            RoomCard(
                title = room,
                onClick = {

                    // Opens the room that was clicked
                    onRoomClick(room)
                }
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        // Add room section
        Text(
            text = "Add a Room",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(10.dp))

        // User types the new room name here
        OutlinedTextField(
            value = newRoomName,
            onValueChange = {
                newRoomName = it
            },
            label = {
                Text("Room Name")
            },
            placeholder = {
                Text("Example: Office")
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Adds the new room to the list
        Button(
            onClick = {

                // Makes sure the room name is not empty
                if (newRoomName.isNotBlank()) {

                    rooms = rooms + newRoomName.trim()

                    // Clears the text box after adding
                    newRoomName = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("+ Add Room")
        }

        Spacer(modifier = Modifier.height(30.dp))
    }
}


// Creates the card for each room
@Composable
fun RoomCard(
    title: String,
    onClick: () -> Unit
) {

    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 7.dp),

        // Rounded corners
        shape = RoundedCornerShape(20.dp),

        // Light green background
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFE7F1EA)
        ),

        // Small shadow
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Room icon
            Text(
                text = "🏠",
                fontSize = 30.sp
            )

            Spacer(modifier = Modifier.width(18.dp))

            Column {

                // Room name
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(3.dp))

                // Small description
                Text(
                    text = "Tap to organize this room",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )

                //
            }
        }
    }
}