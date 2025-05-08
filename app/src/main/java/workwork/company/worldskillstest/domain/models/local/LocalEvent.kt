package workwork.company.worldskillstest.domain.models.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "events")
data class LocalEvent(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val details: String,
    val imageRes: String,
    val images: List<String>,
    val isRead: Boolean = false,  // По умолчанию не прочитано
    val viewCount: Int = 0        // По умолчанию 0 просмотров
)