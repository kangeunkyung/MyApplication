package atsoultions.eunkong.myapplication.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import atsoultions.eunkong.myapplication.ListItem;
import atsoultions.eunkong.myapplication.ListViewAdapter;
import atsoultions.eunkong.myapplication.R;
import atsoultions.eunkong.myapplication.database.ContactDbManager;
import atsoultions.eunkong.myapplication.util.PreferenceUtil;
import atsoultions.eunkong.myapplication.util.TextUtil;

import static atsoultions.eunkong.myapplication.constant.Define.FROM_WHERE;
import static atsoultions.eunkong.myapplication.constant.Define.FROM_WIDGET_PROVIDER;
import static atsoultions.eunkong.myapplication.constant.Define.TYPE;
import static atsoultions.eunkong.myapplication.constant.Define.TYPE_ADD;
import static atsoultions.eunkong.myapplication.widget.WidgetProvider.APP_WIDGET_ADD;

/**
 * 참고 사이트 http://blog.naver.com/gyeom__/220815406153
 */
public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private Context mContext;
    private ListViewAdapter mListViewAdapter;
    private LinearLayout mllEmptyLayout, mllListLayout;
    private ListView mListView;
    private Button mBtnReset;
    private ImageView mIvInfo, mIvAdd;

    private ArrayList<ListItem> mItemArrayList = new ArrayList<>();
    private ContactDbManager mSQLiteDB;


    public MainActivity() {

    }
    public MainActivity(Context context) {
        Log.i(TAG, "MainActivity 생성자");
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "[onCreate()]");

        initView();

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

        updateView();

        if(PreferenceUtil.getInstance(this).getIsFirstLaunch() == false) {
            startActivity(new Intent(MainActivity.this, TutorialActivity.class));
        }

        String action= getIntent().getAction();
        Log.i(TAG, "[onCreate()] action : " + action);
        if(TextUtils.isEmpty(action) == false && action.equals(APP_WIDGET_ADD)) {
            startActivity(new Intent(MainActivity.this, UpdateActivity.class));
        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        String action= intent.getAction();

        if(TextUtils.isEmpty(action) == false && action.equals(APP_WIDGET_ADD)) {
            startActivity(new Intent(MainActivity.this, UpdateActivity.class));
        }

        Log.i(TAG, "[onNewIntent()] action : " + action);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume()");

        mItemArrayList = mSQLiteDB.loadData();
        mListViewAdapter.setData(mItemArrayList);
        mListViewAdapter.notifyDataSetChanged();

        updateView();

    }

    private void initView() {

        mllEmptyLayout = findViewById(R.id.ll_empty_layout);
        mllListLayout = findViewById(R.id.ll_list_layout);
        mIvInfo = findViewById(R.id.iv_info);
        mIvAdd = findViewById(R.id.iv_add);
        mBtnReset = findViewById(R.id.btn_reset);
        mListView = findViewById(R.id.listview);
        mListViewAdapter = new ListViewAdapter(MainActivity.this);

        // 데이터 초기화 -> TODO 추후 삭제 필요
        mBtnReset.setOnClickListener(onClickListener);
        // 계좌 등록
        mIvAdd.setOnClickListener(onClickListener);
        // 서비스 안내
        mIvInfo.setOnClickListener(onClickListener);

    }

    private void updateView() {

        if(mItemArrayList.isEmpty()) {
            mllEmptyLayout.setVisibility(View.VISIBLE);
            mllListLayout.setVisibility(View.GONE);
        } else {
            mllEmptyLayout.setVisibility(View.GONE);
            mllListLayout.setVisibility(View.VISIBLE);
        }
    }


    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch(view.getId()) {
                // 안내화면
                case R.id.iv_info:
                    startActivity(new Intent(MainActivity.this, InformationActivity.class));
                    break;

                // 추가
                case R.id.iv_add:
                    Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
                    intent.putExtra(TYPE, TYPE_ADD);
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


    public View getFindViewById(int viewId) {
        Log.d(TAG, "[getFindViewById()] viewId : " + viewId);
        return ((MainActivity) mContext).findViewById(viewId);
    }

}
