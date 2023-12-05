package me.reezy.cosmo.foundation.span

import android.graphics.Paint
import android.graphics.Typeface
import android.text.TextPaint
import android.text.style.MetricAffectingSpan


class TypefaceStyle(private val newType: Typeface) : MetricAffectingSpan() {

    override fun updateDrawState(paint: TextPaint) {
        apply(paint, newType)
    }

    override fun updateMeasureState(paint: TextPaint) {
        apply(paint, newType)
    }

    private fun apply(paint: Paint, tf: Typeface) {
        val oldStyle: Int = paint.typeface?.style ?: 0

        val fake = oldStyle and tf.style.inv()
        if (fake and Typeface.BOLD != 0) {
            paint.isFakeBoldText = true
        }

        if (fake and Typeface.ITALIC != 0) {
            paint.textSkewX = -0.25f
        }

        paint.typeface = tf
    }
}