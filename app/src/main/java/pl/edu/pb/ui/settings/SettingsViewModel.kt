package pl.edu.pb.ui.settings

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import pl.edu.pb.common.viewmodel.BaseViewModel
import pl.edu.pb.domain.usecase.GetRefreshIntervalUseCase
import pl.edu.pb.domain.usecase.GetRefreshStateUseCase
import pl.edu.pb.domain.usecase.SaveRefreshIntervalUseCase
import pl.edu.pb.domain.usecase.SaveRefreshStateUseCase
import pl.edu.pb.domain.usecase.saveRefreshInterval
import pl.edu.pb.ui.settings.SettingsIntent.ChangeRefreshInterval
import pl.edu.pb.ui.settings.SettingsIntent.ChangeRefreshState
import pl.edu.pb.ui.settings.SettingsUiState.PartialState
import pl.edu.pb.ui.settings.SettingsUiState.PartialState.*
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val saveRefreshIntervalUseCase: SaveRefreshIntervalUseCase,
    private val saveRefreshStateUseCase: SaveRefreshStateUseCase,
    getRefreshIntervalUseCase: GetRefreshIntervalUseCase,
    getRefreshStateUseCase: GetRefreshStateUseCase,
    savedStateHandle: SavedStateHandle,
    settingsInitialState: SettingsUiState,
) : BaseViewModel<SettingsUiState, PartialState, SettingsEvent, SettingsIntent>(
    savedStateHandle,
    settingsInitialState,
) {

    init {
        viewModelScope.launch {
            acceptIntent(
                ChangeRefreshState(
                    getRefreshStateUseCase(REFRESH_STATE).getOrDefault(true)
                )
            )
            acceptIntent(
                ChangeRefreshInterval(
                    getRefreshIntervalUseCase(REFRESH_INTERVAL).getOrDefault(5000) / CONST.toFloat()
                )
            )
        }
    }

    override fun mapIntents(intent: SettingsIntent): Flow<PartialState> = when (intent) {
        is ChangeRefreshInterval -> changeRefreshInterval(intent.refreshInterval)
        is ChangeRefreshState -> changeRefreshState(intent.isRefreshOn)
    }

    override fun reduceUiState(previousState: SettingsUiState, partialState: PartialState): SettingsUiState = when (partialState) {
        is RefreshIntervalChanged -> previousState.copy(
            refreshInterval = partialState.refreshInterval,
            isError = false,
        )
        is RefreshStateChanged -> previousState.copy(
            isRefreshOn = partialState.isRefreshOn,
        )
        is Error -> previousState.copy(
            isError = true,
        )
    }

    private fun changeRefreshInterval(refreshInterval: Float): Flow<PartialState> = flow {
        saveRefreshIntervalUseCase(REFRESH_INTERVAL, (refreshInterval * CONST).toInt())
        emit(RefreshIntervalChanged(refreshInterval))
    }

    private fun changeRefreshState(isRefreshOn: Boolean): Flow<PartialState> = flow {
        saveRefreshStateUseCase(REFRESH_STATE, isRefreshOn)
        emit(RefreshStateChanged(isRefreshOn))
    }

    private companion object {
        private const val REFRESH_INTERVAL = "REFRESH_INTERVAL"
        private const val REFRESH_STATE = "REFRESH_STATE"
        private const val CONST = 1000
    }
}