package com.app.garant.presenter.screens.map

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.app.garant.R
import com.app.garant.data.other.StaticValue
import com.app.garant.databinding.ScreenMapBinding
import com.app.garant.utils.showToast
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView
import com.yandex.mapkit.user_location.UserLocationLayer
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ScreenMap : Fragment(R.layout.screen_map) {
    val bind by viewBinding(ScreenMapBinding::bind)

    private var mapView: MapView? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var locationUser: Point? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        MapKitFactory.setApiKey(API_KEY)
        MapKitFactory.initialize(requireContext());
        return inflater.inflate(R.layout.screen_map, container, false)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initClicks()

        mapView = view.findViewById(R.id.mapview) as MapView

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())


        mapView?.map?.addCameraListener { map, cameraPosition, cameraUpdateReason, b ->
            locationUser = Point(cameraPosition.target.latitude, cameraPosition.target.longitude)
        }


        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                mapView?.map?.move(
                    CameraPosition(
                        Point(41.300483, 69.270064),
                        14.0f,
                        0.0f,
                        0.0f
                    ),
                    Animation(Animation.Type.SMOOTH, 5f),
                    null
                )
            }

        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )

    }


    override fun onStop() {
        mapView!!.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapView!!.onStart()
    }

    private fun initClicks() {

        bind.back.setOnClickListener {
            findNavController().popBackStack()
        }

        bind.currentLocation.setOnClickListener {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->

                mapView?.map?.move(
                    CameraPosition(
                        Point(41.300483, 69.270064),
                        14.0f,
                        0.0f,
                        0.0f
                    ),
                    Animation(Animation.Type.LINEAR, 5f),
                    null
                )
            }

        }


        bind.choice.setOnClickListener {
            if (locationUser != null) {
                StaticValue.location = locationUser
                findNavController().popBackStack()
            } else {
                showToast("Error")
            }
        }

    }

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {

            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {

            }
            else -> {

            }
        }
    }


    companion object {
        const val API_KEY = "4d71f10a-b1dd-4ee5-82a2-a8b6851c2bf7"
    }
}