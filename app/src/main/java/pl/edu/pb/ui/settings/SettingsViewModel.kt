package pl.edu.pb.ui.settings

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import pl.edu.pb.common.viewmodel.BaseViewModel
import pl.edu.pb.ui.settings.SettingsUiState.PartialState
import pl.edu.pb.ui.settings.SettingsUiState.PartialState.*
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    settingsInitialState: SettingsUiState,
) : BaseViewModel<SettingsUiState, PartialState, SettingsEvent, SettingsIntent>(
    savedStateHandle,
    settingsInitialState,
) {
    override fun mapIntents(intent: SettingsIntent): Flow<PartialState> {
        TODO("Not yet implemented")
    }

    override fun reduceUiState(previousState: SettingsUiState, partialState: PartialState): SettingsUiState = when (partialState) {
        is Loading -> previousState.copy(
            isLoading = true,
            isError = false,
        )
        is Success -> previousState.copy(
            isLoading = false,
            isError = false,
        )
        is Error -> previousState.copy(
            isLoading = false,
            isError = true,
        )
    }
}