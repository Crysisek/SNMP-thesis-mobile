package pl.edu.pb.ui.search

sealed class SearchIntent {
    object QueryCleared : SearchIntent()
    data class QueryChanged(val query: String) : SearchIntent()
    data class SearchFocusChanged(val isFocused: Boolean) : SearchIntent()
    data class ClientClicked(val id: String) : SearchIntent()
}