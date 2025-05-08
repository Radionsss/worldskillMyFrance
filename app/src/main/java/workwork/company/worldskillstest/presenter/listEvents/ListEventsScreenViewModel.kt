package workwork.company.worldskillstest.presenter.listEvents

import android.location.Location
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import workwork.company.worldskillstest.domain.MainRepository
import workwork.company.worldskillstest.domain.models.local.LocalEvent
import workwork.company.worldskillstest.presenter.listEvents.useCase.GetEventsUseCase
import javax.inject.Inject

@HiltViewModel
class ListEventsScreenViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val getEventsUseCase: GetEventsUseCase,
    ) : ViewModel() {

    private val _events = MutableStateFlow<List<LocalEvent>>(emptyList())
    val events: StateFlow<List<LocalEvent>> = _events.asStateFlow()

    init {
        viewModelScope.launch {
            getEventsUseCase.getEvents().collect {
                _events.value = it
            }
        }
    }
     fun incrementViewCount(eventId: String) {
         viewModelScope.launch {
             mainRepository.incrementViewCount(eventId)
         }
    }

     fun markAsRead(eventId: String) {
         viewModelScope.launch {
             mainRepository.markAsRead(eventId)
         }
    }
}