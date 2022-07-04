package com.example.diplom

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.core.content.ContextCompat


class GPStracker(c: Context) : LocationListener {
    var context: Context = c

    fun getLocation(): Location? {

        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(context, "Разрешение не предоставлено!\nВ настройках разрешите\nприложению геолокацию.", Toast.LENGTH_LONG).show()
            return null
        }
        val lm =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager //подключаем менеджер локаций

        val isGPSEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)

        // проверяем что GPS включен
        if (isGPSEnabled) {
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10f, this)
            return lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        } else {
            Toast.makeText(context, "Пожалуйста, включите GPS! =)", Toast.LENGTH_LONG).show()
        }
        return null
    }

    override fun onLocationChanged(p0: Location) {}

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}

    override fun onProviderEnabled(provider: String) {}

    override fun onProviderDisabled(provider: String) {}
}