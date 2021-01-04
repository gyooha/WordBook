package io.seroo.wordbook

import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.ExperimentalLayout
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.setContent
import dagger.hilt.android.AndroidEntryPoint
import io.seroo.wordbook.component.game.GameView
import io.seroo.wordbook.component.game.GameViewModel
import io.seroo.wordbook.component.setting.SettingView
import io.seroo.wordbook.component.setting.SettingViewModel
import io.seroo.wordbook.component.word.WordCreateView
import io.seroo.wordbook.component.word.WordEditView
import io.seroo.wordbook.component.word.WordView
import io.seroo.wordbook.component.word.WordViewModel
import io.seroo.wordbook.ui.WordBookTheme

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val navigationViewModel: NavigationViewModel by viewModels()
    private val wordViewModel: WordViewModel by viewModels()
    private val settingViewModel: SettingViewModel by viewModels()
    private val gameViewModel: GameViewModel by viewModels()

    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let { settingViewModel.saveCSVToWrodsDataBase(uri) }
    }

    @ExperimentalFoundationApi
    @ExperimentalLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
                        ScreenState.SETTING -> SettingView(
                            navigationViewModel = navigationViewModel,
                            settingViewModel = settingViewModel,
                            activityResultLauncher = getContent
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
