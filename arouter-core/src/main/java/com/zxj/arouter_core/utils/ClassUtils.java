package com.zxj.arouter_core.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.text.TextUtils;
import android.util.Log;
import com.zxj.arouter_core.thread.DefaultPoolExecutor;
import dalvik.system.DexFile;

public class ClassUtils {

    private static final String TAG = "ARouter";
    /**
     * 获得程序所有的APK (instant run 会产生很多split apk)
     *
     * @param context
     * @return
     */
    private static List<String> getSourcePaths(Context context) throws NameNotFoundException {
        ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(
            context.getPackageName(), 0);
        List<String> sourcePaths = new ArrayList<>();
        sourcePaths.add(applicationInfo.sourceDir);
        Log.d(TAG,"sourcePaths: "+ sourcePaths.toString() + " applicationInfo.sourceDir:"+applicationInfo.sourceDir);
        // instant run
        if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
            if (null != applicationInfo.splitPublicSourceDirs) {
                sourcePaths.addAll(Arrays.asList(applicationInfo.sourceDir));
            }
        }
        return sourcePaths;
    }

    /**
     * 得到路由表的类名
     *
     * @param context
     * @param packageName
     * @return
     */
    public static Set<String> getFileNameByPackageName(Application context,
                                                       final String packageName)
        throws NameNotFoundException, InterruptedException {
        final Set<String> classNames = new HashSet<>();
        List<String> paths = getSourcePaths(context);
        //使用同步计数器判断均处理完成
        final CountDownLatch countDownLatch = new CountDownLatch(paths.size());
        ThreadPoolExecutor threadPoolExecutor = DefaultPoolExecutor.newDefaultPoolExecutor(
            paths.size());
        for (final String path : paths) {
            threadPoolExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    DexFile dexFile = null;

                    try {
                        //加载apk中的dex 并遍历获得所有包名为 {packageName} 的类
                        dexFile = new DexFile(path);
                        Enumeration<String> entries = dexFile.entries();
                        while (entries.hasMoreElements()) {
                            String className = entries.nextElement();
                            if (!TextUtils.isEmpty(className) && className.startsWith(
                                packageName)) {
                                classNames.add(className);
                            }
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (null != dexFile) {
                            try {
                                dexFile.close();
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                        }
                        //释放一个
                        countDownLatch.countDown();
                    }

                }
            });
        }

        //等待执行完成
        countDownLatch.await();
        return classNames;
    }

}
