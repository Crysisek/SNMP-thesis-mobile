package pl.edu.pb.ui.clientdetails.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StatusDisplayable(
    val receivingTime: String,
    val nameStatusPair: Map<String, String>,
) : Parcelable {

    companion object {
        fun empty() = StatusDisplayable(
            receivingTime = "",
            nameStatusPair = emptyMap(),
        )
    }
}
