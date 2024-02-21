@file:Suppress("NOTHING_TO_INLINE")

package me.reezy.cosmo.span

import android.graphics.Color
import android.text.Layout.Alignment
import android.text.SpannableStringBuilder
import android.text.style.AlignmentSpan
import android.text.style.LeadingMarginSpan


inline fun SpannableStringBuilder.paragraph(
    noinline builderAction: SpannableStringBuilder.() -> Unit,
) = inBlock(AlignmentSpan.Standard(Alignment.ALIGN_NORMAL), builderAction = builderAction)


inline fun SpannableStringBuilder.center(
    noinline builderAction: SpannableStringBuilder.() -> Unit,
) = inBlock(AlignmentSpan.Standard(Alignment.ALIGN_CENTER), builderAction = builderAction)


inline fun SpannableStringBuilder.intent(
    first: Int = 0,
    rest: Int = first,
    noinline builderAction: SpannableStringBuilder.() -> Unit,
) = inBlock(LeadingMarginSpan.Standard(first, rest), builderAction = builderAction)

inline fun SpannableStringBuilder.quote(
    color: Int = Color.GRAY,
    noinline builderAction: SpannableStringBuilder.() -> Unit,
) = inBlock(quote(color), builderAction = builderAction)

inline fun SpannableStringBuilder.bullet(
    color: Int = Color.BLACK,
    noinline builderAction: SpannableStringBuilder.() -> Unit,
) = inBlock(bullet(color), builderAction = builderAction)

