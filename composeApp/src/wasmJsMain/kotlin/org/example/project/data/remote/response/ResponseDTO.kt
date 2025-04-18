package org.example.project.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class ResponseDTO(
    val content: List<StudentDTO>,
    val empty: Boolean,
    val first: Boolean,
    val last: Boolean,
    val number: Int,
    val numberOfElements: Int,
    val pageable: Pageable,
    val size: Int,
    val sort: SortX,
    val totalElements: Int,
    val totalPages: Int
)