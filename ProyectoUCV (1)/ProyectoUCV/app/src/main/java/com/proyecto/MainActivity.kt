package com.proyecto

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.proyecto.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private  val PREFS_NAME = "history_prefs"
    private  val KEY_HISTORY_LIST = "history_list"
    private lateinit var binding : ActivityMainBinding
    var aulas = ArrayList<Aula>()
    var pisoActual : Int = 1
    var pabActual : Int = 2
    var actualGeoPoint : GeoPoint = GeoPoint(0.0,0.0)
    lateinit var location : LocationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkPermissions()
        setListeners()
    }

    private fun init() {
        getLocations()
        initLocationManager()
        selectFloor()
    }

    private fun calculatePosition() {
        var inMap = false
        for(position in aulas) {
            if(position.iamHere(actualGeoPoint,5.0) && pisoActual == position.floor) {
                cargarImagen(position.image)
                inMap = true
            }
        }
        if(!inMap) {
            if(pisoActual <= 8) {
                changeImage(R.drawable.pabellon_b_1)
            } else {
                changeImage(R.drawable.pabellon_b_9)
            }
        }
    }

    private fun cargarImagen(name: Int) {
        try {
            binding.imageView.setImageResource(name)
        }catch (e : Exception) {
            if(pisoActual <= 8) {
                changeImage(R.drawable.pabellon_b_1)
            } else {
                changeImage(R.drawable.pabellon_b_9)
            }
        }
    }

    private fun changeImage(image: Int) {
        binding.imageView.setImageResource(image)
    }

    val locationManagerListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            actualGeoPoint = GeoPoint(location.latitude, location.longitude)
            calculatePosition()
        }

    }

    @SuppressLint("MissingPermission")
    private fun initLocationManager() {
        location = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        location.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,1f,locationManagerListener)
    }

    private fun getLocations() {
        val pabellonB = obtenerDatosPabellon()
        aulas.clear()
        aulas.addAll(pabellonB.aulas)
    }

    private fun checkPermissions() {
        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 123)
        } else {
            init()
        }
    }


    @SuppressLint("MissingPermission")
    private fun getLastKnownLocation() {
        val lastKnownLocation = location.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        if (lastKnownLocation != null) {
            actualGeoPoint = GeoPoint(lastKnownLocation.latitude, lastKnownLocation.longitude)
            calculatePosition()
        }
    }


    private fun setListeners() {
        binding.btnMenu.setOnClickListener {
            drawerUpdate()
        }

        binding.btnSearch.setOnClickListener {
            drawerUpdate()
            var selectLocation = SelectLocationFragment()
            selectLocation.itemList = aulas.map {
                "Pabellón "+it.pab+" Piso "+it.floor+" "+it.name
            }.toList()

            selectLocation.close = {
                selectLocation.dismiss()
            }

            selectLocation.success = { pab, floor, label ->
                pisoActual = floor
                pabActual = pab
                saveHistory(this,History(label,getCurrentDateString()))
                selectFloor()
                toggleVisibilityPab()
                selectLocation.dismiss()
            }

            selectLocation.show(supportFragmentManager,"selectLocation")
        }

        binding.containerB.setOnClickListener {
            pabActual = 2
            pisoActual = 1
            toggleVisibilityPab()
        }

        binding.floor1B.setOnClickListener {
            pabActual = 2
            pisoActual = 1
            selectFloor()
        }
        binding.floor2B.setOnClickListener {
            pabActual = 2
            pisoActual = 2
            selectFloor()
        }
        binding.floor3B.setOnClickListener {
            pabActual = 2
            pisoActual = 3
            selectFloor()
        }
        binding.floor4B.setOnClickListener {
            pabActual = 2
            pisoActual = 4
            selectFloor()
        }
        binding.floor5B.setOnClickListener {
            pabActual = 2
            pisoActual = 5
            selectFloor()
        }
        binding.floor6B.setOnClickListener {
            pabActual = 2
            pisoActual = 6
            selectFloor()
        }
        binding.floor7B.setOnClickListener {
            pabActual = 2
            pisoActual = 7
            selectFloor()
        }
        binding.floor8B.setOnClickListener {
            pabActual = 2
            pisoActual = 8
            selectFloor()
        }
        binding.floor9B.setOnClickListener {
            pabActual = 2
            pisoActual = 9
            selectFloor()
        }
        binding.floor10B.setOnClickListener {
            pabActual = 2
            pisoActual = 10
            selectFloor()
        }
        binding.floor11B.setOnClickListener {
            pabActual = 2
            pisoActual = 11
            selectFloor()
        }
        binding.floor12B.setOnClickListener {
            pabActual = 2
            pisoActual = 12
            selectFloor()
        }
    }

    private fun selectFloor() {

        when(pabActual) {
            2 -> {
                binding.textPositionNow.text = "Pabellón B piso $pisoActual"

                when(pisoActual) {

                    1 -> {
                        deselectFloor()
                        binding.viewFloor1B.setBackgroundColor(Color.RED)
                    }
                    2 -> {
                        deselectFloor()
                        binding.viewFloor2B.setBackgroundColor(Color.RED)
                    }
                    3 -> {
                        deselectFloor()
                        binding.viewFloor3B.setBackgroundColor(Color.RED)
                    }
                    4 -> {
                        deselectFloor()
                        binding.viewFloor4B.setBackgroundColor(Color.RED)
                    }
                    5 -> {
                        deselectFloor()
                        binding.viewFloor5B.setBackgroundColor(Color.RED)
                    }
                    6 -> {
                        deselectFloor()
                        binding.viewFloor6B.setBackgroundColor(Color.RED)
                    }
                    7 -> {
                        deselectFloor()
                        binding.viewFloor7B.setBackgroundColor(Color.RED)
                    }
                    8 -> {
                        deselectFloor()
                        binding.viewFloor8B.setBackgroundColor(Color.RED)
                    }
                    9 -> {
                        deselectFloor()
                        binding.viewFloor9B.setBackgroundColor(Color.RED)
                    }
                    10 ->{
                        deselectFloor()
                        binding.viewFloor10B.setBackgroundColor(Color.RED)
                    }
                    11 -> {
                        deselectFloor()
                        binding.viewFloor11B.setBackgroundColor(Color.RED)
                    }

                    12 -> {
                        deselectFloor()
                        binding.viewFloor12B.setBackgroundColor(Color.RED)
                    }
                }
            }
        }
        calculatePosition()
    }

    private fun deselectFloor() {
        binding.viewFloor1B.setBackgroundColor(Color.WHITE)
        binding.viewFloor2B.setBackgroundColor(Color.WHITE)
        binding.viewFloor3B.setBackgroundColor(Color.WHITE)
        binding.viewFloor4B.setBackgroundColor(Color.WHITE)
        binding.viewFloor5B.setBackgroundColor(Color.WHITE)
        binding.viewFloor6B.setBackgroundColor(Color.WHITE)
        binding.viewFloor7B.setBackgroundColor(Color.WHITE)
        binding.viewFloor8B.setBackgroundColor(Color.WHITE)
        binding.viewFloor9B.setBackgroundColor(Color.WHITE)
        binding.viewFloor10B.setBackgroundColor(Color.WHITE)
        binding.viewFloor11B.setBackgroundColor(Color.WHITE)
        binding.viewFloor12B.setBackgroundColor(Color.WHITE)
    }

    private fun toggleVisibilityPab() {
        selectFloor()
        when (pabActual) {
            2 -> {
                toggleContainerVisibility(binding.containerFloorB)
            }
        }
    }

    private fun toggleContainerVisibility(
        visibleContainer: View,
        vararg hiddenContainers: View
    ) {
        if (visibleContainer.visibility == View.VISIBLE) {
            visibleContainer.visibility = View.GONE
        } else {
            visibleContainer.visibility = View.VISIBLE
            hiddenContainers.forEach { it.visibility = View.GONE }
        }
    }


    private fun drawerUpdate() {
        if (binding.drawer.isDrawerOpen(GravityCompat.START)) {
            binding.drawer.closeDrawer(GravityCompat.START)
        } else {
            binding.drawer.openDrawer(GravityCompat.START)
        }
    }

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun saveHistory(context: Context, newHistory: History) {
        val sharedPreferences = getSharedPreferences(context)
        val editor = sharedPreferences.edit()
        val listHistory = getHistoryList(context)
        listHistory.list.add(newHistory)
        val gson = Gson()
        val json = gson.toJson(listHistory)
        editor.putString(KEY_HISTORY_LIST, json)
        editor.apply()
    }

    fun getHistoryList(context: Context): ListHistory {
        val sharedPreferences = getSharedPreferences(context)
        val gson = Gson()
        val json = sharedPreferences.getString(KEY_HISTORY_LIST, null)

        return if (json != null) {
            val type = object : TypeToken<ListHistory>() {}.type
            gson.fromJson(json, type)
        } else {
            ListHistory()
        }
    }

    fun getCurrentDateString(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd mm:ss", Locale.getDefault())
        val currentDate = Date()
        return dateFormat.format(currentDate)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 123) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                Log.e("Permisos", "Permiso de ubicación concedido")
                init()
            } else {
                // Permiso denegado
                Log.e("Permisos", "Permiso de ubicación denegado")
            }
        }
    }
}