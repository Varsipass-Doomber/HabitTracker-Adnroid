package com.example.habittracker.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val AppShapes = Shapes(
    small = RoundedCornerShape(8.dp),   // Для маленьких элементов (чипсы, теги)
    medium = RoundedCornerShape(12.dp), // Для карточек, кнопок
    large = RoundedCornerShape(16.dp)   // Для больших диалогов, bottom sheets
)