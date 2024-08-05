@file:Suppress("NOTHING_TO_INLINE")

package me.reezy.cosmo.span

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.text.style.BackgroundColorSpan
import android.text.style.ParagraphStyle
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.text.style.TypefaceSpan
import androidx.core.text.buildSpannedString
import me.reezy.cosmo.span.compat.TypefaceCompat
import me.reezy.cosmo.span.style.TextColorSpan
import me.reezy.cosmo.span.style.TextStrokeSpan
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

fun CharSequence.setSpans(regex: String? = null, buildSpans: SpannableStringBuilder.() -> Array<Any>): Spanned {
    val s = SpannableStringBuilder(this)
    if (regex != null) {
        val m = Pattern.compile(regex).matcher(this)

        while (m.find()) {
            val start = m.start()
            val end = m.end()

            for (span in s.buildSpans()) {
                s.setSpan(span, start, end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
            }
        }
    } else {
        for (span in s.buildSpans()) {
            s.setSpan(span, 0, length, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        }
    }
    return s
}

fun CharSequence.setStroke(regex: String? = null, color: Int = Color.BLACK, width: Int = 1f.dp, letterSpacing: Int = 0) = setSpans(regex) {
    arrayOf(TextStrokeSpan(color, width, letterSpacing))
}


fun CharSequence.setStyle(regex: String? = null, scale: Float? = null, size: Int? = null, color: Int? = null, typeface: Typeface? = null, style: Int? = null, bgColor: Int? = null,): Spanned {
    return setSpans(regex) {
        val spans = mutableListOf<Any>()
        color?.let {
            spans.add(TextColorSpan(it))
        }

        scale?.let {
            spans.add(RelativeSizeSpan(it))
        }

        size?.let {
            spans.add(AbsoluteSizeSpan(it))
        }

        style?.let {
            spans.add(StyleSpan(it))
        }

        bgColor?.let {
            spans.add(BackgroundColorSpan(it))
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

inline fun CharSequence.setNumberStyle(scale: Float? = null, size: Int? = null, color: Int? = null, typeface: Typeface? = null, style: Int? = null): Spanned {
    return setStyle("[+-]?[0-9]+(\\.[0-9]+)*[%]?", scale, size, color, typeface, style)
}


inline fun List<String>.setLabelStyle(
    colors: List<Int>,
    height: Int,
    corner: Int = -1,
    padding: Int = 3f.dp,
    spacing: Int = 3f.dp,
    stroke: Int = 0,
) = buildSpannedString {
    addLabels(this@setLabelStyle, colors, height, corner, padding, spacing, stroke)
}

inline fun List<String>.setLabelStyle(
    color: Int,
    height: Int,
    corner: Int = -1,
    padding: Int = 3f.dp,
    spacing: Int = 3f.dp,
    stroke: Int = 0,
) = buildSpannedString {
    addLabels(this@setLabelStyle, listOf(color), height, corner, padding, spacing, stroke)
}


fun Context.getDrawable(resId: Int, width: Int, height: Int = width): Drawable {
    val drawable = getDrawable(resId)!!
    drawable.setBounds(0, 0, width, height)
    return drawable
}

@PublishedApi
internal val Float.dp: Int get() = (Resources.getSystem().displayMetrics.density * this).toInt()



