@file:Suppress("NOTHING_TO_INLINE")

package me.reezy.cosmo.span

import android.content.res.Resources
import android.graphics.Typeface
import android.os.Build
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.ParagraphStyle
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.text.style.TypefaceSpan
import android.util.Log
import androidx.core.text.inSpans
import me.reezy.cosmo.span.compat.TypefaceCompat
import me.reezy.cosmo.span.style.TextColorSpan
import java.util.regex.Pattern



fun SpannableStringBuilder.inBlock(vararg spans: ParagraphStyle, builderAction: SpannableStringBuilder.() -> Unit): SpannableStringBuilder {
    if (isNotEmpty() && this[length - 1] != '\n') {
        append("\n");
    }
    val start = length
    builderAction()
    if (isNotEmpty() && this[length - 1] != '\n') {
        append("\n");
    }
    for (span in spans) setSpan(span, start, length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    return this
}

fun String.setSpans(regex: String, buildSpans: SpannableStringBuilder.() -> Array<Any>): Spanned {
    val s = SpannableStringBuilder(this)
    val m = Pattern.compile(regex).matcher(this)

    while (m.find()) {
        val start = m.start()
        val end = m.end()

        for (span in s.buildSpans()) {
            s.setSpan(span, start, end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        }
    }
    return s
}

fun String.setNumberStyle(scale: Float? = null, color: Int? = null, typeface: Typeface? = null, style: Int? = null): Spanned {
    return setSpans("[0-9]+(\\.[0-9]+)*") {
        val spans = mutableListOf<Any>()
        color?.let {
            spans.add(TextColorSpan(it))
        }

        scale?.let {
            spans.add(RelativeSizeSpan(it))
        }

        style?.let {
            spans.add(StyleSpan(it))
        }

        typeface?.let {
            val span = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                TypefaceSpan(typeface)
            } else {
                TypefaceCompat(typeface)
            }
            spans.add(span)
        }
        spans.toTypedArray()
    }
}

@PublishedApi
internal val Float.dp: Int get() = (Resources.getSystem().displayMetrics.density * this).toInt()



