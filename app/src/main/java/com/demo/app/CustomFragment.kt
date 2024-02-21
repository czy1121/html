package com.demo.app

import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.TextView
import androidx.core.text.buildSpannedString
import androidx.core.text.inSpans
import androidx.fragment.app.Fragment
import me.reezy.cosmo.span.bold
import me.reezy.cosmo.span.br
import me.reezy.cosmo.span.color
import me.reezy.cosmo.span.inBlock
import me.reezy.cosmo.span.lineHeight
import me.reezy.cosmo.span.scale
import me.reezy.cosmo.span.style.RoundSpan
import me.reezy.cosmo.span.style.Text3dSpan
import me.reezy.cosmo.span.style.TextStrokeSpan

class CustomFragment : Fragment(R.layout.fragment_text) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tv = requireView() as TextView

        tv.movementMethod = LinkMovementMethod.getInstance()
        tv.text = buildSpannedString {
            inSpans(scale(2f), bold, Text3dSpan(Color.WHITE, (-2f).dp), color(Color.GRAY)) {
                append("Text3dSpan 12345678990")
            }
            br()
            inSpans(TextStrokeSpan(Color.RED, 2f.dp)) {
                append("TextStrokeSpan 12345678990")
            }
            br()

            inBlock(lineHeight(24f.dp)) {
                listOf("round", "span", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine").forEach {
                    inSpans(RoundSpan(Color.RED, 3f.dp, 20f.dp, 3f.dp, 4f.dp)) {
                        append(it)
                    }
                }
            }
        }
    }

    private val Float.dp: Int get() = (Resources.getSystem().displayMetrics.density * this).toInt()
}