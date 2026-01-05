package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.theme.MyApplicationTheme // ✅ Use our single, unified theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // ✅ The entire app is wrapped in one theme
            MyApplicationTheme {
                FieldResearchApp()
            }
        }
    }
}

@Composable
fun FieldResearchApp() {
    val navController = rememberNavController()
    // ✅ State is declared once, at the top level
    val tasks = remember { mutableStateListOf<String>() }

    NavHost(
        navController = navController,
        startDestination = "task_list"
    ) {
        // ✅ No need to wrap this in another theme
        composable("task_list") {
            TaskListScreen(
                tasks = tasks,
                onAddTaskClick = { navController.navigate("create_task") }
            )
        }

        // ✅ No need to wrap this in another theme
        composable("create_task") {
            CreateTaskScreen(
                onSaveTask = { newTask ->
                    if (newTask.isNotBlank()) {
                        tasks.add(newTask)
                    }
                    navController.popBackStack()
                },
                // ✅ Use the correct parameter name: onBackClick
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
