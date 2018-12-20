package com.phjethva.mysql_db_crud_kotlin.utils

/**
 * @author PJET APPS (Pratik Jethva)
 * Check Out My Other Repositories On Github: https://github.com/phjethva
 * Visit My Website: https://www.pjetapps.com
 * Follow My Facebook Page: https://www.facebook.com/pjetapps
 */

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object Utils {

    fun getCurrentTime(): String? {
        val date = Calendar.getInstance().time
        val outputPattern = "dd/MM/yyyy kk:mm:ss"
        val outputFormat = SimpleDateFormat(outputPattern)
        return outputFormat.format(date)
    }

    fun formatDateTime(strDateTime: String): String? {
        val inputPattern = "dd/MM/yyyy kk:mm:ss"
        val outputPattern = "dd MMM yyyy hh:mm:ss a"
        val inputFormat = SimpleDateFormat(inputPattern)
        val outputFormat = SimpleDateFormat(outputPattern)
        var date: Date? = null
        var str: String? = null
        try {
            date = inputFormat.parse(strDateTime)
            str = outputFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return str
    }

}