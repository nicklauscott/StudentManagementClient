package org.example.project.presentation.screens.login

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import org.example.project.domain.model.user.Login
import org.example.project.domain.model.user.Registration
import org.example.project.presentation.components.*
import org.example.project.presentation.screens.login.stateandevent.SignInAndSignUpEvent
import org.example.project.presentation.theme.LocalAppTheme
import org.koin.core.context.GlobalContext

@Composable
fun SignInAndSignUpScreen() {
    val appTheme = LocalAppTheme.current
    val manager = remember { GlobalContext.get().get<SignInAndSignUpScreenManager>() }
    var showLoginScreen by remember { mutableStateOf(true) }

    if (manager.signInAndSignUpState.value.signUpSuccessful) {
        showLoginScreen = true
    }

    Box(
        modifier = Modifier.fillMaxSize()
            .background(appTheme.colors.mainBackGround4),
        contentAlignment = Alignment.Center
    ) {
        AnimatedContent(showLoginScreen) { targetState ->
            when (targetState) {
                true -> SignInScreen(
                    loginIn = manager.signInAndSignUpState.value.signingIn,
                    registeredEmail = manager.signInAndSignUpState.value.registeredEmail,
                    onSignUpClick = { showLoginScreen = false },
                ) { login ->  manager.onEvent(SignInAndSignUpEvent.SignInEvent(login)) }
                false -> SignUpScreen(
                    registering = manager.signInAndSignUpState.value.signingUp,
                    onSignInClick = { showLoginScreen = true }
                ) { registration -> manager.onEvent(SignInAndSignUpEvent.SignUpEvent(registration)) }
            }
        }
    }
}


@Composable
fun SignInScreen(
    loginIn: Boolean, registeredEmail: String, onSignUpClick: () -> Unit,
    onSignInClick: (Login) -> Unit
) {
    val appTheme = LocalAppTheme.current

    var emailTextState by remember { mutableStateOf(TextFieldValue(registeredEmail)) }
    var passwordState by remember { mutableStateOf(TextFieldValue("")) }
    val passwordPattern = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{9,}\$")
    val emailPattern = Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}\$")

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
    ) {

        HeaderText("Sign in")

        Spacer(modifier = Modifier.height(25.dp))

        EnhacedTextAndTextFieldCell(
            modifier = Modifier.width(350.dp),
            id = 1, label = "Email",
            textState = emailTextState.text,
            canEdit = !loginIn,
            singleLine = true,
            onTextChange = { emailTextState = TextFieldValue(it) },
            errorAndSuccess = {
                !emailTextState.text.matches(emailPattern) to emailTextState.text.matches(emailPattern)
            }
        )

        EnhacedTextAndTextFieldCell(
            modifier = Modifier.width(350.dp),
            id = 1, label = "Password",
            textState = passwordState.text,
            isPassword = true,
            canEdit = !loginIn,
            singleLine = true,
            onTextChange = {
                passwordState = TextFieldValue(it)
            },
            errorAndSuccess = {
                !passwordState.text.matches(passwordPattern) to passwordState.text.matches(passwordPattern)
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        CustomButton(
            modifier = Modifier.width(350.dp).height(50.dp),
            text = "Sign in",
            enabled = !loginIn && passwordState.text.matches(passwordPattern) && emailTextState.text.matches(emailPattern),
            saving = loginIn
        ) {
            onSignInClick(Login(emailTextState.text, passwordState.text))
        }

        Row(modifier = Modifier.width(350.dp).padding(end = 3.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
            ) {
            InfoCell(
                "Forgot account?", style = MaterialTheme.typography.body2,
                horizontalPadding = 3.dp,
                color = appTheme.colors.button, clickable = true
            ) {}
            InfoCell(
                "Sign up", style = MaterialTheme.typography.body2,
                color = appTheme.colors.button, clickable = true
            ) { onSignUpClick() }
        }

    }

}

@Composable
fun SignUpScreen(registering: Boolean, onSignInClick: () -> Unit, onSignUpClick: (Registration) -> Unit) {
    val appTheme = LocalAppTheme.current

    var firstNameTextState by remember { mutableStateOf(TextFieldValue("")) }
    var lastNameTextState by remember { mutableStateOf(TextFieldValue("")) }
    var emailTextState by remember { mutableStateOf(TextFieldValue("")) }
    var passwordState by remember { mutableStateOf(TextFieldValue("")) }
    val nameErrorAndSuccess: (String) -> Pair<Boolean, Boolean> = { (it.length < 3) to (it.length >= 3) }
    val passwordPattern = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{9,}\$")
    val emailPattern = Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}\$")

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeaderText("Sign up")

        Spacer(modifier = Modifier.height(25.dp))

        Row(modifier = Modifier.width(750.dp), horizontalArrangement = Arrangement.SpaceAround) {
            val modifier = Modifier
            // first name
            EnhacedTextAndTextFieldCell(
                modifier = modifier.weight(0.47f),
                id = 1, label = "First Name",
                textState = firstNameTextState.text,
                canEdit = !registering,
                singleLine = true,
                onTextChange = { firstNameTextState = TextFieldValue(it) },
                errorAndSuccess = { nameErrorAndSuccess(firstNameTextState.text) }
            )

            Spacer(modifier = modifier.weight(0.06f))

            // last name
            EnhacedTextAndTextFieldCell(
                modifier = modifier.weight(0.47f),
                id = 1, label = "Last Name",
                textState = lastNameTextState.text,
                canEdit = !registering,
                singleLine = true,
                onTextChange = { lastNameTextState = TextFieldValue(it) },
                errorAndSuccess = { nameErrorAndSuccess(lastNameTextState.text) }
            )
        }

        Row(modifier = Modifier.width(750.dp), horizontalArrangement = Arrangement.SpaceAround) {
            val modifier = Modifier

            EnhacedTextAndTextFieldCell(
                modifier = modifier.weight(0.47f),
                id = 1, label = "Email",
                textState = emailTextState.text,
                canEdit = !registering,
                singleLine = true,
                onTextChange = { emailTextState = TextFieldValue(it) },
                errorAndSuccess = {
                    !emailTextState.text.matches(emailPattern) to emailTextState.text.matches(emailPattern)
                }
            )

            Spacer(modifier = modifier.weight(0.06f))

            EnhacedTextAndTextFieldCell(
                modifier = modifier.weight(0.47f),
                id = 1, label = "Password",
                textState = passwordState.text,
                isPassword = true,
                canEdit = !registering,
                singleLine = true,
                onTextChange = {
                    passwordState = TextFieldValue(it)
                },
                errorAndSuccess = {
                    !passwordState.text.matches(passwordPattern) to passwordState.text.matches(passwordPattern)
                }
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        CustomButton(
            modifier = Modifier.width(350.dp).height(50.dp),
            text = "Sign up",
            enabled = !registering && passwordState.text.matches(passwordPattern) && emailTextState.text.matches(emailPattern)
                    && nameErrorAndSuccess(firstNameTextState.text).second && nameErrorAndSuccess(lastNameTextState.text).second,
            saving = registering
        ) {
            onSignUpClick(Registration(
                firstName = firstNameTextState.text, lastName = lastNameTextState.text,
                email = emailTextState.text, password = passwordState.text,
            ))
        }

        Row(modifier = Modifier.width(350.dp).padding(end = 3.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            InfoCell(
                "Sign in", style = MaterialTheme.typography.body2,
                color = appTheme.colors.button, clickable = true
            ) { onSignInClick() }
        }
    }

}