package com.example.bt4.screen.appScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.bt4.Task
import com.example.bt4.TaskViewModel
import com.example.bt4.screen.SmartTasksTopBar

@Composable
fun AppScreenOne(
    navController: NavController,
    taskViewModel: TaskViewModel = viewModel(),
    onTaskClick: (Int) -> Unit = {}
) {
    // Sử dụng uiState từ TaskViewModel
    val uiState by taskViewModel.uiState.collectAsState()
    
    // Gọi API để lấy danh sách task khi màn hình được tạo
    LaunchedEffect(key1 = Unit) {
        taskViewModel.fetchTasks()
    }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)) // Background color giống trong hình
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
        ) {
            // Sử dụng SmartTasksTopBar có sẵn
            SmartTasksTopBar()
            
            // Nội dung chính
            when {
                uiState.isLoading -> {
                    // Hiển thị loading
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                uiState.isError || uiState.tasks.isEmpty() -> {
                    // Dùng EmptyScreen hiện có khi có lỗi hoặc không có dữ liệu
                    EmptyScreen(navController = navController)
                }
                else -> {
                    // Hiển thị danh sách tasks
                    TaskListScreen(
                        tasks = uiState.tasks,
                        onTaskClick = onTaskClick
                    )
                }
            }
        }
        
        // BottomBar sẽ được xử lý riêng trong MainActivity
        // Để dành khoảng trống cho BottomBar
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun TaskListScreen(
    tasks: List<Task>,
    onTaskClick: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Tiêu đề SmartTasks đã được hiển thị trong TopBar
        
        items(tasks) { task ->
            TaskCardItem(task = task, onClick = { onTaskClick(task.id) })
        }
        
        // Thêm khoảng trống ở cuối để không bị che bởi BottomBar
        item {
            Spacer(modifier = Modifier.height(72.dp))
        }
    }
}

@Composable
fun TaskCardItem(task: Task, onClick: () -> Unit) {
    // Xác định màu nền dựa trên category hoặc priority của task
    val backgroundColor = when {
        task.category.equals("work", ignoreCase = true) -> Color(0xFFF8D7DA) // Light red (like in the image)
        task.category.equals("health", ignoreCase = true) -> Color(0xFFD1E7DD) // Light green
        task.category.equals("fitness", ignoreCase = true) -> Color(0xFFCFE2FF) // Light blue (like in the image)
        task.priority.equals("high", ignoreCase = true) -> Color(0xFFF8D7DA) // Light red
        task.priority.equals("medium", ignoreCase = true) -> Color(0xFFD1E7DD) // Light green
        else -> Color(0xFFCFE2FF) // Light blue
    }
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Task title với checkbox
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Checkbox để hiển thị trạng thái completed
                Checkbox(
                    checked = task.status.equals("completed", ignoreCase = true),
                    onCheckedChange = null, // Read-only
                    colors = CheckboxDefaults.colors(
                        checkedColor = MaterialTheme.colorScheme.primary,
                        uncheckedColor = MaterialTheme.colorScheme.outline
                    )
                )
                
                Spacer(modifier = Modifier.width(8.dp))
                
                // Task title
                Text(
                    text = task.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            
            // Task description
            Text(
                text = task.description,
                fontSize = 14.sp,
                modifier = Modifier.padding(start = 40.dp), // Căn thẳng với text title
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Status và dueDate
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Status
                Text(
                    text = "Status: ${formatStatus(task.status)}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
                
                // Due date
                Text(
                    text = formatDueDate(task.dueDate),
                    fontSize = 14.sp
                )
            }
        }
    }
}

// Hàm để định dạng status
fun formatStatus(status: String): String {
    return when {
        status.equals("completed", ignoreCase = true) -> "Completed"
        status.equals("in_progress", ignoreCase = true) -> "In Progress"
        else -> "Pending"
    }
}

// Hàm để định dạng ngày giờ theo định dạng giống trong hình "14:00 2500-03-26"
fun formatDueDate(dateString: String): String {
    val parts = dateString.split("T")
    if (parts.size < 2) return dateString
    
    val date = parts[0]
    val time = parts[1].substringBefore(".")
    val timeFormatted = if (time.length >= 5) time.substring(0, 5) else time
    
    return "$timeFormatted $date"
}

@Preview(showBackground = true)
@Composable
fun TaskCardItemPreview() {
    val task = Task(
        id = 1,
        title = "Complete Android Project",
        description = "Finish the UI, integrate API, and write documentation",
        status = "IN_PROGRESS",
        priority = "HIGH",
        category = "WORK",
        dueDate = "2500-03-26T14:00:00",
        createdAt = "2024-03-23T08:00:00",
        updatedAt = "2024-03-23T08:00:00",
        subtasks = null,
        attachments = null,
        reminders = null
    )
    
    Surface {
        TaskCardItem(task = task, onClick = {})
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun AppScreenOnePreview() {
    val tasks = listOf(
        Task(
            id = 1,
            title = "Complete Android Project",
            description = "Finish the UI, integrate API, and write documentation",
            status = "IN_PROGRESS",
            priority = "HIGH",
            category = "WORK",
            dueDate = "2500-03-26T14:00:00",
            createdAt = "2024-03-23T08:00:00",
            updatedAt = "2024-03-23T08:00:00",
            subtasks = null,
            attachments = null,
            reminders = null
        ),
        Task(
            id = 2,
            title = "Doctor Appointment 2",
            description = "This task is related to Work. It needs to be completed",
            status = "TODO",
            priority = "MEDIUM",
            category = "HEALTH",
            dueDate = "2500-03-26T14:00:00",
            createdAt = "2024-03-23T08:00:00",
            updatedAt = "2024-03-23T08:00:00",
            subtasks = null,
            attachments = null,
            reminders = null
        ),
        Task(
            id = 3,
            title = "Meeting",
            description = "This task is related to Fitness. It needs to be completed",
            status = "TODO",
            priority = "LOW",
            category = "FITNESS",
            dueDate = "2500-03-26T14:00:00",
            createdAt = "2024-03-23T08:00:00",
            updatedAt = "2024-03-23T08:00:00",
            subtasks = null,
            attachments = null,
            reminders = null
        )
    )
    
    MaterialTheme {
        Surface {
            Box(modifier = Modifier.background(Color(0xFFF5F5F5))) {
                Column {
                    // Giả lập SmartTasksTopBar
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "SmartTasks",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2196F3)
                        )
                        
                        Spacer(modifier = Modifier.width(8.dp))
                        
                        Text(
                            text = "A simple and efficient to-do app",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                    
                    TaskListScreen(
                        tasks = tasks,
                        onTaskClick = {}
                    )
                }
                
                // Giả lập BottomBar
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .background(Color.White)
                        .align(Alignment.BottomCenter)
                )
            }
        }
    }
}



