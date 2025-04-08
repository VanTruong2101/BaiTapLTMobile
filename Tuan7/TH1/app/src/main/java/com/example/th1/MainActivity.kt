package com.example.th1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.th1.ui.theme.AppColors
import com.example.th1.ui.theme.TH1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val themePreferences = ThemePreferences(this)
            val viewModel: ThemeViewModel = viewModel(factory = ThemeViewModelFactory(themePreferences))
            val theme by viewModel.theme

            TH1Theme(theme = theme) {
                SettingScreen(viewModel)
            }
        }
    }
}

@Composable
fun SettingScreen(viewModel: ThemeViewModel) {
    // Lấy giá trị theme hiện tại từ ViewModel (read-only)
    val currentTheme by viewModel.theme
    // Tạo MutableState để lưu theme tạm thời
    val selectedTheme = remember { mutableStateOf(currentTheme) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "SETTING",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Choosing the right theme sets the tone and personality of your app",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Theme options
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ThemeOption(
                color = AppColors.LightBackground,
                text = "Light",
                isSelected = selectedTheme.value == ThemePreferences.LIGHT,
                onClick = { selectedTheme.value = ThemePreferences.LIGHT }
            )
            ThemeOption(
                color = AppColors.DarkBackground,
                text = "Dark",
                isSelected = selectedTheme.value == ThemePreferences.DARK,
                onClick = { selectedTheme.value = ThemePreferences.DARK }
            )
            ThemeOption(
                color = AppColors.PinkBackground,
                text = "Pink",
                isSelected = selectedTheme.value == ThemePreferences.PINK,
                onClick = { selectedTheme.value = ThemePreferences.PINK }
            )
            ThemeOption(
                color = AppColors.BlueBackground,
                text = "Blue",
                isSelected = selectedTheme.value == ThemePreferences.BLUE,
                onClick = { selectedTheme.value = ThemePreferences.BLUE }
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Apply button
        Button(
            onClick = { viewModel.saveTheme(selectedTheme.value) },
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .height(48.dp),
            shape = RoundedCornerShape(24.dp)
        ) {
            Text(text = "Apply", fontSize = 16.sp)
        }
    }
}

@Composable
fun ThemeOption(color: Color, text: String, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(80.dp)
            .background(color, shape = RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            color = if (color == AppColors.DarkBackground) Color.White else Color.Black
        )
    }
}