package atsoultions.eunkong.myapplication.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;

import atsoultions.eunkong.myapplication.R;

/**
 * Created by eunkong on 2017. 10. 31..
 */

public class InformationActivity extends Activity {

    private static final String TAG = InformationActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_info);

        findViewById(R.id.iv_prev).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
