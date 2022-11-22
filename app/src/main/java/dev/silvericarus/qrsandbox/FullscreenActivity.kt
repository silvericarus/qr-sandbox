package dev.silvericarus.qrsandbox

import androidx.appcompat.app.AppCompatActivity
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.WindowInsets
import android.widget.LinearLayout
import android.widget.TextView
import dev.silvericarus.qrsandbox.databinding.ActivityFullscreenBinding

/**
 * The starter activity, that explains how the app works and shows
 * you the buttons that take you to the other components of the app
 */
class FullscreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFullscreenBinding
    private lateinit var fullscreenContent: TextView
    private lateinit var fullscreenContentControls: LinearLayout

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFullscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(false)


        fullscreenContentControls = binding.fullscreenContent

        //Redirect to the scanner activity
        binding.btnScan.setOnTouchListener { view, motionEvent ->
            Log.d(view.id.toString(), motionEvent.rawX.toString())
            Log.d(view.id.toString(), motionEvent.rawY.toString())
            return@setOnTouchListener (true)
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