package org.example.project.presentation.screens.index

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.example.project.domain.repository.AuthRepository

class IndexScreenManager(private val authRepository: AuthRepository) {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    val isTokenValid: MutableState<Boolean> = mutableStateOf(false)

    init { verifyToken() }

    fun verifyToken() {
        scope.launch { isTokenValid.value = authRepository.isTokenValid() }
    }

    fun signInSuccessful() { isTokenValid.value = true }

}