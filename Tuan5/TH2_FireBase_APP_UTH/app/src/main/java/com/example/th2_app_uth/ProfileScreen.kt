package com.example.th2_app_uth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ProfileScreen(navController: NavHostController) {
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser

    val name = remember { mutableStateOf(currentUser?.displayName ?: "Unknown") }
    val email = remember { mutableStateOf(currentUser?.email ?: "No email") }
    val dob = remember { mutableStateOf("21/02/2004") }

    LaunchedEffect(currentUser) {
        name.value = currentUser?.displayName ?: "Unknown"
        email.value = currentUser?.email ?: "No email"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TopBar("Profile", navController)
        Spacer(modifier = Modifier.height(14.dp))

        Box(
            modifier = Modifier
                .size(120.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Image(
                imageVector = Icons.Default.Person,
                contentDescription = "User Avatar",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .border(1.dp, Color.Gray, CircleShape)
            )
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Update Avatar",
                tint = Color.Gray,
                modifier = Modifier
                    .size(26.dp)
                    .offset(y = (15).dp)
                    .align(Alignment.BottomEnd)
                    .background(Color.Black.copy(alpha = 0.6f), CircleShape)
                    .clip(CircleShape)
                    .padding(6.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Căn trái các tiêu đề và trường nhập
        Text(
            text = "Name",
            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = name.value,
            onValueChange = { },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            textStyle = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Gray)
        )

        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Email",
            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = email.value,
            onValueChange = { },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            textStyle = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Gray)
        )

        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Date of Birth",
            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = dob.value,
            onValueChange = { },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            textStyle = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Gray),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "Edit Date of Birth",
                    tint = Color.Gray,
                    modifier = Modifier
                        .clickable { println("Edit Date of Birth clicked!") }
                        .size(30.dp)
                )
            }
        )

        Spacer(modifier = Modifier.height(300.dp))
        Button(
            onClick = { },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF3184DE),
                contentColor = Color.White
            ),
            modifier = Modifier
                .size(width = 380.dp, height = 50.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = "Back",
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp)
            )
        }
    }
}

@Composable
fun TopBar(title: String, navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 26.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_b),
            contentDescription = "Back Icon",
            modifier = Modifier
                .size(40.dp)
                .clickable {
                    navController.popBackStack()
                }
        )

        Text(
            text = title,
            color = Color.Blue,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .weight(1f)

        )
        Spacer(modifier = Modifier.size(40.dp))
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewDefault(){
    ProfileScreen(navController = NavHostController(LocalContext.current))
}