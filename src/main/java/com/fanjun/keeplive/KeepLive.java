package com.fanjun.keeplive;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;

import com.fanjun.keeplive.config.ForegroundNotification;
import com.fanjun.keeplive.config.KeepLiveService;
import com.fanjun.keeplive.service.JobHandlerService;
import com.fanjun.keeplive.service.LocalService;
import com.fanjun.keeplive.service.RemoteService;

/**
 * 保活工具
 */
public final class KeepLive {
    public static ForegroundNotification foregroundNotification = null;
    public static KeepLiveService keepLiveService = null;

    /**
     * 启动保活
     *
     * @param application            your application
     * @param foregroundNotification 前台服务
     * @param keepLiveService        保活业务
     */
    public static void startWork(@NonNull Application application, @NonNull ForegroundNotification foregroundNotification, KeepLiveService keepLiveService) {
        if (isMain(application)) {
            KeepLive.foregroundNotification = foregroundNotification;
            KeepLive.keepLiveService = keepLiveService;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //启动定时器，在定时器中启动本地服务和守护进程
                Intent intent = new Intent(application, JobHandlerService.class);
                application.startService(intent);
            } else {
                //启动本地服务
                application.startService(new Intent(application, LocalService.class));
                //启动守护进程
                Intent intent = new Intent(application, RemoteService.class);
                application.startService(intent);
            }
        }
    }

    private static boolean isMain(Application application) {
        int pid = android.os.Process.myPid();
        String processName = "";
        ActivityManager mActivityManager = (ActivityManager) application.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                processName = appProcess.processName;
                break;
            }
        }
        String packageName = application.getPackageName();
        if (processName.equals(packageName)) {
            return true;
        }
        return false;
    }
}
