package org.example.project.presentation.screens.sidepanel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.example.project.domain.reload
import org.example.project.domain.repository.AuthRepository
import org.example.project.presentation.screens.sidepanel.stateandevent.SidePanelEvent
import org.example.project.presentation.screens.sidepanel.stateandevent.SidePanelState

class SidePanelScreenManager(private val authRepository: AuthRepository) {

    private val _state: MutableState<SidePanelState> = mutableStateOf(SidePanelState())
    val state: State<SidePanelState> = _state

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    fun onEvent(event: SidePanelEvent) {
        when (event) {
            SidePanelEvent.SignOut -> signOut()
        }
    }

    private fun signOut() {
        scope.launch {
            authRepository.logOut()
            reload()
        }
    }
}