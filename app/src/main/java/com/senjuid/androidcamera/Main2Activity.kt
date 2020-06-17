package com.senjuid.androidcamera

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.senjuid.camera.CameraPlugin
import com.senjuid.camera.CameraPluginListener
import com.senjuid.camera.CameraPluginOptions
import kotlinx.android.synthetic.main.activity_main2.*

class Main2Activity : AppCompatActivity() {

    private var cameraPlugin: CameraPlugin? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        cameraPlugin = CameraPlugin(this)
        cameraPlugin?.setCameraPluginListener(object : CameraPluginListener {
            override fun onSuccess(photoPath: String) {
                Toast.makeText(this@Main2Activity, photoPath, Toast.LENGTH_LONG).show()
            }

            override fun onCancel() {
                Toast.makeText(this@Main2Activity, "Canceled", Toast.LENGTH_LONG).show()
            }
        })

        button_click.setOnClickListener {
            val quality = et_quality.text.toString().toInt()
            val maxSize = et_max_size.text.toString().toInt()

            val options = CameraPluginOptions.Builder()
                    .setMaxSize(maxSize)
                    .setQuality(quality)
                    .build()
            cameraPlugin?.open(options)
        }
    }

    /** Uncomment if the activity isn't androidx.appcompat.app.AppCompatActivity */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        cameraPlugin?.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }
}
