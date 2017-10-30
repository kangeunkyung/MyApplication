package atsoultions.eunkong.myapplication.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import atsoultions.eunkong.myapplication.R;

/**
 * Created by eunkong on 2017. 10. 30..
 */

public class CustomDialog extends Dialog {

    private TextView mTitleView;
    private TextView mContentView;
    private Button mLeftButton;
    private Button mRightButton;
    private String mTitle;
    private String mContent;

    private View.OnClickListener mLeftClickListener;
    private View.OnClickListener mRightClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 다이얼로그 외부 화면 흐리게 표현
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.activity_custom_dialog);

        mTitleView = findViewById(R.id.tv_title);
        mContentView = findViewById(R.id.tv_content);
        mLeftButton = findViewById(R.id.btn_left);
        mRightButton = findViewById(R.id.btn_right);

        if(TextUtils.isEmpty(mTitle) == false) {
            mTitleView.setVisibility(View.VISIBLE);
            mTitleView.setText(mTitle);
        } else {
            mTitleView.setVisibility(View.GONE);
        }
        if(TextUtils.isEmpty(mContent) == false) {
            mContentView.setVisibility(View.VISIBLE);
            mContentView.setText(mContent);
        } else {
            mContentView.setVisibility(View.GONE);
        }

        if(mLeftClickListener != null && mRightClickListener == null) {
            mLeftButton.setOnClickListener(mLeftClickListener);

        } else if(mLeftClickListener == null && mRightClickListener != null) {
            mRightButton.setOnClickListener(mRightClickListener);

        } else if(mLeftClickListener != null && mRightClickListener != null) {
            mLeftButton.setOnClickListener(mLeftClickListener);
            mRightButton.setOnClickListener(mRightClickListener);
        }
    }

    public CustomDialog(Context context, String title, View.OnClickListener btnLeftClickListener) {
        super(context);
        mTitle = title;
        mLeftClickListener = btnLeftClickListener;

    }

    public CustomDialog(Context context, String title, String content, View.OnClickListener btnLeftClickListener) {
        super(context);
        mTitle = title;
        mContent = content;
        mLeftClickListener = btnLeftClickListener;

    }

    public CustomDialog(Context context, String title, View.OnClickListener btnLeftClickListener, View.OnClickListener btnRightClickListener) {
        super(context);
        mTitle = title;
        mLeftClickListener = btnLeftClickListener;
        mRightClickListener = btnRightClickListener;
    }

    public CustomDialog(Context context, String title, String content,View.OnClickListener btnLeftClickListener, View.OnClickListener btnRightClickListener) {
        super(context);
        mTitle = title;
        mContent = content;
        mLeftClickListener = btnLeftClickListener;
        mRightClickListener = btnRightClickListener;
    }

}
