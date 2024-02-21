package com.demo.app

import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.text.style.IconMarginSpan
import android.view.View
import android.widget.TextView
import androidx.core.graphics.drawable.toBitmap
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.core.text.inSpans
import androidx.fragment.app.Fragment
import com.demo.app.databinding.FragmentHtmlBinding
import me.reezy.cosmo.span.bgColor
import me.reezy.cosmo.span.br
import me.reezy.cosmo.span.bullet
import me.reezy.cosmo.span.center
import me.reezy.cosmo.span.inBlock
import me.reezy.cosmo.span.intent
import me.reezy.cosmo.span.paragraph
import me.reezy.cosmo.span.quote

class BlockFragment : Fragment(R.layout.fragment_text) {



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tv = requireView() as TextView

        tv.movementMethod = LinkMovementMethod.getInstance()
        tv.text = buildSpannedString {
            inBlock(IconMarginSpan(resources.getDrawable(R.mipmap.ic_launcher).toBitmap()!!, 10f.dp)) {
                append("IconMarginSpan \none two three four five six seven eight nine ten ")
            }


            br()

            intent(20f.dp, 0) {
                inSpans(bgColor(Color.RED)) {
                    append("intent text \none two three four five six seven eight nine ten one two three four five six seven eight nine ten")
                }
            }

            br()

            paragraph {
                append("paragraph text \none two three four five six seven eight nine ten one two three four five six seven eight nine ten one two three")
            }

            br()

            center {
                append("center text \none two three four five six seven eight nine ten one two three four five six seven eight nine ten one two three")
            }

            br()

            quote {
                append("quote text \none two three four five six seven eight nine ten one two three four five six seven eight nine ten")
            }

            br()

            bullet(Color.MAGENTA) { append("bullet text1") }
            bullet(Color.MAGENTA) { append("bullet text2\nbullet one two three four five") }
            bullet(Color.MAGENTA) { append("bullet text3") }
        }
    }

    private val Float.dp: Int get() = (Resources.getSystem().displayMetrics.density * this).toInt()
}