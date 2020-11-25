package io.seroo.wordbook.component.word

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.savedinstancestate.savedInstanceState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun WordUpdateComponent(wordUIModel: WordUIModel?, onClick: (WordUIModel) -> Unit) {
    var word by savedInstanceState { wordUIModel?.word ?: "" }
    var meanFirst by savedInstanceState { wordUIModel?.meanFirst ?: "" }
    var meanSecond by savedInstanceState { wordUIModel?.meanSecond ?: "" }

    Column(
        modifier = Modifier.fillMaxWidth().fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        TextField(
            value = word,
            onValueChange = { word = it },
            textStyle = TextStyle(Color.White),
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1
        )
        TextField(
            value = meanFirst,
            onValueChange = { meanFirst = it },
            textStyle = TextStyle(Color.White),
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1
        )
        TextField(
            value = meanSecond,
            onValueChange = { meanSecond = it },
            textStyle = TextStyle(Color.White),
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1
        )
        Button(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            onClick = {
                onClick(
                    WordUIModel(
                        id = wordUIModel?.id ?: 0,
                        word = word,
                        meanFirst = meanFirst,
                        meanSecond = meanSecond,
                        createdAt = wordUIModel?.createdAt ?: System.currentTimeMillis(),
                        System.currentTimeMillis()
                    )
                )
            },
            enabled = word.isNotEmpty() || meanFirst.isNotEmpty() || meanSecond.isNotEmpty()
        ) {
            Text(text = "수정하기")
        }
    }
}