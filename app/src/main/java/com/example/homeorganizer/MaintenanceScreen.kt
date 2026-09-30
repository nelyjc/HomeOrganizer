package com.example.homeorganizer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


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