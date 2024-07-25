package com.xavier_carpentier.realestatemanager.datasource.utils

import androidx.room.TypeConverter
import java.util.Calendar

/**
 * Type converters to allow Room to reference complex data types.
 */
class CalendarToLongConverter {
    @TypeConverter
    fun calendarToLong(calendar :Calendar?) :Long? = calendar?.timeInMillis

    @TypeConverter
    fun longToCalendar(long :Long?) :Calendar? = if(long==null){
        null
    }else{
        Calendar.getInstance().apply { timeInMillis = long }
    }
}