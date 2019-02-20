package com.imaginarybank.imaginarybank.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


/**
 * Created by RinaSelmani on 18-Feb-19.
 */
class WorkingHoursModel(

    @SerializedName("day")
    @Expose
    var day: Int? = null,
    @SerializedName("start_hours")
    @Expose
    var start_hours: Int? = null,
    @SerializedName("start_minutes")
    @Expose
    var start_minutes: Int? = null,
    @SerializedName("end_hours")
    @Expose
    var end_hours: Int? = null,
    @SerializedName("end_minutes")
    @Expose
    var end_minutes: Int? = null
) : Parcelable {

    fun returnWorkingHours(): String {
        var workingHours = ""
        if (start_hours == -1) {
            workingHours = "Zatvoreno"
        } else {
            workingHours = "" + start_hours + ":" + start_minutes + "-" + end_hours + ":" + end_minutes
        }
        return workingHours
    }

    fun returnDayOfWeek(): String {
        var dayOfWeek = ""
        when (day) {
            0 -> dayOfWeek = "Monday"
            1 -> dayOfWeek = "Tuesday"
            2 -> dayOfWeek = "Wednesday"
            3 -> dayOfWeek = "Thursday"
            4 -> dayOfWeek = "Friday"
            5 -> dayOfWeek = "Saturday"
            6 -> dayOfWeek = "Sunday"


        }
        return dayOfWeek
    }

    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(day)
        parcel.writeValue(start_hours)
        parcel.writeValue(start_minutes)
        parcel.writeValue(end_hours)
        parcel.writeValue(end_minutes)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<WorkingHoursModel> {
        override fun createFromParcel(parcel: Parcel): WorkingHoursModel {
            return WorkingHoursModel(parcel)
        }

        override fun newArray(size: Int): Array<WorkingHoursModel?> {
            return arrayOfNulls(size)
        }
    }
}