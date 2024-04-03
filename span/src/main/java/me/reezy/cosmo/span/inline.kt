@file:Suppress("NOTHING_TO_INLINE")

package me.reezy.cosmo.span

import android.content.Context
import android.graphics.Typeface
import android.text.SpannableStringBuilder
import android.text.style.AbsoluteSizeSpan
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.text.style.ImageSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StrikethroughSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import androidx.core.text.inSpans
import me.reezy.cosmo.span.style.LabelSpan
import me.reezy.cosmo.span.style.TextColorSpan


inline fun SpannableStringBuilder.br(): SpannableStringBuilder = append("\n")
inline fun SpannableStringBuilder.bold(text: String) = inSpans(StyleSpan(Typeface.BOLD)) { append(text) }
inline fun SpannableStringBuilder.italic(text: String) = inSpans(StyleSpan(Typeface.ITALIC)) { append(text) }
inline fun SpannableStringBuilder.underline(text: String) = inSpans(UnderlineSpan()) { append(text) }
inline fun SpannableStringBuilder.strike(text: String) = inSpans(StrikethroughSpan()) { append(text) }

inline fun SpannableStringBuilder.size(size: Int, text: String) = inSpans(AbsoluteSizeSpan(size)) { append(text) }
inline fun SpannableStringBuilder.scale(proportion: Float, text: String) = inSpans(RelativeSizeSpan(proportion)) { append(text) }
inline fun SpannableStringBuilder.color(color: Int, text: String) = inSpans(TextColorSpan(color)) { append(text) }
inline fun SpannableStringBuilder.bgColor(color: Int, text: String) = inSpans(BackgroundColorSpan(color)) { append(text) }

inline fun SpannableStringBuilder.image(context: Context, resourceId: Int) = inSpans(ImageSpan(context, resourceId)) { append(" ") }

inline fun SpannableStringBuilder.clickable(text: String, crossinline action: () -> Unit) = inSpans(clickable(action)) { append(text) }


fun SpannableStringBuilder.labels(
    texts: List<String>,
    colors: List<Int>,
    height: Int,
    cornerRadius: Int = -1,
    padding: Int = 3f.dp,
    spacing: Int = 3f.dp,
    stroke: Int = 0,
): SpannableStringBuilder {
    if (stroke > 0) {
        for ((index, text) in texts.withIndex()) {
            val color = colors[index % colors.size]
            inSpans(LabelSpan(color, height, cornerRadius, padding, spacing, stroke), color(color)) {
                append(text)
            }
        }
    } else {
        for ((index, text) in texts.withIndex()) {
            inSpans(LabelSpan(colors[index % colors.size], height, cornerRadius, padding, spacing, stroke)) {
                append(text)
            }
        }
    }
    return this
}

inline fun SpannableStringBuilder.labels(
    texts: List<String>,
    color: Int,
    height: Int,
    cornerRadius: Int = -1,
    padding: Int = 3f.dp,
    spacing: Int = 3f.dp,
    stroke: Int = 0,
) = labels(texts, listOf(color), height, cornerRadius, padding, spacing, stroke)


