package pl.edu.pb.ui.home

sealed class HomeIntent {
    object GetClients : HomeIntent()
    object RefreshClients : HomeIntent()
    data class ClientClicked(val id: String): HomeIntent()
}