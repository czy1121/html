package com.demo.app

import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.text.style.DynamicDrawableSpan
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.text.buildSpannedString
import androidx.core.text.inSpans
import androidx.fragment.app.Fragment
import me.reezy.cosmo.span.bold
import me.reezy.cosmo.span.br
import me.reezy.cosmo.span.clickable
import me.reezy.cosmo.span.color
import me.reezy.cosmo.span.text
import me.reezy.cosmo.span.image
import me.reezy.cosmo.span.inBlock
import me.reezy.cosmo.span.italic
import me.reezy.cosmo.span.lineHeight
import me.reezy.cosmo.span.scale
import me.reezy.cosmo.span.strike
import me.reezy.cosmo.span.underline

class InlineFragment : Fragment(R.layout.fragment_text) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tv = requireView() as TextView

        tv.movementMethod = LinkMovementMethod.getInstance()
        tv.text = buildSpannedString {
            inBlock(lineHeight(30f.dp)) {
                bold("bold text")
                br()
                italic("italic text")
                br()
                underline("underline text")
                br()
                strike("strike text")
                br()
                text("relative size x 2", scale = 2f)
                br()
                text("text foreground color", color = Color.RED)
                br()
                text("text background color", bgColor = Color.RED)
                br()
                text("text no style")
                br()
                clickable("clickable text") {
                    Toast.makeText(requireContext(), "clickable text clicked", Toast.LENGTH_SHORT).show()
                }
                br()
                inSpans(color(Color.RED), bold, underline, strike, scale(1.5f), clickable {
                    Toast.makeText(requireContext(), "multiple style text clicked", Toast.LENGTH_SHORT).show()
                }) {
                    append("multiple style text")
                }
            }
            br()
            inBlock() {
                inSpans() {
                    append("image")
                    image(requireContext(), R.mipmap.ic_launcher, 40f.dp, align = DynamicDrawableSpan.ALIGN_CENTER)
                    append("image")
                }
            }
        }

    }

    private val Float.dp: Int get() = (Resources.getSystem().displayMetrics.density * this).toInt()
}