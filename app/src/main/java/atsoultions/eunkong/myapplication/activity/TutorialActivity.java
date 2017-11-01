package atsoultions.eunkong.myapplication.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import atsoultions.eunkong.myapplication.R;
import atsoultions.eunkong.myapplication.adapter.ImageAdapter;
import atsoultions.eunkong.myapplication.util.PreferenceUtil;

/**
 * Created by eunkong on 2017. 10. 30..
 */

public class TutorialActivity extends Activity {

    private ViewPager mViewPager;
    private ImageView mIvIndicator1, mIvIndicator2, mIvIndicator3;
    private Button mBtnRegister;
    private ImageView mIvDot;
    private TextView tvTextview;

    private ImageAdapter imageAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        PreferenceUtil.getInstance(this).setIsFirstLaunch(true);

        tvTextview = findViewById(R.id.tv_slide_num);
        mViewPager = findViewById(R.id.viewpager);
        mIvIndicator1 = findViewById(R.id.iv_indicator1);
        mIvIndicator2 = findViewById(R.id.iv_indicator2);
        mIvIndicator3 = findViewById(R.id.iv_indicator3);
        mBtnRegister = findViewById(R.id.btn_register_account);


        // 계좌등록 버튼
        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TutorialActivity.this, UpdateActivity.class);
                startActivity(intent);
                finish();
            }
        });


        imageAdapter = new ImageAdapter(this);

        mViewPager.setAdapter(imageAdapter);
        mViewPager.setCurrentItem(0);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        mIvIndicator1.setImageResource(R.drawable.icon_slide_on);
                        mIvIndicator2.setImageResource(R.drawable.icon_slide_off);
                        mIvIndicator3.setImageResource(R.drawable.icon_slide_off);
                        tvTextview.setText(position + "번째 슬라이드");
                        break;
                    case 1:
                        mIvIndicator1.setImageResource(R.drawable.icon_slide_off);
                        mIvIndicator2.setImageResource(R.drawable.icon_slide_on);
                        mIvIndicator3.setImageResource(R.drawable.icon_slide_off);
                        tvTextview.setText(position + "번째 슬라이드");
                        break;
                    case 2:
                        mIvIndicator1.setImageResource(R.drawable.icon_slide_off);
                        mIvIndicator2.setImageResource(R.drawable.icon_slide_off);
                        mIvIndicator3.setImageResource(R.drawable.icon_slide_on);
                        tvTextview.setText(position + "번째 슬라이드");
                        break;

                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

}
