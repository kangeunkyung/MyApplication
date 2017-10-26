package atsoultions.eunkong.myapplication.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by eunkong on 2017. 10. 19..
 */

public class ContactDBHelper extends SQLiteOpenHelper {
    private static final String TAG = ContactDBHelper.class.getSimpleName();

    public static final int DB_VERSION = 1;

    public static final String DBFILE_CONTACT = "contact.db";



    public ContactDBHelper(Context context) {
        super(context, DBFILE_CONTACT, null, DB_VERSION);
        Log.i(TAG, "[ContactDBHelper()]");
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "[onCreate()] create table : " + ContactDB.SQL_CREATE_TBL);
        db.execSQL(ContactDB.SQL_CREATE_TBL);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int verVersion) {
//        onUpgrade(db, oldVersion, verVersion);
    }
}
