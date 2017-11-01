package atsoultions.eunkong.myapplication.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by eunkong on 2017. 10. 30..
 */

public class PreferenceUtil {

    private static PreferenceUtil instance;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Context mContext;

    private static final String PREF_KEY = "PREF_KEY";

    private static final String PREF_IS_FIRST_LAUNCH = "PREF_KEY_FIRST_LAUNCH";

    public PreferenceUtil(Context context) {
        mContext = context;
        preferences = mContext.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public static PreferenceUtil getInstance(Context context) {
        if(instance == null)
            instance = new PreferenceUtil(context);
        return instance;
    }

    public void setIsFirstLaunch(boolean isFirst) {
        editor.putBoolean(PREF_IS_FIRST_LAUNCH, isFirst);
        editor.commit();
    }

    public boolean getIsFirstLaunch() {
        return preferences.getBoolean(PREF_IS_FIRST_LAUNCH, false);
    }

}
