package io.seroo.wordbook

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.setContent
import dagger.hilt.android.AndroidEntryPoint
import io.seroo.wordbook.component.alarm.AlarmView
import io.seroo.wordbook.component.alarm.AlarmViewModel
import io.seroo.wordbook.component.game.GameView
import io.seroo.wordbook.component.game.GameViewModel
import io.seroo.wordbook.component.word.WordCreateView
import io.seroo.wordbook.component.word.WordEditView
import io.seroo.wordbook.component.word.WordView
import io.seroo.wordbook.component.word.WordViewModel
import io.seroo.wordbook.ui.WordBookTheme

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val navigationViewModel: NavigationViewModel by viewModels()
    private val wordViewModel: WordViewModel by viewModels()
    private val alarmViewModel: AlarmViewModel by viewModels()
    private val gameViewModel: GameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gameViewModel.init()
        setContent {
            WordBookTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    val screenState by navigationViewModel.currentScreenState.observeAsState(initial = ScreenState.HOME)
                    when (screenState) {
                        ScreenState.HOME -> WordView(
                            navigationViewModel = navigationViewModel,
                            wordViewModel = wordViewModel
                        )
                        ScreenState.ALARM -> AlarmView(
                            navigationViewModel = navigationViewModel,
                            alarmViewModel = alarmViewModel
                        )
                        ScreenState.CREATE -> WordCreateView(
                            navigationViewModel = navigationViewModel,
                            wordViewModel = wordViewModel
                        )
                        ScreenState.EDITOR -> WordEditView(
                            navigationViewModel = navigationViewModel,
                            wordViewModel = wordViewModel
                        )
                        ScreenState.GAME -> GameView(
                            navigationViewModel = navigationViewModel,
                            gameViewModel = gameViewModel
                        )
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        when (navigationViewModel.movePreviousOrExit()) {
            true -> super.onBackPressed()
            false -> Unit
        }
    }
}
