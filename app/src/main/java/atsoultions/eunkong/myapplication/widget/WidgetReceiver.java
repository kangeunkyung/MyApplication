package atsoultions.eunkong.myapplication.widget;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import atsoultions.eunkong.myapplication.util.TextUtil;

/**
 * Created by eunkong on 2017. 10. 26..
 */

public class WidgetReceiver extends BroadcastReceiver {

    private static final String TAG = WidgetReceiver.class.getSimpleName();


    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
        Log.i(TAG, "[onReceive()] action : " + action);
        String text = intent.getStringExtra(AppWidgetManager.EXTRA_APPWIDGET_ID);
        Log.i(TAG, "[onReceive()] text : " + text);

        TextUtil.copyText(context, text);
    }
}
