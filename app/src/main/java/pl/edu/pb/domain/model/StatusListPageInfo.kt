package pl.edu.pb.domain.model

data class StatusListPageInfo(
    val statuses: List<Status>,
    val currentPage: Int,
    val totalPages: Int,
)