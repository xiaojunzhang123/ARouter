package com.zxj.arouter_core;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Set;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import androidx.core.app.ActivityCompat;
import com.zxj.arouter_annotation.Extra;
import com.zxj.arouter_annotation.RouteMeta;
import com.zxj.arouter_core.callback.NavigationCallback;
import com.zxj.arouter_core.exception.NoRouteFoundException;
import com.zxj.arouter_core.template.IRouteGroup;
import com.zxj.arouter_core.template.IRouteRoot;
import com.zxj.arouter_core.template.IService;
import com.zxj.arouter_core.utils.ClassUtils;

public class ARouter {

    private static final String ROUTE_ROOT_PAKCAGE = "com.zxj.arouter.routes";
    private static final String SDK_NAME = "ARouter";
    private static final String SEPARATOR = "_";
    private static final String SUFFIX_ROOT = "Root";
    private static final String TAG = "ARouter";
    private static ARouter aRouter;
    private static Application mContext;
    private Handler mHandler;

    private ARouter() {
        mHandler = new Handler(Looper.getMainLooper());
    }

    public static ARouter getInstance() {
        if (aRouter == null) {
            synchronized (ARouter.class) {
                if (aRouter == null) {
                    aRouter = new ARouter();
                }
            }
        }
        return aRouter;
    }

    public static void init(Application application) {
        mContext = application;
        try {
            loadInfo();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG + " init", "初始化失敗！", e);
        }
    }

    private static void loadInfo()
        throws NameNotFoundException, InterruptedException, ClassNotFoundException,
        NoSuchMethodException, IllegalAccessException, InvocationTargetException,
        InstantiationException {
        // 获得所有 apt 生成的路由类的全类名 (路由表)
        Set<String> routerMap = ClassUtils.getFileNameByPackageName(mContext,
            ROUTE_ROOT_PAKCAGE);
        Log.d(TAG + " loadInfo", routerMap.toString());
        for (String className : routerMap) {
            //root 中注册的是分组信息，将分组信息加入仓库中
            if (className.startsWith(
                ROUTE_ROOT_PAKCAGE + "." + SDK_NAME + SEPARATOR + SUFFIX_ROOT)) {
                ((IRouteRoot)(Class.forName(className).getConstructor().newInstance())).loadInto(
                    Warehouse.groupsIndex);
            }
        }
        Log.d(TAG + " Warehouse gi", Warehouse.groupsIndex.toString());
        for (Map.Entry<String, Class<? extends IRouteGroup>> stringClassEntry :
            Warehouse.groupsIndex
                .entrySet()) {
            Log.d(TAG + " loadInfo",
                "Root 映射表[" + stringClassEntry.getKey() + " : " + stringClassEntry.getValue()
                    + "]");
        }

    }

    public PostCard build(String path) {
        if (TextUtils.isEmpty(path)) {
            throw new RuntimeException("路由地址无效!");
        } else {
            return build(path, extractGroup(path));
        }
    }

    private PostCard build(String path, String group) {
        if (TextUtils.isEmpty(path) || TextUtils.isEmpty(group)) {
            throw new RuntimeException("路由地址无效!");
        } else {
            return new PostCard(path, group);
        }
    }

    private String extractGroup(String path) {
        if (TextUtils.isEmpty(path) || !path.startsWith("/")) {
            throw new RuntimeException(path + " : 不能提取group.");
        }
        try {
            String defaultGroup = path.substring(1, path.indexOf("/", 1));
            if (TextUtils.isEmpty(defaultGroup)) {
                throw new RuntimeException(path + " : 不能提取group.");
            } else {
                return defaultGroup;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    protected Object navigation(final Context context, final PostCard postcard,
                                final int requestCode, final NavigationCallback callback) {

        if (callback != null) {
          return   _navigation(context, postcard, requestCode, callback);

        } else {
           return  _navigation(context, postcard, requestCode, callback);
        }

    }

    protected Object _navigation(Context context, PostCard postcard, int requestCode,
                                 NavigationCallback callback) {
        try {
            prepareCard(postcard);
        } catch (NoRouteFoundException e) {
            e.printStackTrace();
            //没有找到
            if (null != callback) {
                callback.onLost(postcard);
            }
            return null;
        }
        //找到目标
        if (null != callback) {
            callback.onFound(postcard);
        }

        switch (postcard.getType()) {
            case ACTIVITY:
                final Context currentContext = null == context ? mContext : context;
                final Intent intent = new Intent(currentContext, postcard.getDestination());
                intent.putExtras(postcard.getExtras());
                int flags = postcard.getFlags();
                if (-1 != flags) {
                    intent.setFlags(flags);
                } else if (!(currentContext instanceof Activity)) {
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                }
                mHandler.post(() -> {
                    // 可能需要返回码
                    if (requestCode > 0) {
                        ActivityCompat.startActivityForResult((Activity)currentContext, intent,
                            requestCode, postcard.getOptionsBundle());
                    } else {
                        ActivityCompat.startActivity(currentContext, intent,
                            postcard.getOptionsBundle());
                    }

                    if ((0 != postcard.getEnterAnim() || 0 != postcard.getExitAnim()) &&
                        currentContext instanceof Activity) {
                        //老版本
                        ((Activity)currentContext).overridePendingTransition(
                            postcard.getEnterAnim(),
                            postcard.getExitAnim());
                    }

                    //跳转完成
                    if (null != callback) {
                        callback.onArrival(postcard);
                    }
                });

                break;
            case ISERVICE:
                IService service = postcard.getService();
                return service;
            default:
                break;
        }
        return null;
    }

    private void prepareCard(PostCard card) {
        /**
         * 1. 先在 Warehouse.routes 中查找目标封装对象
         * 2. 如果在 Warehouse.routes 查到不到就在 Warehouse.groupsIndex中查找，如果在 Warehouse
         * .groupsIndex找到了，就将其从Warehouse.groupsIndex集合中移除到 Warehouse.routes
         */
        RouteMeta routeMeta = Warehouse.routes.get(card.getPath());
        if (null == routeMeta) {
            Class<? extends IRouteGroup> groupMeta = Warehouse.groupsIndex.get(card.getGroup());
            if (null == groupMeta) {
                throw new NoRouteFoundException(
                    "没找到对应路由：分组=" + card.getGroup() + "   路径=" + card.getPath());
            }
            IRouteGroup iRouteGroup;
            try {
                iRouteGroup = groupMeta.getConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException("路由分组映射表记录失败.", e);
            }
            iRouteGroup.loadInto(Warehouse.routes);
            //已经准备了就可以移除了 （不会一直存着内存中）
            Warehouse.groupsIndex.remove(card.getGroup());
            //再次进入 else
            prepareCard(card);
        } else {
            //类 要跳转的activity 或 IService实现类
            card.setDestination(routeMeta.getDestination());
            card.setType(routeMeta.getType());
            switch (routeMeta.getType()) {
                case ISERVICE:
                    Class<?> destination = routeMeta.getDestination();
                    IService service = Warehouse.services.get(destination);
                    if (null == service) {
                        try {
                            service = (IService)destination.getConstructor().newInstance();
                            Warehouse.services.put(destination, service);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    card.setService(service);
                    break;
                default:
                    break;
            }
        }
    }

    public void inject(Activity activity) {
        ExtraManager.getInstance().loadExtras(activity);
    }

}
