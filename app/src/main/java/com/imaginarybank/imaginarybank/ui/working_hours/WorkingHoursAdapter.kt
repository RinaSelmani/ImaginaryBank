package com.imaginarybank.imaginarybank.ui.working_hours

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.imaginarybank.imaginarybank.R
import com.imaginarybank.imaginarybank.databinding.WorkingHoursItemBinding
import com.imaginarybank.imaginarybank.model.WorkingHoursModel

/**
 * Created by RinaSelmani on 18-Feb-19.
 */
class WorkingHoursAdapter : RecyclerView.Adapter<WorkingHoursAdapter.WorkingHoursItemViewModel>() {
    var list: List<WorkingHoursModel> = emptyList()
    override fun onCreateViewHolder(container: ViewGroup, position: Int): WorkingHoursItemViewModel {
        val binding: WorkingHoursItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(container.context),
            R.layout.working_hours_item, container, false
        )
        return WorkingHoursItemViewModel(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setWorkingHoursList(workingHours: List<WorkingHoursModel>) {
        list = workingHours
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(container: WorkingHoursItemViewModel, position: Int) {
        container.binding.workingItem = list[position]
    }


    inner class WorkingHoursItemViewModel(val binding: WorkingHoursItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }
}