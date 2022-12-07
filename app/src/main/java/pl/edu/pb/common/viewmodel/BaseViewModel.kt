package pl.edu.pb.common.viewmodel

import android.os.Parcelable
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
abstract class BaseViewModel<UI_STATE: Parcelable, PARTIAL_UI_STATE, EVENT, INTENT>(
    savedStateHandle: SavedStateHandle,
    initialState: UI_STATE,
): ViewModel() {
    private val intentFlow = MutableSharedFlow<INTENT>()
    private val eventChannel = Channel<EVENT>(Channel.BUFFERED)
    val event = eventChannel.receiveAsFlow()
    val uiState = savedStateHandle.getStateFlow(SAVED_UI_STATE_KEY, initialState)

    init {
        viewModelScope.launch {
            intentFlow
                .flatMapMerge { mapIntents(it) }
                .scan(uiState.value, ::reduceUiState)
                .catch { it.message?.let { message -> Log.e(TAG, message) } }
                .collect {
                    savedStateHandle[SAVED_UI_STATE_KEY] = it
                }
        }
    }

    fun acceptIntent(intent: INTENT) {
        viewModelScope.launch {
            intentFlow.emit(intent)
        }
    }

    protected fun publicEvent(event: EVENT) {
        viewModelScope.launch {
            eventChannel.send(event)
        }
    }

    protected abstract fun mapIntents(intent: INTENT): Flow<PARTIAL_UI_STATE>

    protected abstract fun reduceUiState(
        previousState: UI_STATE,
        partialState: PARTIAL_UI_STATE,
    ): UI_STATE

    private companion object {
        private const val SAVED_UI_STATE_KEY = "savedUiStateKey"
        private const val TAG = "BaseViewModel"
    }
}