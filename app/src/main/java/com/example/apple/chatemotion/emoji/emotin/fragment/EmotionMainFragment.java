package com.example.apple.chatemotion.emoji.emotin.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.apple.chatemotion.R;
import com.example.apple.chatemotion.emoji.emotin.adapter.HorizontalRecyclerviewAdapter;
import com.example.apple.chatemotion.emoji.emotin.adapter.NoHorizontalScrollerVPAdapter;
import com.example.apple.chatemotion.emoji.emotin.model.ImageModel;
import com.example.apple.chatemotion.emoji.emotin.utils.EmotionUtils;
import com.example.apple.chatemotion.emoji.emotin.utils.GlobalOnItemClickManagerUtils;
import com.example.apple.chatemotion.emoji.emotin.utils.SharedPreferencedUtils;
import com.example.apple.chatemotion.emoji.emotin.view.EmotionKeyboard;
import com.example.apple.chatemotion.emoji.emotin.view.NoHorizontalScrollerViewPager;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by
 * Time  16/1/6 下午5:26
 * Email
 * Description:表情主界面
 */
public class EmotionMainFragment extends BaseFragment implements View.OnClickListener {

    //是否绑定当前Bar的编辑框的flag
    public static final String BIND_TO_EDITTEXT = "bind_to_edittext";
    //是否隐藏bar上的编辑框和发生按钮
    public static final String HIDE_BAR_EDITTEXT_AND_BTN = "hide bar's editText and btn";
    public static final String HIDE_img = "hideimg";
    public static final String HIDE_VIDEO = "hidevideo";
    public static final String HIDE_VOICE = "hidevoice";
    public static final String HIDE_RED = "hidered";
    public static final String HIDE_location = "hidelocation";
    boolean img, video, voice, red, location;
    //当前被选中底部tab
    private static final String CURRENT_POSITION_FLAG = "CURRENT_POSITION_FLAG";
    @BindView(R.id.iv_voice)
    ImageView ivVoice;
    @BindView(R.id.iv_keybord_left)
    ImageView ivKeybordleft;
    @BindView(R.id.et_message)
    EditText etMessage;
    @BindView(R.id.btn_voice)
    Button btnVoice;
    @BindView(R.id.rl_editbar_bg)
    LinearLayout rl_editbar_bg;
    @BindView(R.id.iv_face)
    ImageView ivFace;
    @BindView(R.id.iv_keybord_right)
    ImageView ivKeybordright;
    @BindView(R.id.iv_open_other)
    ImageView ivOpen;
    @BindView(R.id.iv_close_other)
    ImageView ivClose;
    @BindView(R.id.tv_sendmsg)
    TextView tvSend;
    @BindView(R.id.ll_editarea)
    LinearLayout llEditarea;
    @BindView(R.id.ll_img)
    LinearLayout llImg;
    @BindView(R.id.ll_viedo)
    LinearLayout llViedo;
    @BindView(R.id.ll_voice)
    LinearLayout llVoice;
    @BindView(R.id.ll_redbg)
    LinearLayout llReadbg;
    //    @BindView(R.id.ll_menu)
//    LinearLayout llMenu;
    @BindView(R.id.vp_emotionview_layout)
    NoHorizontalScrollerViewPager llEmoj;
    @BindView(R.id.recyclerview_horizontal)
    RecyclerView recyclerview_horizontal;
    @BindView(R.id.ll_emotion_layout)
    LinearLayout llEmotionLayout;
    Unbinder unbinder;
    @BindView(R.id.ll_location)
    LinearLayout llLocation;
    @BindView(R.id.vp_menu_layout)
    NoHorizontalScrollerViewPager llMenu;
    @BindView(R.id.llt_otheremotion)
    LinearLayout lltOtheremotion;
    //    @BindView(R.id.llt_bar)
//    LinearLayout lltBar;
    private int CurrentPosition = 0;
    //底部水平tab

    private HorizontalRecyclerviewAdapter horizontalRecyclerviewAdapter;
    //表情面板
    public EmotionKeyboard mEmotionKeyboard;

    //需要绑定的内容view
    private View contentView;

    //不可横向滚动的ViewPager


    //是否绑定当前Bar的编辑框,默认true,即绑定。
    //false,则表示绑定contentView,此时外部提供的contentView必定也是EditText
    private boolean isBindToBarEditText = true;

    //是否隐藏bar上的编辑框和发生按钮,默认不隐藏
    private boolean isHidenBarEditTextAndBtn = false;

    List<Fragment> fragments = new ArrayList<>();


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
        View rootView = inflater.inflate(R.layout.fragment_main_emotion, container, false);
        ButterKnife.bind(this, rootView);
        isHidenBarEditTextAndBtn = args.getBoolean(EmotionMainFragment.HIDE_BAR_EDITTEXT_AND_BTN);
        img = args.getBoolean(EmotionMainFragment.HIDE_img);
        video = args.getBoolean(EmotionMainFragment.HIDE_VIDEO);
        voice = args.getBoolean(EmotionMainFragment.HIDE_VOICE);
        red = args.getBoolean(EmotionMainFragment.HIDE_RED);
        location = args.getBoolean(EmotionMainFragment.HIDE_location);
        setShowother();
        //获取判断绑定对象的参数
        isBindToBarEditText = args.getBoolean(EmotionMainFragment.BIND_TO_EDITTEXT);
        initView(rootView);
        mEmotionKeyboard = EmotionKeyboard.with(getActivity())
                .setEmotionView(rootView.findViewById(R.id.ll_emotion_layout))//绑定表情面板
                .bindToContent(contentView)//绑定内容view
                .bindToEditText(!isBindToBarEditText ? ((EditText) contentView) : ((EditText) rootView.findViewById(R.id.et_message)), new EmotionKeyboard.Showface() {
                    @Override
                    public void show() {
                        ivFace.setVisibility(View.VISIBLE);
                        ivKeybordright.setVisibility(View.GONE);
                    }
                })//判断绑定那种EditView
//                .bindToEmotionButton(rootView.findViewById(R.id.iv_face))//绑定表情按钮
                .build();
        initListener();
        initDatas();
        //创建全局监听
        GlobalOnItemClickManagerUtils globalOnItemClickManager = GlobalOnItemClickManagerUtils.getInstance(getActivity());

        if (isBindToBarEditText) {
            //绑定当前Bar的编辑框
            globalOnItemClickManager.attachToEditText(etMessage);

        } else {
            // false,则表示绑定contentView,此时外部提供的contentView必定也是EditText
            globalOnItemClickManager.attachToEditText((EditText) contentView);
            mEmotionKeyboard.bindToEditText((EditText) contentView, new EmotionKeyboard.Showface() {
                @Override
                public void show() {
                    ivFace.setVisibility(View.VISIBLE);
                    ivKeybordright.setVisibility(View.GONE);
                }
            });
        }
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    /**
     * 绑定内容view
     *
     * @param contentView
     * @return
     */
    public void bindToContentView(View contentView) {
        this.contentView = contentView;
    }

    /**
     * 初始化view控件
     */
    protected void initView(View rootView) {
        if (isHidenBarEditTextAndBtn) {//隐藏
            etMessage.setVisibility(View.GONE);
            tvSend.setVisibility(View.GONE);
            rl_editbar_bg.setBackgroundResource(R.color.bg_edittext_color);
        } else {
            etMessage.setVisibility(View.VISIBLE);
//            tvSend.setVisibility(View.VISIBLE);
            rl_editbar_bg.setBackgroundResource(R.drawable.shape_bg_reply_edittext);
        }

        etMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String message = etMessage.getText().toString().trim();
                if (message.length() > 0) {
                    tvSend.setVisibility(View.VISIBLE);
                    ivOpen.setVisibility(View.GONE);
                    ivClose.setVisibility(View.GONE);

                } else {
                    tvSend.setVisibility(View.GONE);
                    ivClose.setVisibility(View.GONE);
                    ivOpen.setVisibility(View.VISIBLE);
                }
                if (null != emlisteners) {
                    emlisteners.onEditchage(llEditarea.getHeight());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ivClose.setOnClickListener(this);
        ivOpen.setOnClickListener(this);
        ivFace.setOnClickListener(this);
        ivKeybordright.setOnClickListener(this);
        ivVoice.setOnClickListener(this);
        ivKeybordleft.setOnClickListener(this);
        btnVoice.setOnTouchListener((v, event) -> {
            if (null != emlisteners) {
                emlisteners.onbtnvoice(llViedo, event, btnVoice.getHeight());
            }
            return false;
        });
        tvSend.setOnClickListener(this);

        llImg.setOnClickListener(this);
        llViedo.setOnClickListener(this);
        llVoice.setOnClickListener(this);
        llReadbg.setOnClickListener(this);
        llLocation.setOnClickListener(this);
    }

    public void setShowother() {
        if (img)
            llImg.setVisibility(View.VISIBLE);
        else
            llImg.setVisibility(View.GONE);
        if (video)
            llViedo.setVisibility(View.VISIBLE);
        else
            llViedo.setVisibility(View.GONE);
        if (voice)
            llVoice.setVisibility(View.VISIBLE);
        else
            llVoice.setVisibility(View.GONE);
        if (red)
            llReadbg.setVisibility(View.VISIBLE);
        else
            llReadbg.setVisibility(View.GONE);
        if (location)
            llLocation.setVisibility(View.VISIBLE);
        else
            llLocation.setVisibility(View.GONE);
    }

    /**
     * 初始化监听器
     */
    Emlisteners emlisteners;

    public void setEmlisteners(Emlisteners emlisteners) {
        this.emlisteners = emlisteners;
    }

    public void setBtnVoicen(String emlisteners) {
        btnVoice.setText(emlisteners);
    }

    public void setclose(boolean tr) {
        lltOtheremotion.setVisibility(View.GONE);  llEmoj.setVisibility(View.GONE);
        llMenu.setVisibility(View.GONE);
        ivKeybordright.setVisibility(View.GONE);
        ivClose.setVisibility(View.GONE);
        if (tvSend.getVisibility() == View.VISIBLE) {
            ivOpen.setVisibility(View.GONE);
        } else {
            ivOpen.setVisibility(View.VISIBLE);
        }
        ivFace.setVisibility(View.VISIBLE);
        if (!tr)
            isInterceptBackPress3();
        else
            mEmotionKeyboard.Closekeybord();
    }

    protected void initListener() {

    }

//    public int Getchath() {
//        return lltBar.getHeight();
//    }

    public interface Emlisteners {
        void onEditchage(int aaa);

        void openother();

        void closeother();

        void onbtnvoice(View v, MotionEvent event, int h);

        void showtop();

        void sendmsg(String msg);

        void showimg();

        void showred();

        void showvoice();

        void showvideo();

        void showlocation();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_sendmsg:
                if (null != emlisteners) {
                    emlisteners.sendmsg(etMessage.getText().toString());
                    etMessage.setText("");
                }

                break;
            case R.id.iv_open_other:
                rl_editbar_bg.setVisibility(View.VISIBLE);
                btnVoice.setVisibility(View.GONE);
                ivVoice.setVisibility(View.VISIBLE);
                etMessage.setVisibility(View.VISIBLE);
                ivKeybordleft.setVisibility(View.GONE);
                ivOpen.setVisibility(View.GONE);
                ivClose.setVisibility(View.VISIBLE);

                btnVoice.setVisibility(View.GONE);
                etMessage.setVisibility(View.VISIBLE);
                llMenu.setVisibility(View.VISIBLE);
                lltOtheremotion.setVisibility(View.GONE);  llEmoj.setVisibility(View.GONE);
                ivFace.setVisibility(View.VISIBLE);
                ivKeybordright.setVisibility(View.GONE);
                mEmotionKeyboard.Showem();
                if (null != emlisteners) {
                    emlisteners.closeother();
                }
                break;
            case R.id.iv_close_other:

                rl_editbar_bg.setVisibility(View.VISIBLE);
                btnVoice.setVisibility(View.GONE);
                ivVoice.setVisibility(View.VISIBLE);
                etMessage.setVisibility(View.VISIBLE);
                ivKeybordleft.setVisibility(View.GONE);
                if (TextUtil.isEmpty(etMessage.getText())) {
                    tvSend.setVisibility(View.GONE);
                    ivClose.setVisibility(View.GONE);
                    ivOpen.setVisibility(View.VISIBLE);
                } else {
                    tvSend.setVisibility(View.VISIBLE);
                    ivClose.setVisibility(View.GONE);
                    ivOpen.setVisibility(View.GONE);
                }
                llMenu.setVisibility(View.GONE);
                ivClose.setVisibility(View.GONE);
                ivOpen.setVisibility(View.VISIBLE);
                ivKeybordleft.setVisibility(View.GONE);
                ivVoice.setVisibility(View.VISIBLE);
                lltOtheremotion.setVisibility(View.GONE);  llEmoj.setVisibility(View.GONE);
                llMenu.setVisibility(View.GONE);
                mEmotionKeyboard.Showkeybord();
                if (null != emlisteners) {
                    emlisteners.closeother();
                }
                break;
            case R.id.iv_face:
                rl_editbar_bg.setVisibility(View.VISIBLE);
                btnVoice.setVisibility(View.GONE);
                ivFace.setVisibility(View.GONE);
                ivKeybordright.setVisibility(View.VISIBLE);
                llMenu.setVisibility(View.GONE);
                if (tvSend.getVisibility() == View.VISIBLE) {
                    ivOpen.setVisibility(View.GONE);
                } else {
                    ivOpen.setVisibility(View.VISIBLE);
                }
                ivClose.setVisibility(View.GONE);
                ivVoice.setVisibility(View.VISIBLE);
                ivKeybordleft.setVisibility(View.GONE);
                ivVoice.setVisibility(View.VISIBLE);
                etMessage.setVisibility(View.VISIBLE);
                ivKeybordleft.setVisibility(View.GONE);
                llEmoj.setVisibility(View.VISIBLE);
                lltOtheremotion.setVisibility(View.VISIBLE);
                mEmotionKeyboard.Showem();
                if (null != emlisteners) {
                    emlisteners.closeother();
                }
                break;
            case R.id.iv_keybord_right:

                lltOtheremotion.setVisibility(View.GONE);  llEmoj.setVisibility(View.GONE);
                ivFace.setVisibility(View.VISIBLE);
                ivKeybordright.setVisibility(View.GONE);
                btnVoice.setVisibility(View.GONE);
                ivKeybordleft.setVisibility(View.GONE);
                ivVoice.setVisibility(View.VISIBLE);
                lltOtheremotion.setVisibility(View.GONE);  llEmoj.setVisibility(View.GONE);
                llMenu.setVisibility(View.GONE);
                ivClose.setVisibility(View.GONE);
                if (tvSend.getVisibility() == View.VISIBLE) {
                    ivOpen.setVisibility(View.GONE);
                } else {
                    ivOpen.setVisibility(View.VISIBLE);
                }
                mEmotionKeyboard.Showkeybord();
                if (null != emlisteners) {
                    emlisteners.openother();
                }
                break;
            case R.id.iv_voice:
                rl_editbar_bg.setVisibility(View.GONE);
                ivVoice.setVisibility(View.GONE);
                ivKeybordleft.setVisibility(View.VISIBLE);
                ivKeybordright.setVisibility(View.GONE);
                ivFace.setVisibility(View.VISIBLE);
                tvSend.setVisibility(View.GONE);
                ivClose.setVisibility(View.GONE);
                ivOpen.setVisibility(View.VISIBLE);
                etMessage.setVisibility(View.GONE);
                btnVoice.setVisibility(View.VISIBLE);
                llMenu.setVisibility(View.GONE);
                lltOtheremotion.setVisibility(View.GONE);  llEmoj.setVisibility(View.GONE);
                isInterceptBackPress();
                if (null != emlisteners) {
                    emlisteners.openother();
                }
                break;
            case R.id.iv_keybord_left:
                rl_editbar_bg.setVisibility(View.VISIBLE);
                btnVoice.setVisibility(View.GONE);
                ivVoice.setVisibility(View.VISIBLE);
                etMessage.setVisibility(View.VISIBLE);
                ivKeybordleft.setVisibility(View.GONE);
                if (TextUtil.isEmpty(etMessage.getText())) {
                    tvSend.setVisibility(View.GONE);
                    ivClose.setVisibility(View.GONE);
                    ivOpen.setVisibility(View.VISIBLE);

                } else {
                    tvSend.setVisibility(View.VISIBLE);
                    ivClose.setVisibility(View.GONE);
                    ivOpen.setVisibility(View.GONE);
                }

                llEmoj.setVisibility(View.INVISIBLE);
                llMenu.setVisibility(View.GONE);

                mEmotionKeyboard.Showem();
                if (null != emlisteners) {
                    emlisteners.closeother();
                }
                etMessage.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mEmotionKeyboard.Showkeybord();
                    }
                }, 20L);
                break;
            case R.id.ll_redbg:
                ivClose.setVisibility(View.GONE);
                ivOpen.setVisibility(View.VISIBLE);
                isInterceptBackPress();
                if (null != emlisteners) {
                    emlisteners.showred();
                }
                break;
            case R.id.ll_img:
                ivClose.setVisibility(View.GONE);
                ivOpen.setVisibility(View.VISIBLE);
                isInterceptBackPress();
                if (null != emlisteners) {
                    emlisteners.showimg();
                }
                break;
            case R.id.ll_viedo:
                ivClose.setVisibility(View.GONE);
                ivOpen.setVisibility(View.VISIBLE);
                isInterceptBackPress();
                if (null != emlisteners) {
                    emlisteners.showvideo();
                }
                break;
            case R.id.ll_voice:
                ivClose.setVisibility(View.GONE);
                ivOpen.setVisibility(View.VISIBLE);
                isInterceptBackPress();
                if (null != emlisteners) {
                    emlisteners.showvoice();
                }
                break;
            case R.id.ll_location:
                ivClose.setVisibility(View.GONE);
                ivOpen.setVisibility(View.VISIBLE);
                isInterceptBackPress();
                if (null != emlisteners) {
                    emlisteners.showlocation();
                }
                break;
        }
    }


    /**
     * 数据操作,这里是测试数据，请自行更换数据
     */
    protected void initDatas() {
        replaceFragment();
        replaceFragmentmeun();
        List<ImageModel> list = new ArrayList<>();
        ImageModel model1 = new ImageModel();
        model1.icon = getResources().getDrawable(R.drawable.ic_emotion);
        model1.flag = "经典笑脸";
        model1.isSelected = true;
        list.add(model1);
                        ImageModel model = new ImageModel();
                model.icon = getResources().getDrawable(R.drawable.ic_plus);
                model.flag = "其他笑脸" ;
                model.isSelected = false;
                list.add(model);
//        for (int i = 0; i < fragments.size(); i++) {
//            if (i == 0) {
//                ImageModel model1 = new ImageModel();
//                model1.icon = getResources().getDrawable(R.drawable.ic_emotion);
//                model1.flag = "经典笑脸";
//                model1.isSelected = true;
//                list.add(model1);
//            } else {
//                ImageModel model = new ImageModel();
//                model.icon = getResources().getDrawable(R.drawable.ic_plus);
//                model.flag = "其他笑脸" + i;
//                model.isSelected = false;
//                list.add(model);
//            }
//        }

        //记录底部默认选中第一个
        CurrentPosition = 0;
        SharedPreferencedUtils.setInteger(getActivity(), CURRENT_POSITION_FLAG, CurrentPosition);

        //底部tab
        horizontalRecyclerviewAdapter = new HorizontalRecyclerviewAdapter(getActivity(), list);
        recyclerview_horizontal.setHasFixedSize(true);//使RecyclerView保持固定的大小,这样会提高RecyclerView的性能
        recyclerview_horizontal.setAdapter(horizontalRecyclerviewAdapter);
        recyclerview_horizontal.setLayoutManager(new GridLayoutManager(getActivity(), 1, GridLayoutManager.HORIZONTAL, false));
        //初始化recyclerview_horizontal监听器
        horizontalRecyclerviewAdapter.setOnClickItemListener(new HorizontalRecyclerviewAdapter.OnClickItemListener() {
            @Override
            public void onItemClick(View view, int position, List<ImageModel> datas) {
//                //获取先前被点击tab
//                int oldPosition = SharedPreferencedUtils.getInteger(getActivity(), CURRENT_POSITION_FLAG, 0);
//                //修改背景颜色的标记
//                datas.get(oldPosition).isSelected = false;
//                //记录当前被选中tab下标
//                CurrentPosition = position;
//                datas.get(CurrentPosition).isSelected = true;
//                SharedPreferencedUtils.setInteger(getActivity(), CURRENT_POSITION_FLAG, CurrentPosition);
//                //通知更新，这里我们选择性更新就行了
//                horizontalRecyclerviewAdapter.notifyItemChanged(oldPosition);
//                horizontalRecyclerviewAdapter.notifyItemChanged(CurrentPosition);
//                //viewpager界面切换
//                llEmoj.setCurrentItem(position, false);
            }

            @Override
            public void onItemLongClick(View view, int position, List<ImageModel> datas) {
            }
        });


    }

    private void replaceFragment() {
        //创建fragment的工厂类
        FragmentFactory factory = FragmentFactory.getSingleFactoryInstance();
        //创建修改实例
        EmotiomComplateFragment f1 = (EmotiomComplateFragment) factory.getFragment(EmotionUtils.EMOTION_CLASSIC_TYPE);
        fragments.add(f1);
        Bundle b = null;
        for (int i = 0; i < 1; i++) {
            b = new Bundle();
            b.putString("Interge", "Fragment-" + i);
            Fragment1 fg = Fragment1.newInstance(Fragment1.class, b);
            fragments.add(fg);
        }

        NoHorizontalScrollerVPAdapter adapter = new NoHorizontalScrollerVPAdapter(getActivity().getSupportFragmentManager(), fragments);
        llEmoj.setAdapter(adapter);
    }

    private void replaceFragmentmeun() {
        ArrayList<Integer> list = new ArrayList<>();
        if (img)
            list.add(1);

        if (video)
            list.add(2);
        if (voice)
            list.add(3);

        if (red)
            list.add(4);

        if (location)
            list.add(5);


        Bundle bundle = new Bundle();
        bundle.putIntegerArrayList("list", list);

        MenuComplateFragment f1 = MenuComplateFragment.newInstance(MenuComplateFragment.class, bundle);
        f1.setOnitemclick(new MenuComplateFragment.Onitemclick() {
            @Override
            public void Onclick(int index) {
                ivClose.setVisibility(View.GONE);
                ivOpen.setVisibility(View.VISIBLE);
                isInterceptBackPress();
                switch (index) {
                    case 1:

                        if (null != emlisteners) {
                            emlisteners.showimg();
                        }
                        break;
                    case 2:
                        if (null != emlisteners) {
                            emlisteners.showvideo();
                        }
                        break;
                    case 3:
                        if (null != emlisteners) {
                            emlisteners.showvoice();
                        }
                        break;
                    case 4:
                        if (null != emlisteners) {
                            emlisteners.showred();
                        }
                        break;
                    case 5:
                        if (null != emlisteners) {
                            emlisteners.showlocation();
                        }
                        break;

                }
            }
        });
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(f1);
        NoHorizontalScrollerVPAdapter adapter = new NoHorizontalScrollerVPAdapter(getActivity().getSupportFragmentManager(), fragments);
        llMenu.setAdapter(adapter);
    }

    /**
     * 是否拦截返回键操作，如果此时表情布局未隐藏，先隐藏表情布局
     *
     * @return true则隐藏表情布局，拦截返回键操作
     * false 则不拦截返回键操作
     */
    public boolean isInterceptBackPress() {
        if (null != emlisteners)
            emlisteners.showtop();

        mEmotionKeyboard.Closekeybord();
        return mEmotionKeyboard.interceptBackPress();
    }

    public boolean isInterceptBackPress2() {
        if (null != emlisteners)
            emlisteners.showtop();
        lltOtheremotion.setVisibility(View.GONE);  llEmoj.setVisibility(View.GONE);
        ivFace.setVisibility(View.VISIBLE);
        ivKeybordright.setVisibility(View.GONE);
        btnVoice.setVisibility(View.GONE);
        ivKeybordleft.setVisibility(View.GONE);
        ivVoice.setVisibility(View.VISIBLE);
        lltOtheremotion.setVisibility(View.GONE);  llEmoj.setVisibility(View.GONE);
        llMenu.setVisibility(View.GONE);
        ivClose.setVisibility(View.GONE);
        if (tvSend.getVisibility() == View.VISIBLE) {
            ivOpen.setVisibility(View.GONE);
        } else {
            ivOpen.setVisibility(View.VISIBLE);
        }
        mEmotionKeyboard.Closekeybord();
        return mEmotionKeyboard.interceptBackPress();
    }

    public void isInterceptBackPress3() {

        lltOtheremotion.setVisibility(View.GONE);  llEmoj.setVisibility(View.GONE);
        ivFace.setVisibility(View.VISIBLE);
        ivKeybordright.setVisibility(View.GONE);
        btnVoice.setVisibility(View.GONE);
        ivKeybordleft.setVisibility(View.GONE);
        ivVoice.setVisibility(View.VISIBLE);

        llMenu.setVisibility(View.GONE);
        ivClose.setVisibility(View.GONE);

        rl_editbar_bg.setVisibility(View.VISIBLE);

        etMessage.setVisibility(View.VISIBLE);

        if (TextUtil.isEmpty(etMessage.getText())) {
            tvSend.setVisibility(View.GONE);
            ivClose.setVisibility(View.GONE);
            ivOpen.setVisibility(View.VISIBLE);

        } else {
            tvSend.setVisibility(View.VISIBLE);
            ivClose.setVisibility(View.GONE);
            ivOpen.setVisibility(View.GONE);
        }


        mEmotionKeyboard.Closeem();
        mEmotionKeyboard.Closekeybord();
        if (null != emlisteners)
            emlisteners.showtop();

    }

    public boolean isInterceptBackPress1() {
        return mEmotionKeyboard.interceptBackPress1();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}


