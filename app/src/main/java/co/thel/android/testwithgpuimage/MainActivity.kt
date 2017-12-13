package co.thel.android.testwithgpuimage

import android.Manifest
import android.graphics.Bitmap
import android.hardware.Camera
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ToggleButton
import jp.co.cyberagent.android.gpuimage.GPUImage
import jp.co.cyberagent.android.gpuimage.GPUImageFilter
import jp.co.cyberagent.android.gpuimage.GPUImageFilterGroup
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var mGPUImage: GPUImage? = null
    private var mImageBitMap: Bitmap? = null
    private var mCamera: Camera? = null

    private var mEncodeAndMux: EncodeAndMuxTest? = null
//    private val mCameraToMpeg: CameraToMpegTest = CameraToMpegTest()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestPermission()

        val filterGroup = GPUImageFilterGroup()
        filterGroup.addFilter(GPUImageFilter())

        mGPUImage = GPUImage(this)
        mGPUImage?.setGLSurfaceView(surfaceView)
        mGPUImage?.setFilter(filterGroup)

        mCamera = Camera.open()
        mGPUImage?.setUpCamera(mCamera)

        toggleButton.textOff = "Ready"
        toggleButton.textOn = "Recording"

        mEncodeAndMux = EncodeAndMuxTest()
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
        println("isChecked: ${button.isChecked}")
        surfaceView.queueEvent(Runnable {

        })
    }

}
