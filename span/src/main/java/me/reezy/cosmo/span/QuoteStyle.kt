package me.reezy.cosmo.foundation.span

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Parcel
import android.os.Parcelable
import android.text.Layout
import android.text.ParcelableSpan
import android.text.style.LeadingMarginSpan


class QuoteStyle(
    private val color: Int = Color.BLUE,
    private val gap: Int = dp2px(3f),
    private val stripe: Int = dp2px(10f)
) : LeadingMarginSpan, ParcelableSpan {


    override fun getSpanTypeId(): Int {
        return 0
    }

    override fun getLeadingMargin(first: Boolean): Int {
        return gap + stripe
    }

    override fun drawLeadingMargin(
        c: Canvas, p: Paint, x: Int, dir: Int,
        top: Int, baseline: Int, bottom: Int,
        text: CharSequence, start: Int, end: Int,
        first: Boolean, layout: Layout
    ) {
        val style = p.style
        val color = p.color

        p.style = Paint.Style.FILL
        p.color = this.color

        c.drawRect(x.toFloat(), top.toFloat(), (x + dir * stripe).toFloat(), bottom.toFloat(), p)

        p.style = style
        p.color = color
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(this.color)
        dest.writeInt(this.gap)
        dest.writeInt(this.stripe)
    }

    private constructor(p: Parcel) : this(p.readInt(), p.readInt(), p.readInt()) {
    }

    companion object {
        private fun dp2px(dp: Float): Int {
            return (Resources.getSystem().displayMetrics.density * dp).toInt()
        }

        @JvmField
        val CREATOR: Parcelable.Creator<QuoteStyle> = object : Parcelable.Creator<QuoteStyle> {
            override fun createFromParcel(source: Parcel): QuoteStyle {
                return QuoteStyle(source)
            }

            override fun newArray(size: Int): Array<QuoteStyle?> {
                return arrayOfNulls(size)
            }
        }
    }
}