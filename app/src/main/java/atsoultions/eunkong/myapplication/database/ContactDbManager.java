package atsoultions.eunkong.myapplication.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.util.ArrayList;

import atsoultions.eunkong.myapplication.ListItem;
import atsoultions.eunkong.myapplication.R;
import atsoultions.eunkong.myapplication.util.ImageUtil;

import static atsoultions.eunkong.myapplication.database.ContactDB.SQL_INSERT_DEFAULT;
import static atsoultions.eunkong.myapplication.database.ContactDB.SQL_INSERT_OPTIONAL;
import static atsoultions.eunkong.myapplication.database.ContactDB.SQL_SELECT_FROM_NO;

/**
 * Created by eunkong on 2017. 10. 18..
 */

public class ContactDbManager {
    private static final String TAG = ContactDbManager.class.getSimpleName();

    private Context mContext;
    private ContactDBHelper dbHelper;


    public ContactDbManager(Context context) {
        mContext = context;
        dbHelper = new ContactDBHelper(mContext);
    }


    /**
     * 데이터베이스의 테이블 생성
     */
    public void initTable() {
        Log.i(TAG, "[initTable()] create table : " + ContactDB.SQL_CREATE_TBL);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        db.execSQL(ContactDB.SQL_CREATE_TBL);

    }

    /**
     * 데이터베이스에 저장된 데이터 조회
     */
    public ArrayList<ListItem> loadData() {
        Log.i(TAG, "[loadData()]");
        // 데이터 조회를 위한 SELECT 문 실행 결과 데이터를 cursor 타입으로 리턴받은 다음 moveToNext()함수를 통해 커서로 전달된 레코드 집합(Record Set)을 탐색
        // SELECT + FROM CONTACT

        ArrayList<ListItem> itemArrayList = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(ContactDB.SQL_SELECT, null);

        while (cursor.moveToNext()) {
            Log.i(TAG, "[loadData()] cursor position : " + cursor.getPosition());
            int no = cursor.getInt(0);
            String bank = cursor.getString(1);
            int bankIndex = cursor.getInt(2);
            String account = cursor.getString(3);
            String user = cursor.getString(4);
            String nickname = cursor.getString(5);

            ListItem listItem = new ListItem();

            if (listItem != null) {
                listItem.setNo(no);
                listItem.setBank(bank);
                listItem.setBankIndex(bankIndex);
                listItem.setAccount(account);
                listItem.setUser(user);
                listItem.setNickname(nickname);
            }
            Log.i(TAG, "[loadData()] data : " + listItem.toString());
            itemArrayList.add(listItem);
        }

        return itemArrayList;
    }


    /**
     * 데이터베이스에 데이터 추가
     */
    public void addDataDefault(ListItem listItem) {
        Log.i(TAG, "[addDataDefault()]");

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        int bankIndex = 0;
        String bank = "";
        String account = "";

        if (listItem != null) {
            bank = listItem.getBank();
            bankIndex = listItem.getBankIndex();
            account = listItem.getAccount();
        }


        String sqlInsert = SQL_INSERT_DEFAULT +
                "('" + bank + "', " +
                "'" + bankIndex + "', " +
                "'" + account + "') ";

        Log.i(TAG, "[addDataDefault()] insert 명령문 : " + sqlInsert);
        db.execSQL(sqlInsert);
    }


    /**
     * 데이터베이스에 데이터 추가
     */
    public void addDataOptional(ListItem listItem) {
        Log.i(TAG, "[addDataOptional()]");

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String bank = "";
        int bankIndex = 0;
        String account = "";
        String user = "";
        String nickname = "";

        if (listItem != null) {
            bank = listItem.getBank();
            bankIndex = listItem.getBankIndex();
            account = listItem.getAccount();
            user = listItem.getUser();
            nickname = listItem.getNickname();
            Log.i(TAG, "[addDataOptional()] bankIndex : " + bankIndex);
        }


        String sqlInsert = SQL_INSERT_OPTIONAL +
                "('" + bank + "', " +
                bankIndex + ", " +
                "'" + account + "', " +
                "'" + user + "', " +
                "'" + nickname + "') ";

        Log.i(TAG, "[addDataOptional()] insert 명령문 : " + sqlInsert);
        db.execSQL(sqlInsert);
    }

    /**
     * 칼럼 no값에 해당하는 데이터 조회
     */
    public ListItem loadDataFromNo(int no) {
        Log.i(TAG, "[loadDataFromNo()]");
        // 데이터 조회를 위한 SELECT 문 실행 결과 데이터를 cursor 타입으로 리턴받은 다음 moveToNext()함수를 통해 커서로 전달된 레코드 집합(Record Set)을 탐색
        // SELECT * FROM CONTACT WHERE NO = ;

        ListItem listItem = new ListItem();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sqlSelect = SQL_SELECT_FROM_NO + no;
        Log.i(TAG, "[loadDataFromNo()] select문 : " + sqlSelect);
        Cursor cursor = db.rawQuery(sqlSelect, null);

        while (cursor.moveToNext()) {
            Log.i(TAG, "[loadDataFromNo()] cursor position : " + cursor.getPosition());
            String bank = cursor.getString(1);
            int bankIndex = cursor.getInt(2);
            String account = cursor.getString(3);
            String user = cursor.getString(4);
            String nickname = cursor.getString(5);

            if (listItem != null) {
                listItem.setNo(no);
                listItem.setBank(bank);
                listItem.setBankIndex(bankIndex);
                listItem.setAccount(account);
                listItem.setUser(user);
                listItem.setNickname(nickname);
            }
            Log.i(TAG, "[loadDataFromNo()] data : " + listItem.toString());
        }

        return listItem;
    }

        /**
         * 데이터베이스에 데이터 추가
         */
    public void addData(ListItem listItem) {
        Log.i(TAG, "[addData()]");

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        int no = 0;
        String name = "";
        String phone = "";
        byte[] image = null;
        String imageName = "";

        if (listItem != null) {
            no = listItem.getNo();
            name = listItem.getBank();
            phone = listItem.getAccount();
        }


//        String sqlInsert = "INSERT INTO CONTACT " +
//                "(NO, NAME, PHONE, IMAGE) VALUES " +
//                "(" + no + ", " +
//                "'" + name + "', " +
//                "'" + phone + "', " +
//                "'" + ImageUtil.getDrawableToByteArray(mContext.getResources().getDrawable(R.drawable.ic_launcher)  + "') ";
//
//        Log.i(TAG, "insert sql문 : " + sqlInsert);//
//        db.execSQL(sqlInsert);

        SQLiteStatement insertStmt = db.compileStatement(ContactDB.SQL_INSERT_ALL);

        insertStmt.bindLong(1, no);

        if(name != null)
            insertStmt.bindString(2, name);

        if(phone != null)
            insertStmt.bindString(3, phone);

        if(image != null)
            insertStmt.bindBlob(4, image);

        if(imageName != null)
            insertStmt.bindString(5, imageName);

        insertStmt.toString();
        insertStmt.execute();


    }

    /**
     * 데이터베이스에 이미지 저장
     */
    public void updateBlob() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String sql = "UPDATE CONTACT SET IMAGE =  ";
        SQLiteStatement insertStmt = db.compileStatement(sql);
        insertStmt.bindBlob(1, ImageUtil.getDrawableToByteArray(mContext.getResources().getDrawable(R.drawable.ic_launcher)));
        insertStmt.execute();
    }

    /**
     * 데이버베이스 데이터 변경
     */

    public void updateNamePhoneImageName(int no, String name, String phone, String imageName) {
        Log.i(TAG, "[updateData()]");
        String sqlUpdate = "UPDATE CONTACT " +
                "SET NAME='" + name + "', PHONE='" + phone + "', IMAGENAME='" + imageName + "'" +
                " WHERE NO=" + no;

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL(sqlUpdate);
    }


    /**
     * 데이버베이스 데이터 변경
     */

    public void updateData(String sqlUpdate) {
        Log.i(TAG, "[updateData()]");
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL(sqlUpdate);
    }

    /**
     * 데이버베이스 데이터 변경
     */

    public void updateData(ListItem listItem, int no) {
        Log.i(TAG, "[updateData()]");

        String sqlUpdate = "UPDATE CONTACT " +
                "SET BANK='" + listItem.getBank() + "', BANK_INDEX=" + listItem.getBankIndex() +", ACCOUNT='" + listItem.getAccount() + "', USER='" + listItem.getUser() +"', NICKNAME='" + listItem.getNickname() + "'" +
                " WHERE NO=" + no;

        Log.i(TAG, "[updateData()] update 명령문  : " + sqlUpdate);

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL(sqlUpdate);
    }

    /**
     * 데이터베이스에서 모든 데이터 삭제
     */
    public void deleteData() {
        Log.i(TAG, "[deleteData()]");

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL(ContactDB.SQL_DELETE_ALL);
    }

    /**
     * 데이터베이스에서 데이터 삭제
     */
    public void deleteDataOne(int no) {
        Log.i(TAG, "[deleteDataOne()]");
        String sqlDelete = ContactDB.SQL_DELETE_ONE + no;
        Log.i(TAG, "[deleteDataOne()] delete 명령문 : " + sqlDelete);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL(sqlDelete);
    }

    /**
     * 데이터베이스에서 테이블 삭제
     */
    public void deleteTable() {
        Log.i(TAG, "[deleteTable()]");

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL(ContactDB.SQL_DROP_TBL);

    }

}
