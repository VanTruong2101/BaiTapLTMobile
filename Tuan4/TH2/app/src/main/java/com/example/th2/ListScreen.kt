package com.example.th2

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.th2.ui.theme.TopBar

@Composable
fun ListScreen(navController: NavController, viewModel: TaskViewModel = viewModel()) {
    val uiState = viewModel.uiState.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .padding(horizontal = 16.dp)
    ) {
        TopBar(title = "List", navController) // Truyền NavController

        when {
            uiState.isLoading -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(Modifier.align(Alignment.Center))
                }
            }
            uiState.isError -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    EmptyScreen(navController)
                    uiState.errorMessage?.let {
                        Text(text = it, color = MaterialTheme.colorScheme.error)
                    }
                    Button(onClick = { viewModel.refresh() }) {
                        Text("Retry")
                    }
                }
            }
            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 16.dp, bottom = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.tasks) { task ->
                        TaskItem(task = task, navController = navController)
                    }
                }
            }
        }
    }
}

@Composable
fun TaskItem(task: Task, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate("detail/${task.id}") // Điều hướng đến DetailScreen với taskId
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = task.title, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = task.description, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Status: ${task.status}", style = MaterialTheme.typography.bodySmall)
            task.subtasks?.let { subtasks ->
                Text(text = "Subtasks: ${subtasks.joinToString { it.title }}", style = MaterialTheme.typography.bodySmall)
            }
            task.attachments?.let { attachments ->
                Text(text = "Attachments: ${attachments.joinToString { it.fileName }}", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}