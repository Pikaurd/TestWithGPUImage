package co.thel.android.testwithgpuimage

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import co.thel.android.videolib.Constants
import co.thel.android.videolib.views.CameraView;
import kotlinx.android.synthetic.main.activity_recorder.*
import java.util.concurrent.Executors

class RecorderActivity : AppCompatActivity() {

    private var recordFlag = false

    private val executorService = Executors.newSingleThreadExecutor()

    private val recorderRunnable = object: Runnable {
        override fun run() {
            recordFlag = true
            val time = System.currentTimeMillis()
            val savePath = "/sdcard/Download/test_$time.mp4"

            try {
                cameraView.setSavePath(savePath)
                cameraView.startRecord()
                Thread.sleep(1000 * 5)
                recordFlag = false
                cameraView.stopRecord()
                recordComplete(savePath)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recorder)

        Constants.getInstance().context = applicationContext
    }


    fun toggleAction(view: View) {
//        cameraView.switchCamera()
        if (!recordFlag) {
            cameraView.changeBeautyLevel(0)
            executorService.execute(recorderRunnable)
        }
    }

    private fun recordComplete(path: String) {
        toggleButton2.isChecked = false
        runOnUiThread {
            Toast.makeText(this, "Saved video to $path", Toast.LENGTH_SHORT).show()
        }
    }
}
