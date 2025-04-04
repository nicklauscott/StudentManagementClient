package org.example.project.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class SortX(
    val empty: Boolean,
    val sorted: Boolean,
    val unsorted: Boolean
)