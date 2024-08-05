@file:Suppress("NOTHING_TO_INLINE")

package me.reezy.cosmo.span

import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.text.SpannableStringBuilder
import android.text.style.AbsoluteSizeSpan
import android.text.style.BackgroundColorSpan
import android.text.style.BulletSpan
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.LeadingMarginSpan
import android.text.style.LineHeightSpan
import android.text.style.ParagraphStyle
import android.text.style.QuoteSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StrikethroughSpan
import android.text.style.StyleSpan
import android.text.style.TypefaceSpan
import android.text.style.UnderlineSpan
import android.view.View
import me.reezy.cosmo.span.style.TextColorSpan
import me.reezy.cosmo.span.compat.BulletCompat
import me.reezy.cosmo.span.compat.LineHeightCompat
import me.reezy.cosmo.span.compat.QuoteCompat
import me.reezy.cosmo.span.compat.TypefaceCompat
import me.reezy.cosmo.span.style.TextStrokeSpan

inline val SpannableStringBuilder.bold get() = StyleSpan(Typeface.BOLD)
inline val SpannableStringBuilder.italic get() = StyleSpan(Typeface.ITALIC)
inline val SpannableStringBuilder.big get() = RelativeSizeSpan(1.25f)
inline val SpannableStringBuilder.small get() = RelativeSizeSpan(0.8f)
inline val SpannableStringBuilder.underline get() = UnderlineSpan()
inline val SpannableStringBuilder.strike get() = StrikethroughSpan()
inline fun SpannableStringBuilder.size(size: Int) = AbsoluteSizeSpan(size)
inline fun SpannableStringBuilder.scale(proportion: Float) = RelativeSizeSpan(proportion)
inline fun SpannableStringBuilder.color(color: Int) = TextColorSpan(color)
inline fun SpannableStringBuilder.fgColor(color: Int) = ForegroundColorSpan(color)
inline fun SpannableStringBuilder.bgColor(color: Int) = BackgroundColorSpan(color)
inline fun SpannableStringBuilder.typeface(typeface: Typeface) = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
    TypefaceSpan(typeface)
} else {
    TypefaceCompat(typeface)
}

inline fun SpannableStringBuilder.stroke(color: Int = Color.BLACK, width: Int = 1f.dp, letterSpacing: Int = 0) = TextStrokeSpan(color, width, letterSpacing)

inline fun SpannableStringBuilder.clickable(crossinline action: () -> Unit) = object : ClickableSpan() {
    override fun onClick(widget: View) {
        action()
    }
}


inline fun SpannableStringBuilder.intent(first: Int, rest: Int = first): ParagraphStyle = LeadingMarginSpan.Standard(first, rest)

inline fun SpannableStringBuilder.lineHeight(height: Int): ParagraphStyle = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
    LineHeightSpan.Standard(height)
} else {
    LineHeightCompat(height)
}

inline fun SpannableStringBuilder.quote(color: Int = Color.GRAY, stripe: Int = 3f.dp, gap: Int = 10f.dp): ParagraphStyle = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> QuoteSpan(color, stripe, gap)
    else -> QuoteCompat(color, stripe, gap)
}

inline fun SpannableStringBuilder.bullet(color: Int = Color.BLACK, radius: Int = 3f.dp, gap: Int = 10f.dp): ParagraphStyle = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.P -> BulletSpan(gap, color, radius)
    else -> BulletCompat(gap, color, radius)
}
