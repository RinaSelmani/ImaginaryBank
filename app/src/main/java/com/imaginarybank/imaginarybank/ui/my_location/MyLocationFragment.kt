package com.imaginarybank.imaginarybank.ui.my_location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Resources
import android.databinding.DataBindingUtil
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import com.imaginarybank.imaginarybank.R
import com.imaginarybank.imaginarybank.databinding.MyLocationFragmentBinding
import com.imaginarybank.imaginarybank.events.FindMyLocationEvent
import com.imaginarybank.imaginarybank.events.OpenActivityWithExtraEvent
import com.imaginarybank.imaginarybank.events.ShowPinsInMapEvent
import com.imaginarybank.imaginarybank.model.LocationListModel
import com.imaginarybank.imaginarybank.ui.location_detail_activity.LocationDetailActivity
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * Created by RinaSelmani on 17-Feb-19.
 */
class MyLocationFragment : Fragment(), OnMapReadyCallback {

    val REQUEST_CURRENT_LOCATION_PERMISSION = 1
    lateinit var binding: MyLocationFragmentBinding
    lateinit var googleMap: GoogleMap
    lateinit var myLocationMarker: Marker
    lateinit var currentLocationMarker: Marker
    lateinit var pins: List<LocationListModel>
    private var locationManager: LocationManager? = null
    private var mFusedLocationProviderClient: FusedLocationProviderClient? = null
    private val INTERVAL: Long = 2000
    private val FASTEST_INTERVAL: Long = 1000
    internal lateinit var mLocationRequest: LocationRequest
    override fun onMapReady(map: GoogleMap?) {
        googleMap = map!!
        try {
            googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    context!!, R.raw.style_map_google
                )
            )

        } catch (e: Resources.NotFoundException) {
        }
        googleMap.uiSettings.isMyLocationButtonEnabled = true

        setPinsInMap(binding.model?.getListData()!!)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.my_location_fragment, container, false)
        binding.model = MyLocationViewModel(binding)
        locationManager = activity!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        mLocationRequest = LocationRequest()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.map.onCreate(null)
        binding.map.onResume()
        binding.map.getMapAsync(this)
    }

    private fun findCurrentLocation() {
        if (!locationManager!!.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            buildAlertMessageNoGps()
        } else {
            startLocationUpdates()
        }
    }

    private fun buildAlertMessageNoGps() {
        Toast.makeText(context!!, "Turn on GPS please", Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("MissingPermission")
    protected fun startLocationUpdates() {

        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.setInterval(INTERVAL)
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL)

        val builder = LocationSettingsRequest.Builder()
        builder.addLocationRequest(mLocationRequest)
        val locationSettingsRequest = builder.build()

        val settingsClient = LocationServices.getSettingsClient(activity!!)
        settingsClient.checkLocationSettings(locationSettingsRequest)

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity!!)
        mFusedLocationProviderClient!!.requestLocationUpdates(
            mLocationRequest, mLocationCallback,
            Looper.myLooper()
        )
    }


    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            locationResult.lastLocation
            onLocationChanged(locationResult.lastLocation)
        }
    }

    fun onLocationChanged(location: Location) {
        // New location has now been determined
        val mLastLocation = LatLng(location.latitude, location.longitude)
        setCurrentLocationInMap(mLastLocation)

    }

    fun setCurrentLocationInMap(location: LatLng) {
        val currentLocation = LatLng(location.latitude, location.longitude)
        val markerOptions = MarkerOptions().position(
            currentLocation
        ).title("Your Location").snippet("Current:Location")
        markerOptions.icon(
            BitmapDescriptorFactory.fromResource(R.mipmap.ic_pin_user)
        )

        currentLocationMarker = googleMap.addMarker(markerOptions)
        val cameraPosition = CameraPosition.Builder()
            .target(currentLocation).zoom(12f).build()
        googleMap.animateCamera(
            CameraUpdateFactory
                .newCameraPosition(cameraPosition)
        )
    }

    private fun stoplocationUpdates() {
        mFusedLocationProviderClient!!.removeLocationUpdates(mLocationCallback)
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe
    fun event(pinsList: List<LocationListModel>) {
        pins = pinsList
    }

    @Subscribe
    fun event(findMyLocationEvent: FindMyLocationEvent) {
        showLocationPermissionDialog()
    }

    @Subscribe
    fun event(showPinsInMapEvent: ShowPinsInMapEvent) {
        if (::currentLocationMarker.isInitialized) {
            currentLocationMarker.remove()
        }
        stoplocationUpdates()
        setPinsInMap(binding.model?.getListData()!!)

    }

    fun setPinsInMap(list: List<LocationListModel>) {
        var iterator = 0
        for (pins in list) {
            iterator++
            val location = LatLng(pins.location?.lat!!, pins.location?.long!!)
            var pin: Int = R.mipmap.ic_pin_atm
            val markerOptions = MarkerOptions().position(
                location
            ).snippet("${pins.name}:${pins.address}").title("${pins.id}")
            when (pins.type) {
                "atm" -> {
                    pin = R.mipmap.ic_pin_atm
                }
                "branch" -> {
                    pin = R.mipmap.ic_pin_branch

                }
                "user" -> {
                    pin = R.mipmap.ic_pin_user

                }
            }
            markerOptions.icon(
                BitmapDescriptorFactory.fromResource(pin)
            )
            myLocationMarker = googleMap.addMarker(markerOptions)

            if (iterator == list.size - 1) {
                googleMap.setInfoWindowAdapter(InfoWindowAdapter(context!!))
                googleMap.setOnInfoWindowClickListener { marker ->
                    if (!marker.title.equals("Your Location")) {
                        val pinsLocation = binding.model?.pinsLocation
                        for (i in pinsLocation!!) {
                            if (i.id == marker.title.toInt()) {
                                EventBus.getDefault().post(OpenActivityWithExtraEvent(LocationDetailActivity(), i))
                                break
                            }
                        }
                    }

                }
                myLocationMarker.showInfoWindow()

                val cameraPosition = CameraPosition.Builder()
                    .target(location).zoom(12f).build()
                googleMap.animateCamera(
                    CameraUpdateFactory
                        .newCameraPosition(cameraPosition)
                )
            }
        }

    }


    fun showLocationPermissionDialog() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(
                activity!!,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        ) {
            Toast.makeText(context!!, "Turn on gps settings in app to continue ", Toast.LENGTH_SHORT).show()
            return
        }

        if (ContextCompat.checkSelfPermission(activity!!, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            // Permission is not granted
            ActivityCompat.requestPermissions(
                activity!!,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CURRENT_LOCATION_PERMISSION
            )
        } else {
            //show currentLocation
            findCurrentLocation()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CURRENT_LOCATION_PERMISSION -> {
                if ((grantResults.size >= 0) and (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    findCurrentLocation()
                }
            }
        }
    }


}