package com.example.habittracker.ui.screen.spheres

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.core.graphics.toColorInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SphereDetailScreen(
    viewModel: SphereDetailViewModel,
    onNavigateBack: () -> Unit,
    onEditClick: () -> Unit
) {
    val sphere by viewModel.sphere.collectAsStateWithLifecycle()
    val tasks by viewModel.tasks.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(sphere?.name ?: "Загрузка...") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
                    }
                },
                actions = {
                    IconButton(onClick = onEditClick) {
                        Icon(Icons.Default.Edit, contentDescription = "Редактировать")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Card(modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)
                    ) {
                        Text("Уровень ${sphere?.level ?: 1}", style = MaterialTheme.typography.titleLarge)

                        Spacer(modifier = Modifier.height(8.dp))

                        Text("Текущий XP: ${sphere?.currentXp ?: 0}", style = MaterialTheme.typography.bodyLarge)

                        Spacer(modifier = Modifier.height(8.dp))

                        LinearProgressIndicator(
                            progress = { 0.5f }, // TODO: Add actual formula
                            modifier = Modifier.fillMaxWidth().height(8.dp),
                            color = Color((sphere?.colorHex ?: "#7B5A43").toColorInt()),
                            trackColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    }
                }
            }

            item {
                Text("Задачи в этой сфере:", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(top = 16.dp))
            }

            if (tasks.isEmpty()) {
                item {
                    Text("Нет активных задач для этой сферы.", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            } else {
                items(tasks, key = { it.id }) { task ->
                    // TODO: Import TaskCard or make another
                    Card(modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(task.title, style = MaterialTheme.typography.bodyLarge)
                            Text("XP: ${task.importance * task.difficulty}", style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
        }
    }
}