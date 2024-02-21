package me.reezy.cosmo.span.style

import android.graphics.Canvas
import android.graphics.Paint
import android.text.style.ReplacementSpan
import androidx.annotation.ColorInt
import androidx.annotation.Dimension


class Text3dSpan(
    @ColorInt private val dualColor: Int,
    @Dimension private val offsetY: Int
) : ReplacementSpan() {

    override fun getSize(paint: Paint, text: CharSequence, start: Int, end: Int, fontMetrics: Paint.FontMetricsInt?): Int {
        if (fontMetrics != null && paint.fontMetricsInt != null) {
            fontMetrics.bottom = paint.fontMetricsInt.bottom
            fontMetrics.top = paint.fontMetricsInt.top
            fontMetrics.descent = paint.fontMetricsInt.descent
            fontMetrics.leading = paint.fontMetricsInt.leading
        }
        return paint.measureText(text.toString().substring(start until end)).toInt()
    }


    override fun draw(canvas: Canvas, text: CharSequence, start: Int, end: Int, x: Float, top: Int, y: Int, bottom: Int, paint: Paint) {
        val originTextColor = paint.color

        val newPaint = Paint(paint)

        newPaint.color = dualColor
        canvas.drawText(text, start, end, x, y.toFloat() + offsetY, newPaint)

        newPaint.color = originTextColor
        canvas.drawText(text, start, end, x, y.toFloat(), paint)
    }

}