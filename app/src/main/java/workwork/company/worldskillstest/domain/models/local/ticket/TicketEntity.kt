package workwork.company.worldskillstest.domain.models.local.ticket

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tickets")
data class TicketEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,                   // Уникальный идентификатор билета (Primary Key)
    val ticketType: TicketFilter,            // Тип билета
    val name: String,          // Имя аудитории
    val audienceName: String,          // Имя аудитории
    val time: String,                  // Время создания билета (ГГГГ-ММ-ДД ЧЧ:ММ)
    val seat: String,                  // Место (например: "A6 Row7 Column9")
    val ticketImagePath: String ,       // Локальный путь или URL изображения билета
    val orderIndex: Int // Поле для хранения порядка элементов
)