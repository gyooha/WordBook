package io.seroo.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WordWrapper(
    @SerialName("words")
    val words: List<Word>
)