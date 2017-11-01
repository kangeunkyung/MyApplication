package atsoultions.eunkong.myapplication.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import atsoultions.eunkong.myapplication.ListItem;
import atsoultions.eunkong.myapplication.R;
import atsoultions.eunkong.myapplication.database.ContactDB;
import atsoultions.eunkong.myapplication.database.ContactDbManager;
import atsoultions.eunkong.myapplication.dialog.CustomDialog;
import atsoultions.eunkong.myapplication.util.TextUtil;
import atsoultions.eunkong.myapplication.widget.WidgetProvider;

import static atsoultions.eunkong.myapplication.constant.Define.FROM_WHERE;
import static atsoultions.eunkong.myapplication.constant.Define.FROM_WIDGET_PROVIDER;

/**
 * Created by eunkong on 2017. 10. 18..
 */

public class UpdateActivity extends Activity {
    private final static String TAG = UpdateActivity.class.getSimpleName();

    private Context mContext;

    private LinearLayout mLayoutOptionalInput;
    private TextView mTvTitle;
    private Spinner mSpinnerBank;
    private EditText mEtAccount, mEtUser, mEtNickname;
    private Button mBtnSave;
    private ImageView mIvDelete, mIvPrev;

    private String mSelectedBank;
    private int mSelectedPosition;


    // 데이터 추가인지, 수정인지 구분하는 플래그
    private boolean mIsAdd;
    private boolean mIsInput;

    int no;

    private ContactDbManager mSQLiteDB;

    private CustomDialog mCustomDialog;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        mContext = UpdateActivity.this;

        mSQLiteDB = new ContactDbManager(UpdateActivity.this);
        mSQLiteDB.initTable();

        Intent intent = getIntent();
        no = intent.getIntExtra(ContactDB._ID, 0);

        initView();

        if("TYPE_UPDATE".equalsIgnoreCase(intent.getStringExtra("TYPE"))){
            mIsAdd = false;
            mIvDelete.setVisibility(View.VISIBLE);
            mTvTitle.setText("계좌수정");
            ListItem listItem = mSQLiteDB.loadDataFromNo(no);
            setData(listItem);
        } else {
            mIsAdd = true;
            mIvDelete.setVisibility(View.INVISIBLE);
            mTvTitle.setText("계좌등록");

        }

        Log.i(TAG, " bank: " + mSpinnerBank.getSelectedItemPosition() +  ", account : " + mEtAccount.getText().toString());

    }

    private void initView() {

        mLayoutOptionalInput = findViewById(R.id.layout_optional_input);
        mTvTitle             = findViewById(R.id.tv_title);
        mSpinnerBank         = findViewById(R.id.spinner_bank);
        mEtAccount           = findViewById(R.id.et_account);
        mEtUser              = findViewById(R.id.et_user);
        mEtNickname          = findViewById(R.id.et_nickname);
        mBtnSave             = findViewById(R.id.btn_save);
        mIvPrev              = findViewById(R.id.iv_prev);
        mIvDelete            = findViewById(R.id.iv_delete);

        mBtnSave.setOnClickListener(onClickListener);
        mIvDelete.setOnClickListener(onClickListener);
        mIvPrev.setOnClickListener(onClickListener);

        // 은행선택
        mSpinnerBank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                mSelectedPosition = position;
                mSelectedBank = parent.getItemAtPosition(mSelectedPosition).toString();

                if(mSelectedPosition == 0) {
                    mLayoutOptionalInput.setVisibility(View.GONE);
                    mEtNickname.setText("");
                } else {
                    mLayoutOptionalInput.setVisibility(View.VISIBLE);
                    mEtNickname.setText(mSelectedBank + " 계좌");
                }

                if(mSelectedPosition != 0 && TextUtils.isEmpty(mEtAccount.getText().toString()) == false)
                    mBtnSave.setBackgroundResource(R.color.colorPrimary);
                else
                    mBtnSave.setBackgroundResource(R.color.colorLightGrey);

                Log.i(TAG, "selected position " + mSelectedPosition + ", item : " + mSelectedBank);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // 계좌번호
        mEtAccount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count){
                Log.i(TAG, "[onTextChanged()] count : " + count + ", character : " + charSequence.toString());
                if (count > 0 && mSelectedPosition != 0)
                    mBtnSave.setBackgroundResource(R.color.colorPrimary);
                else
                    mBtnSave.setBackgroundResource(R.color.colorLightGrey);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });


        // 은행 및 계좌번호 입력 유무에 따른 버튼 색상
        if(TextUtils.isEmpty(mEtAccount.getText().toString()) == false && mSpinnerBank.getSelectedItemPosition() != 0) {
            mBtnSave.setBackgroundResource(R.color.colorPrimary);
        } else {
            mBtnSave.setBackgroundResource(R.color.colorLightGrey);
        }

    }

    /**
     * 데이터 세팅
     */
    private void setData(ListItem listItem) {

        // 첫번째 포지션은 '은행선택' 데이터
        mSpinnerBank.setSelection(listItem.getBankIndex());
        mEtAccount.setText(listItem.getAccount());

        if(TextUtils.isEmpty(listItem.getUser()) == false) {
            mEtUser.setText(listItem.getUser());
        }

        if(TextUtils.isEmpty(listItem.getNickname()) == false) {
            mEtNickname.setText(listItem.getNickname());
        }
    }


    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {

                case R.id.iv_prev:
                    onBackPressed();
                    break;

                case R.id.btn_save:

                    Log.i(TAG, "mSelectedPosition : "+ mSelectedPosition);
                    // TODO 추후 토스트 삭제
                    if(mSelectedPosition == 0) {
                        Toast.makeText(mContext, "은행을 입력해주세요", Toast.LENGTH_SHORT).show();
                        return;
                    } else if(TextUtils.isEmpty(mEtAccount.getText().toString())) {
                        Toast.makeText(mContext, "계좌번호를 선택해 주세요", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if(mIsAdd) {
                        // 데이터 추가
                        ListItem listItem = new ListItem(mSelectedBank, mSelectedPosition, mEtAccount.getText().toString(), mEtUser.getText().toString(), mEtNickname.getText().toString());
                        mSQLiteDB.addDataOptional(listItem);

                        // 데이터 수정
                    } else {

                        ListItem listItem = new ListItem(mSelectedBank, mSelectedPosition, mEtAccount.getText().toString(), mEtUser.getText().toString(), mEtNickname.getText().toString());
                        mSQLiteDB.updateData(listItem, no);
                    }

                    updateWidget();

                    finish();

                    break;

                case R.id.iv_delete:

                    String content = mSelectedBank + " 계좌 정보를\n삭제하시겠습니까?";
                    mCustomDialog = new CustomDialog(UpdateActivity.this, content, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mSQLiteDB.deleteDataOne(no);
                            updateWidget();
                            mCustomDialog.dismiss();
                            finish();
                        }
                    }, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mCustomDialog.dismiss();
                        }
                    });

                    mCustomDialog.show();

                    break;

            }
        }
    };

    /**
     * 위젯 데이터 업데이트
     */
    private void updateWidget() {
        Log.i(TAG, "[updateWidget()]");

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(mContext);
        ComponentName thisWidget = new ComponentName(mContext, WidgetProvider.class);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.listview_widget);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        Log.i(TAG, "[onActivityResult()] requestCode : " + requestCode + ", intent : " + intent);

    }
}
