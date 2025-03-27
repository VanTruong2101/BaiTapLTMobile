package com.example.th2_app_uth

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.th2_app_uth.ui.theme.TH2_APP_UTHTheme
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


private const val TAG = "GoogleSignIn"

@Composable
fun StartScreen(navController: NavHostController) {
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    val signInClient: SignInClient = Identity.getSignInClient(context)

    val signInRequest = BeginSignInRequest.builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setServerClientId("1063102053472-54ud8df4slqchi7h65bf4v4hb238ksn5.apps.googleusercontent.com")
                .setFilterByAuthorizedAccounts(false)
                .build()
        )
        .setAutoSelectEnabled(true)
        .build()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        try {
            val credential = signInClient.getSignInCredentialFromIntent(result.data)
            val idToken = credential.googleIdToken
            if (idToken != null) {
                val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
                auth.signInWithCredential(firebaseCredential)
                    .addOnSuccessListener {
                        Log.d(TAG, "signInWithCredential:success")
                        navController.navigate("ProfileScreen") // Sửa tên route
                    }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "signInWithCredential:failure", e)
                    }
            } else {
                Log.w(TAG, "No ID token!")
            }
        } catch (e: Exception) { // Xử lý mọi ngoại lệ
            Log.w(TAG, "Sign in with Google failed", e)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = null,
            modifier = Modifier
                .size(220.dp)
                .background(Color(0xFFB5D3F5), RoundedCornerShape(16.dp))
                .padding(16.dp)
        )
        Spacer(modifier = Modifier.size(16.dp))
        Text(text = "SmartTasks", style = TextStyle(color = Color.Blue, fontWeight = FontWeight.Bold, fontSize = 28.sp))
        Spacer(modifier = Modifier.size(16.dp))
        Text(text = "A simple and efficient to-do app", style = TextStyle(color = Color.Black, fontSize = 16.sp))
        Spacer(modifier = Modifier.size(100.dp))
        Text(text = "Welcome", style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp))
        Spacer(modifier = Modifier.size(14.dp))
        Text(text = "Ready to explore? Log in to get started.", style = TextStyle(fontSize = 16.sp))
        Spacer(modifier = Modifier.size(24.dp))
        Button(
            onClick = {
                signInClient.beginSignIn(signInRequest)
                    .addOnSuccessListener { result ->
                        Log.d(TAG, "Begin sign in successful")
                        try {
                            launcher.launch(IntentSenderRequest.Builder(result.pendingIntent.intentSender).build())
                        } catch (e: Exception) {
                            Log.w(TAG, "Couldn't start Sign In: ${e.localizedMessage}", e)
                        }
                    }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "Begin sign in failed", e)
                    }
            },
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .size(width = 300.dp, height = 55.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB5D3F5)),
            shape = RoundedCornerShape(8.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_gg),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = "SIGN IN WITH GOOGLE",
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TH2_APP_UTHTheme {
        StartScreen(navController = NavHostController(LocalContext.current))
    }
}