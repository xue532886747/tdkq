package com.example.towerdriver.utils.timeutil

/**
 * @author 53288
 * @description
 * @date 2021/8/18
 */
object NewTimeTools {

    fun getDay(time: Long): String {
        if (time > 0) {
            val day = (time / (1000 * 60 * 60 * 24)).toString()
            return day
        } else {
            return "00"
        }
    }

    fun getHour(time: Long): String {
        if (time > 0) {
            val day = time / (1000 * 60 * 60 * 24)
            var hour = ((time - day * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60)).toString()
            if (hour.length == 1) {
                hour = "0$hour"
            }
            return hour
        } else {
            return "00"
        }
    }

    fun getMin(time: Long): String {
        if (time > 0) {
            val day = time / (1000 * 60 * 60 * 24)
            val hour = (time - day * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60)
            var min = ((time - day * (1000 * 60 * 60 * 24) - hour * (1000 * 60 * 60)) / (1000 * 60)).toString()
            if (min.length == 1) {
                min = "0$min"
            }
            return min
        } else {
            return "00"
        }
    }


    fun getSec(time: Long): String {
        if (time > 0) {
            val day = time / (1000 * 60 * 60 * 24)
            val hour = (time - day * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60)
            val min = (time - day * (1000 * 60 * 60 * 24) - hour * (1000 * 60 * 60)) / (1000 * 60)
            var sec = ((time - day * (1000 * 60 * 60 * 24) - hour * (1000 * 60 * 60) - min * (1000 * 60)) / 1000).toString()
            if (sec.length == 1) {
                sec = "0$sec"
            }
            return sec
        } else {
            return "00"
        }
    }
}