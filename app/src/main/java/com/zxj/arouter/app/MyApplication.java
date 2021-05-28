package com.zxj.arouter.app;

import android.app.Application;
import com.zxj.arouter_core.ARouter;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ARouter.init(this);
    }
}
