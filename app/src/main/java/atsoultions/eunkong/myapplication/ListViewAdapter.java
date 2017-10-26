package atsoultions.eunkong.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import atsoultions.eunkong.myapplication.activity.UpdateActivity;
import atsoultions.eunkong.myapplication.database.ContactDB;
import atsoultions.eunkong.myapplication.util.TextUtil;


/**
 * Created by eunkong on 2017. 10. 18..
 */

public class ListViewAdapter extends BaseAdapter {
    private final static String TAG = ListViewAdapter.class.getSimpleName();

    private Context mContext;
    private List<ListItem> mList = new ArrayList<>();

    public ListViewAdapter(Context context) {
        mContext = context;
    }

    public void setData(ArrayList<ListItem> listItems) {
        if(mList != null) {
            mList.clear();
            mList.addAll(listItems);
        }
    }

    @Override
    public int getCount() {
        return (mList == null) ? 0 : mList.size();
    }

    @Override
    public ListItem getItem(int i) {
        return (mList == null) ? null : mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final ViewHolder viewHolder;

        if(view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.listitem, null, true);

            viewHolder.tvBank = view.findViewById(R.id.tv_bank);
            viewHolder.tvAccount = view.findViewById(R.id.tv_account);
            viewHolder.tvUser= view.findViewById(R.id.tv_user);
            viewHolder.tvNickname = view.findViewById(R.id.tv_nickname);
            viewHolder.ivModify = view.findViewById(R.id.iv_modify);
            viewHolder.btnCopy = view.findViewById(R.id.btn_copy);

            view.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) view.getTag();
        }


        if(mList != null) {

            // 은행
            viewHolder.tvBank.setText(mList.get(i).getBank());
            // 계좌번호
            viewHolder.tvAccount.setText(mList.get(i).getAccount());

            // 예금주
            if(TextUtils.isEmpty(mList.get(i).getUser())) {
                viewHolder.tvUser.setVisibility(View.GONE);
            } else {
                viewHolder.tvUser.setVisibility(View.VISIBLE);
                viewHolder.tvUser.setText("(예금주 : " + mList.get(i).getUser() + ")");
            }

            // 별명
            if(TextUtils.isEmpty(mList.get(i).getNickname())) {
                viewHolder.tvNickname.setVisibility(View.GONE);
            } else {
                viewHolder.tvNickname.setVisibility(View.VISIBLE);
                viewHolder.tvNickname.setText(mList.get(i).getNickname());
            }



            final ListItem listItem = mList.get(i);
            // 복사
            viewHolder.btnCopy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext, "복사되었습니다.", Toast.LENGTH_SHORT).show();
                    TextUtil.copyText(mContext, listItem);
                }
            });

            // 편집
            viewHolder.ivModify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, UpdateActivity.class);
                    intent.putExtra("TYPE", "TYPE_UPDATE");
                    intent.putExtra(ContactDB._ID, mList.get(i).getNo());
                    mContext.startActivity(intent);
                }
            });

            Log.i(TAG, "[getView()] " + mList.get(i).toString());
        }
        return view;
    }

    class ViewHolder {
        TextView tvBank;
        TextView tvUser;
        TextView tvAccount;
        TextView tvNickname;
        Button btnCopy;
        ImageView ivModify;
    }
}
