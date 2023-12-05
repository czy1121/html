package me.reezy.cosmo.foundation.span

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.text.style.ReplacementSpan

class RoundStyle(
    private var backgroundColor: Int = 0,
    private var cornerRadius: Float = -1f,
    private val height: Int = 0,
    private val padding: Int = 0,
    private val spacing: Int = 0
) : ReplacementSpan() {


    private var mWidth = 0

    override fun draw(canvas: Canvas, text: CharSequence, start: Int, end: Int, x: Float, top: Int, y: Int, bottom: Int, paint: Paint) {
        val newPaint = Paint(paint)

        newPaint.color = backgroundColor
        val rect = RectF(x, top.toFloat(), x + mWidth + padding * 2, (top + height).toFloat())
        canvas.drawRoundRect(rect, cornerRadius, cornerRadius, newPaint)

        newPaint.color = paint.color
        newPaint.textAlign = Paint.Align.CENTER
        canvas.drawText(text, start, end, rect.centerX(), (rect.centerY() - (newPaint.descent() + newPaint.ascent()) / 2) - 3, newPaint)
    }

    override fun getSize(paint: Paint, text: CharSequence, start: Int, end: Int, fm: Paint.FontMetricsInt?): Int {
        val newPaint = Paint(paint)
        if (fm != null) {
            fm.top = -height
            fm.ascent = -height
            fm.descent = 0
            fm.bottom = 0
        }
        mWidth = (newPaint.measureText(text, start, end) + padding * 2).toInt()
        if (mWidth < height || end - start == 1) {
            mWidth = height
        }
        if (cornerRadius == -1f) {
            cornerRadius = (height / 2).toFloat()
        }
        return mWidth + padding * 2 + spacing
    }
}