package workwork.company.worldskillstest.presenter.audio

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import workwork.company.worldskillstest.domain.MainRepository
import workwork.company.worldskillstest.domain.models.local.AudioEntity
import javax.inject.Inject

@HiltViewModel
class AudioViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {
    private val _audios = MutableStateFlow<List<AudioEntity>>(emptyList())
    val audios: StateFlow<List<AudioEntity>> = _audios.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getAllAudios().collect {
                _audios.value = it
            }
        }
    }

    fun addAudio(filePath: String) {
        viewModelScope.launch {
            repository.insertAudio(filePath)
        }
    }

}