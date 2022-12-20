package pl.edu.pb.ui.home.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import pl.edu.pb.ui.clientdetails.model.StatusDisplayable

@Parcelize
data class ClientDisplayable(
    val username: String,
    val createdAt: String,
    val latestUpdateAt: String,
    val condition: String,
    val latestStatus: StatusDisplayable,
) : Parcelable
