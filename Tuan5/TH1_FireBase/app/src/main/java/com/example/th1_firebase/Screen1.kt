package com.example.th1_firebase

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

private const val TAG = "GoogleSignIn"

@Composable
fun Screen1(navController: NavController) {
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()


    val signInClient: SignInClient = Identity.getSignInClient(context)

    // Cấu hình BeginSignInRequest
    val signInRequest = BeginSignInRequest.builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setServerClientId("270364026139-7714rbhsukji3hl7ja01nqi28rfso2pm.apps.googleusercontent.com")
                .setFilterByAuthorizedAccounts(false) // Cho phép chọn tài khoản Google bất kỳ
                .build()
        )
        .setAutoSelectEnabled(true) // Tự động chọn tài khoản nếu chỉ có 1 tài khoản
        .build()

    // Launcher để xử lý kết quả đăng nhập
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        try {
            val credential = signInClient.getSignInCredentialFromIntent(result.data)
            val idToken = credential.googleIdToken
            if (idToken != null) {
                Log.d(TAG, "Got ID token.")
                val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
                auth.signInWithCredential(firebaseCredential)
                    .addOnSuccessListener {
                        Log.d(TAG, "signInWithCredential:success")
                        navController.navigate("screenSuccess")
                    }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "signInWithCredential:failure", e)
                        navController.navigate("failScreen")
                    }
            } else {
                Log.w(TAG, "No ID token!")
                navController.navigate("failScreen")
            }
        } catch (e: ApiException) {
            Log.w(TAG, "Sign in with Google failed", e)
            navController.navigate("failScreen")
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Button(
            onClick = {
                // Bắt đầu quá trình đăng nhập
                signInClient.beginSignIn(signInRequest)
                    .addOnSuccessListener { result ->
                        Log.d(TAG, "Begin sign in successful")
                        try {
                            launcher.launch(
                                IntentSenderRequest.Builder(result.pendingIntent.intentSender).build()
                            )
                        } catch (e: Exception) {
                            Log.w(TAG, "Couldn't start Sign In: ${e.localizedMessage}")
                            navController.navigate("failScreen")
                        }
                    }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "Begin sign in failed", e)
                        navController.navigate("failScreen")
                    }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Blue,
                contentColor = Color.White
            ),
            modifier = Modifier.padding(50.dp)
                .size(width = 300.dp, height = 50.dp)
        ) {
            Text(text = "Login by Gmail", style = TextStyle(fontSize = 18.sp), fontWeight = FontWeight.Bold)
        }
    }

}