package io.seroo.wordbook

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel

class RootViewModel @ViewModelInject constructor(): ViewModel() {
    private var screenState by mutableStateOf(listOf(ScreenState.HOME))

    private var screenPosition = 0

    var currentScreenState by mutableStateOf(screenState[screenPosition])
        private set

    fun addScreenState(state: ScreenState) {
        screenState = screenState.toMutableList().apply {
            add(state)
        }
        screenPosition++
        currentScreenState = state
    }

    fun resetScreenState() {
        screenState = listOf(ScreenState.HOME)
        screenPosition = 0
        currentScreenState = ScreenState.HOME
    }

    fun isPopOrExit(): Boolean {
        return if (screenPosition == 0) {
            true
        } else {
            screenState = screenState.toMutableList().apply {
                removeAt(screenPosition)
            }
            screenPosition--
            currentScreenState = screenState[screenPosition]
            false
        }
    }
}

enum class ScreenState {
    HOME,
    EDITOR,
    ALARM
}