package me.reezy.cosmo.html

import android.text.Html.TagHandler
import android.text.Editable
import org.xml.sax.*

class HtmlContentHandler(private val elementHandler: ElementHandler? = null) : ContentHandler, TagHandler {

    private var contentHandler: ContentHandler? = null
    private var editable: Editable? = null

    override fun handleTag(opening: Boolean, tag: String, output: Editable, xmlReader: XMLReader) {
        if (contentHandler == null) {
            editable = output
            contentHandler = xmlReader.contentHandler
            xmlReader.contentHandler = this
        }
    }

    override fun setDocumentLocator(locator: Locator) {
        contentHandler?.setDocumentLocator(locator)
    }

    override fun startDocument() {
        contentHandler?.startDocument()
    }

    override fun endDocument() {
        contentHandler?.endDocument()
    }

    override fun startPrefixMapping(prefix: String, uri: String) {
        contentHandler?.startPrefixMapping(prefix, uri)
    }


    override fun endPrefixMapping(prefix: String) {
        contentHandler?.endPrefixMapping(prefix)
    }


    override fun startElement(uri: String, localName: String, qName: String, attributes: Attributes) {

        if (elementHandler?.handleStartTag(localName, editable!!, attributes) != true) {
            contentHandler?.startElement(uri, localName, qName, attributes)
        }
    }

    override fun endElement(uri: String, localName: String, qName: String) {
        if (elementHandler?.handleEndTag(localName, editable!!) != true) {
            contentHandler?.endElement(uri, localName, qName)
        }
    }

    override fun characters(ch: CharArray, start: Int, length: Int) {
        contentHandler?.characters(ch, start, length)
    }

    override fun ignorableWhitespace(ch: CharArray, start: Int, length: Int) {
        contentHandler?.ignorableWhitespace(ch, start, length)
    }


    override fun processingInstruction(target: String, data: String) {
        contentHandler?.processingInstruction(target, data)
    }


    override fun skippedEntity(name: String) {
        contentHandler?.skippedEntity(name)
    }
}