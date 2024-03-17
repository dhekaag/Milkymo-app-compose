package com.milkymo.milky_mo.features.auth.login

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.milkymo.milky_mo.R
import com.milkymo.milky_mo.component.Toast
import com.milkymo.milky_mo.features.auth.login.LoginViewModel.LoginUiState.ErrorFromAPI
import com.milkymo.milky_mo.features.auth.login.LoginViewModel.LoginUiState.Initiate
import com.milkymo.milky_mo.features.auth.login.LoginViewModel.LoginUiState.LoadingFromAPI
import com.milkymo.milky_mo.features.auth.login.LoginViewModel.LoginUiState.LoginSuccess
import com.milkymo.milky_mo.theme.blackHint
import com.milkymo.milky_mo.theme.milkymoTheme
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel,
    navigateToHome: () -> Unit,
    isLogin: Boolean
) {
    val state by loginViewModel.state.collectAsState()
    LoginPage(
        viewModel = loginViewModel,
        state
    )
    if (isLogin){
        navigateToHome.invoke()
    }
    if (state is LoginSuccess){
        LaunchedEffect(Unit){
            navigateToHome.invoke()
        }
    }
    if (state is ErrorFromAPI){
        (state as ErrorFromAPI).message?.let { Toast(it) }
    }
}



@Composable
fun LoginPage(
    viewModel: LoginViewModel,
    state: LoginViewModel.LoginUiState?
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
    ) {
        Box(
            modifier = Modifier
                .padding(bottom = it.calculateBottomPadding())
                .background(MaterialTheme.colorScheme.primary)
                .fillMaxSize()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(top = 42.dp)
            ) {
                // App Logo
                Image(
                    painter = painterResource(id = R.drawable.milkymo_logo),
                    contentDescription = "App Logo",
                    Modifier.size(300.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                // Card
                LoginCard(
                    idPeternak = viewModel.idPeternak,
                    password = viewModel.password,
                    isRememberMe = viewModel.isRememberMe,
                    onLoginClicked = { viewModel.login() }  ,
                    onForgotAccountClicked = { viewModel.forgotAccount()},
                    passwordVisibility = viewModel.passwordVisibility,
                    viewModel = viewModel,
                    state = state
                )
            }
        }
    }
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun LoginCard(
    idPeternak: MutableState<String>,
    password: MutableState<String>,
    isRememberMe: MutableState<Boolean>,
    passwordVisibility: MutableState<Boolean>,
    onLoginClicked: () -> Unit,
    onForgotAccountClicked: () -> Unit,
    viewModel: LoginViewModel,
    state: LoginViewModel.LoginUiState?
){
    Card(
        modifier = Modifier
            .fillMaxSize(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 34.dp)
        ) {
            Text(
                text = "Hallo, Peternak!",
                modifier = Modifier.padding(top = 40.dp),
                fontSize = TextUnit(value = 14F, TextUnitType.Sp),
                fontWeight = FontWeight.SemiBold,

            )
            Spacer(modifier = Modifier.height(10.dp))

            val _annotatedString = buildAnnotatedString {
                withStyle(style = SpanStyle(Color.Black.copy(alpha = 0.6F), fontWeight = FontWeight.Medium)){
                append("Untuk Menggunakan aplikasi MilkyMo, silahkan masukkan ")}
                withStyle(style = SpanStyle(Color.Black, fontWeight = FontWeight.SemiBold)) {
                    append("ID Peternak")
                }
            }
            Text(
                text = _annotatedString,
                style = MaterialTheme.typography.bodyLarge.copy(letterSpacing = TextUnit(0.9F, TextUnitType.Sp)),
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Left

            )

            Spacer(modifier = Modifier.height(25.dp))

            OutlinedTextField(
                value = idPeternak.value,
                onValueChange = { idPeternak.value = it  },
                singleLine = true,
                textStyle = TextStyle(fontSize = 13.sp, fontWeight = FontWeight.SemiBold),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(15.dp),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.PersonOutline,
                        contentDescription = "profile icon",
                        Modifier.size(24.dp)
                    )
                },
                placeholder = {
                    Text(
                        text = "Masukkan id peternak",
                        style = TextStyle(
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 13.sp,
                            color = blackHint
                        )
                    )
                },
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password.value,
                onValueChange = { password.value = it  },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(fontSize = 13.sp, fontWeight = FontWeight.SemiBold),
                shape = RoundedCornerShape(15.dp),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Password,
                        contentDescription = "profile icon",
                        modifier = Modifier.size(24.dp)
                    )
                },
                visualTransformation = if (passwordVisibility.value) PasswordVisualTransformation()
                else VisualTransformation.None,
                trailingIcon = {
                    IconButton(
                        onClick = {
                            passwordVisibility.value = !passwordVisibility.value
                        }) {
                        Icon(
                            imageVector =
                            if (passwordVisibility.value)
                                Icons.Default.Visibility
                            else
                                Icons.Default.VisibilityOff,
                            contentDescription = "Visibility Icon",
                            modifier = Modifier.size(20.dp)
                        )
                    }
                },
                placeholder = {
                    Text(
                        text = "Masukkan password",
                        style = TextStyle(
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 13.sp,
                            color = blackHint
                        )
                    )
                },
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(start = 15.dp, top = 3.dp)

                ) {
                    ColouredCheckbox(checked = isRememberMe)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Ingat saya",
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        ),
                        color = blackHint
                    )
                }

                TextButton(
                    onClick = onForgotAccountClicked  ,
                ) {
                    Text(
                        text = "Lupa ID peternak?",
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            ElevatedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(36.dp),
                shape = RoundedCornerShape(12.dp),
                enabled =
                    when(state){
                        LoadingFromAPI -> false
                        is  LoginSuccess,Initiate -> true
                        else -> {true}
                    },

                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    disabledContainerColor = MaterialTheme.colorScheme.primary.copy(0.2F)
                ),
                onClick = onLoginClicked,
            ) {
                when(state){
                    LoadingFromAPI -> CircularProgressIndicator(color = MaterialTheme.colorScheme.primary, strokeCap = StrokeCap.Square, modifier = Modifier.size(24.dp))
                    else -> { Text(
                        text = "MASUK",
                        style = MaterialTheme.typography.titleMedium.copy(fontSize = 15.sp, letterSpacing = 0.9.sp),
                        color = Color.White
                    )}
                }
            }
        }
    }
}


@Composable
fun MyPasswordTextField(
    modifier: Modifier = Modifier.fillMaxWidth(),
    value: String,
    onValueChange: MutableState<String>,
    singleLine: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    shape: RoundedCornerShape = RoundedCornerShape(16.dp),
    leadingIcon: Int?  = null,
    placeholder: String? = null,
) {
    OutlinedTextField(
        value = value,
        onValueChange = { onValueChange.value = it  },
        singleLine = singleLine,
        keyboardOptions = keyboardOptions,
        modifier = modifier,
        shape = shape,
        leadingIcon = {
            leadingIcon?.let {iconId ->
                val imageVector = ImageVector.vectorResource(id = iconId)
                Icon(
                    imageVector = imageVector,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            }
        },
        placeholder = {
            placeholder?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.labelSmall
                )
            }},
    )
}

@Composable
fun ColouredCheckbox(checked: MutableState<Boolean>) {
    Checkbox(
        modifier = Modifier.size(12.dp),
        checked = checked.value,
        onCheckedChange = { checked.value = it },
        enabled = true,
        colors = CheckboxDefaults.colors(MaterialTheme.colorScheme.primary)
    )
}

@Preview
@Composable
fun LoginPreview() {
    milkymoTheme {
        val viewModel: LoginViewModel = hiltViewModel()
      LoginPage(viewModel = viewModel, state = null)
    }
}