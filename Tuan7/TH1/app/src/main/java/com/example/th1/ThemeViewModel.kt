package com.example.th1

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ThemeViewModel(private val themePreferences: ThemePreferences) : ViewModel() {
    private val _theme = mutableStateOf(ThemePreferences.LIGHT)
    val theme: State<String> = _theme

    init {
        // Đọc theme từ DataStore khi khởi tạo
        viewModelScope.launch {
            themePreferences.theme.collect { savedTheme ->
                _theme.value = savedTheme
            }
        }
    }

    // Lưu theme mới
    fun saveTheme(newTheme: String) {
        viewModelScope.launch {
            themePreferences.saveTheme(newTheme)
        }
    }
}