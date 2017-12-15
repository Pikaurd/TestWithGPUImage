package co.thel.android.jni;

public class AudioJniUtils {
    static {
        System.loadLibrary("audio-mix-lib");
    }

    public static native byte[] audioMix(
            byte[] sourceA,
            byte[] sourceB,
            byte[] dst,
            float firstVol,
            float secondVol
    );
}
