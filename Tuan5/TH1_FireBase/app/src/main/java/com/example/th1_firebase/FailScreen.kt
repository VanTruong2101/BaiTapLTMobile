package com.example.th1_firebase

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun FailScreen(navController: NavController) {
    Column(modifier = Modifier.padding(50.dp)
        .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Button(
            onClick = { navController.navigate("screen1") },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Blue,
                contentColor = Color.White
            ),
            modifier = Modifier.size(width = 300.dp, height = 50.dp)
        ) {
            Text(text = "Login by Gmail", style = TextStyle(fontSize = 18.sp))
        }
        Column(modifier = Modifier.padding(top = 20.dp)
            .background(Color(0xFFEB9797),
                shape = RoundedCornerShape(16.dp)
            )
            .size(width = 300.dp, height = 150.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Text(text = "Google Sign-In Failed",
                 style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "User canceled the Google sign-in process",
                style = TextStyle(fontSize = 18.sp) ,
                textAlign = TextAlign.Center
            )
        }

    }
}