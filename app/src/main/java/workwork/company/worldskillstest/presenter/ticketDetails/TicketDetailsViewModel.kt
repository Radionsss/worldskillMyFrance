package workwork.company.worldskillstest.presenter.ticketDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import workwork.company.worldskillstest.domain.MainRepository
import workwork.company.worldskillstest.presenter.listEvents.useCase.GetEventsUseCase
import javax.inject.Inject

@HiltViewModel
class TicketDetailsViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    ) : ViewModel() {

     fun markAsRead(eventId: String) {
         viewModelScope.launch {
             mainRepository.markAsRead(eventId)
         }
    }
}