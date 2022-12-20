package pl.edu.pb.domain.model

import java.time.Instant

data class Status(
    val receivingTime: Instant,
    val nameStatusPair: Map<String, String>,
)
