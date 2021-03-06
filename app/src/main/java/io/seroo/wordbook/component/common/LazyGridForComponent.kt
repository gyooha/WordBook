package io.seroo.wordbook.component.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
@Deprecated("use LazyVerticalGrid")
fun <T> LazyGridForIndexed(
    modifier: Modifier = Modifier,
    items: List<T> = listOf(),
    rows: Int,
    padding: Dp,
    onClick: (Int) -> Unit,
    itemAlignment: Alignment = Alignment.Center,
    itemContent: @Composable LazyItemScope.(T, Int) -> Unit
) {
    val chunkedList = items.chunked(rows)
    LazyColumn(
        modifier = modifier.padding(horizontal = padding)
    ) {
        itemsIndexed(chunkedList) { index, item ->
            if (index == 0) Spacer(modifier = Modifier.padding(top = padding, bottom = padding / 2))
            if (index == item.size - 1) Spacer(
                modifier = Modifier.padding(
                    top = padding / 2,
                    bottom = padding
                )
            )

            Row {
                item.forEachIndexed { innerIndex, innerItem ->
                    val currentIndex = index * rows + innerIndex
                    Box(
                        modifier = Modifier
                            .clickable(onClick = { onClick(currentIndex) })
                            .padding(8.dp)
                            .weight(1F),
                        contentAlignment = itemAlignment
                    ) { itemContent(innerItem, currentIndex) }
                }
                repeat(rows - item.size) {
                    Box(modifier = Modifier.weight(1f).padding(8.dp))
                }
            }
        }
    }
}