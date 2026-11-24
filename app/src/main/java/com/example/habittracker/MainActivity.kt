package com.example.habittracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Hexagon
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.habittracker.ui.navigation.Screen
import com.example.habittracker.ui.screen.calendar.CalendarScreen
import com.example.habittracker.ui.screen.settings.SettingsScreen
import com.example.habittracker.ui.screen.spheres.AddSphereScreen
import com.example.habittracker.ui.screen.spheres.SphereDetailScreen
import com.example.habittracker.ui.screen.spheres.SpheresScreen
import com.example.habittracker.ui.screen.spheres.SpheresViewModel
import com.example.habittracker.ui.screen.tasks.AddTaskScreen
import com.example.habittracker.ui.screen.tasks.TasksScreen
import com.example.habittracker.ui.theme.HabitTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HabitTrackerTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val app = context.applicationContext as App



    val items = listOf(
        Screen.TaskScreen to stringResource(R.string.TasksNavigation_Title),
        Screen.CalendarScreen to stringResource(R.string.CalendarNavigation_Title),
        Screen.SpheresScreen to stringResource(R.string.SpheresNavigation_Title),
        Screen.SettingsScreen to stringResource(R.string.SettingsNavigation_Title)
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                contentColor = MaterialTheme.colorScheme.onSurfaceVariant
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                items.forEach { (screen, title) ->
                    NavigationBarItem(
                        selected = currentRoute == screen.route,
                        onClick = {
                            if (currentRoute != screen.route) {
                                navController.popBackStack(navController.graph.startDestinationId, inclusive = false)
                                navController.navigate(screen.route) {
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            indicatorColor = MaterialTheme.colorScheme.primary,
                            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                        icon = {
                            val icon = when (screen) {
                                Screen.TaskScreen -> Icons.Default.List
                                Screen.CalendarScreen -> Icons.Default.DateRange
                                Screen.SpheresScreen -> Icons.Default.Star
                                Screen.SettingsScreen -> Icons.Default.Settings
                                else -> Icons.Default.Hexagon
                            }
                            Icon(imageVector = icon, contentDescription = title)
                        },
                        label = { Text(text = title) }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.TaskScreen.route,
            modifier = Modifier.padding(bottom =  innerPadding.calculateBottomPadding())
        ) {
//            Tasks
            composable(Screen.TaskScreen.route) {
                TasksScreen(
                    onNavigateAdd = {
                        navController.navigate(Screen.AddTaskScreen.route)
                    }
                )
            }
            composable(Screen.AddTaskScreen.route) {
                AddTaskScreen(
                    onNavigateBack = {
                        navController.navigate(Screen.TaskScreen.route)
                    }
                )
            }

//            Calendar
            composable(Screen.CalendarScreen.route) { CalendarScreen() }

//            Spheres
            composable(Screen.SpheresScreen.route) {
                val spheresViewModel: SpheresViewModel = viewModel(
                    factory = SpheresViewModel.provideFactory(app.container.sphereRepository)
                )
                SpheresScreen(
                    viewModel = spheresViewModel,
                    onNavigationAdd = { navController.navigate(Screen.AddSphereScreen.route) },
                    onSphereClick = { sphereId ->
                        navController.navigate(Screen.SphereDetailScreen.createRoute(sphereId))
                    }
                )
            }
            composable(Screen.AddSphereScreen.route) {
                val spheresViewModel: SpheresViewModel = viewModel(
                    factory = SpheresViewModel.provideFactory(app.container.sphereRepository)
                )
                AddSphereScreen(
                    onNavigateBack = { navController.popBackStack() },
                    onSave = { name, selectedColor -> spheresViewModel.addSphere(name, selectedColor) }
                )
            }

            composable(
                route = Screen.SphereDetailScreen.route,
                arguments = listOf(navArgument("sphereId") { type = NavType.LongType })
            ) { backStackEntry ->
                val sphereId = backStackEntry.arguments?.getLong("sphereId") ?: 0L
                SphereDetailScreen(
                    sphereId = sphereId,
                    onNavigateBack = { navController.popBackStack() }
                )
            }

//            Settings
            composable(Screen.SettingsScreen.route) { SettingsScreen() }
        }
    }
}