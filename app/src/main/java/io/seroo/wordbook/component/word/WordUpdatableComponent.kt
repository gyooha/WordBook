package io.seroo.wordbook.component.word

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@Composable
fun WordUpdateableComponent(wordUIModel: WordUIModel?, onClick: (WordUIModel) -> Unit) {
    var word by remember { mutableStateOf(wordUIModel?.word ?: "") }
    var meanFirst by remember { mutableStateOf(wordUIModel?.meanFirst ?: "") }
    var meanSecond by remember { mutableStateOf(wordUIModel?.meanSecond ?: "") }

    val focusRequester1 = FocusRequester()
    val focusRequester2 = FocusRequester()
    val focusRequester3 = FocusRequester()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        TextField(
            value = word,
            onValueChange = { word = it },
            textStyle = TextStyle(Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester1),
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusRequester2.requestFocus()
                }
            ),
            maxLines = 1,
        )
        TextField(
            value = meanFirst,
            onValueChange = { meanFirst = it },
            textStyle = TextStyle(Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester2),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            singleLine = true,
            keyboardActions = KeyboardActions(
                onNext = {
                    focusRequester3.requestFocus()
                }
            ),
            maxLines = 1
        )
        TextField(
            value = meanSecond,
            onValueChange = { meanSecond = it },
            textStyle = TextStyle(Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester3),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    when (word.isNotEmpty() && meanFirst.isNotEmpty()) {
                        true -> {
//                            hideSoftwareKeyboard()
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
                        }
                    }
                }
            ),
            singleLine = true,
            maxLines = 1
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
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
            enabled = (word.isNotEmpty() && meanFirst.isNotEmpty()) || meanSecond.isNotEmpty()
        ) {
            Text(text = "수정하기")
        }
    }
}