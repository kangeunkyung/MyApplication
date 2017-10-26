package atsoultions.eunkong.myapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

import atsoultions.eunkong.myapplication.ListItem;
import atsoultions.eunkong.myapplication.ListViewAdapter;
import atsoultions.eunkong.myapplication.R;
import atsoultions.eunkong.myapplication.database.ContactDbManager;
import atsoultions.eunkong.myapplication.util.TextUtil;

/**
 * 참고 사이트 http://blog.naver.com/gyeom__/220815406153
 */
public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private ListViewAdapter mListViewAdapter;
    private ListView mListView;
    private Button mBtnReset;
    private ImageView mBtnAdd;

    private ArrayList<ListItem> mItemArrayList = new ArrayList<>();
    private ContactDbManager mSQLiteDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnAdd = findViewById(R.id.btn_add);
        mBtnReset = findViewById(R.id.btn_reset);
        mListView = findViewById(R.id.listview);
        mListViewAdapter = new ListViewAdapter(MainActivity.this);

        // 데이터베이스 생성
        mSQLiteDB = new ContactDbManager(this);

        // 테이블 삭제
//        mSQLiteDB.deleteTable();

        // 테이블 생성
        mSQLiteDB.initTable();

        // 데이터 생성
        mItemArrayList = mSQLiteDB.loadData();
        mListViewAdapter.setData(mItemArrayList);
        mListView.setAdapter(mListViewAdapter);

        // 데이터 초기화
        mBtnReset.setOnClickListener(onClickListener);

        // 데이터 추가
        mBtnAdd.setOnClickListener(onClickListener);


    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume()");

        mItemArrayList = mSQLiteDB.loadData();
        mListViewAdapter.setData(mItemArrayList);
        mListViewAdapter.notifyDataSetChanged();

    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch(view.getId()) {
                // 추가
                case R.id.btn_add:
                    Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
                    intent.putExtra("TYPE", "TYPE_ADD");
                    startActivity(intent);
                    break;

                // 초기화 -> TODO 추후 삭제 필요
                case R.id.btn_reset:
                    mSQLiteDB.deleteData();
                    mItemArrayList = mSQLiteDB.loadData();
                    mListViewAdapter.setData(mItemArrayList);
                    mListViewAdapter.notifyDataSetChanged();
                    break;

                // 복사
                case R.id.btn_copy:
                    TextUtil.copyText(MainActivity.this, "");

            }
        }
    };


}
