package com.imaginarybank.imaginarybank.ui.location_detail_activity

import android.app.Activity
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.imaginarybank.imaginarybank.R
import com.imaginarybank.imaginarybank.databinding.LocationDetailActivityBinding
import com.imaginarybank.imaginarybank.events.CloseActivityEvent
import com.imaginarybank.imaginarybank.model.LocationListModel
import com.imaginarybank.imaginarybank.ui.working_hours.WorkingHoursAdapter
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * Created by RinaSelmani on 18-Feb-19.
 */
class LocationDetailActivity : Activity() {
    lateinit var binding: LocationDetailActivityBinding
    lateinit var locationItem: LocationListModel
    lateinit var workingHoursAdapter: WorkingHoursAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.location_detail_activity)
        binding.workingDayyRecycleview.layoutManager = LinearLayoutManager(this)
        workingHoursAdapter= WorkingHoursAdapter()
        val extra = intent.extras
        if (extra != null) {
            locationItem = extra.getParcelable("location_detail")!!
            binding.model = LocationDetailViewModel(binding, locationItem,workingHoursAdapter)

        }
    }

    public override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    public override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe
    fun event(closeActivityEvent: CloseActivityEvent) {
        onBackPressed()
    }

}