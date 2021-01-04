package io.seroo.wordbook.component.root

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.BottomNavigation
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import io.seroo.wordbook.NavigationViewModel
import io.seroo.wordbook.ScreenState
import io.seroo.wordbook.ui.purple200

@Composable
fun BottomNavigationComponent(navigationViewModel: NavigationViewModel) {
    val screenState by navigationViewModel.currentScreenState.observeAsState(initial = ScreenState.HOME)

    BottomNavigation {
        Row {
            TextButton(modifier = Modifier.weight(1f).fillMaxHeight(), onClick = { navigationViewModel.resetScreenState() }) {
                Text(text = "홈", color = if (screenState == ScreenState.HOME) purple200 else Color.White)
            }

            TextButton(modifier = Modifier.weight(1f).fillMaxHeight(), onClick = { navigationViewModel.addScreenState(ScreenState.SETTING) }) {
                Text(text = "알림", color = if (screenState == ScreenState.SETTING) purple200 else Color.White)
            }
        }
    }
}