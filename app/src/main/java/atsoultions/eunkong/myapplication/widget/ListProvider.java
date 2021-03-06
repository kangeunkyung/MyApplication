package atsoultions.eunkong.myapplication.widget;

import android.app.LauncherActivity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetHost;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;

import atsoultions.eunkong.myapplication.ListItem;
import atsoultions.eunkong.myapplication.ListViewAdapter;
import atsoultions.eunkong.myapplication.R;
import atsoultions.eunkong.myapplication.database.ContactDbManager;

import static atsoultions.eunkong.myapplication.widget.WidgetProvider.APP_WIDGET_CLICK;
import static atsoultions.eunkong.myapplication.widget.WidgetProvider.APP_WIDGET_COPY;
import static atsoultions.eunkong.myapplication.widget.WidgetProvider.EXTRA_BUNDLE;

/**
 * Created by eunkong on 2017. 10. 26..
 */

public class ListProvider implements RemoteViewsService.RemoteViewsFactory {

    private static final String TAG = ListProvider.class.getSimpleName();

    private ContactDbManager mSQLiteDB;
    private ArrayList<ListItem> listItemList = new ArrayList();
    private Context mContext = null;
    private int appWidgetId;
    private final int WIDGET_LIST_SIZE = 3;


    public ListProvider(Context context, Intent intent) {
        Log.i(TAG, "[ListProvider()]");
        mContext = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);

    }

    private void initData() {
        Log.i(TAG, "[initData()]");
        listItemList.clear();

        // 데이터베이스 생성
        mSQLiteDB = new ContactDbManager(mContext);

        // 테이블 삭제
//        mSQLiteDB.deleteTable();

        // 테이블 생성
        mSQLiteDB.initTable();

        // 데이터 생성
        listItemList = mSQLiteDB.loadData();

    }

    @Override
    public void onCreate() {
        Log.i(TAG, "[onCreate()]");
        initData();

    }

    @Override
    public void onDataSetChanged() {
        Log.i(TAG, "[onDataSetChanged()]");
        listItemList = mSQLiteDB.loadData();
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "[onDestroy()]");

    }

    @Override
    public int getCount() {
//        return (listItemList == null) ? 0 : listItemList.size();
        if(listItemList == null)
            return 0;
        else {
            if(listItemList.size() > 3)
                return WIDGET_LIST_SIZE;
            else
                return listItemList.size();
        }
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Log.i(TAG, "[getViewAt()] position : " + position);

        final RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.widget_listitem);

        if(listItemList != null) {
            ListItem listItem = listItemList.get(position);
            remoteViews.setTextViewText(R.id.tv_widget_bank, listItem.getBank());
            remoteViews.setTextViewText(R.id.tv_widget_account, listItem.getAccount());
            if(TextUtils.isEmpty(listItem.getUser()) == false)
                remoteViews.setTextViewText(R.id.tv_widget_user, "(예금주 : " + listItem.getUser() + ")");


            // button click event
            Intent fillIntent = new Intent(mContext, WidgetProvider.class);
            Bundle extra = new Bundle();
            extra.putInt(WidgetProvider.EXTRA_ITEM, position);
            extra.putString("bank", listItem.getBank());
            extra.putString("account", listItem.getAccount());
            extra.putString("user", listItem.getUser());
            extra.putInt("viewId", R.id.btn_widget_copy);
            Log.d(TAG, "[getViewAt()] viewId : " + R.id.btn_widget_copy);

            fillIntent.putExtra(EXTRA_BUNDLE, extra);
            fillIntent.setAction(APP_WIDGET_CLICK);
            remoteViews.setOnClickFillInIntent(R.id.btn_widget_copy, fillIntent);
            Log.i(TAG, "[getViewAt()] listItem : " + listItem.toString());
        }
        return remoteViews;
    }

    private void registerButtonListener(RemoteViews remoteView, int id) {

        Log.i(TAG, "[registerBUttonListener()] id : " + id);
        Intent intent = new Intent(APP_WIDGET_CLICK);
        intent.putExtra("id", id);
        intent.setClass(mContext, WidgetReceiver.class);
        PendingIntent pending = PendingIntent.getBroadcast(mContext.getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteView.setOnClickPendingIntent(id, pending);

    }


    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
