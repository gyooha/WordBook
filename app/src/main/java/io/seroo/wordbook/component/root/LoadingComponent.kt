package io.seroo.wordbook.component.root

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun LoadingComponent() {
    Column(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
        CircularProgressIndicator()
    }
}