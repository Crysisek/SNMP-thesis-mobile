package pl.edu.pb.domain.model

import java.time.Instant

data class Client(
    val username: String,
    val createdAt: Instant,
    val latestUpdateAt: Instant,
    val condition: String,
)
