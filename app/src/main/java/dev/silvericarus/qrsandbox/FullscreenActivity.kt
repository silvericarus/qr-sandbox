package dev.silvericarus.qrsandbox

import androidx.appcompat.app.AppCompatActivity
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.SearchManager
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.WindowInsets
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator
import dev.silvericarus.qrsandbox.databinding.ActivityFullscreenBinding
import java.util.regex.Pattern

/**
 * The starter activity, that explains how the app works and shows
 * you the buttons that take you to the other components of the app
 */
class FullscreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFullscreenBinding
    private lateinit var fullscreenContent: TextView
    private lateinit var fullscreenContentControls: LinearLayout


    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val regexUrl: Pattern = Pattern.compile("https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&\\/=]*)")
        val result = IntentIntegrator.parseActivityResult(resultCode, data)
        if (resultCode == RESULT_OK && regexUrl.matcher(result.contents).matches())
        {
            AlertDialog.Builder(this)
                .setMessage(getString(R.string.scan_feedback_dialog_message) + "${result.contents}?")
                .setPositiveButton(R.string.qr_scan_notification_ok)
                { dialogInterface, i ->
                    val intent = Intent(this, SandboxActivity::class.java)
                    intent.putExtra("desiredURL", result.contents)
                    startActivity(intent)
                }
                .setNegativeButton(R.string.qr_scan_notification_ko)
                { dialogInterface, i ->
                    dialogInterface.cancel()
                }
                .create()
                .show()
        }
        else if (!regexUrl.matcher(result.contents).matches())
            run {
                val toast: Toast = Toast.makeText(
                    applicationContext,
                    R.string.no_url_qr_toast,
                    Toast.LENGTH_LONG
                )
                toast.show()
            }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFullscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setBackgroundDrawable(getDrawable(R.color.everSinceTheDay))


        fullscreenContentControls = binding.fullscreenContent

        //Do the scan of the QR Code
        binding.btnScan.setOnClickListener { view ->
            val intentIntegrator = IntentIntegrator(this)
            intentIntegrator
                .setDesiredBarcodeFormats(listOf(IntentIntegrator.QR_CODE))
                //.setTimeout(5000)
                .setTorchEnabled(false)
                //.setBarcodeImageEnabled(true)
                .setBeepEnabled(false)

            intentIntegrator.initiateScan()
        }

        //Redirect to the history/record activity
        binding.btnRecord.setOnTouchListener { view, motionEvent ->
            Log.d(view.id.toString(), motionEvent.rawX.toString())
            Log.d(view.id.toString(), motionEvent.rawY.toString())
            return@setOnTouchListener (true)
        }

        //Redirect to the settings activity
        binding.btnSettings.setOnTouchListener { view, motionEvent ->
            Log.d(view.id.toString(), motionEvent.rawX.toString())
            Log.d(view.id.toString(), motionEvent.rawY.toString())
            return@setOnTouchListener (true)
        }
    }

}