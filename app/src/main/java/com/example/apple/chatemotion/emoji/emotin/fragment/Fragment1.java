package com.example.apple.chatemotion.emoji.emotin.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.apple.chatemotion.R;


/**
 * Created by 
 * Time  16/1/7 上午10:26
 * Email 
 * Description:
 */
public class Fragment1 extends BaseFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView= LayoutInflater.from(getActivity()).inflate(R.layout.fragment1,null);
        TextView tv= (TextView) rootView.findViewById(R.id.tv);

        tv.setText(args.getString("Interge"));
        return rootView ;
    }
}
