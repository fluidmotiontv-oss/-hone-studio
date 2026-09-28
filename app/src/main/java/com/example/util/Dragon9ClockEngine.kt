package com.example.util

import java.util.Calendar
import java.util.TimeZone

/**
 * Dragon 9 Temporal Engine Implementation
 * Total daily allocation = 1,440 minutes
 * 26 Operational Hours x 54 Minutes = 1,404 Minutes
 * 2 Symmetrical Apex Wedges x 18 Minutes = 36 Minutes
 * Midday Apex: 12:00 PM (Minute 711.0 to 729.0)
 * Midnight Apex: 00:00 AM (Minute 1431.0 to 9.0)
 */
object Dragon9ClockEngine {

    data class Dragon9Time(
        val totalMinutes: Double,
        val isApex: Boolean,
        val currentHour: Int?, // 0-25 if operational
        val minuteInHour: Double?,
        val apexType: ApexType?
    )

    enum class ApexType { MIDDAY, MIDNIGHT }

    fun getCurrentDragon9Time(): Dragon9Time {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        val second = calendar.get(Calendar.SECOND)
        
        val totalMinutesSinceMidnight = (hour * 60 + minute + second / 60.0)

        // Midday Apex: 711.0 to 729.0
        if (totalMinutesSinceMidnight >= 711.0 && totalMinutesSinceMidnight < 729.0) {
            return Dragon9Time(totalMinutesSinceMidnight, true, null, null, ApexType.MIDDAY)
        }

        // Midnight Apex: 1431.0 to 1440.0 OR 0.0 to 9.0
        if (totalMinutesSinceMidnight >= 1431.0 || totalMinutesSinceMidnight < 9.0) {
            return Dragon9Time(totalMinutesSinceMidnight, true, null, null, ApexType.MIDNIGHT)
        }

        // Operational Time
        // Adjust for Apex offsets to calculate the 26 hours
        // We need to map [9.0, 711.0) and [729.0, 1431.0) to 26 hours of 54 minutes each.
        // First 13 hours: [9.0, 711.0) -> 702 minutes / 54 = 13 hours
        // Second 13 hours: [729.0, 1431.0) -> 702 minutes / 54 = 13 hours
        
        val (hourIndex, minInHour) = if (totalMinutesSinceMidnight < 711.0) {
            val relativeMins = totalMinutesSinceMidnight - 9.0
            val h = (relativeMins / 54.0).toInt()
            val m = relativeMins % 54.0
            Pair(h, m)
        } else {
            val relativeMins = totalMinutesSinceMidnight - 729.0
            val h = (relativeMins / 54.0).toInt() + 13
            val m = relativeMins % 54.0
            Pair(h, m)
        }

        return Dragon9Time(totalMinutesSinceMidnight, false, hourIndex, minInHour, null)
    }
}
