package com.imaginarybank.imaginarybank.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by RinaSelmani on 18-Feb-19.
 */
class ListModel {
    @SerializedName("data")
    @Expose
    var data: List<LocationListModel>? = null
}