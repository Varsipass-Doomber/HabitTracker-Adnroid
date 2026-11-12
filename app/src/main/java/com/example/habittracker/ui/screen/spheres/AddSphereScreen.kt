package com.example.habittracker.ui.screen.spheres

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.habittracker.R

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddSphereScreen(
    onNavigateBack: () -> Unit
) {
    var name by remember { mutableStateOf("") }

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
                onValueChange = { name = it },
                label = { Text(stringResource(R.string.SphereNameParameterFieldTitle)) },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = { /* TODO */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.SaveFieldText))
            }
        }
    }
}