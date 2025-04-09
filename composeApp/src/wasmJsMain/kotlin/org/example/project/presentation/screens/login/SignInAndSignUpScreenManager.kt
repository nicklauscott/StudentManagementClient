package org.example.project.presentation.screens.login

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.example.project.domain.alert
import org.example.project.domain.constant.AuthResponse
import org.example.project.domain.mapper.user.toRequest
import org.example.project.domain.model.user.Login
import org.example.project.domain.model.user.Registration
import org.example.project.domain.reload
import org.example.project.domain.repository.AuthRepository
import org.example.project.presentation.screens.login.stateandevent.SignInAndSignUpEvent
import org.example.project.presentation.screens.login.stateandevent.SignInAndSignUpState

class SignInAndSignUpScreenManager(private val authRepository: AuthRepository) {

    private val _signInAndSignUpState: MutableState<SignInAndSignUpState> = mutableStateOf(SignInAndSignUpState())
    val signInAndSignUpState: State<SignInAndSignUpState> = _signInAndSignUpState

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)


    fun onEvent(event: SignInAndSignUpEvent) {
        when (event) {
            is SignInAndSignUpEvent.SignInEvent -> signIn(event.login)
            is SignInAndSignUpEvent.SignUpEvent -> signUp(event.registration)
        }
    }

    private fun signIn(login: Login) {
        _signInAndSignUpState.value = signInAndSignUpState.value.copy(signingIn = true)
        scope.launch {
            val response = authRepository.login(login.toRequest())
            if (response is AuthResponse.LoginFailed) {
                _signInAndSignUpState.value = signInAndSignUpState.value.copy(signingIn = false, signInSuccessful = false)
                alert("Login failed\n${response.messages}")
                return@launch
            }

            if (response is AuthResponse.LoginSuccessful) {
                _signInAndSignUpState.value = signInAndSignUpState.value.copy(signingIn = false, signInSuccessful = true)
                reload()
            }
        }
    }

    private fun signUp(registration: Registration) {
        _signInAndSignUpState.value = signInAndSignUpState.value.copy(signingUp = true)
        scope.launch {
            val response = authRepository.register(registration.toRequest())
            if (response is AuthResponse.RegistrationFailure) {
                _signInAndSignUpState.value = signInAndSignUpState.value.copy(signingUp = false, signUpSuccessful = false)
                val errorMessage = response.messages.joinToString("\n")
                alert("Registration failed\n$errorMessage")
                return@launch
            }

            if (response is AuthResponse.RegistrationSuccessful) {
                _signInAndSignUpState.value = signInAndSignUpState.value.copy(
                    signingUp = false, signUpSuccessful = true,
                    registeredEmail = response.email
                )
            }
        }
    }
}