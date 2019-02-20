package com.imaginarybank.imaginarybank.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



/**
 * Created by RinaSelmani on 18-Feb-19.
 */
class LocationListModel (
    @SerializedName("id")
    @Expose
    var id: Int? = null,
    @SerializedName("name")
    @Expose
    var name: String? = null,
    @SerializedName("address")
    @Expose
    var address: String? = null,
    @SerializedName("phone")
    @Expose
    var phone: String? = null,
    @SerializedName("email")
    @Expose
    var email: String? = null,
    @SerializedName("website")
    @Expose
    var website: String? = null,
    @SerializedName("type")
    @Expose
    var type: String? = null,
    @SerializedName("location")
    @Expose
    var location: LocationModel? = null,
    @SerializedName("working_hours")
    @Expose
    var working_hours: List<WorkingHoursModel>? = null
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(LocationModel::class.java.classLoader),
        parcel.createTypedArrayList(WorkingHoursModel)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(name)
        parcel.writeString(address)
        parcel.writeString(phone)
        parcel.writeString(email)
        parcel.writeString(website)
        parcel.writeString(type)
        parcel.writeParcelable(location, flags)
        parcel.writeTypedList(working_hours)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LocationListModel> {
        override fun createFromParcel(parcel: Parcel): LocationListModel {
            return LocationListModel(parcel)
        }

        override fun newArray(size: Int): Array<LocationListModel?> {
            return arrayOfNulls(size)
        }
    }
}