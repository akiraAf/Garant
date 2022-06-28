package com.app.garant.utils

import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.app.garant.app.App

fun getColor(@ColorRes colorResId: Int): Int = ContextCompat.getColor(App.instance, android.R.color.holo_red_dark)

infix fun String.spannableText(query: String): SpannableString {
    val spanBuild = SpannableString(this)
    var startPos = -1
    var endPos = -1

    val fcs = ForegroundColorSpan(App.instance.resources.getColor(android.R.color.holo_red_dark))
    startPos = this.indexOf(query, 0, ignoreCase = true)
    endPos = startPos + query.length
    if (startPos > -1) {
        spanBuild.setSpan(fcs, startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
    return spanBuild
}