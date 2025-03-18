package com.example.th2

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.th2.ui.theme.TH2Theme
import com.example.th2.ui.theme.TopBar

@Composable
fun DetailScreen(taskId: Int, navController: NavController, viewModel: TaskViewModel = viewModel()) {
    val uiState = viewModel.uiState.collectAsState().value

    LaunchedEffect(Unit) {
        if (uiState.tasks.isEmpty() && !uiState.isLoading) {
            viewModel.fetchTasks()
        }
    }

    LaunchedEffect(uiState.tasks) {
        if (uiState.tasks.isNotEmpty()) {
            viewModel.selectTask(taskId)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBar("Detail", navController) // Truyền NavController

        when {
            uiState.isLoading -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(Modifier.align(Alignment.Center))
                    Text(
                        text = "Loading task details...",
                        modifier = Modifier.align(Alignment.Center).padding(top = 48.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
            uiState.isError -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = uiState.errorMessage ?: "An error occurred",
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { viewModel.fetchTasks() }) {
                        Text("Retry")
                    }
                }
            }
            uiState.selectedTask == null -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Task not found",
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center
                    )
                }
            }
            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {
                    item {
                        Text(
                            text = uiState.selectedTask.title,
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = uiState.selectedTask.description,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                    }

                    item {
                        Text(
                            text = "Subtasks",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        uiState.selectedTask.subtasks?.let { subtasks ->
                            Column {
                                subtasks.forEach { subtask ->
                                    OutlinedCard(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(bottom = 8.dp)
                                    ) {
                                        Text(
                                            text = "${subtask.title}. It needs to be completed",
                                            style = MaterialTheme.typography.bodyMedium,
                                            modifier = Modifier.padding(16.dp)
                                        )
                                    }
                                }
                            }
                        } ?: Text(
                            text = "No subtasks available",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                    }

                    item {
                        Text(
                            text = "Attachments",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                        )
                        uiState.selectedTask.attachments?.let { attachments ->
                            Column {
                                attachments.forEach { attachment ->
                                    OutlinedCard(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(bottom = 8.dp)
                                    ) {
                                        Text(
                                            text = attachment.fileName,
                                            style = MaterialTheme.typography.bodyMedium,
                                            modifier = Modifier.padding(16.dp)
                                        )
                                    }
                                }
                            }
                        } ?: Text(
                            text = "No attachments available",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    TH2Theme {
        DetailScreen(taskId = 1, navController = rememberNavController())
    }
}