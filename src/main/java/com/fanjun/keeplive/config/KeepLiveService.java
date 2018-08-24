package com.fanjun.keeplive.config;

import android.content.Context;

/**
 * 需要保活的服务
 */
public interface KeepLiveService {
    /**
     * 运行中
     * 由于服务可能会多次自动启动，该方法可能重复调用
     *
     * @param context 所在服务上下文
     */
    void onWorking(Context context);

    /**
     * 服务终止
     * 由于服务可能会被多次终止，该方法可能重复调用，需同onWorking配套使用，如注册和注销
     *
     * @param context 所在服务上下文
     */
    void onStop(Context context);
}
