package com.demo.app

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.demo.app.databinding.ActivityMainBinding
import me.reezy.cosmo.html.CustomElementHandler
import me.reezy.cosmo.html.HTML

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val binding by lazy { ActivityMainBinding.bind(findViewById<ViewGroup>(android.R.id.content).getChildAt(0)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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



        binding.hello.movementMethod = LinkMovementMethod.getInstance()

        binding.hello.text = HtmlCompat.fromHtml(html1, HtmlCompat.FROM_HTML_MODE_COMPACT)

        binding.world.movementMethod = LinkMovementMethod.getInstance()

        binding.world.text = HTML.fromHtml(html1, elementHandler = CustomElementHandler {
            Toast.makeText(this, "action = $it", Toast.LENGTH_SHORT).show()
        })
    }
}