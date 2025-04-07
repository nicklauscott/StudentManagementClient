package org.example.project.domain.mapper.student

import org.example.project.data.remote.response.student.Pageable
import org.example.project.domain.model.student.PagingAndSort

fun Pageable.toPagingAndSorting(): PagingAndSort = PagingAndSort(page = this.pageNumber, size = this.pageSize)
