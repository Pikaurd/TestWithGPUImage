package co.thel.android.testwithgpuimage

import android.Manifest
import android.graphics.Bitmap
import android.hardware.Camera
import android.opengl.GLSurfaceView
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ToggleButton
import jp.co.cyberagent.android.gpuimage.*
import jp.co.cyberagent.android.utils.CameraHelper
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File


class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"

    private var mGPUImage: GPUImage? = null
    internal var mCameraHelper: CameraHelper? = null
    private var mCamera: Camera? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestPermission()

        setup()
        setupUI()
    }

    private fun setup() {
//        val filterGroup = GPUImageFilterGroup()
////        filterGroup.addFilter(GPUImageFilter())
//        filterGroup.addFilter(GPUImageColorInvertFilter())

        surfaceView.setEGLContextClientVersion(2)
        assert(surfaceView != null)

        mGPUImage = GPUImage(this)
        mGPUImage?.setGLSurfaceView(surfaceView)
        mGPUImage?.setFilter(GPUImageColorInvertFilter())

        mCamera = Camera.open()
        var cameraParams = mCamera!!.parameters
        var cameraResolutions = cameraParams.supportedPictureSizes
        val sizes: List<Pair<Int, Int>> = cameraResolutions
                .map { Pair(it.width, it.height) }
                .filter { it.second.toFloat() / it.first.toFloat() == 9/16.toFloat() }

        Log.d(TAG, "camera size: ${sizes}")
        cameraParams.setPictureSize(1280, 720)
        mCamera?.parameters = cameraParams
        mGPUImage?.setUpCamera(mCamera)
    }

    private fun setupUI() {
        toggleButton.textOff = "Ready"
        toggleButton.textOn = "Recording"

    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT > 22) {
            var arr = arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA
            )
            requestPermissions(arr, 1)
        }
    }

    fun toggleButtonAction(view: View) {
        val button = view as ToggleButton
        Log.d(TAG, "isChecked: ${button.isChecked}")
        val outputFile = File("/sdcard/Download/output.mp4")
        mGPUImage!!.renderer!!.mOutputFile = outputFile
        mGPUImage?.setRotation(Rotation.ROTATION_90)
        surfaceView.queueEvent({
            mGPUImage?.renderer?.changeRecordingState(button.isChecked)
        })
    }

}
