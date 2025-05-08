package workwork.company.worldskillstest.domain

import kotlinx.coroutines.flow.Flow
import workwork.company.worldskillstest.domain.models.local.AudioEntity
import workwork.company.worldskillstest.domain.models.local.LocalEvent
import workwork.company.worldskillstest.domain.models.local.ticket.TicketEntity
import workwork.company.worldskillstest.domain.models.remote.Event

interface MainRepository {
    suspend fun fetchEvents(): List<Event> // Получение событий из JSON (API)
    fun getLocalEvents(): Flow<List<LocalEvent>> // Получение всех локальных событий (Flow)
    fun getAllAudios(): Flow<List<AudioEntity>> // Получение всех локальных событий (Flow)
    suspend fun saveLocalEvents(events: List<LocalEvent>) // Сохранение событий в базу данных
    suspend fun incrementViewCount(eventId: String) // Увеличение viewCount на 1
    suspend fun markAsRead(eventId: String) // Пометка события как прочитанного

    suspend fun insertTicket(ticket: TicketEntity) // Вставка билета в базу данных
    fun getAllTickets(): Flow<List<TicketEntity>> // Получение всех билетов (Flow)
    suspend fun getTicketById(id: Int): TicketEntity? // Получение билета по ID
    suspend fun deleteTicket(ticket: TicketEntity) // Удаление билета
    suspend fun updateAllTicketsOrder(tickets: List<TicketEntity>)
    suspend fun insertTickets(tickets: List<TicketEntity>)
    suspend fun updateTicketsOrder(tickets: List<TicketEntity>)

    suspend fun insertAudio(filePath: String)
    suspend fun deleteAudio(audio: AudioEntity)
}