package com.example.habittracker.ui.screen.tasks

import android.graphics.drawable.shapes.Shape
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.habittracker.R
import com.example.habittracker.ui.theme.AppShapes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksScreen(
    onNavigateAdd : () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.TasksScreen_Header))
                        },
                actions = {
                    IconButton(onClick = onNavigateAdd) {
                        Icon(Icons.Default.Add, contentDescription = "Добавить задачу")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Text(
                    text = stringResource(R.string.TaskDangerSection_Header),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                )
            }
            item { TaskCard(title = "Просроченная задача", sphere = "Телосложение", xp = 15) }

            item {
                Text(
                    text = stringResource(R.string.TasksTodaySection_Header),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                )
            }
            item { TaskCard(title = "Сделать зарядку", sphere = "Телосложение", xp = 10) }
            item { TaskCard(title = "Выпить 8 стаканов воды", sphere = "Здоровье", xp = 12) }

            item {
                Text(
                    text = stringResource(R.string.UpcomingTasksSection_Header),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                )
            }
            item { TaskCard(title = "Прочитать главу книги", sphere = "Учеба", xp = 20) }
        }
    }
}

@Composable
fun TaskCard(title: String, sphere: String, xp: Int) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(12.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(text = title, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium)
                Text(text = sphere, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Text(text = "+$xp XP", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
            IconButton(
                onClick = { /* TODO */ },
                shape = AppShapes.medium
            ) {
                Icon(Icons.Default.Check, contentDescription = "Выполнить задачу")
            }
        }
    }
}