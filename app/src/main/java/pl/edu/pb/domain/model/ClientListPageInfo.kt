package pl.edu.pb.domain.model

data class ClientListPageInfo(
    val clients: List<Client>,
    val currentPage: Int,
    val totalPages: Int,
)
