package com.proyecto

import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class Aula(
    var name: String = "",
    var position: GeoPoint = GeoPoint(0.0, 0.0),
    var floor: Int = 1,
    var pab: String = "",
    var image : Int
) {
    fun iamHere(position: GeoPoint, radius: Double): Boolean {
        val distance = distanceTo(position)
        return distance <= radius
    }

    //calcula la distancia entre dos puntos
    private fun distanceTo(other: GeoPoint): Double {
        val R = 6371000.0 // radio de la tierra
        val latDistance = Math.toRadians(other.latitude - this.position.latitude)
        val lonDistance = Math.toRadians(other.longitude - this.position.longitude)
        val a = sin(latDistance / 2.0) * sin(latDistance / 2.0) +
                cos(Math.toRadians(this.position.latitude)) * cos(Math.toRadians(other.latitude)) *
                sin(lonDistance / 2.0) * sin(lonDistance / 2.0)
        val c = 2.0 * atan2(sqrt(a), sqrt(1.0 - a))
        return R * c
    }
}