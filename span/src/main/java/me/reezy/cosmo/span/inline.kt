@file:Suppress("NOTHING_TO_INLINE")

package me.reezy.cosmo.span

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Typeface
import android.text.Layout.Alignment
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.text.style.AlignmentSpan
import android.text.style.BackgroundColorSpan
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.IconMarginSpan
import android.text.style.ImageSpan
import android.text.style.LeadingMarginSpan
import android.text.style.ParagraphStyle
import android.text.style.RelativeSizeSpan
import android.text.style.StrikethroughSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.view.View
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.core.text.inSpans
import me.reezy.cosmo.span.style.RoundSpan
import me.reezy.cosmo.span.style.Text3dSpan
import me.reezy.cosmo.span.style.TextStrokeSpan


inline fun SpannableStringBuilder.br(): SpannableStringBuilder = append("\n")
inline fun SpannableStringBuilder.bold(text: String) = inSpans(StyleSpan(Typeface.BOLD)) { append(text) }
inline fun SpannableStringBuilder.italic(text: String) = inSpans(StyleSpan(Typeface.ITALIC)) { append(text) }
inline fun SpannableStringBuilder.underline(text: String) = inSpans(UnderlineSpan()) { append(text) }
inline fun SpannableStringBuilder.strike(text: String) = inSpans(StrikethroughSpan()) { append(text) }

inline fun SpannableStringBuilder.size(size: Int, text: String) = inSpans(AbsoluteSizeSpan(size)) { append(text) }
inline fun SpannableStringBuilder.scale(proportion: Float, text: String) = inSpans(RelativeSizeSpan(proportion)) { append(text) }
inline fun SpannableStringBuilder.color(color: Int, text: String) = inSpans(ForegroundColorSpan(color)) { append(text) }
inline fun SpannableStringBuilder.bgColor(color: Int, text: String) = inSpans(BackgroundColorSpan(color)) { append(text) }

inline fun SpannableStringBuilder.image(context: Context, resourceId: Int) = inSpans(ImageSpan(context, resourceId)) { append(" ") }

inline fun SpannableStringBuilder.clickable(text: String, crossinline action: () -> Unit) = inSpans(clickable(action)) { append(text) }
