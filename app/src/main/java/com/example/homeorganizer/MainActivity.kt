package com.example.homeorganizer
import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import com.example.homeorganizer.ui.theme.HomeOrganizerTheme


//THIS IS THE START OF JUST THE MAIN SCREEN**********************************
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

//THIS IS THE END OF THE MAIN SCREEN**********************************************



