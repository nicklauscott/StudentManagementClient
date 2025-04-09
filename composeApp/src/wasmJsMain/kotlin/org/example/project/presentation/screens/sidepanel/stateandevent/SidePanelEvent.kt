package org.example.project.presentation.screens.sidepanel.stateandevent

sealed interface SidePanelEvent {
    data object SignOut: SidePanelEvent
}