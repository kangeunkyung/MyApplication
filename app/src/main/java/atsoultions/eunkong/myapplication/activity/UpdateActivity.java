package atsoultions.eunkong.myapplication.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
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
import atsoultions.eunkong.myapplication.util.TextUtil;
import atsoultions.eunkong.myapplication.widget.WidgetProvider;
import atsoultions.eunkong.myapplication.widget.WidgetService;

import static atsoultions.eunkong.myapplication.widget.WidgetProvider.APP_WIDGET_CLICK;

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

        if("TYPE_ADD".equalsIgnoreCase(intent.getStringExtra("TYPE"))) {
            mIsAdd = true;
            mIvDelete.setVisibility(View.INVISIBLE);
            mTvTitle.setText("계좌등록");

        } else {
            mIsAdd = false;
            mIvDelete.setVisibility(View.VISIBLE);
            mTvTitle.setText("계좌수정");
            ListItem listItem = mSQLiteDB.loadDataFromNo(no);
            setData(listItem);
        }

        mEtAccount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count){
                Log.i(TAG, "[onTextChanged()] count : " + count + ", character : " + charSequence.toString());
                if (count > 0) {
                    mIsInput = true;
                }
                if (count > 0 && mSelectedPosition != 0)
                    mBtnSave.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                else
                    mBtnSave.setBackgroundColor(getResources().getColor(R.color.colorGrey));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        Log.i(TAG, " bank: " + mSpinnerBank.getSelectedItemPosition() +  ", account : " + mEtAccount.getText().toString());
        if(TextUtils.isEmpty(mEtAccount.getText().toString()) == false && mSpinnerBank.getSelectedItemPosition() != 0) {
           Log.i(TAG, "모두 입력된 상태");
           mBtnSave.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        } else {
            Log.i(TAG, "모두 입력되지 않은 상태");
            mBtnSave.setBackgroundColor(getResources().getColor(R.color.colorGrey));
        }

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

                if(position != 0) {
                    mLayoutOptionalInput.setVisibility(View.VISIBLE);
                }

                if(mSelectedPosition !=0 && mIsInput == true)
                    mBtnSave.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                else
                    mBtnSave.setBackgroundColor(getResources().getColor(R.color.colorGrey));
                Log.i(TAG, "selected position " + mSelectedPosition + ", item : " + parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });




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
                    finish();
                    break;

                case R.id.btn_save:

                    Log.i(TAG, "mSelectedPosition : "+ mSelectedPosition);
                    // TODO 은행 선택 및 계좌번호 입력 안했을 경우 알림 팝업 뜨도록 설정
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
                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);

                    alertDialogBuilder.setTitle("알림")
                            .setMessage("카카오뱅크 계좌 정보를\n삭제하시겠습니까?")
                            .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            })
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    mSQLiteDB.deleteDataOne(no);
                                    updateWidget();
                                    finish();
                                }
                            });

                    alertDialogBuilder.show();


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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.i(TAG, "[onActivityResult()] requestCode : " + requestCode + ", data : " + data);

    }
}
