package io.seroo.wordbook

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.focus.ExperimentalFocus
import androidx.compose.ui.platform.setContent
import dagger.hilt.android.AndroidEntryPoint
import io.seroo.wordbook.component.alarm.AlarmView
import io.seroo.wordbook.component.alarm.AlarmViewModel
import io.seroo.wordbook.component.word.WordCreateView
import io.seroo.wordbook.component.word.WordEditView
import io.seroo.wordbook.component.word.WordView
import io.seroo.wordbook.component.word.WordViewModel
import io.seroo.wordbook.ui.WordBookTheme

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val rootViewModel: RootViewModel by viewModels()
    private val wordViewModel: WordViewModel by viewModels()
    private val alarmViewModel: AlarmViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WordBookTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    when (rootViewModel.currentScreenState) {
                        ScreenState.HOME -> WordView(
                            rootViewModel = rootViewModel,
                            wordViewModel = wordViewModel
                        )
                        ScreenState.ALARM -> AlarmView(
                            rootViewModel = rootViewModel,
                            alarmViewModel = alarmViewModel
                        )
                        ScreenState.CREATE -> WordCreateView(
                            rootViewModel = rootViewModel,
                            wordViewModel = wordViewModel
                        )
                        ScreenState.EDITOR -> WordEditView(
                            rootViewModel = rootViewModel,
                            wordViewModel = wordViewModel
                        )
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        when (rootViewModel.isPopOrExit()) {
            true -> super.onBackPressed()
            false -> Unit
        }
    }
}
