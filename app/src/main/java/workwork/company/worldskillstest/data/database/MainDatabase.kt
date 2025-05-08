package workwork.company.worldskillstest.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import workwork.company.worldskillstest.data.database.convertors.PhotosConverter
import workwork.company.worldskillstest.data.database.dao.MainDao
import workwork.company.worldskillstest.domain.models.local.AudioEntity
import workwork.company.worldskillstest.domain.models.local.LocalEvent
import workwork.company.worldskillstest.domain.models.local.ticket.TicketEntity

class MainDatabase internal constructor(private val database: MainRoomDatabase) {
    val mainDao: MainDao
        get() = database.mainDao()
}

@Database(entities = [LocalEvent::class, TicketEntity::class, AudioEntity::class,], version = 1, exportSchema = false)
@TypeConverters( PhotosConverter::class,) // Register both converters
internal abstract class MainRoomDatabase : RoomDatabase() {
    abstract fun mainDao(): MainDao
}

fun MainDatabase(applicationContext: Context): MainDatabase {
    val mainRoomDatabase = Room.databaseBuilder(
        checkNotNull(applicationContext.applicationContext), MainRoomDatabase::class.java, "Lintu"
    ).build()
    return MainDatabase(mainRoomDatabase)
}