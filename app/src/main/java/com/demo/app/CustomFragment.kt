package com.demo.app

import android.content.res.Resources
import android.graphics.Color
import android.graphics.Typeface
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
import me.reezy.cosmo.span.style.LabelSpan
import me.reezy.cosmo.span.style.Text3dSpan
import me.reezy.cosmo.span.style.TextStrokeSpan
import me.reezy.cosmo.span.labels
import me.reezy.cosmo.span.setNumberStyle

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

            inBlock(lineHeight(40f.dp)) {
                listOf("label", "span", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine").forEach {
                    inSpans(LabelSpan(Color.RED, 20f.dp, cornerRadius = 3f.dp, padding = 3f.dp, spacing = 4f.dp)) {
                        append(it)
                    }
                }
            }

            inBlock(lineHeight(40f.dp)) {
                val tags = listOf("label", "span", "一二", "三四五", "六七八", "哈哈哈哈", "five", "six", "seven", "eight", "nine")
                val colors = listOf(Color.RED, Color.BLUE, Color.MAGENTA)
                labels(tags, colors, 20f.dp, cornerRadius = 0, stroke = 1f.dp, spacing = 5f.dp)
            }


            append("\"一1二22三3.456四五\".setNumberStyle(2f, color = Color.RED, style = Typeface.BOLD_ITALIC)")
            br()
            append("一1二22三3.456四五".setNumberStyle(2f, color = Color.RED, style = Typeface.BOLD_ITALIC))
        }

    }

    private val Float.dp: Int get() = (Resources.getSystem().displayMetrics.density * this).toInt()
}