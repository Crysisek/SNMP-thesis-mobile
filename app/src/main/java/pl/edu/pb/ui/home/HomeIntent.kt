package pl.edu.pb.ui.home

sealed class HomeIntent {
    object GetClients : HomeIntent()
    data class ClientClicked(val id: String): HomeIntent()
}