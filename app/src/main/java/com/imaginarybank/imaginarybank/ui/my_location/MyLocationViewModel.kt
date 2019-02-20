package com.imaginarybank.imaginarybank.ui.my_location

import com.google.gson.Gson
import com.imaginarybank.imaginarybank.R
import com.imaginarybank.imaginarybank.databinding.MyLocationFragmentBinding
import com.imaginarybank.imaginarybank.inputStreamToString
import com.imaginarybank.imaginarybank.model.ListModel
import com.imaginarybank.imaginarybank.model.LocationListModel
import org.greenrobot.eventbus.EventBus

/**
 * Created by RinaSelmani on 18-Feb-19.
 */
class MyLocationViewModel(val binding: MyLocationFragmentBinding) {
    var pinsLocation: List<LocationListModel> = emptyList()

    init {
        getListData()
    }

    fun getListData(): List<LocationListModel> {
        val myJson =
            inputStreamToString(binding.root.context.getResources().openRawResource(R.raw.ibl_data))
        val list = Gson().fromJson(myJson, ListModel::class.java)
        val locationList = list.data!!
        pinsLocation = locationList
        return locationList
    }
}