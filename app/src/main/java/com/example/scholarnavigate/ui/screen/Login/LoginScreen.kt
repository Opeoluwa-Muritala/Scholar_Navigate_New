package com.example.scholarnavigate.ui.screen.Login

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.scholarnavigate.data.util.webclientId
import com.example.scholarnavigate.navigate.Graph
import com.example.scholarnavigate.ui.screen.Login.components.GoogleSignIn
import com.example.scholarnavigate.ui.theme.ScholarNavigateTheme
import com.example.scholarnavigate.ui.theme.onSurfaceVariantLight
import com.example.scholarnavigate.ui.theme.primaryLight
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.launch

@Composable
fun LoginUi(
    navController: NavController
) {

    val viewModel: SignInViewModel = hiltViewModel()
    val googleSignInState = viewModel.googleState.value

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
            val account =
                com.google.android.gms.auth.api.signin.GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                val result = account.getResult(ApiException::class.java)
                val credentials = GoogleAuthProvider.getCredential(result.idToken, null)
                viewModel.googleSignIn(credentials)
            } catch (it: ApiException) {
                print(it)
            }
        }

    val state = viewModel.signInState.collectAsState(initial = null)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
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
                    GoogleSignIn(
                        onClick = {
                            val gso =
                                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                    .requestEmail()
                                    .requestIdToken(webclientId.clientId)
                                    .build()
                            val googleSingInClient =
                                com.google.android.gms.auth.api.signin.GoogleSignIn.getClient(
                                    context,
                                    gso
                                )
                            launcher.launch(googleSingInClient.signInIntent)
                        }
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                }

            }
        }

    }

    LaunchedEffect(key1 = state.value?.isSuccess) {
        scope.launch {
            if (state.value?.isSuccess?.isNotEmpty() == true) {
                val success = state.value?.isSuccess
                Toast.makeText(context, "$success", Toast.LENGTH_LONG).show()
            }
        }
    }

    LaunchedEffect(key1 = state.value?.isError) {
        scope.launch {
            if (state.value?.isError?.isNotEmpty() == true) {
                val error = state.value?.isError
                Toast.makeText(context, "$error", Toast.LENGTH_LONG).show()
            }
        }
    }

    LaunchedEffect(key1 = googleSignInState.success) {
        scope.launch {
            if (googleSignInState.success != null) {
            }
            googleSignInState.success?.user?.let {
                viewModel.addUser(
                    UserData(
                        userId = 1,
                        username = it.displayName.toString(),
                        profilePictureUrl = it.photoUrl?.toString(),
                        email = it.email
                    )
                )
            }
                Toast.makeText(context, "Sign In Success", Toast.LENGTH_LONG).show()
                navController.navigate(Graph.HOME)

        }
    }
    LaunchedEffect(key1 = googleSignInState.error) {
        scope.launch {
            if (googleSignInState.error != null) {
                val signInError = googleSignInState.error

                Toast.makeText(context, signInError, Toast.LENGTH_LONG).show()
            }
        }
    }
}

