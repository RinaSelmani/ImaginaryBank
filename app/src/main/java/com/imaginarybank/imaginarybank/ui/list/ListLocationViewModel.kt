package com.imaginarybank.imaginarybank.ui.list

import android.text.Editable
import android.text.TextWatcher
import com.imaginarybank.imaginarybank.R
import com.imaginarybank.imaginarybank.databinding.ListFragmentBinding
import com.imaginarybank.imaginarybank.inputStreamToString
import com.google.gson.Gson
import com.imaginarybank.imaginarybank.model.ListModel
import com.imaginarybank.imaginarybank.model.LocationListModel


/**
 * Created by RinaSelmani on 17-Feb-19.
 */
class ListLocationViewModel(
    val binding: ListFragmentBinding,
    val listAdapter: ListAdapter
) {

    var locationList: List<LocationListModel> = emptyList()

    init {
        getListData()
        filterListImplementation()
    }

    fun getListData() {
        val myJson =
            inputStreamToString(binding.root.context.getResources().openRawResource(com.imaginarybank.imaginarybank.R.raw.ibl_data))
        val list = Gson().fromJson(myJson, ListModel::class.java)
        locationList = list.data!!
        listAdapter.setLocationList(locationList)
        binding.listRecycleview.adapter = listAdapter

    }

    fun filterListImplementation() {
        binding.searchEdittx.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable) {
                if (p0.equals(" ")) {

                    listAdapter.setLocationList(locationList)
                } else {
                    filter(p0.toString())
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })
    }

    private fun filter(text: String) {

        val filteredList: MutableList<LocationListModel> = mutableListOf()

        for (i in 0..locationList.size - 1) {
            val isNameEqual = locationList[i].name!!.toLowerCase().contains(text.toLowerCase())
            val isAddressEqual = locationList[i].address!!.toLowerCase().contains(text.toLowerCase())
            if (isNameEqual or isAddressEqual) {
                filteredList.add(locationList.get(i))
            }
        }
        filterList((filteredList))


    }

    fun filterList(filterdNames: List<LocationListModel>) {
        listAdapter.setLocationList(filterdNames)
    }
}