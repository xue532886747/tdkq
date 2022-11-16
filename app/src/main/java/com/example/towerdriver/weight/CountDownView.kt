package com.example.towerdriver.weight

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import com.example.towerdriver.R
import com.example.towerdriver.utils.timeutil.NewTimeTools

/**
 * @author 53288
 * @description
 * @date 2021/8/18
 */
class CountDownView(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    var tv_hour: TextView
    var tv_minute: TextView
    var tv_second: TextView
    var tv_day: TextView

    init {
        val inflate = LayoutInflater.from(context).inflate(R.layout.act_count_down, this)
        tv_hour = inflate.findViewById(R.id.tv_hour)
        tv_minute = inflate.findViewById(R.id.tv_minute)
        tv_second = inflate.findViewById(R.id.tv_second)
        tv_day = inflate.findViewById(R.id.tv_day)
    }

    fun getTime(long: Long) {
        tv_day.text = NewTimeTools.getDay(long)
        tv_hour.text = NewTimeTools.getHour(long)
        tv_minute.text = NewTimeTools.getMin(long)
        tv_second.text = NewTimeTools.getSec(long)
    }
}

