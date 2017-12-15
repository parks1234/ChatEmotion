package com.example.apple.chatemotion;

import android.app.Application;
import android.content.Context;

/**
 * Created by  王学波 on 2017/12/15.
 */

public class App extends Application{
    static App contex;
    
    @Override
    public void onCreate() {
        super.onCreate();
        contex=this;
    }

    public static Context getInstance() {
        return contex;
    }
}
