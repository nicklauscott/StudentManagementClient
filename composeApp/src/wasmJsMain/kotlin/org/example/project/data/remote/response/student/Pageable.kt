package org.example.project.data.remote.response.student

import kotlinx.serialization.Serializable

@Serializable
data class Pageable(
    val offset: Int,
    val pageNumber: Int,
    val pageSize: Int,
    val paged: Boolean,
    val sort: SortX,
    val unpaged: Boolean
)