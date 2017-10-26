package atsoultions.eunkong.myapplication.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;

import atsoultions.eunkong.myapplication.R;

/**
 * Created by eunkong on 2017. 10. 19..
 */

public class ImageUtil {
    private final static String TAG = ImageUtil.class.getSimpleName();

    private static Context mContext;

    public ImageUtil(Context context) {
        mContext = context;
    }

    /**
     * drawable 이미지를 ByteArray(=blob) 타입으로 변환
     * @param drawable
     * @return
     */
    static public byte[] getDrawableToByteArray(Drawable drawable) {
        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();

        // [compress 포맷 옵션]
        // Bitmap.CompressFormat.PNG은 배경이 투명으로
        // Bitmap.CompressFormat.JPG은 배경이 검정색으로 저장됨
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] data = stream.toByteArray();

        return data;
    }

    /**
     * drawable 이미지를 Bitmap 타입으로 변환
     * @param drawable
     * @return
     */
    static public Bitmap getDrawableToBitmap(Context context, Drawable drawable) {

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);

        return bitmap;
    }

    /**
     * ByteArray를 Bitmap으로 변환
     * @param context
     * @param image
     * @return
     */
    static public Bitmap getByteArrayToBitmap(Context context, byte[] image) {

        // image가 없으면 기본 이미지로 설정
        if(image == null)
            image = getDrawableToByteArray(context.getResources().getDrawable(R.drawable.user));

        return BitmapFactory.decodeByteArray(image, 0, image.length);

    }

    /**
     * Bitmap을 ByteArray로 변환
     * @param bitmap
     * @return
     */
    static public byte[] getBitmapToByteArray(Bitmap bitmap) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        return byteArray;
    }


    /**
     * 기본 이미지 파일명 생성
     */
    static public String getDefaultImgName() {
        String imageFileName = System.currentTimeMillis() + ".jpg";

        String directory = Environment.getExternalStorageDirectory().getAbsolutePath() + "/eunkong";
        File file = new File(directory);

        // 폴더가 존재하지 않으면 생성
        if(!file.exists()) {
            file.mkdirs();
        }

        file = new File(directory + "/" + imageFileName);
        Log.i(TAG, "[getDefaultImgName()] image : "  + file.getAbsolutePath());
        return file.getAbsolutePath();

    }


}
