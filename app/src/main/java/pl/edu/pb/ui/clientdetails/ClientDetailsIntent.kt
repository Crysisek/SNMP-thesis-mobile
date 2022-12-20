package pl.edu.pb.ui.clientdetails

sealed class ClientDetailsIntent {
    object GetStatuses : ClientDetailsIntent()
    object SortStatuses : ClientDetailsIntent()
    object StatusClicked : ClientDetailsIntent()
    object BackClicked : ClientDetailsIntent()
    data class SortByClicked(val sortBy: String) : ClientDetailsIntent()
    data class SortDirClicked(val sortDir: BottomSheetUiState.SortDir) : ClientDetailsIntent()
}