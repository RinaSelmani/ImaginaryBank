package com.imaginarybank.imaginarybank.ui.location_detail_activity

import android.view.View
import com.imaginarybank.imaginarybank.databinding.LocationDetailActivityBinding
import com.imaginarybank.imaginarybank.events.CloseActivityEvent
import com.imaginarybank.imaginarybank.getCurrentDayOfWeekBasedOnDate
import com.imaginarybank.imaginarybank.model.LocationListModel
import com.imaginarybank.imaginarybank.model.WorkingHoursModel
import com.imaginarybank.imaginarybank.ui.working_hours.WorkingHoursAdapter
import org.greenrobot.eventbus.EventBus

/**
 * Created by RinaSelmani on 18-Feb-19.
 */
class LocationDetailViewModel(
    val binding: LocationDetailActivityBinding,
    locationItem: LocationListModel,
    val workingAdapter: WorkingHoursAdapter
) {
    init {
        setLocation(locationItem)
    }

    fun setLocation(location: LocationListModel) {
        binding.location = location
        if (location.type.equals("atm")) {
            binding.pinTypeImagebutton.isSelected = true
            binding.requestMeetingHolder.visibility = View.GONE
            binding.contactCentreHolder.visibility = View.GONE
            binding.workHoursHolder.visibility = View.GONE
        }
        val workingHours = location.working_hours
        if (workingHours != null) {
            val workingDaysList: MutableList<WorkingHoursModel> = mutableListOf()
            for (i in 0..6) {
                workingDaysList.add(WorkingHoursModel(i, -1, -1, -1, -1))
            }
            for (items in 0..workingHours.size - 1) {
                workingDaysList[items] = workingHours[items]
            }
            workingAdapter.setWorkingHoursList(workingDaysList)
            binding.workingDayyRecycleview.adapter = workingAdapter

            binding.isOpenTxtview.setText(getCurrentDayOfWeekBasedOnDate(workingDaysList))
        }


    }

    fun closeActivity() {
        EventBus.getDefault().post(CloseActivityEvent())
    }

    fun showWorkingHours() {
        binding.workingHoursHolder.visibility = View.VISIBLE
    }

    fun hideWorkingHoursHolder() {
        binding.workingHoursHolder.visibility = View.GONE

    }
}