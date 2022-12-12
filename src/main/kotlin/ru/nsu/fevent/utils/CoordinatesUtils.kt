package ru.nsu.fevent.utils

import kotlin.math.*

object CoordinatesUtils {
    const val EARTH_RADIUS_IN_METRES = 6_371_000

    fun calculateDistanceInMetresBetweenLocations(
        userLocation: Location, eventLocation: Location): Int {

        val latitudeDifference = Math.toRadians((userLocation.latitude - eventLocation.latitude))
        val longitudeDifference = Math.toRadians((userLocation.longitude - eventLocation.longitude))

        val haversineOfCentralAngle = sin(latitudeDifference / 2) * sin(latitudeDifference / 2) +
                cos(Math.toRadians(userLocation.latitude)) *
                cos(Math.toRadians(eventLocation.latitude)) *
                sin(longitudeDifference / 2) * sin(longitudeDifference / 2)

        val centralAngle = 2 * atan2(sqrt(haversineOfCentralAngle), sqrt(1 - haversineOfCentralAngle))

        return (EARTH_RADIUS_IN_METRES * centralAngle).roundToInt()
    }
}