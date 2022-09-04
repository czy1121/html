package me.reezy.cosmo.html

import android.os.Build
import android.text.Html
import android.text.Spanned
import androidx.core.text.HtmlCompat

object HTML {
    fun fromHtml(source: String, flags: Int = HtmlCompat.FROM_HTML_MODE_COMPACT, imageGetter: Html.ImageGetter? = null, elementHandler: ElementHandler? = null): Spanned {
        if (Build.VERSION.SDK_INT >= 24) {
            return HtmlCompat.fromHtml(source, flags, imageGetter, HtmlContentHandler(elementHandler))
        }
        return HtmlCompat.fromHtml(source, flags, imageGetter, HtmlContentHandler(CompatElementHandler(flags, elementHandler)))
    }
}