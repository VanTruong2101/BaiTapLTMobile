package com.example.th1_firebase

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import androidx.compose.foundation.shape.RoundedCornerShape

@Composable
fun ScreenSuccess(navController: NavController) {
    val user = FirebaseAuth.getInstance().currentUser
    val displayName = user?.displayName ?: "User" // hiển thị tên user
    val email = user?.email ?: "Email"  // hiển thị email
    Column(
        modifier = Modifier
            .padding(50.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                FirebaseAuth.getInstance().signOut()
                navController.navigate("screen1") {
                    popUpTo("screen1") { inclusive = true }
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Blue,
                contentColor = Color.White
            ),
            modifier = Modifier.size(width = 300.dp, height = 50.dp)
        ) {
            Text(text = "Logout", style = TextStyle(fontSize = 20.sp))
        }
        Column(
            modifier = Modifier
                .padding(top = 20.dp)
                .background(
                    color = Color(0xFF4AABD2),
                    shape = RoundedCornerShape(16.dp) // Bo góc với bán kính 16dp
                )
                .size(width = 300.dp, height = 150.dp),
                verticalArrangement = Arrangement.Center, // Căn giữa theo chiều dọc
                horizontalAlignment = Alignment.CenterHorizontally // Căn giữa theo chiều ngang
        ) {
            Text(
                text = "Success!",
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Hi $email",
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Welcome to UTHSmartTasks",
                style = TextStyle( fontSize = 18.sp)
            )
        }
    }
}