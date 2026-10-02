package com.example.scholarnavigate.ui.screen.Login

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.firebaseauthyt.presentation.login_screen.SignInViewModel
import com.example.scholarnavigate.R
import com.example.scholarnavigate.data.module.UserData
import com.example.scholarnavigate.navigate.Graph
import com.example.scholarnavigate.ui.screen.Login.components.GoogleSignInButton
import com.example.scholarnavigate.ui.theme.ScholarNavigateTheme
import com.example.scholarnavigate.ui.theme.onSurfaceVariantLight
import com.example.scholarnavigate.ui.theme.primaryLight
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@Composable
fun LoginUi(
    navController: NavController
) {

    val viewModel: SignInViewModel = hiltViewModel()
    val context = LocalContext.current
    val token = context.getString(R.string.webId)
    var user by remember { mutableStateOf(Firebase.auth.currentUser) }
    val launcher = rememberFirebaseAuthLauncher(
        onAuthComplete = { result ->
            user = result.user
            viewModel.addUser(
                UserData(
                    userId = 1,
                    username = user?.displayName,
                    profilePictureUrl = user?.photoUrl.toString(),
                    email = user?.email
                )
            )
            navController.navigate(Graph.HOME) {
                popUpTo(Graph.AUTHENTCATION) { inclusive = true }
            }
            Toast.makeText(context,"SingIn Successful", Toast.LENGTH_LONG).show()
        },
        onAuthError = {
            user = null
            Toast.makeText(context,"Couldn't SignIn", Toast.LENGTH_LONG).show()
        }
    )


    ScholarNavigateTheme {
        Column(
            Modifier
                .fillMaxSize()
                .background(
                    if (isSystemInDarkTheme()) primaryLight else MaterialTheme.colorScheme.inversePrimary
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    if (isSystemInDarkTheme()) onSurfaceVariantLight else MaterialTheme.colorScheme.onPrimaryContainer
                )
            ) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        modifier = Modifier.size(200.dp),
                        painter = painterResource(id = R.drawable.applogo),
                        contentDescription = "Scholar Navigate",
                        colorFilter = ColorFilter.tint(if (isSystemInDarkTheme()) Color.White else Color.Blue)
                    )
                    Spacer(modifier = Modifier.height(25.dp))
                    GoogleSignInButton(
                        onClick = {
                            val gso =
                                GoogleSignInOptions
                                    .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                    .requestIdToken(token)
                                    .requestEmail()
                                    .build()
                            val googleSignInClient = GoogleSignIn
                                .getClient(context, gso)
                            launcher
                                .launch(googleSignInClient.signInIntent)
                        },



                    )
                    Spacer(modifier = Modifier.height(30.dp))
                }

            }
        }
    }

}


@Composable
private fun rememberFirebaseAuthLauncher(
    onAuthComplete: (AuthResult) -> Unit,
    onAuthError: (ApiException) -> Unit
): ManagedActivityResultLauncher<Intent, ActivityResult> {
    val scope = rememberCoroutineScope()
    return rememberLauncherForActivityResult(
        ActivityResultContracts
        .StartActivityForResult()) { result ->
        val task = GoogleSignIn
            .getSignedInAccountFromIntent(result.data)
        try {
            val account = task
                .getResult(ApiException::class.java)!!
            Log.d("GoogleAuth", "account $account")
            val idToken = account.idToken
            if (idToken == null) {
                Log.d("GoogleAuth", "ID token is null")
                onAuthError(ApiException(com.google.android.gms.common.api.Status.RESULT_INTERNAL_ERROR))
                return@rememberLauncherForActivityResult
            }
            val credential = GoogleAuthProvider
                .getCredential(idToken, null)
            scope.launch {
                try {
                    val authResult = Firebase
                        .auth.signInWithCredential(credential).await()
                    onAuthComplete(authResult)
                } catch (e: Exception) {
                    Log.d("GoogleAuth", "signInWithCredential failed: $e")
                    onAuthError(ApiException(com.google.android.gms.common.api.Status.RESULT_INTERNAL_ERROR))
                }
            }
        } catch (e: ApiException) {
            Log.d("GoogleAuth", e.toString())
            onAuthError(e)
        }
    }
}
