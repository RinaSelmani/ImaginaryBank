package com.imaginarybank.imaginarybank.ui.my_location

import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView
import android.view.View
import android.widget.Toast
import com.google.android.gms.maps.model.Marker

import com.google.android.gms.maps.GoogleMap
import com.imaginarybank.imaginarybank.R


/**
 * Created by RinaSelmani on 18-Feb-19.
 */
class InfoWindowAdapter(internal var context: Context) : GoogleMap.InfoWindowAdapter {
    lateinit var inflater: LayoutInflater
    override fun getInfoContents(marker: Marker): View? {
        return null
    }

    override fun getInfoWindow(marker: Marker): View {
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = inflater.inflate(R.layout.marker_information_item, null)
        val placeName = v.findViewById(R.id.place_name_txtview) as TextView
        val placeAddress = v.findViewById(R.id.place_address_txtview) as TextView
        val data = marker.snippet.split(":")
        placeName.text = data[0]
        placeAddress.text = data[1]
        if (data[0].equals("your"))
            v.visibility = View.GONE
        return v
    }
}