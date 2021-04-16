package io.seroo.wordbook.component.setting

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.seroo.data.model.WordWrapper
import io.seroo.data.repository.FileRepository
import io.seroo.data.repository.WordRepository
import io.seroo.wordbook.ContextWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.io.InputStream

class SettingViewModel @ViewModelInject constructor(
    private val wordRepository: WordRepository,
    private val contextWrapper: ContextWrapper,
    private val fileRepository: FileRepository
) : ViewModel() {

    private val _settings = MutableLiveData(listOf(SettingType.SAVE, SettingType.LOAD))
    val settings: LiveData<List<SettingType>> get() = _settings

    fun saveWordsToJsonFile() {
        wordRepository.selectWords()
            .flatMapMerge { fileRepository.writeFileByWords(WordWrapper(it)) }
            .flowOn(Dispatchers.IO)
            .onEach {
                contextWrapper.run {
                    startActivity(getFileSendIntent(it))
                }
            }
            .catch { it.printStackTrace() }
            .flowOn(Dispatchers.Main)
            .launchIn(viewModelScope)
    }

    fun saveJsonFileToWordDataBase(inputStream: InputStream) {
        fileRepository.readJsonFileToWords(inputStream)
            .onEach {
                Log.d("GYH", "result : $it")
            }
            .flatMapMerge { wordRepository.replace(*it.toTypedArray()) }
            .catch { it.printStackTrace() }
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }
}