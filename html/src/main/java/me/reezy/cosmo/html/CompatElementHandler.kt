package me.reezy.cosmo.html

import android.graphics.Typeface
import android.text.*
import android.text.style.*
import android.util.Log
import androidx.core.text.HtmlCompat
import org.xml.sax.Attributes

internal class CompatElementHandler(private val flags: Int, private val handler: ElementHandler?) : BaseElementHandler(), ElementHandler {

    private class Bullet
    private class Blockquote
    private class Heading(val level: Int)

    private val headingSizes = floatArrayOf(
        1.5f, 1.4f, 1.3f, 1.2f, 1.1f, 1f
    )

    private fun getMarginParagraph() = getMargin(HtmlCompat.FROM_HTML_SEPARATOR_LINE_BREAK_PARAGRAPH)
    private fun getMarginHeading() = getMargin(HtmlCompat.FROM_HTML_SEPARATOR_LINE_BREAK_HEADING)
    private fun getMarginListItem() = getMargin(HtmlCompat.FROM_HTML_SEPARATOR_LINE_BREAK_LIST_ITEM)
    private fun getMarginList() = getMargin(HtmlCompat.FROM_HTML_SEPARATOR_LINE_BREAK_LIST)
    private fun getMarginDiv() = getMargin(HtmlCompat.FROM_HTML_SEPARATOR_LINE_BREAK_DIV)
    private fun getMarginBlockquote() = getMargin(HtmlCompat.FROM_HTML_SEPARATOR_LINE_BREAK_BLOCKQUOTE)

    private fun getMargin(flag: Int): Int = if (flag and flags != 0) 1 else 2


    override fun handleStartTag(tag: String, output: Editable, attributes: Attributes): Boolean {
//        Log.d("OoO", "opening, output: $output")

        when (tag.lowercase()) {
            "br" -> {}
            "p" -> {
                startBlockElement(output, attributes, getMarginParagraph())
                startCssStyle(output, attributes, flags)
            }
            "div" -> startBlockElement(output, attributes, getMarginDiv())
            "blockquote" -> {
                startBlockElement(output, attributes, getMarginBlockquote())
                start(output, Blockquote())
            }
            "h1", "h2", "h3", "h4", "h5", "h6" -> {
                startBlockElement(output, attributes, getMarginHeading())
                start(output, Heading(tag[1] - '1'))
            }

            "ul" -> startBlockElement(output, attributes, getMarginList())
            "li" -> {
                startBlockElement(output, attributes, getMarginListItem())
                start(output, Bullet())
                startCssStyle(output, attributes, flags)
            }

            "span" -> startCssStyle(output, attributes, flags)
            "s", "del", "strike" -> start(output, Strikethrough())

            else -> return handler?.handleStartTag(tag, output, attributes) ?: false
        }
        return true
    }


    override fun handleEndTag(tag: String, output: Editable): Boolean {
//        Log.d("OoO", "closing, output: $output")
        when (tag.lowercase()) {
            "br" -> output.append('\n')
            "p" -> {
                endCssStyle(output)
                endBlockElement(output)
            }
            "div" -> endBlockElement(output)
            "blockquote" -> {
                endBlockElement(output)
                end(output, Blockquote::class.java, QuoteSpan())
            }
            "h1", "h2", "h3", "h4", "h5", "h6" -> {
                getLast(output, Heading::class.java)?.let {
                    setSpanFromMark(output, it, RelativeSizeSpan(headingSizes[it.level]), StyleSpan(Typeface.BOLD))
                }
                endBlockElement(output)
            }
            "ul" -> endBlockElement(output)
            "li" -> {
                endCssStyle(output)
                endBlockElement(output)
                end(output, Bullet::class.java, BulletSpan())
            }
            "span" -> endCssStyle(output)
            "s", "del", "strike" -> end(output, Strikethrough::class.java, StrikethroughSpan())
            else -> return handler?.handleEndTag(tag, output) ?: false
        }
        return true
    }
}