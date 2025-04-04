package org.example.project.domain.mapper

import org.example.project.data.remote.response.Pageable
import org.example.project.domain.model.PagingAndSort

fun Pageable.toPagingAndSorting(): PagingAndSort = PagingAndSort(page = this.pageNumber, size = this.pageSize)
