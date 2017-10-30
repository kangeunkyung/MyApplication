package atsoultions.eunkong.myapplication.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import atsoultions.eunkong.myapplication.ListItem;

/**
 * Created by eunkong on 2017. 10. 25..
 */

public class TextUtil {

    private final static String TAG = ImageUtil.class.getSimpleName();

    private static Context mContext;

    public TextUtil(Context context) {
        mContext = context;
    }

    /**
     * 클립보드에 텍스트 저장
     * @param context
     * @param listItem
     */
    static public void copyText(Context context, ListItem listItem) {

        // ex) KB국민은행 91234567890 (예금주:강은경)
        final StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(listItem.getBank() + " ");
        stringBuffer.append(listItem.getAccount() + " ");
        if(TextUtils.isEmpty(listItem.getUser()) == false)
            stringBuffer.append("(예금주 : " + listItem.getUser() + ")" + " ");
//        if(TextUtils.isEmpty(listItem.getNickname()) == false)
//            stringBuffer.append(listItem.getNickname());

        String text = stringBuffer.toString();

        if (TextUtils.isEmpty(text) == false) {
            Log.i(TAG, "[copyText()] listItem : " + text);
            ClipData clip = ClipData.newPlainText("text", text);
            ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            cm.setPrimaryClip(clip);
        }
    }

    /**
     * 클립보드에 텍스트 저장
     * @param context
     * @param text
     */
    static public void copyText(Context context, String text) {

        if (TextUtils.isEmpty(text) == false) {
            Log.i(TAG, "[copyText()] text : " + text + "\n클립보드에 저장됨");
            ClipData clip = ClipData.newPlainText("text", text);
            ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            cm.setPrimaryClip(clip);
        }
    }

    static public void setTextDefault(String bank, String account) {
    }


}
