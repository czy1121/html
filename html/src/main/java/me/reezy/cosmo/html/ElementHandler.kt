package me.reezy.cosmo.html;

import android.text.Editable;
import org.xml.sax.Attributes

interface ElementHandler {
    fun handleStartTag(tag: String, output: Editable, attributes: Attributes): Boolean
    fun handleEndTag(tag: String, output: Editable): Boolean
}