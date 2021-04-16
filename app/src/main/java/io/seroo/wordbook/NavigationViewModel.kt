package io.seroo.wordbook

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel @Inject constructor(): ViewModel() {

    private var screenStates = listOf(ScreenState.HOME)

    private var screenPosition = 0

    private val _currentScreenState = MutableLiveData<ScreenState>()
    val currentScreenState: LiveData<ScreenState> get() = _currentScreenState

    fun addScreenState(state: ScreenState) {
        screenStates = screenStates.toMutableList().apply {
            add(state)
        }

        screenPosition++
        _currentScreenState.value = state
    }

    fun resetScreenState() {
        screenStates = listOf(ScreenState.HOME)
        screenPosition = 0
        _currentScreenState.value = ScreenState.HOME
    }

    fun movePreviousOrExit(): Boolean {
        return if (screenPosition == 0) {
            true
        } else {
            screenStates = screenStates.toMutableList().apply {
                removeAt(screenPosition)
            }

            screenPosition--
            _currentScreenState.value = screenStates.getOrNull(screenPosition) ?: ScreenState.HOME
            false
        }
    }
}

enum class ScreenState {
    HOME,
    EDITOR,
    CREATE,
    SETTING,
    GAME
}