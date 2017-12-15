
package com.example.apple.chatemotion.emoji.emotin.utils;

import android.support.v4.util.ArrayMap;

import com.example.apple.chatemotion.App;
import com.example.apple.chatemotion.emoji.EmojiConversionUtils;

import java.util.Map;
import java.util.Set;


/**
 * @author : 
 * @time : 2016年1月5日 上午11:32:33
 * @email : 
 * @description :表情加载类,可自己添加多种表情，分别建立不同的map存放和不同的标志符即可
 */
public class EmotionUtils {

	/**
	 * 表情类型标志符
	 */
	public static final int EMOTION_CLASSIC_TYPE=0x0001;//经典表情

	/**
	 * key-表情文字;
	 * value-表情图片资源
	 */
	public static ArrayMap<String, Integer> EMPTY_MAP;
	public static ArrayMap<String, Integer> EMOTION_CLASSIC_MAP=new ArrayMap<>();
	public static ArrayMap<String, Integer> EMOTION_CLASSIC_MAPall=new ArrayMap<>();

	static {
		EMPTY_MAP = new ArrayMap<>();
		EMOTION_CLASSIC_MAP = new ArrayMap<>();

		EmojiConversionUtils.INSTANCE.init(App.getInstance());

		
		Set<Map.Entry<String, String>> entrySet = EmojiConversionUtils.INSTANCE.emojiMap.entrySet();
		for (Map.Entry<String, String> entry : entrySet) {
			int resId = App.getInstance().getResources().getIdentifier(entry.getValue(), "drawable",
					App.getInstance().getPackageName());
			EMOTION_CLASSIC_MAP.put(entry.getKey(),resId);
		}
		Set<Map.Entry<String, String>> entrySet1 = EmojiConversionUtils.INSTANCE.emojiMapall.entrySet();
		for (Map.Entry<String, String> entry : entrySet1) {
			int resId = App.getInstance().getResources().getIdentifier(entry.getValue(), "drawable",
					App.getInstance().getPackageName());
			EMOTION_CLASSIC_MAPall.put(entry.getKey(),resId);
		}
	

//		EMOTION_CLASSIC_MAP.put("[兔子]", R.drawable.d_tuzi);
	
	}

	/**
	 * 根据名称获取当前表情图标R值
	 * @param EmotionType 表情类型标志符
	 * @param imgName 名称
	 * @return
	 */
	public static int getImgByName(int EmotionType,String imgName) {
		Integer integer=null;
		switch (EmotionType){
			case EMOTION_CLASSIC_TYPE:
				integer = EMOTION_CLASSIC_MAP.get(imgName);
				break;
			default:
				LogUtils.e("the emojiMap is null!! Handle Yourself ");
				break;
		}
		return integer == null ? -1 : integer;
	}

	/**
	 * 根据类型获取表情数据
	 * @param EmotionType
	 * @return
	 */
	public static ArrayMap<String, Integer> getEmojiMap(int EmotionType){
		ArrayMap EmojiMap=null;
		switch (EmotionType){
			case EMOTION_CLASSIC_TYPE:
				EmojiMap=EMOTION_CLASSIC_MAP;
				break;
			default:
				EmojiMap=EMPTY_MAP;
				break;
		}
		return EmojiMap;
	}
}
