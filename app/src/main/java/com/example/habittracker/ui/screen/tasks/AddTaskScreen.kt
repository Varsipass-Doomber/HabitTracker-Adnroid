package com.example.habittracker.ui.screen.tasks

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
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
    var selectedSphere by remember { mutableStateOf("") } // TODO: Replace with actual object

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
                singleLine = true,
                value = title,
                onValueChange = { title = it },
                label = { Text(stringResource(R.string.TaskNameParameter_FieldTitle)) },
                modifier = Modifier.fillMaxWidth()
            )

            SphereDropdown(
                 spheres = listOf("Телосложение", "Учеба", "Готовка"),
                 selectedSphere = selectedSphere,
                 onSphereSelected = { selectedSphere = it }
             )

            Text(stringResource(R.string.ImportanceParameter_Header) + "${importance.toInt()}")
            Slider(
                colors = SliderDefaults.colors(
                    thumbColor = MaterialTheme.colorScheme.primary,
                    activeTrackColor = MaterialTheme.colorScheme.primary,
                ),
                value = importance,
                onValueChange = { importance = it },
                valueRange = 1f..5f, steps = 3
            )

            Text(stringResource(R.string.DifficultyParameter_Header) + "${difficulty.toInt()}")
            Slider(
                colors = SliderDefaults.colors(
                    thumbColor = MaterialTheme.colorScheme.primary,
                    activeTrackColor = MaterialTheme.colorScheme.primary,
                ),
                value = difficulty,
                onValueChange = { difficulty = it },
                valueRange = 1f..5f, steps = 3)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(stringResource(R.string.RepeatParameter_Header), textAlign = TextAlign.Center)
                Switch(
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = MaterialTheme.colorScheme.onPrimary,
                        checkedTrackColor = MaterialTheme.colorScheme.primary,
                        uncheckedThumbColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        uncheckedTrackColor = MaterialTheme.colorScheme.surfaceVariant
                    ),
                    checked = isHabit,
                    onCheckedChange = { isHabit = it }
                )
            }
            if (isHabit) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                    FilterChip(
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                            labelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                            ),
                        selected = true,
                        onClick = { /* TODO */ },
                        label = { Text("Каждый день") }
                    )
                    FilterChip(
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                            labelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        ),
                        selected = false,
                        onClick = { /* TODO */ },
                        label = { Text("По дням недели") }
                    )
                }
            }

            Button(
                onClick = { /* TODO: Implement saving */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)
            ) {
                Text(stringResource(R.string.SaveTask_Text))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SphereDropdown(
    spheres: List<String>, selectedSphere: String, // TODO: Replace fake parameters
    onSphereSelected: (String) -> Unit // TODO: Replace with actual object
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
    ) {
        OutlinedTextField(
            value = selectedSphere,
            onValueChange = {},
            readOnly = true,
            label = { Text("Сфера") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            spheres.forEach { sphere ->
                DropdownMenuItem(
                    text = { Text(sphere) },
                    onClick = {
                        onSphereSelected(sphere)
                        expanded = false
                    }
                )
            }
        }
    }
}
