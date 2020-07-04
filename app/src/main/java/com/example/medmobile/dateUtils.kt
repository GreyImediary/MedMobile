package com.example.medmobile

import java.text.SimpleDateFormat
import java.util.*

fun convertDateToIso(calendar: Calendar): String {
    val date = calendar.time

    val sdf = SimpleDateFormat("yyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale("ru"))

    return sdf.format(date)
}