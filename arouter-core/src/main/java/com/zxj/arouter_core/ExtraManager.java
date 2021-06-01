package com.zxj.arouter_core;

import android.app.Activity;
import android.util.Log;
import android.util.LruCache;
import com.zxj.arouter_core.template.IExtra;

public class ExtraManager {

    private static ExtraManager extraManager;
    private LruCache<String, IExtra> classCache;
    public static final String SUFFIX_AUTOWIRED = "_Extra";


    public static ExtraManager getInstance(){
        if (extraManager == null){
            synchronized (ExtraManager.class){
                if (extraManager == null){
                    extraManager = new ExtraManager();
                }
            }
        }
        return extraManager;
    }

    public ExtraManager(){
       classCache  = new LruCache<>(60);
    }

    public void loadExtras(Activity activity){
        String className = activity.getClass().getName();
        IExtra iExtra = classCache.get(className);
        try {
            if (null == iExtra){
                //创建一个实现了IExtra 接口的运行时类对象
                iExtra = (IExtra)Class.forName(activity.getClass().getName() +
                    SUFFIX_AUTOWIRED).getConstructor().newInstance();
                Log.e("ExtraManager iExtra",activity.getClass().getName() +
                    SUFFIX_AUTOWIRED);
            }
            iExtra.loadExtra(activity);
            classCache.put(className,iExtra);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
