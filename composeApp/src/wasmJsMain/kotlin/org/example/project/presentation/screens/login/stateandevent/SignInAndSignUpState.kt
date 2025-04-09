package org.example.project.presentation.screens.login.stateandevent

data class SignInAndSignUpState(
    val signingIn: Boolean = false,
    val signInSuccessful: Boolean = false,

    val signingUp: Boolean = false,
    val signUpSuccessful: Boolean = false,
    val registeredEmail: String = ""
)
