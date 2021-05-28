package com.zxj.arouter_core.callback;

import com.zxj.arouter_core.PostCard;

public interface NavigationCallback {

    /**
     * 找到跳转页面
     * @param postCard
     */
    void onFound(PostCard postCard);

    /**
     * 未找到
     * @param postCard
     */
    void onLost(PostCard postCard);

    /**
     * 成功跳转
     * @param postCard
     */
    void onArrival(PostCard postCard);

    /**
     * 中断了路由跳转
     * @param throwable
     */
    void onInterrupt(Throwable throwable);

}
