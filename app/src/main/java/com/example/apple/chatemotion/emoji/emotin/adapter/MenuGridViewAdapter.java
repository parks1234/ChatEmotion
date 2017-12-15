package com.example.apple.chatemotion.emoji.emotin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.apple.chatemotion.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by
 * Time  17/12/7 下午4:46
 * Email
 * Description:
 */
public class MenuGridViewAdapter extends BaseAdapter {

    private Context context;
    private List<Integer> emotionNames;


    public MenuGridViewAdapter(Context context, List<Integer> emotionNames) {
        this.context = context;
        this.emotionNames = emotionNames;

    }

    @Override
    public int getCount() {
        // +1 最后一个为删除按钮
        return emotionNames.size();
    }

    @Override
    public Integer getItem(int position) {
        return emotionNames.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (null == convertView) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_of_menugrid, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) convertView.getTag();
        switch (emotionNames.get(position)) {
            case 1:
                viewHolder.img.setImageDrawable(context.getResources().getDrawable(R.mipmap.sharemore_pic3x));
                viewHolder.text.setText("照片");
                break;
            case 2:
                viewHolder.img.setImageDrawable(context.getResources().getDrawable(R.mipmap.sharemore_videovoip3x));
                viewHolder.text.setText("视频");
                break;
            case 3:
                viewHolder.img.setImageDrawable(context.getResources().getDrawable(R.mipmap.sharemore_voiceinput3x));
                viewHolder.text.setText("语音");
                break;
            case 4:
                viewHolder.img.setImageDrawable(context.getResources().getDrawable(R.mipmap.sharemore_ajq));
                viewHolder.text.setText("红包");
                break;
            case 5:
                viewHolder.img.setImageDrawable(context.getResources().getDrawable(R.mipmap.sharemore_location3x));
                viewHolder.text.setText("位置");
                break;
            default:
                viewHolder.img.setImageDrawable(null);
                viewHolder.text.setText("");
                break;
        }


        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.img)
        ImageView img;
        @BindView(R.id.text)
        TextView text;
        @BindView(R.id.ll_img)
        LinearLayout llImg;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
