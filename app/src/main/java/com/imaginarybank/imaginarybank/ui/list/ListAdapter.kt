package com.imaginarybank.imaginarybank.ui.list

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.imaginarybank.imaginarybank.R
import com.imaginarybank.imaginarybank.databinding.ListItemBinding
import com.imaginarybank.imaginarybank.events.OpenActivityWithExtraEvent
import com.imaginarybank.imaginarybank.model.LocationListModel
import com.imaginarybank.imaginarybank.ui.location_detail_activity.LocationDetailActivity
import org.greenrobot.eventbus.EventBus

/**
 * Created by RinaSelmani on 17-Feb-19.
 */

class ListAdapter : RecyclerView.Adapter<ListAdapter.ListItemViewModel>() {
    var list: List<LocationListModel> = emptyList()
    override fun onCreateViewHolder(container: ViewGroup, position: Int): ListItemViewModel {
        val binding: ListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(container.context),
            R.layout.list_item, container, false
        )
        binding.model = ListItemViewModel(binding)
        return ListItemViewModel(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setLocationList(calls: List<LocationListModel>) {
        list = calls
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(container: ListItemViewModel, position: Int) {
        val location_item = list[position]
        container.binding.location = location_item
        setImage(container.binding.typeOfPinImagebutton, location_item.type!!)

    }

    fun setImage(imageView: ImageView, type: String) {
        when (type) {
            "branch" -> {
                imageView.setImageResource(R.mipmap.ic_branch)
            }
            "atm" -> {
                imageView.setImageResource(R.mipmap.ic_atm)

            }
        }

    }

    inner class ListItemViewModel(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun openLocationDetailActivity(locationItem: LocationListModel) {
            EventBus.getDefault().post(OpenActivityWithExtraEvent(LocationDetailActivity(), locationItem))
        }
    }
}