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
            fontMetrics.bottom = paint.fontMetricsInt.bottom
            fontMetrics.top = paint.fontMetricsInt.top
            fontMetrics.descent = paint.fontMetricsInt.descent
            fontMetrics.leading = paint.fontMetricsInt.leading
        }
        return paint.measureText(text.toString().substring(start until end)).toInt()
    }


    override fun draw(canvas: Canvas, text: CharSequence, start: Int, end: Int, x: Float, top: Int, y: Int, bottom: Int, paint: Paint) {

        val color = paint.color

        paint.color = strokeColor
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = strokeWidth.toFloat()
        canvas.drawText(text, start, end, x, y.toFloat(), paint)

        paint.color = color
        paint.style = Paint.Style.FILL
        canvas.drawText(text, start, end, x, y.toFloat(), paint)
    }

}