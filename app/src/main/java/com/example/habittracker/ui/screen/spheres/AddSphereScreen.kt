package com.example.habittracker.ui.screen.spheres

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.habittracker.R
import androidx.core.graphics.toColorInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddSphereScreen(
    onNavigateBack: () -> Unit,
    onSave: (String, String) -> Unit
) {
    val colors = listOf("#7B5A43", "#4A6741", "#8B5A2B", "#5B4B8A", "#8B3A3A", "#4A6B8A")
    var selectedColor by remember { mutableStateOf(colors.first()) }

    var name by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }
    var isSaving by remember { mutableStateOf(false) }



    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.AddSphereScreenTitle)) },
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
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = {
                    if (it.length < 25) name = it
                    isError = false
                },
                label = { Text(stringResource(R.string.SphereNameParameterFieldTitle)) },
                modifier = Modifier.fillMaxWidth(),
                isError = isError,
                supportingText = {
                    if (isError) {
                        Text("Длина названия от 4 до 30 символов")
                    }
                }
            )

            Text("Цвет сферы", style = MaterialTheme.typography.bodyMedium)

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                colors.forEach { colorHex ->
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(Color(colorHex.toColorInt()))
                            .border(
                                width = if (selectedColor == colorHex) 3.dp else 1.dp,
                                color = MaterialTheme.colorScheme.onBackground,
                                shape = CircleShape
                            )
                            .clickable { selectedColor = colorHex }
                    )
                }
            }

            Button(
                onClick = {
                    if (name.length in 4..<30 && !isSaving) {
                        isSaving = true
                        onSave(name, selectedColor)
                        onNavigateBack()
                    } else {
                        isError = true
                        isSaving = false
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isSaving
            ) {
                Text(stringResource(R.string.SaveFieldText))
            }
        }
    }
}