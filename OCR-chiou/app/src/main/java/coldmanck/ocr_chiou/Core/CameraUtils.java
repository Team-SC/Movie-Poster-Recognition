package coldmanck.ocr_chiou.Core;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.util.Log;

/**
 * Created by Fadi on 5/11/2014.
 */
public class CameraUtils {

    static final String TAG = "DBG_ " + CameraUtils.class.getName();

    public static boolean deviceHasCamera(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    public static Camera getCamera() {
        try {
            return Camera.open();
        } catch (Exception e) {
            Log.e(TAG, "Cannot getCamera()");
            return null;
        }
    }
}
