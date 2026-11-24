package com.example.habittracker.ui.navigation

sealed class Screen(val route : String) {
    object TaskScreen : Screen("task_screen")
    object CalendarScreen : Screen("calendar_screen")
    object SpheresScreen : Screen("spheres_screen")
    object SettingsScreen : Screen("settings_screen")
    object AddTaskScreen : Screen("add_task_screen")
    object AddSphereScreen : Screen("add_sphere_screen")
    object SphereDetailScreen : Screen("sphere_detail_screen/{sphereId}") {
        fun createRoute(sphereId: Long) = "sphere_detail_screen/$sphereId"
    }
}