package workwork.company.worldskillstest.data

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.Json
import workwork.company.worldskillstest.R
import workwork.company.worldskillstest.data.database.MainDatabase
import workwork.company.worldskillstest.data.database.dao.MainDao
import workwork.company.worldskillstest.domain.MainRepository
import workwork.company.worldskillstest.data.remote.ApiService
import workwork.company.worldskillstest.domain.models.local.AudioEntity
import workwork.company.worldskillstest.domain.models.local.LocalEvent
import workwork.company.worldskillstest.domain.models.local.ticket.TicketEntity
import workwork.company.worldskillstest.domain.models.remote.Event
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val database: MainDatabase,
    @ApplicationContext private val context: Context
) : MainRepository {

    private val mainDao: MainDao = database.mainDao

    // 1. Получение событий из API (JSON)
    override suspend fun fetchEvents(): List<Event> {
        val inputStream = context.resources.openRawResource(R.raw.events_data) // Правильный способ открыть файл из res/raw
        val jsonString = inputStream.bufferedReader().use { it.readText() }
        return Json.decodeFromString(jsonString)
    }

    // 2. Получение всех локальных событий из базы
    override fun getLocalEvents(): Flow<List<LocalEvent>> {
        return mainDao.getAllEvents() // Room автоматически возвращает Flow
    }

    override fun getAllAudios(): Flow<List<AudioEntity>> {
        return mainDao.getAllAudios()
    }

    // 3. Сохранение списка локальных событий в базу данных
    override suspend fun saveLocalEvents(events: List<LocalEvent>) {
        mainDao.insertEvents(events)
    }

    // 4. Увеличение `viewCount` для определенного события
    override suspend fun incrementViewCount(eventId: String) {
        mainDao.incrementViewCount(eventId)
    }

    // 5. Пометка события как прочитанного (`isRead = true`)
    override suspend fun markAsRead(eventId: String) {
        mainDao.markAsRead(eventId)
    }
    override suspend fun insertTicket(ticket: TicketEntity) {
        mainDao.insertTicket(ticket)
    }
    override suspend fun insertTickets(tickets: List<TicketEntity>) {
        mainDao.insertTickets(tickets)
    }
    override fun getAllTickets(): Flow<List<TicketEntity>> {
        return mainDao.getAllTickets() // Возвращаем Flow из DAO
    }

    override suspend fun getTicketById(id: Int): TicketEntity? {
        return mainDao.getTicketById(id)
    }

    override suspend fun deleteTicket(ticket: TicketEntity) {
        mainDao.deleteTicket(ticket)
    }
    override suspend fun updateTicketsOrder(tickets: List<TicketEntity>) {
        mainDao.updateAll(tickets)
    }
    override suspend fun updateAllTicketsOrder(tickets: List<TicketEntity>) {
      //  mainDao.updateAllTicketsOrder(tickets)
    }

    override suspend fun insertAudio(filePath: String) {
        val audio = AudioEntity(filePath = filePath, timestamp = System.currentTimeMillis())
        mainDao.insert(audio)
    }

    override  suspend fun deleteAudio(audio: AudioEntity) {
        mainDao.delete(audio)
    }
}