package atsoultions.eunkong.myapplication.widget;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViewsService;

/**
 * Created by eunkong on 2017. 10. 26..
 */

public class WidgetService extends RemoteViewsService {
    private static final String TAG = WidgetService.class.getSimpleName();

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        Log.i(TAG, "[onGetViewFactory()] appWidgetId : " + appWidgetId);

        return new ListProvider(getApplicationContext(), intent);
    }
}
