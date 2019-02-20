package com.imaginarybank.imaginarybank

import android.icu.text.SimpleDateFormat
import android.os.Build
import android.support.annotation.RequiresApi
import com.imaginarybank.imaginarybank.model.WorkingHoursModel
import java.io.IOException
import java.io.InputStream
import java.text.ParseException
import java.util.*


/**
 * Created by RinaSelmani on 18-Feb-19.
 */


fun inputStreamToString(inputStream: InputStream): String? {
    try {
        val bytes = ByteArray(inputStream.available())
        inputStream.read(bytes, 0, bytes.size)
        return String(bytes)
    } catch (e: IOException) {
        return null
    }

}

@RequiresApi(Build.VERSION_CODES.N)
fun getCurrentDayOfWeekBasedOnDate(weeks: List<WorkingHoursModel>): String {
    val dayofWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
    val myDate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().time)
    val myTime = myDate.subSequence(11, myDate.length - 3).removePrefix(" ").toString()
    var dayOfWeekPosition = 0
    when (dayofWeek) {
        Calendar.MONDAY -> dayOfWeekPosition = 0
        Calendar.TUESDAY -> dayOfWeekPosition = 1
        Calendar.WEDNESDAY -> dayOfWeekPosition = 2
        Calendar.THURSDAY -> dayOfWeekPosition = 3
        Calendar.FRIDAY -> dayOfWeekPosition = 4
        Calendar.SATURDAY -> dayOfWeekPosition = 5
        Calendar.SUNDAY -> dayOfWeekPosition = 6

    }
    for (days in weeks) {
        if (days.day == dayOfWeekPosition) {
            if ((days.start_hours != -1) and (days.start_minutes != -1)) {
                val format = SimpleDateFormat("HH:mm")
                try {
                    val currentTime = format.parse(myTime)
                    val startTime = format.parse("" + days.start_hours + ":" + days.start_minutes)
                    val endTime = format.parse("" + days.end_hours + ":" + days.end_minutes)
                    if (currentTime > startTime && currentTime < endTime) {
                        return "Opended"
                    }

                } catch (e: ParseException) {
                    e.printStackTrace()
                }

                break
            }
        }

    }
    return "Closed"

}