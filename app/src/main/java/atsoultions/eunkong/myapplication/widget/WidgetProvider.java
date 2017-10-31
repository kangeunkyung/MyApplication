package atsoultions.eunkong.myapplication.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.appwidget.AppWidgetProviderInfo;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.TextureView;
import android.widget.RemoteViews;
import android.widget.Toast;

import atsoultions.eunkong.myapplication.ListItem;
import atsoultions.eunkong.myapplication.R;
import atsoultions.eunkong.myapplication.activity.MainActivity;
import atsoultions.eunkong.myapplication.activity.UpdateActivity;
import atsoultions.eunkong.myapplication.util.TextUtil;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP;

/**
 * Created by eunkong on 2017. 10. 24..
 */

public class WidgetProvider extends AppWidgetProvider {

    private static final String TAG = WidgetProvider.class.getSimpleName();

    public static final String APP_WIDGET_COPY = "android.appwidget.action.APPWIDGET_COPY";
    public static final String APP_WIDGET_CLICK = "android.appwidget.action.APPWIDGET_CLICK";
    public static final String EXTRA_ITEM = "EXTRA_ITEM";
    public static final String EXTRA_BUNDLE = "EXTRA_BUNDLE";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        Log.i(TAG, "[onReceive()] action : " + action);

        if (AppWidgetManager.ACTION_APPWIDGET_UPDATE.equals(action)) {
            Log.i(TAG, "[onReceive()] ACTION_APPWIDGET_UPDATE");

            Bundle extras = intent.getExtras();

            if (extras != null) {
                int[] appWidgetId = extras.getIntArray(AppWidgetManager.EXTRA_APPWIDGET_IDS);
                if (appWidgetId != null && appWidgetId.length > 0)
                    this.onUpdate(context, AppWidgetManager.getInstance(context), appWidgetId);
            }

        } else if(APP_WIDGET_CLICK.equals(action)) {

            Bundle bundle = intent.getBundleExtra(EXTRA_BUNDLE);

            int position = bundle.getInt(EXTRA_ITEM, 0);
            String bank = bundle.getString("bank");
            String account = bundle.getString("account");

            ListItem listItem = new ListItem(bank, account);
            TextUtil.copyText(context, listItem);
            Log.i(TAG, "[onReceive()] APP_WIDGET_CLICK - position : " + position + ", bank : " + bank + ", account : " + account);
        }
        super.onReceive(context, intent);
    }

    // 위젯 갱신 주기에 따라 위젯을 갱신할 때 호출됨
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int size = appWidgetIds.length;

        Log.i(TAG, "[onUpdate()] size : " + size);

        ListItem listItem = new ListItem();

        for (int i = 0; i < size; i++) {
            int appWidgetId = appWidgetIds[i];
            RemoteViews remoteViews = updateWidgetListView(context, appWidgetId);

            // 리스트아이템 내 복사 버튼
            Log.i(TAG, "[onUpdate()] listItem.toString()  : " + listItem.toString());
            Intent clickIntent = new Intent(context, WidgetProvider.class);
            clickIntent.setAction(APP_WIDGET_CLICK);
            PendingIntent clickPI = PendingIntent.getBroadcast(context, 0, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setPendingIntentTemplate(R.id.listview_widget, clickPI);

            // 설정 버튼
            remoteViews.setOnClickPendingIntent(R.id.iv_setting, getPendingSelfIntent(context, MainActivity.class));
            // 계좌등록 버튼
            remoteViews.setOnClickPendingIntent(R.id.btn_add_account, getPendingSelfIntent(context, UpdateActivity.class));

            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    // 위젯이 사용자에 의해 제거될 때 호출됨
    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        Log.i(TAG, "[onDeleted()]");
    }

    // 위젯이 처음 생성될 때 호출되며, 동일한 위젯의 경우 처음 호출됨
    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        Log.i(TAG, "[onEnabled()]");
    }

    // 위젯의 마지막 인스턴스가 제거될 때 호출됨
    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        Log.i(TAG, "[onDisabled()]");

    }

    /**
     * 위젯 리스트뷰 생성
     * @param context
     * @param appWidgetId
     * @return
     */
    private RemoteViews updateWidgetListView(Context context, int appWidgetId) {
        Log.i(TAG, "[updateWidgetListView()]");

        RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        Intent serviceIntent = new Intent(context, WidgetService.class);
        serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        serviceIntent.setData(Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME)));
        remoteView.setRemoteAdapter(appWidgetId, R.id.listview_widget, serviceIntent);
        remoteView.setEmptyView(R.id.listview_widget, R.id.layout_empty_view);
        return remoteView;
    }

    protected PendingIntent getPendingSelfIntent(Context context, Class<?> className) {
        Intent configIntent = new Intent(context, className);
        configIntent.putExtra("TYPE", "TYPE_ADD");
        configIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        return PendingIntent.getActivity(context, 0, configIntent, 0);
    }

    private void clickEventListItem(Context context, RemoteViews remoteViews, int appWidgetId ) {

        Log.i(TAG, "[clickEbentListItem()]");
        // button click event
        Intent clickIntent = new Intent();
        clickIntent.setAction(WidgetProvider.APP_WIDGET_COPY);
        clickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        clickIntent.setData(Uri.parse(clickIntent.toUri(Intent.URI_INTENT_SCHEME)));
        PendingIntent clickPI = PendingIntent.getBroadcast(context, 0, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setPendingIntentTemplate(R.id.listview_widget, clickPI);

    }

}
