package atsoultions.eunkong.myapplication;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by eunkong on 2017. 10. 31..
 */

public class TimerHandler {
    private final static String TAG = TimerHandler.class.getSimpleName();

    private Context mContext;
    private int timerCount;
    private Timer timer;
    private Handler handler = new Handler();


    public TimerHandler(Context context) {
        mContext = context;
    }
    public void startTimer(final View view) {
        Log.i(TAG, "[startTimer()]");
        timer = new Timer();
        timer.schedule(new TimerTask()
        {
            @Override
            public void run() {
                Log.i(TAG, "[startTimer()] run() timerCount : " + timerCount);
                timerCount++;
                update(view);
            }
        }, 0, 2000);

    }

    public void startTimer(final int viewId) {
        Log.i(TAG, "[startTimer()]");
        timer = new Timer();
        timer.schedule(new TimerTask()
        {
            @Override
            public void run() {
                Log.i(TAG, "[startTimer()] run() timerCount : " + timerCount);
                timerCount++;
                update(viewId);
            }
        }, 0, 2000);

    }

    public void stopTimer() {
        Log.i(TAG, "[stopTimer()] timerCount : " + timerCount);
        timer.cancel();
        timerCount = 0;
    }

    public void update(final View view)
    {
        Runnable updater = new Runnable() {
            public void run() {
                Log.i(TAG, "[update()] run() timerCount : " + timerCount);
                Button btnCopy = (Button) view;

                if(timerCount >= 2) {
                    btnCopy.setBackgroundResource(R.drawable.bg_input_default);
                    btnCopy.setText("복사");
                    stopTimer();
                } else {
                    btnCopy.setBackgroundResource(R.drawable.icon_check);
                    btnCopy.setText("");
                }
            }
        };

        handler.post(updater);
    }

    public void update(final int viewId)
    {
        Runnable updater = new Runnable() {
            public void run() {
                Log.i(TAG, "[update()] run() timerCount : " + timerCount);
                Button btnCopy = ((Activity)mContext).findViewById(viewId);

                if(timerCount >= 2) {
                    btnCopy.setBackgroundResource(R.drawable.bg_input_default);
                    btnCopy.setText("복사");
                    stopTimer();
                } else {
                    btnCopy.setBackgroundResource(R.drawable.icon_check);
                    btnCopy.setText("");
                }
            }
        };

        handler.post(updater);
    }

}
