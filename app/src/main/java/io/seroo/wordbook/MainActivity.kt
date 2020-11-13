package io.seroo.wordbook

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.platform.setContent
import dagger.hilt.android.AndroidEntryPoint
import io.seroo.wordbook.component.alarm.AlarmViewModel
import io.seroo.wordbook.component.root.RootComponent
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
                    RootComponent(rootViewModel = rootViewModel, wordViewModel = wordViewModel, alarmViewModel = alarmViewModel)
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
