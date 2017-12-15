package com.example.apple.chatemotion;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

import com.example.apple.chatemotion.emoji.emotin.fragment.EmotionMainFragment;

public class MainActivity extends AppCompatActivity {

    //对应三种状态
    public static final int NOT_VOICE = 1;//没有操作
    public static final int CUR_VOICE = 2;//正在说话
    public static final int CANCEL_VOICE = 3;//取消发送

    private int mCurState = NOT_VOICE;//当前状态
    private boolean mIsVoice;//是否在录音

    

    ListView lvMessage;
    private MediaPlayer mediaPlayer = new MediaPlayer();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvMessage = (ListView) findViewById(R.id.lv_message);
        initEmotionMainFragment(lvMessage,true,false,false,false,true);
        emotionMainFragment.setEmlisteners(new EmotionMainFragment.Emlisteners() {
            @Override
            public void showlocation() {
          
            }
            @Override
            public void onEditchage(int aaa) {
            
//                LinearLayoutManager layoutManager = (LinearLayoutManager) lvMessage.getLayoutManager();
//                layoutManager.scrollToPositionWithOffset(emMessageList.size(), -(llEditarea - ResolutionUtil.dip2px(ChatActivityG.this, 50)));

            }

            @Override
            public void openother() {


            }

            @Override
            public void closeother() {


            }

            @Override
            public void onbtnvoice(View v, MotionEvent event, int h) {
//                String[] permission = new String[]{
//                        Manifest.permission.RECORD_AUDIO};
//                setPermission(permission, "为了您可以正常使用录音设备，\n请点击\"设置\"-\"权限\"-打开 \"存储空间\"与\"录音机\" 权限。\n最后点击两次后退按钮，即可返回。");
//                setOnPermissionCheckedListener(audio);
//                checkPermission();
                if (true) {


                    //当前坐标
                    float x = event.getX();
                    float y = event.getY();
                    switch (event.getAction()) {//当前手势
                        //不同手势切换不同的状态
                        case MotionEvent.ACTION_DOWN:
                            try {
                                Thread.sleep(10);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

//                            recoderUtils.startRecord();//开始录音
//                            mHandler.postDelayed(mPollTask, 0);
//                            changeState(CUR_VOICE);
                            break;
                        case MotionEvent.ACTION_MOVE:
                            if (mIsVoice) {//如果开始录音 才进行坐标判断
                                //根据手指的区域判断是否要取消录音
                               
                            }
                            break;
                        case MotionEvent.ACTION_UP:
                            //弹起手指时判断上一个状态进行操作

//                            mHandler.removeCallbacks(mPollTask);
                            if (mCurState == CANCEL_VOICE) {//取消录音时弹起 才进行取消操作
//                                recoderUtils.cancelRecord();// 取消录音，停止动画
                               
                            } else if (mCurState == CUR_VOICE) {//正在录音 抬起时发送语音

//                                recoderUtils.stopRecord();// 停止录音，等待发送
                       
                            }

//                            reset();//状态重置

                            break;
                        case MotionEvent.ACTION_CANCEL: // 异常
//                            reset();//状态重置

                            break;
                    }
                }
            }

            @Override
            public void showtop() {

            }

            @Override
            public void sendmsg(String msg) {
              
            }

            @Override
            public void showimg() {
                //选取图片
//                String[] permissionss = {
//                        Manifest.permission.CAMERA,
//                        Manifest.permission.READ_EXTERNAL_STORAGE
//                        , Manifest.permission.WRITE_EXTERNAL_STORAGE
//                };
//                setPermission(permissionss, "为了您可以正常使用应用，\n请点击\"设置\"-\"权限\"-打开 \"相机和读取照片\" 权限。\n最后点击两次后退按钮，即可返回。");

          
            }

            @Override
            public void showred() {

            }

            @Override
            public void showvoice() {

            }

            @Override
            public void showvideo() {

            }
        });
    }
    public EmotionMainFragment emotionMainFragment;

    public void initEmotionMainFragment(View lvMessage,boolean img, boolean video, boolean voice, boolean red, boolean loca) {
        //构建传递参数
        Bundle bundle = new Bundle();
        //绑定主内容编辑框
        bundle.putBoolean(EmotionMainFragment.BIND_TO_EDITTEXT, true);
        //隐藏控件
        bundle.putBoolean(EmotionMainFragment.HIDE_BAR_EDITTEXT_AND_BTN, false);
        bundle.putBoolean(EmotionMainFragment.HIDE_img, img);
        bundle.putBoolean(EmotionMainFragment.HIDE_VIDEO, video);
        bundle.putBoolean(EmotionMainFragment.HIDE_VOICE, voice);
        bundle.putBoolean(EmotionMainFragment.HIDE_RED, red);
        bundle.putBoolean(EmotionMainFragment.HIDE_location, loca);
        //替换fragment
        //创建修改实例
        emotionMainFragment = EmotionMainFragment.newInstance(EmotionMainFragment.class, bundle);
        emotionMainFragment.bindToContentView(lvMessage);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // Replace whatever is in thefragment_container view with this fragment,
        // and add the transaction to the backstack
        transaction.replace(R.id.fl_emotionview_main, emotionMainFragment);
        transaction.addToBackStack(null);
        //提交修改
        transaction.commit();
    }
    public boolean isCancel(float x, float y, int h) {
        //判断手的位置是否移动到控件上/下方300的位置 进行取消(微信好像只判断移动到按钮上方)
        if (y < h - 300 || y > h + 300) {
            return true;
        }
        return false;

    }
}
