package com.demo.app

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import com.demo.app.databinding.FragmentHtmlBinding
import me.reezy.cosmo.html.CustomElementHandler
import me.reezy.cosmo.html.HTML

class HtmlFragment : Fragment(R.layout.fragment_html) {


    private val binding by lazy { FragmentHtmlBinding.bind(requireView()) }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


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
            Toast.makeText(requireContext(), "action = $it", Toast.LENGTH_SHORT).show()
        })
    }
}