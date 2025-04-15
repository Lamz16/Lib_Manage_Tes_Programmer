package com.lamz.libmanage.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Helper {
    fun getTodayDate(): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return formatter.format(Date())
    }

}