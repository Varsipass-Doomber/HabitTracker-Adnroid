package com.example.habittracker.ui.screen.tasks

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.habittracker.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(
    onNavigateBack: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var importance by remember { mutableFloatStateOf(3f) }
    var difficulty by remember { mutableFloatStateOf(3f) }
    var isHabit by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.AddTaskScreen_Header)) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text(stringResource(R.string.TaskNameParameter_FieldTitle)) },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text(stringResource(R.string.SphereChoiceParameter_FieldTitle)) },
                modifier = Modifier.fillMaxWidth(),
                enabled = false
            )

            Text(stringResource(R.string.ImportanceParameter_Header) + "${importance.toInt()}")
            Slider(value = importance, onValueChange = { importance = it }, valueRange = 1f..5f, steps = 3)

            Text(stringResource(R.string.DifficultyParameter_Header) + "${difficulty.toInt()}")
            Slider(value = difficulty, onValueChange = { difficulty = it }, valueRange = 1f..5f, steps = 3)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(stringResource(R.string.RepeatParameter_Header))
                Switch(checked = isHabit, onCheckedChange = { isHabit = it })
            }

            Button(
                onClick = { /* TODO: Сохранение */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)
            ) {
                Text(stringResource(R.string.SaveTask_Text))
            }
        }
    }
}