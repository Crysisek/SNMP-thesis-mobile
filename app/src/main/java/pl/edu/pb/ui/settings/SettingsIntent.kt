package pl.edu.pb.ui.settings

sealed class SettingsIntent {
    data class ChangeRefreshInterval(val refreshInterval: Float): SettingsIntent()
    data class ChangeRefreshState(val isRefreshOn: Boolean): SettingsIntent()
}