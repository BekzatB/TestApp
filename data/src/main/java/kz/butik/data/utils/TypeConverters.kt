package kz.butik.data.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kz.butik.data.local_models.SourceLocalModel

object TypeConverters {
    @TypeConverter
    @JvmStatic
    fun fromSourceLocalModel(data: SourceLocalModel): String? {
        return Gson().toJson(data)
    }

    @TypeConverter
    @JvmStatic
    fun toSourceLocalModel(data: String): SourceLocalModel? {
        val type = object : TypeToken<SourceLocalModel>() {}.type
        return Gson().fromJson(data, type)
    }
}