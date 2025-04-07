package org.example.project.domain.model.student

data class PagingAndSort(
    val page: Int = 0,
    val size: Int = 500,
    val sortBy: String = "id",
    val sortOrder: String = "asc"
)