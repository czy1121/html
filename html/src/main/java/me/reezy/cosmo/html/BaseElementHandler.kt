package me.reezy.cosmo.html

import android.graphics.Color
import android.text.*
import android.text.style.AlignmentSpan
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StrikethroughSpan
import androidx.core.text.HtmlCompat
import org.xml.sax.Attributes
import java.util.regex.Matcher
import java.util.regex.Pattern


abstract class BaseElementHandler {
    internal class Strikethrough
    private class Newline(val numNewlines: Int)
    private class Alignment(val alignment: Layout.Alignment)
    private class Foreground(val foregroundColor: Int)
    private class Background(val backgroundColor: Int)

    private val sTextAlignPattern by lazy { Pattern.compile("(?:\\s+|\\A)text-align\\s*:\\s*(\\S*)\\b") }
    private val sForegroundColorPattern by lazy { Pattern.compile("(?:\\s+|\\A)color\\s*:\\s*(\\S*)\\b") }
    private val sBackgroundColorPattern by lazy { Pattern.compile("(?:\\s+|\\A)background(?:-color)?\\s*:\\s*(\\S*)\\b") }
    private val sTextDecorationPattern by lazy { Pattern.compile("(?:\\s+|\\A)text-decoration\\s*:\\s*(\\S*)\\b") }

    private var mColorMap = mapOf(
        "darkgray" to 0xFFA9A9A9.toInt(),
        "gray" to 0xFF808080.toInt(),
        "lightgray" to 0xFFD3D3D3.toInt(),
        "green" to 0xFF008000.toInt()
    )



    fun <T> getLast(text: Spanned, kind: Class<T>): T? {
        val objs = text.getSpans(0, text.length, kind)
        return if (objs.isEmpty()) null else objs[objs.size - 1]
    }

    fun setSpanFromMark(text: Spannable, mark: Any, vararg spans: Any) {
        val where = text.getSpanStart(mark)
        text.removeSpan(mark)
        val len = text.length
        if (where != len) {
            for (span in spans) {
                text.setSpan(span, where, len, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        }
    }

    fun startBlockElement(text: Editable, attributes: Attributes, margin: Int) {
        val len = text.length
        if (margin > 0) {
            appendNewlines(text, margin)
            start(text, Newline(margin))
        }
        val style = attributes.getValue("", "style")
        if (style != null) {
            val m: Matcher = sTextAlignPattern.matcher(style)
            if (m.find()) {
                val alignment = m.group(1)
                if (alignment.equals("start", ignoreCase = true)) {
                    start(text, Alignment(Layout.Alignment.ALIGN_NORMAL))
                } else if (alignment.equals("center", ignoreCase = true)) {
                    start(text, Alignment(Layout.Alignment.ALIGN_CENTER))
                } else if (alignment.equals("end", ignoreCase = true)) {
                    start(text, Alignment(Layout.Alignment.ALIGN_OPPOSITE))
                }
            }
        }
    }

    fun endBlockElement(text: Editable) {
        getLast(text, Newline::class.java)?.let {
            appendNewlines(text, it.numNewlines)
            text.removeSpan(it)
        }
        getLast(text, Alignment::class.java)?.let {
            setSpanFromMark(text, it, AlignmentSpan.Standard(it.alignment))
        }
    }

    fun start(text: Editable, mark: Any) {
        val len = text.length
        text.setSpan(mark, len, len, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
    }

    fun <T> end(text: Editable, kind: Class<T>, span: Any) {
        getLast(text, kind)?.let {
            setSpanFromMark(text, it, span)
        }
    }

    fun startCssStyle(text: Editable, attributes: Attributes, flags: Int) {
        val style = attributes.getValue("", "style")
        if (style != null) {
            var m: Matcher = sForegroundColorPattern.matcher(style)
            if (m.find()) {
                val c: Int? = m.group(1)?.let { getHtmlColor(flags, it) }
                if (c != -1) {
                    if (c != null) {
                        start(text, Foreground(c or -0x1000000))
                    }
                }
            }
            m = sBackgroundColorPattern.matcher(style)
            if (m.find()) {
                val c: Int? = m.group(1)?.let { getHtmlColor(flags, it) }
                if (c != -1) {
                    if (c != null) {
                        start(text, Background(c or -0x1000000))
                    }
                }
            }
            m = sTextDecorationPattern.matcher(style)
            if (m.find()) {
                val textDecoration = m.group(1)
                if (textDecoration.equals("line-through", ignoreCase = true)) {
                    start(text, Strikethrough())
                }
            }
        }
    }

    fun endCssStyle(text: Editable) {
        getLast(text, Strikethrough::class.java)?.let {
            setSpanFromMark(text, it, StrikethroughSpan())
        }
        getLast(text, Background::class.java)?.let {
            setSpanFromMark(text, it, BackgroundColorSpan(it.backgroundColor))
        }
        getLast(text, Foreground::class.java)?.let {
            setSpanFromMark(text, it, ForegroundColorSpan(it.foregroundColor))
        }
    }

    private fun appendNewlines(text: Editable, minNewline: Int) {
        val len = text.length
        if (len == 0) {
            return
        }
        var existingNewlines = 0
        var i = len - 1
        while (i >= 0 && text[i] == '\n') {
            existingNewlines++
            i--
        }
        for (j in existingNewlines until minNewline) {
            text.append("\n")
        }
    }

    private fun getHtmlColor(flags: Int, color: String): Int {
        if (flags and HtmlCompat.FROM_HTML_OPTION_USE_CSS_COLORS == HtmlCompat.FROM_HTML_OPTION_USE_CSS_COLORS) {
            mColorMap[color.lowercase()]?.let {
                return it
            }
        }

        // If |color| is the name of a color, pass it to Color to convert it. Otherwise,
        // it may start with "#", "0", "0x", "+", or a digit. All of these cases are
        // handled below by XmlUtils. (Note that parseColor accepts colors starting
        // with "#", but it treats them differently from XmlUtils.)
        return if (Character.isLetter(color[0])) {
            try {
                Color.parseColor(color)
            } catch (e: IllegalArgumentException) {
                -1
            }
        } else try {
            convertValueToInt(color, -1)
        } catch (nfe: NumberFormatException) {
            -1
        }
    }

    private fun convertValueToInt(charSeq: CharSequence, defaultValue: Int): Int {
        if (TextUtils.isEmpty(charSeq)) {
            return defaultValue
        }
        val nm = charSeq.toString()
        var value: Int
        var sign = 1
        var index = 0
        val len = nm.length
        var base = 10
        if ('-' == nm[0]) {
            sign = -1
            index++
        }
        if ('0' == nm[index]) {
            //  Quick check for a zero by itself
            if (index == len - 1) return 0
            val c = nm[index + 1]
            if ('x' == c || 'X' == c) {
                index += 2
                base = 16
            } else {
                index++
                base = 8
            }
        } else if ('#' == nm[index]) {
            index++
            base = 16
        }
        return nm.substring(index).toInt(base) * sign
    }
}