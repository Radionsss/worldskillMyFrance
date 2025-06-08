package workwork.company.worldskillstest.presenter.createTicket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import workwork.company.worldskillstest.domain.MainRepository
import workwork.company.worldskillstest.domain.models.local.ticket.TicketEntity
import javax.inject.Inject

@HiltViewModel
class CreateTicketScreenViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    ) : ViewModel() {

    private val _tickets = MutableStateFlow<List<TicketEntity>>(emptyList())
    val tickets: StateFlow<List<TicketEntity>> = _tickets.asStateFlow()

    init {
        viewModelScope.launch {
            mainRepository.getAllTickets().collect {
                _tickets.value = it
            }
        }
    }

     fun createTicket(ticket: TicketEntity) {
         viewModelScope.launch {
             mainRepository.insertTicket(ticket)
         }
    }
}