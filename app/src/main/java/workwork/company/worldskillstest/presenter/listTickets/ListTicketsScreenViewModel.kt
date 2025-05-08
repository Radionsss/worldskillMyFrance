package workwork.company.worldskillstest.presenter.listTickets

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import workwork.company.worldskillstest.domain.MainRepository
import workwork.company.worldskillstest.domain.models.local.ticket.TicketEntity
import workwork.company.worldskillstest.domain.models.local.ticket.TicketFilter
import workwork.company.worldskillstest.presenter.listEvents.useCase.GetEventsUseCase
import javax.inject.Inject

@HiltViewModel
class ListTicketsScreenViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val getEventsUseCase: GetEventsUseCase,
) : ViewModel() {

    private val _tickets = MutableStateFlow<List<TicketEntity>>(emptyList())
    val tickets: StateFlow<List<TicketEntity>> = _tickets.asStateFlow()

    init {
        viewModelScope.launch {
            mainRepository.getAllTickets().collect {
                _tickets.value = it
            }
        }
        addTestTickets()
    }

    fun addTestTickets() {
        viewModelScope.launch {
            val testTickets = (1..10).map { index ->
                TicketEntity(
                    ticketType = if (index % 2 == 0) TicketFilter.OPEN else TicketFilter.CLOSE,
                    name = "Ticket #$index",
                    audienceName = "Audience $index",
                    time = "2025-01-28 12:0$index",
                    seat = "Row${index} Column${index}",
                    ticketImagePath = "https://images.unsplash.com/photo-1544717305-996b815c338c",
                    orderIndex = index
                )
            }
         //   mainRepository.insertTickets(testTickets)
        }
    }

    fun deleteTicket(ticket: TicketEntity) {
        viewModelScope.launch {
            mainRepository.deleteTicket(ticket)
        }
    }
    fun updateTicketsOrder(updatedTickets: List<TicketEntity>) {
        viewModelScope.launch {
          //  _tickets.value = updatedTickets
           mainRepository.updateTicketsOrder(updatedTickets) // Опционально обновляем в базе данных
        }
    }
    //    fun updateAllTicketsOrder(tickets: List<TicketEntity>) {
//         viewModelScope.launch {
//             mainRepository.updateAllTicketsOrder(tickets)
//         }
//    }
}