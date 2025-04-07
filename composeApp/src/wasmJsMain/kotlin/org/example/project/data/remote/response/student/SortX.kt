package org.example.project.data.remote.response.student

import kotlinx.serialization.Serializable

@Serializable
data class SortX(
    val empty: Boolean,
    val sorted: Boolean,
    val unsorted: Boolean
)