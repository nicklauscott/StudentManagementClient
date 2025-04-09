package org.example.project.presentation.screens.sidepanel.stateandevent

import org.example.project.domain.getUserDetail
import org.example.project.domain.model.user.User

data class SidePanelState(
    val currentUser: User = getUserDetail()
)
