package dev.silvericarus.qrsandbox

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.PersistableBundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.MenuItem
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import dev.silvericarus.qrsandbox.databinding.ActivitySandboxBinding

class SandboxActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySandboxBinding
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item))
            return true
        return super.onOptionsItemSelected(item)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        toggle.onConfigurationChanged(newConfig)
    }

    override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onPostCreate(savedInstanceState, persistentState)
        toggle.syncState()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySandboxBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setBackgroundDrawable(getDrawable(R.color.everSinceTheDay))

        val drawerLayout: DrawerLayout = binding.sandboxDrawerLayout
        val webView: WebView = binding.sandboxWebview
        webView.webChromeClient = WebChromeClient()
        val url = intent.getStringExtra("desiredURL")
        val display = DisplayMetrics()
        val fab = binding.floatingActionButton
        fab.setOnClickListener{fab ->
            drawerLayout.openDrawer(Gravity.END)
        }
        windowManager.defaultDisplay.getMetrics(display)
        if (url != null) {
            webView.loadUrl(url)
        }
        else
            webView.loadUrl("https://picsum.photos/" + display.widthPixels + "/" + display.heightPixels)


        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.yes, R.string.no)
        drawerLayout.addDrawerListener(toggle)
/*        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)*/
    }
}