package io.seroo.wordbook.component.setting

import android.net.Uri
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.seroo.csv.CSVRepository
import io.seroo.data.repository.WordRepository
import io.seroo.wordbook.ContextWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class SettingViewModel @ViewModelInject constructor(
    private val wordRepository: WordRepository,
    private val csvRepository: CSVRepository,
    private val contextWrapper: ContextWrapper
) : ViewModel() {

    private val _settings = MutableLiveData(listOf(SettingType.SAVE, SettingType.LOAD))
    val settings: LiveData<List<SettingType>> get() = _settings

    fun saveWordsToCSVFile() {
        wordRepository.selectWords()
            .flatMapMerge { csvRepository.makeCSVFileIntent(it) }
            .flowOn(Dispatchers.IO)
            .onEach { contextWrapper.startActivity(it) }
            .catch { it.printStackTrace() }
            .flowOn(Dispatchers.Main)
            .launchIn(viewModelScope)
    }

    fun saveCSVToWrodsDataBase(uri: Uri) {
        csvRepository.readCSVFileToWords(uri)
            .onEach {
                Log.d("GYH", "result : $it")
            }
            .flatMapMerge { wordRepository.replace(*it.toTypedArray()) }
            .catch { it.printStackTrace() }
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }
}