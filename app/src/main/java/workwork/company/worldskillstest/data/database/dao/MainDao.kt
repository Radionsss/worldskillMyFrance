package workwork.company.worldskillstest.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import workwork.company.worldskillstest.domain.models.local.AudioEntity
import workwork.company.worldskillstest.domain.models.local.LocalEvent
import workwork.company.worldskillstest.domain.models.local.ticket.TicketEntity

@Dao
interface MainDao {
    @Query("SELECT * FROM events")
    fun getAllEvents(): Flow<List<LocalEvent>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvents(events: List<LocalEvent>)

    // Увеличить viewCount на 1
    @Query("UPDATE events SET viewCount = viewCount + 1 WHERE id = :eventId")
    suspend fun incrementViewCount(eventId: String)

    // Установить isRead в true
    @Query("UPDATE events SET isRead = 1 WHERE id = :eventId")
    suspend fun markAsRead(eventId: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTicket(ticket: TicketEntity)

    @Query("SELECT * FROM tickets WHERE id = :id")
    suspend fun getTicketById(id: Int): TicketEntity?

    @Query("UPDATE tickets SET id = :orderIndex WHERE id = :ticketId")
    suspend fun updateTicketOrder(ticketId: Int, orderIndex: Int)

    @Query("SELECT * FROM tickets ORDER BY orderIndex ASC")
    fun getAllTickets(): kotlinx.coroutines.flow.Flow<List<TicketEntity>>

    @Transaction
    suspend fun updateAll(tickets: List<TicketEntity>) {
        tickets.forEachIndexed { index, ticket ->
            updateTicket(ticket.copy(orderIndex = index))
        }
    }
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(audio: AudioEntity)

    @Query("SELECT * FROM audio_records ORDER BY timestamp DESC")
    fun getAllAudios(): Flow<List<AudioEntity>>

    @Delete
    suspend fun delete(audio: AudioEntity)

    @Update
    suspend fun updateTicket(ticket: TicketEntity)

    @Delete
    suspend fun deleteTicket(ticket: TicketEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTickets(tickets: List<TicketEntity>)
}