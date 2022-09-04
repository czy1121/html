package com.demo.app

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import me.reezy.cosmo.html.CustomElementHandler
import me.reezy.cosmo.html.HTML

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val html1 = """
<del>del</del>
<s>s</s>
<strike>strike</strike>

<h1 style="text-align:center">Heading</h1>

Agree to the <action name="privacy">Privacy Policy</action> and the <action name="agreement">User Agreement</action>

<blockquote style="text-align:center">blockquote</blockquote>

<p style="text-align:end">You can reach Michael at:</p>

<ul>
  <li style="color:#ff0000;"><a href="https://example.com">Website</a></li>
  <li style="text-decoration:line-through;"><a href="mailto:m.bluth@example.com">Email</a></li>
  <li style="background-color:#ff0000;"><a href="tel:+123456789">Phone</a></li>
  <li style="color:#ff0000; text-decoration:line-through; background-color:#CC9900;"><a href="tel:+123456789">Test</a></li>
</ul> 
        """.trimIndent()



        findViewById<TextView>(R.id.hello).let {
            it.movementMethod = LinkMovementMethod.getInstance()

            it.text = HtmlCompat.fromHtml(html1, HtmlCompat.FROM_HTML_MODE_COMPACT)
        }
        findViewById<TextView>(R.id.world).let { it ->
            it.movementMethod = LinkMovementMethod.getInstance()

            it.text = HTML.fromHtml(html1, elementHandler = CustomElementHandler {
                Toast.makeText(this, "action = $it", Toast.LENGTH_SHORT).show()
            })
        }
    }
}