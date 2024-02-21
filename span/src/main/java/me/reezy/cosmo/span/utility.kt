@file:Suppress("NOTHING_TO_INLINE")

package me.reezy.cosmo.span

import android.content.res.Resources
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ParagraphStyle


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


@PublishedApi
internal val Float.dp: Int get() = (Resources.getSystem().displayMetrics.density * this).toInt()
