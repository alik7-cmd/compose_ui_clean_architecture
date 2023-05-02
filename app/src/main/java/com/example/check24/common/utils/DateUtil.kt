package com.example.check24.common.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtil {

    fun getDateFromMiliSecond(timesInMillisecond : Long) : String{
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val dateString: String = simpleDateFormat.format(timesInMillisecond)
        return dateString
    }
}