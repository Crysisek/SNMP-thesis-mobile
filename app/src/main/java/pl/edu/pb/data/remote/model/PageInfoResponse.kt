package pl.edu.pb.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class PageInfoResponse(
    val currentPage: Int,
    val totalPages: Int,
    val currentPageSize: Int,
    val totalElements: Long,
)
