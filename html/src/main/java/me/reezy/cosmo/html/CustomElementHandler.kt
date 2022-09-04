package me.reezy.cosmo.html

import android.text.Editable
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View
import org.xml.sax.Attributes

class CustomElementHandler(private val onAction: (String) -> Unit) : BaseElementHandler(), ElementHandler {
    private class Action(val name: String?)

    class ActionSpan(private val name: String, private val onClick: (String) -> Unit) : ClickableSpan() {

        override fun onClick(v: View) {
            onClick(name)
        }

        override fun updateDrawState(ds: TextPaint) {
            ds.color = ds.linkColor
        }
    }

    override fun handleStartTag(tag: String, output: Editable, attributes: Attributes): Boolean {
        when (tag.lowercase()) {
            "action" -> start(output, Action(attributes.getValue("name")))
            else -> return false
        }
        return true
    }

    override fun handleEndTag(tag: String, output: Editable): Boolean {
        when (tag.lowercase()) {
            "action" -> {
                getLast(output, Action::class.java)?.let {
                    setSpanFromMark(output, it, ActionSpan(it.name ?: "", onAction))
                }
            }
            else -> return false
        }
        return true
    }
}