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
import com.google.zxing.integration.android.IntentIntegrator
import dev.silvericarus.qrsandbox.databinding.ActivityFullscreenBinding

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
        if (resultCode == RESULT_OK)
        {
            val result = IntentIntegrator.parseActivityResult(resultCode, data)
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