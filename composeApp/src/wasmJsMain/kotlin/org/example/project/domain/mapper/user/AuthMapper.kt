package org.example.project.domain.mapper.user

import org.example.project.data.remote.response.user.LoginRequest
import org.example.project.data.remote.response.user.RegistrationRequest
import org.example.project.data.remote.response.user.UserDTO
import org.example.project.domain.model.user.Login
import org.example.project.domain.model.user.Registration
import org.example.project.domain.model.user.User

fun Login.toRequest(): LoginRequest = LoginRequest(email, password)

fun Registration.toRequest(): RegistrationRequest =
    RegistrationRequest(email, firstName, lastName, password)

fun UserDTO.toUser(): User = User(email, firstName, lastName)