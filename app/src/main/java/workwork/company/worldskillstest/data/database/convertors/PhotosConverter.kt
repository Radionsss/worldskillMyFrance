package workwork.company.worldskillstest.data.database.convertors

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PhotosConverter {

    @TypeConverter
    fun fromPhotosList(photos: List<String>?): String {
        return Gson().toJson(photos)
    }

    @TypeConverter
    fun toPhotosList(photosString: String): List<String>? {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(photosString, listType)
    }
}