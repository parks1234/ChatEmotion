package com.example.apple.chatemotion.emoji.emotin.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.example.apple.chatemotion.R;
import com.example.apple.chatemotion.emoji.emotin.adapter.EmotionPagerAdapter;
import com.example.apple.chatemotion.emoji.emotin.adapter.MenuGridViewAdapter;
import com.example.apple.chatemotion.emoji.emotin.utils.DisplayUtils;
import com.example.apple.chatemotion.emoji.emotin.view.EmojiIndicatorView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by
 * Time  17/12/5 下午4:32
 * Email
 * Description:可替换的模板表情，gridview实现
 */
public class MenuComplateFragment extends BaseFragment {
    private EmotionPagerAdapter emotionPagerGvAdapter;
    private ViewPager vp_complate_emotion_layout;
    private EmojiIndicatorView ll_point_group;//表情面板对应的点列表
    private int emotion_map_type;
    private ArrayList<Integer> lists = new ArrayList<>();


 


    /**
     * 创建与Fragment对象关联的View视图时调用
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_complate_emotion, container, false);
        lists=args.getIntegerArrayList("list");
        initView(rootView);
        initListener();
        return rootView;
    }

    /**
     * 初始化view控件
     */
    protected void initView(View rootView) {
        vp_complate_emotion_layout = (ViewPager) rootView.findViewById(R.id.vp_complate_emotion_layout);
        ll_point_group = (EmojiIndicatorView) rootView.findViewById(R.id.ll_point_group);
        //获取map的类型
        emotion_map_type = args.getInt(FragmentFactory.EMOTION_MAP_TYPE);
        initEmotion();
    }

    /**
     * 初始化监听器
     */
    protected void initListener() {

        vp_complate_emotion_layout.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            int oldPagerPos = 0;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ll_point_group.playByStartPointToNext(oldPagerPos, position);
                oldPagerPos = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 初始化面板
     * 处理即可。
     */
    private void initEmotion() {
        // 获取屏幕宽度
        int screenWidth = DisplayUtils.getScreenWidthPixels(getActivity());
        // item的间距
        int spacing = DisplayUtils.dp2px(getActivity(), 4);
        // 动态计算item的宽度和高度
        int itemWidth = (screenWidth - spacing * 5) /4;
        //动态计算gridview的总高度
        int gvHeight = itemWidth *2 + spacing * 3;

        List<GridView> emotionViews = new ArrayList<>();
        List<Integer> emotionNames = new ArrayList<>();
     
        // 遍历所有的表情的key
        for (int emojiName : lists) {
            emotionNames.add(emojiName);
            // 每20个表情作为一组,同时添加到ViewPager对应的view集合中
            if (emotionNames.size() == 8) {
                GridView gv = createEmotionGridView(emotionNames, screenWidth, spacing, gvHeight);
                emotionViews.add(gv);
                // 添加完一组表情,重新创建一个表情名字集合
                emotionNames = new ArrayList<>();
            }
        }

        // 判断最后是否有不足20个表情的剩余情况
        if (emotionNames.size() > 0) {
            GridView gv = createEmotionGridView(emotionNames, screenWidth, spacing, gvHeight);
            emotionViews.add(gv);
        }

        //初始化指示器
        ll_point_group.initIndicator(emotionViews.size());
        if (emotionViews.size()<=1)
            ll_point_group.setVisibility(View.INVISIBLE);
        // 将多个GridView添加显示到ViewPager中
        emotionPagerGvAdapter = new EmotionPagerAdapter(emotionViews);
        vp_complate_emotion_layout.setAdapter(emotionPagerGvAdapter);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(screenWidth, gvHeight);
        vp_complate_emotion_layout.setLayoutParams(params);


    }

    /**
     * 创建显示表情的GridView
     */
    private GridView createEmotionGridView(final List<Integer> emotionNames, int gvWidth, int padding, int gvHeight) {
        // 创建GridView
        GridView gv = new GridView(getActivity());
        //设置点击背景透明
        gv.setSelector(android.R.color.transparent);
        
        gv.setNumColumns(4);
        gv.setPadding(2 * padding, padding, 2 * padding, padding);
        gv.setHorizontalSpacing(padding);
        gv.setVerticalSpacing(padding * 2);
        //设置GridView的宽高
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(gvWidth, gvHeight);
        gv.setLayoutParams(params);
        // 给GridView设置表情图片
        MenuGridViewAdapter adapter = new MenuGridViewAdapter(getActivity(), emotionNames);
        gv.setAdapter(adapter);
        //设置全局点击事件
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (null!=onitemclick)
                    onitemclick.Onclick(emotionNames.get(position));
            }
        });
        return gv;
    }
    Onitemclick onitemclick;

    public Onitemclick getOnitemclick() {
        return onitemclick;
    }

    public void setOnitemclick(Onitemclick onitemclick) {
        this.onitemclick = onitemclick;
    }

    interface Onitemclick{
        void  Onclick(int index);
    }
}
