@file:Suppress("NOTHING_TO_INLINE")

package me.reezy.cosmo.span

import android.content.Context
import android.graphics.Typeface
import android.text.SpannableStringBuilder
import android.text.style.AbsoluteSizeSpan
import android.text.style.BackgroundColorSpan
import android.text.style.DynamicDrawableSpan
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

inline fun SpannableStringBuilder.image(context: Context, resourceId: Int, width: Int, height: Int = width, align:Int = DynamicDrawableSpan.ALIGN_BOTTOM)
    = inSpans(ImageSpan(context.getDrawable(resourceId, width, height), align)) { append(" ") }

inline fun SpannableStringBuilder.clickable(text: String, crossinline action: () -> Unit) = inSpans(clickable(action)) { append(text) }



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
