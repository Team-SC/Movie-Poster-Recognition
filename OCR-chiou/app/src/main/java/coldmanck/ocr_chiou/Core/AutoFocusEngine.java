package coldmanck.ocr_chiou.Core;

import android.hardware.Camera;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Fadi on 5/11/2014.
 */
public class AutoFocusEngine implements Camera.AutoFocusCallback {

    static final String TAG = "DBG_" + AutoFocusEngine.class.getName();
    private static final long AUTO_FOCUS_INTERVAL_MS = 2000;

    private Timer timer;
    private Camera camera;
    private TimerTask timerTask;

    private AutoFocusEngine(Camera camera){
        this.camera = camera;
        this.timer = new Timer();
    }

    static public AutoFocusEngine New(Camera camera){
        return  new AutoFocusEngine(camera);
    }

    private boolean running;

    public boolean isRunning() {
        return running;
    }

    public void start() {
        Log.d(TAG, "AutoFocusEngine Started");
        work();
        running = true;
    }

    public void stop() {

        camera.cancelAutoFocus();

        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }

        running = false;

        Log.d(TAG, "AutoFocusEngine Stopped");
    }

    private void work(){
        camera.autoFocus(this);
    }

    @Override
    public void onAutoFocus(boolean success, Camera camera) {
        timerTask = new TimerTask() {
            @Override
            public void run() {
                work();
            }
        };
        timer.schedule(timerTask, AUTO_FOCUS_INTERVAL_MS);
    }
}
