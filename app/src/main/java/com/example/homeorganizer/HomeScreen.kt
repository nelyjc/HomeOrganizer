package com.example.homeorganizer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

//Start of Home Screen*********************
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
            .background(Color(0xFFF7F5F2))
            .padding(24.dp)
    ) {

        // App name /header
        Text(
            text = "🏡 HomeOrganizer",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF2F4F3E)
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Keeping track of your home is easy.",
            style = MaterialTheme.typography.bodyLarge,
            color = Color(0xFF6B6B6B)
        )

        Spacer(modifier = Modifier.height(30.dp))

        OrganizerCard(
            icon = "🛋️",
            title = "Rooms",
            description = "View maintenance by room",
            backgroundColor = Color(0xFFE7F1EA),
            onClick = onRoomsClick
        )

        OrganizerCard(
            icon = "✅",
            title = "Tasks",
            description = "Keep track of things to do",
            backgroundColor = Color(0xFFE8EEF8),
            onClick = onTasksClick
        )

        OrganizerCard(
            icon = "🔧",
            title = "Maintenance",
            description = "View tracking of maintenance",
            backgroundColor = Color(0xFFFFF1DC),
            onClick = onMaintenanceClick
        )

        OrganizerCard(
            icon = "💰",
            title = "Expenses",
            description = "View and update expenses",
            backgroundColor = Color(0xFFF5E8EF),
            onClick = onExpensesClick
        )
    }
}
//Start of cards and style
@Composable
fun OrganizerCard(
    icon: String,
    title: String,
    description: String,
    backgroundColor: Color,
    onClick: () -> Unit
) {

    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
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

            Text(
                text = icon,
                fontSize = 32.sp
            )

            Spacer(modifier = Modifier.width(18.dp))

            Column {

                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF666666)
                )
            }
        }
    }
}