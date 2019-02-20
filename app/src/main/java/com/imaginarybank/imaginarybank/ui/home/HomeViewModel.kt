package com.imaginarybank.imaginarybank.ui.home

import com.google.gson.Gson
import com.imaginarybank.imaginarybank.R
import com.imaginarybank.imaginarybank.databinding.HomeActivityBinding
import com.imaginarybank.imaginarybank.events.FindMyLocationEvent
import com.imaginarybank.imaginarybank.events.ShowPinsInMapEvent
import com.imaginarybank.imaginarybank.inputStreamToString
import com.imaginarybank.imaginarybank.model.ListModel
import org.greenrobot.eventbus.EventBus

/**
 * Created by RinaSelmani on 17-Feb-19.
 */
class HomeViewModel(val binding: HomeActivityBinding,  adapter: HomeFragmentsAdapter) {

    init {
        binding.fragmentsViewpager.adapter = adapter
        showList()
        getData()
    }

    fun getData() {
        val myJson =
            inputStreamToString(binding.root.context.getResources().openRawResource(R.raw.ibl_data))
        val list = Gson().fromJson(myJson, ListModel::class.java)
        EventBus.getDefault().post(list)
    }

    fun findMyLocation() {
        EventBus.getDefault().post("Please wait up to 5 seconds until your location will be detected")
        binding.fragmentsViewpager.currentItem = 0
        binding.myLocationButton.isSelected = true
        binding.locationButton.isSelected = false
        binding.listButton.isSelected = false
        EventBus.getDefault().post(FindMyLocationEvent())
    }

    fun showList() {
        EventBus.getDefault().post(ShowPinsInMapEvent())
        binding.fragmentsViewpager.currentItem = 1
        binding.myLocationButton.isSelected = false
        binding.locationButton.isSelected = false
        binding.listButton.isSelected = true

    }

    fun localizePins() {
        EventBus.getDefault().post(ShowPinsInMapEvent())
        binding.fragmentsViewpager.currentItem = 0
        binding.myLocationButton.isSelected = false
        binding.locationButton.isSelected = true
        binding.listButton.isSelected = false
    }
}