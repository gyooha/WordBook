package io.seroo.data

import io.seroo.data.model.Word
import io.seroo.data.model.WordWrapper
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    private val json = Json {
        coerceInputValues = true
        ignoreUnknownKeys = true
        isLenient = true
        encodeDefaults = true
    }

    @Test
    fun addition_isCorrect() {
        json.decodeFromString<List<Word>>(listWordJson)
            .also { println(it) }
    }

    @Test
    fun addition_isCorrect2() {
        json.decodeFromString<List<Word>>("[]")
            .also { println(it) }
    }

    @Test
    fun addition_isCorrect3() {
        json.decodeFromString<WordWrapper>(wordsWrapperJson)
            .also { println(it) }
    }

    private val listWordJson = """[{"id":-1,"word":"1","mean_first":"2","mean_second":"1","created_at":1606102628440,"updated_at":1606102628440},{"id":1,"word":"1","mean_first":"e","mean_second":"s","created_at":1606102879379,"updated_at":1608123373111},{"id":2,"word":"1e","mean_first":"12314ffff214ttttt","mean_second":"a","created_at":1606102888343,"updated_at":1606272185084},{"id":4,"word":"cccftttt","mean_first":"ssssrfffggtrrr","mean_second":"555556eeee","created_at":1606272047386,"updated_at":1608123255739},{"id":5,"word":"1","mean_first":"2","mean_second":"3","created_at":1606273419435,"updated_at":1606273419435},{"id":6,"word":"ㄱㄱㄱ","mean_first":"ㄴㄴㄴ","mean_second":"ㅁㅁ","created_at":1606913585841,"updated_at":1608123361747},{"id":7,"word":"ㅅ","mean_first":"","mean_second":"","created_at":1608103874898,"updated_at":1608103874898},{"id":8,"word":"ㄷ","mean_first":"ㅈ","mean_second":"ㅈ","created_at":1609748919923,"updated_at":1609748919923}]"""
    private val wordsWrapperJson = """{"words":[{"id":9,"word":"1","mean_first":"1","mean_second":"1","created_at":1610356102851,"updated_at":1610356102851}]}"""
}