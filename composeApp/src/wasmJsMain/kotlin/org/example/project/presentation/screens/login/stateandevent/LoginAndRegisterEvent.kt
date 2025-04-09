package org.example.project.presentation.screens.login.stateandevent

import org.example.project.domain.model.user.Login
import org.example.project.domain.model.user.Registration

sealed interface SignInAndSignUpEvent {
    data class SignInEvent(val login: Login): SignInAndSignUpEvent
    data class SignUpEvent(val registration: Registration): SignInAndSignUpEvent
}