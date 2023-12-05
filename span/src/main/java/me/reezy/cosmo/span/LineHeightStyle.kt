package me.reezy.cosmo.foundation.span

import android.graphics.Paint.FontMetricsInt
import android.text.style.LineHeightSpan


class LineHeightStyle(private val lineHeight: Int) : LineHeightSpan {

    override fun chooseHeight(text: CharSequence, start: Int, end: Int, spanstartv: Int, v: Int, fm: FontMetricsInt) {
        // This is more complicated that I wanted it to be. You can find a good explanation of what the
        // FontMetrics mean here: http://stackoverflow.com/questions/27631736.
        // The general solution is that if there's not enough height to show the full line height, we
        // will prioritize in this order: ascent, descent, bottom, top

        if (-fm.ascent > lineHeight) {
            // Show as much ascent as possible
            fm.ascent = -lineHeight
            fm.top = fm.ascent
            fm.descent = 0
            fm.bottom = fm.descent
        } else if (-fm.ascent + fm.descent > lineHeight) {
            // Show all ascent, and as much descent as possible
            fm.top = fm.ascent
            fm.descent = lineHeight + fm.ascent
            fm.bottom = fm.descent
        } else if (-fm.ascent + fm.bottom > lineHeight) {
            // Show all ascent, descent, as much bottom as possible
            fm.top = fm.ascent
            fm.bottom = fm.ascent + lineHeight
        } else if (-fm.top + fm.bottom > lineHeight) {
            // Show all ascent, descent, bottom, as much top as possible
            fm.top = fm.bottom - lineHeight
        } else {
            // Show proportionally additional ascent and top
            val additional = lineHeight - (-fm.top + fm.bottom)
            fm.top -= additional
            fm.ascent -= additional
        }
    }
}