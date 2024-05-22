package me.reezy.cosmo.span.style

import android.graphics.Canvas
import android.graphics.Paint
import android.text.style.ReplacementSpan
import androidx.annotation.ColorInt
import androidx.annotation.Dimension


class TextStrokeSpan(
    @ColorInt private val strokeColor: Int,
    @Dimension private val strokeWidth: Int
) : ReplacementSpan() {

    override fun getSize(paint: Paint, text: CharSequence, start: Int, end: Int, fontMetrics: Paint.FontMetricsInt?): Int {
        if (fontMetrics != null && paint.fontMetricsInt != null) {
            fontMetrics.top = paint.fontMetricsInt.top
            fontMetrics.ascent = paint.fontMetricsInt.ascent
            fontMetrics.descent = paint.fontMetricsInt.descent
            fontMetrics.bottom = paint.fontMetricsInt.bottom
            fontMetrics.leading = paint.fontMetricsInt.leading
        }
        return paint.measureText(text.toString().substring(start until end)).toInt()
    }


    override fun draw(canvas: Canvas, text: CharSequence, start: Int, end: Int, x: Float, top: Int, y: Int, bottom: Int, paint: Paint) {

        canvas.drawText(text, start, end, x, y.toFloat(), paint)

        val color = paint.color
        val style = paint.style
        val width = paint.strokeWidth

        paint.color = strokeColor
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = strokeWidth.toFloat()
        canvas.drawText(text, start, end, x, y.toFloat(), paint)

        paint.color = color
        paint.style = style
        paint.strokeWidth = width
    }

}