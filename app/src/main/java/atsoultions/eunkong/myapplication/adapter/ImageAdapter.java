package atsoultions.eunkong.myapplication.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import atsoultions.eunkong.myapplication.R;


/**
 * Created by eunkong on 2017. 10. 31..
 */

public class ImageAdapter extends PagerAdapter {
    private static final String TAG = ImageAdapter.class.getSimpleName();

    private LayoutInflater mInflater;
    private Context mContext;
    private Bitmap mImageBitmap;
    private BitmapFactory.Options mOptions;

    private final int[] images = new int[]{R.drawable.tutorial_01, R.drawable.tutorial_02, R.drawable.tutorial_03};

    public ImageAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mOptions = new BitmapFactory.Options();
    }
    @Override
    public int getCount() {
        return images.length;
    }

    /**
     * instantiateItem()에서 생성한 객체를 사용할지 여부 판단
     * @param view
     * @param object
     * @return
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (object);
    }

    /**
     * ViewPager에서 사용할 View를 생성하고 등록
     * @param container
     * @param position
     * @return
     */
    @Override
    public View instantiateItem(ViewGroup container, int position) {
        View view = mInflater.inflate(R.layout.viewpager_layout, null);

        ImageView ivImage = view.findViewById(R.id.iv_viewpager_image);
        ivImage.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        mOptions.inSampleSize = 4;
        mImageBitmap = BitmapFactory.decodeResource(mContext.getResources(), images[position], mOptions);
        ivImage.setImageBitmap(mImageBitmap);

        container.addView(view, 0);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }




}
