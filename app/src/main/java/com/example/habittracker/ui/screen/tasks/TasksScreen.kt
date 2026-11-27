package com.example.habittracker.ui.screen.tasks

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.habittracker.R
import com.example.habittracker.ui.theme.AppShapes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksScreen(
    viewmodel: TasksViewModel,
    onNavigateAdd: () -> Unit,
    onTaskClick: (Long) -> Unit
) {
    val uiState by viewmodel.uiState.collectAsStateWithLifecycle()

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
//            Missed task section
            if (uiState.overdue.isNotEmpty()) {
                item { SectionHeader(stringResource(R.string.TaskDangerSection_Header)) }
                items(uiState.overdue, key = { it.task.id }) { tws ->
                    TaskCard(
                        data = tws,
                        onCardClick = { onTaskClick(tws.task.id) },
                        onDoneClick = { /* TODO: Implement completing */ }
                    )
                }
            }

//            Today tasks section
            if (uiState.today.isNotEmpty()) {
                item { SectionHeader(stringResource(R.string.TasksTodaySection_Header)) }
                items(uiState.today, key = { it.task.id }) { tws ->
                    TaskCard(
                        data = tws,
                        onCardClick = { onTaskClick(tws.task.id) },
                        onDoneClick = { /* Implement completing */ }
                    )
                }
            }

//            Near future tasks section
            if (uiState.soon.isNotEmpty()) {
                item { SectionHeader(stringResource(R.string.UpcomingTasksSection_Header)) }
                items(uiState.soon, key = { it.task.id }) { tws ->
                    TaskCard(
                        data = tws,
                        onCardClick = { onTaskClick(tws.task.id) },
                        onDoneClick = { /* TODO: Implement completing */ }
                    )
                }
            }

            if (uiState.overdue.isEmpty() && uiState.today.isEmpty() && uiState.soon.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier.fillMaxWidth().padding(top = 32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "Нет активных задач",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TaskCard(
    title: String, sphere: String, xp: Int, // TODO: Replace fake parameters
    onCardClick: () -> Unit,
    onDoneClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        onClick = onCardClick
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
                onClick = onDoneClick,
                shape = AppShapes.medium
            ) {
                Icon(Icons.Default.Check, contentDescription = "Выполнить задачу")
            }
        }
    }
}

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
    )
}

@Composable
fun TaskCard(
    data: TaskWithSphere,
    onCardClick: () -> Unit,
    onDoneClick: () -> Unit
) {
    val sphereColor = Color((data.sphere?.colorHex ?: "#7B5A43").toColorInt())

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        onClick = onCardClick
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(12.dp),
                shape = androidx.compose.foundation.shape.CircleShape,
                color = sphereColor
            ) {}

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = data.task.title, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium)
                Text(text = data.sphere?.name ?: "Без сферы", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }

            Text(
                text = "+${data.task.importance * data.task.difficulty} XP",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )

            IconButton(onClick = onDoneClick) {
                Icon(Icons.Default.Check, contentDescription = "Выполнить задачу")
            }
        }
    }
}