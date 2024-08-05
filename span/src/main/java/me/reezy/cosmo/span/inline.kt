@file:Suppress("NOTHING_TO_INLINE")

package me.reezy.cosmo.span

import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.SpannableStringBuilder
import android.text.style.AbsoluteSizeSpan
import android.text.style.BackgroundColorSpan
import android.text.style.ImageSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StrikethroughSpan
import android.text.style.StyleSpan
import android.text.style.TypefaceSpan
import android.text.style.UnderlineSpan
import androidx.core.text.inSpans
import me.reezy.cosmo.span.compat.TypefaceCompat
import me.reezy.cosmo.span.style.LabelSpan
import me.reezy.cosmo.span.style.LinkSpan
import me.reezy.cosmo.span.style.TextColorSpan

fun SpannableStringBuilder.append(text: String, spans: List<Any>): SpannableStringBuilder = if (spans.isEmpty()) {
    append(text)
} else {
    inSpans(*spans.toTypedArray()) {
        append(text)
    }
}

inline fun SpannableStringBuilder.br(): SpannableStringBuilder = append("\n")
inline fun SpannableStringBuilder.bold(text: String) = inSpans(StyleSpan(Typeface.BOLD)) { append(text) }
inline fun SpannableStringBuilder.italic(text: String) = inSpans(StyleSpan(Typeface.ITALIC)) { append(text) }
inline fun SpannableStringBuilder.underline(text: String) = inSpans(UnderlineSpan()) { append(text) }
inline fun SpannableStringBuilder.strike(text: String) = inSpans(StrikethroughSpan()) { append(text) }

inline fun SpannableStringBuilder.text(
    text: String,
    color: Int? = null,
    size: Int? = null,
    typeface: Typeface? = null,
    scale: Float? = null,
    style: Int? = null,
    bgColor: Int? = null,
): SpannableStringBuilder {
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
        spans.add(
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                TypefaceSpan(it)
            } else {
                TypefaceCompat(it)
            }
        )
    }

    return append(text, spans)
}


inline fun SpannableStringBuilder.image(context: Context, resourceId: Int) = inSpans(ImageSpan(context, resourceId)) { append(" ") }

inline fun SpannableStringBuilder.image(context: Context, resourceId: Int, width: Int, height: Int = width, align: Int = 2) =
    inSpans(ImageSpan(context.getDrawable(resourceId, width, height), align)) { append(" ") }

inline fun SpannableStringBuilder.image(drawable: Drawable, align: Int = 2) =
    inSpans(ImageSpan(drawable, align)) { append(" ") }

inline fun SpannableStringBuilder.clickable(text: String, crossinline action: () -> Unit) = inSpans(clickable(action)) { append(text) }

inline fun SpannableStringBuilder.link(text: String, href: String, underline: Boolean = false) = inSpans(LinkSpan(href, underline)) { append(text) }


fun SpannableStringBuilder.addLabel(
    text: String,
    color: Int,
    height: Int,
    corner: Int = -1,
    padding: Int = 3f.dp,
    spacing: Int = 3f.dp,
    stroke: Int = 0,
): SpannableStringBuilder {
    inSpans(LabelSpan(color, height, corner, padding, spacing, stroke)) {
        append(text)
    }
    return this
}


fun SpannableStringBuilder.addLabels(
    texts: List<String>,
    colors: List<Int>,
    height: Int,
    corner: Int = -1,
    padding: Int = 3f.dp,
    spacing: Int = 3f.dp,
    stroke: Int = 0,
): SpannableStringBuilder {
    for ((index, text) in texts.withIndex()) {
        addLabel(text, colors[index % colors.size], height, corner, padding, spacing, stroke)
    }
    return this
}
