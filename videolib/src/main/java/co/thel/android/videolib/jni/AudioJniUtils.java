package co.thel.android.videolib.jni;

public class AudioJniUtils {
    static {
        System.loadLibrary("video-lib-audio-mix-lib");
    }

    public static native byte[] audioMix(
            byte[] sourceA,
            byte[] sourceB,
            byte[] dst,
            float firstVol,
            float secondVol
    );
}
