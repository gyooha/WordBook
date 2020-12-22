package io.seroo.wordbook.component.game

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumnForIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.onActive
import androidx.compose.runtime.onDispose
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.AmbientContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.seroo.wordbook.NavigationViewModel
import io.seroo.wordbook.component.root.LoadingComponent
import io.seroo.wordbook.component.word.WordUIModel

@Composable
fun GameView(navigationViewModel: NavigationViewModel, gameViewModel: GameViewModel) {
    val isGameInit by gameViewModel.isGameInit.observeAsState(false)
    val currentPosition by gameViewModel.currentPosition.observeAsState(0)
    val problemWords by gameViewModel.problemWords.observeAsState(initial = listOf())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "문제를 풀어보아요~") },
            )
        },
    ) {
        when (isGameInit) {
            true -> {
                Log.d("GYH", "gameBodyVisible")
                GameBodyComponent(navigationViewModel, gameViewModel, currentPosition, problemWords)
            }
            false -> gameViewModel.run {
                Log.d("GYH", "getALlWords")
                getAllWordsAndCompose(currentPosition)
                LoadingComponent()
            }
        }
    }
}

@Composable
fun GameBodyComponent(
    navigationViewModel: NavigationViewModel,
    gameViewModel: GameViewModel,
    currentPosition: Int,
    wordList: List<WordUIModel>
) {
    val result = gameViewModel.getResultByPosition(currentPosition)
    val context = AmbientContext.current
    onDispose { gameViewModel.reset() }
    Column {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = result.word,
                modifier = Modifier.padding(8.dp),
                fontSize = 24.sp,
            )
        }

//        LinearProgressIndicator(progress = ((interval / 5F)), modifier = Modifier.fillMaxWidth())

        LazyColumnForIndexed(
            wordList,
            modifier = Modifier.padding(start = 4.dp, end = 4.dp).fillMaxWidth()
        ) { i, item ->
            Row(
                modifier = Modifier.clickable {
                    Toast.makeText(context, if (item.id == result.id) "정답!!" else "오답 ㅜㅜ", Toast.LENGTH_SHORT).show()
                    gameViewModel.run {
                        clickWord(currentPosition, item.id, result.id)
                        when (gameViewModel.isLastPage()) {
                            true -> {
                                navigationViewModel.resetScreenState()
                            }
                            false -> moveToNextPosition()
                        }
                    }
                }.fillMaxWidth().sizeIn(minHeight = 42.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Surface(
                    modifier = Modifier.align(Alignment.CenterVertically)
                        .size(24.dp)
                        .clip(CircleShape)
                        .border(width = 1.dp, shape = CircleShape, color = Color.White),
                    ) {
                    Text(
                        text = "${i + 1}",
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth().fillMaxHeight().align(Alignment.CenterVertically)
                    )
                }
                Spacer(modifier = Modifier.size(8.dp))
                Text(text = item.meanFirst, modifier = Modifier.align(Alignment.CenterVertically))
            }
        }
    }
}