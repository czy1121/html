package me.reezy.cosmo.foundation.span

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.os.Parcel
import android.os.Parcelable
import android.text.Layout
import android.text.ParcelableSpan
import android.text.Spanned
import android.text.style.LeadingMarginSpan


class BulletStyle(
    private var radius: Int = dp(3f),
    private var gap: Int = dp(10f),
    private var color: Int? = null
) : LeadingMarginSpan, ParcelableSpan {


    private var bulletPath: Path? = null


    override fun getSpanTypeId(): Int {
        return 0
    }


    override fun getLeadingMargin(first: Boolean): Int {
        return 2 * radius + gap
    }

    override fun drawLeadingMargin(
        c: Canvas, p: Paint, x: Int, dir: Int,
        top: Int, baseline: Int, bottom: Int,
        text: CharSequence, start: Int, end: Int,
        first: Boolean, l: Layout
    ) {
        if ((text as Spanned).getSpanStart(this) == start) {
            val style = p.style
            var oldcolor = 0

            color?.let {
                oldcolor = p.color
                p.color = it
            }

            p.style = Paint.Style.FILL

            if (c.isHardwareAccelerated) {
                if (bulletPath == null) {
                    bulletPath = Path()
                    bulletPath?.addCircle(0.0f, 0.0f, radius.toFloat(), Path.Direction.CW)
                }

                c.save()
                c.translate((x + dir * radius).toFloat(), (top + bottom) / 2.0f)
                c.drawPath(bulletPath!!, p)
                c.restore()
            } else {
                c.drawCircle((x + dir * radius).toFloat(), (top + bottom) / 2.0f, radius.toFloat(), p)
            }

            color?.let {
                p.color = oldcolor
            }

            p.style = style
        }
    }


    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readValue(Int::class.java.classLoader) as? Int
    )
    
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(radius)
        parcel.writeInt(gap)
        parcel.writeValue(color)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {

        private fun dp(dp: Float): Int {
            return (Resources.getSystem().displayMetrics.density * dp).toInt()
        }

        @JvmField
        val CREATOR: Parcelable.Creator<BulletStyle> = object : Parcelable.Creator<BulletStyle> {
            override fun createFromParcel(source: Parcel): BulletStyle {
                return BulletStyle(source)
            }

            override fun newArray(size: Int): Array<BulletStyle?> {
                return arrayOfNulls(size)
            }
        }
    }


}