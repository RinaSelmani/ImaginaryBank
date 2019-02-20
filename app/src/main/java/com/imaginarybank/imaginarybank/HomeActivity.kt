package com.imaginarybank.imaginarybank

import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.imaginarybank.imaginarybank.databinding.HomeActivityBinding
import com.imaginarybank.imaginarybank.events.OpenActivityWithExtraEvent
import com.imaginarybank.imaginarybank.ui.home.HomeFragmentsAdapter
import com.imaginarybank.imaginarybank.ui.home.HomeViewModel
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class HomeActivity : AppCompatActivity() {
    lateinit var binding: HomeActivityBinding
    lateinit var fragmentsAdapter: HomeFragmentsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.home_activity)
        fragmentsAdapter = HomeFragmentsAdapter(supportFragmentManager)
        binding.fragmentsViewpager.offscreenPageLimit = 2
        binding.model = HomeViewModel(binding, fragmentsAdapter)

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
    fun event(openActivityWithExtraEvent: OpenActivityWithExtraEvent) {
        val intent = Intent(this, openActivityWithExtraEvent.activity::class.java)
        intent.putExtra("location_detail", openActivityWithExtraEvent.parcel)
        startActivity(intent)
    }
}
