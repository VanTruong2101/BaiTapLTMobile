package com.example.th1.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Định nghĩa các màu cơ bản cho Light, Dark, Pink theme
private val LightColorScheme = lightColorScheme(
    primary = AppColors.LightBackground, // Màu nền
    onPrimary = AppColors.LightText,     // Màu chữ trên nền primary
    surface = AppColors.LightBackground, // Màu bề mặt (nền các thành phần)
    onSurface = AppColors.LightText      // Màu chữ trên bề mặt
)

private val DarkColorScheme = darkColorScheme(
    primary = AppColors.DarkBackground,
    onPrimary = AppColors.DarkText,
    surface = AppColors.DarkBackground,
    onSurface = AppColors.DarkText
)

private val PinkColorScheme = lightColorScheme(
    primary = AppColors.PinkBackground,
    onPrimary = AppColors.LightText,
    surface = AppColors.PinkBackground,
    onSurface = AppColors.LightText
)
private val BlueColorScheme = lightColorScheme(
    primary = AppColors.BlueBackground,
    onPrimary = AppColors.LightText,
    surface = AppColors.BlueBackground,
    onSurface = AppColors.LightText
)
// Định nghĩa các màu có sẵn (tạm giữ lại từ code gốc)
object AppColors {
    val LightBackground = Color(0xFFFFFFFF)
    val DarkBackground = Color(0xFF121212)
    val PinkBackground = Color(0xFFFFC1CC)
    val BlueBackground = Color(0xFF81D4FA)
    val LightText = Color(0xFF000000)
    val DarkText = Color(0xFFFFFFFF)
}

@Composable
fun TH1Theme(
    theme: String, // Nhận giá trị theme từ DataStore ("light", "dark", "pink")
    content: @Composable () -> Unit
) {
    // Chọn colorScheme dựa trên theme
    val colorScheme = when (theme) {
        "light" -> LightColorScheme
        "dark" -> DarkColorScheme
        "pink" -> PinkColorScheme
        "blue" -> BlueColorScheme
        else -> LightColorScheme // Mặc định là Light nếu theme không hợp lệ
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}