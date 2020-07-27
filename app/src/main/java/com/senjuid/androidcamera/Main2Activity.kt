package com.senjuid.androidcamera

import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.senjuid.camera.CameraPlugin
import com.senjuid.camera.CameraPluginListener
import com.senjuid.camera.CameraPluginOptions
import kotlinx.android.synthetic.main.activity_main2.*
import java.io.File


class Main2Activity : AppCompatActivity() {

    private var cameraPlugin: CameraPlugin? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        cameraPlugin = CameraPlugin(this)
        cameraPlugin?.setCameraPluginListener(object : CameraPluginListener {
            override fun onSuccess(photoPath: String) {
                showImage(photoPath)
            }

            override fun onCancel() {
                Toast.makeText(this@Main2Activity, "Canceled", Toast.LENGTH_LONG).show()
            }
        })

        button_click.setOnClickListener {
            val quality = et_quality.text.toString()
            val maxSize = et_max_size.text.toString()
            if (quality.isNullOrEmpty()) {
                Toast.makeText(this@Main2Activity, "Please input image quality (1 - 100).", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (maxSize.isNullOrEmpty()) {
                Toast.makeText(this@Main2Activity, "Please input image maximum size.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val options = CameraPluginOptions.Builder()
                    .setMaxSize(maxSize.toInt())
                    .setQuality(quality.toInt())
                    .build()
            cameraPlugin?.open(options)
        }
    }

    fun showImage(imagePath: String) {
        val imgFile = File(imagePath)
        if (imgFile.exists()) {
            val bitmap = BitmapFactory.decodeFile(imgFile.absolutePath)
            iv_preview.setImageBitmap(bitmap)
        }
    }

    /** Uncomment if the activity isn't androidx.appcompat.app.AppCompatActivity */
    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        cameraPlugin?.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }*/
}
