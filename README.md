# KeepLive for Android 安卓保活组件
## 集成了无声音乐（已考虑功耗，降至最低），前台服务、双进程守护、像素保活，jobs五种保活方式
## 主流的魅族、小米、锤子、vivo、努比亚、三星、华为等品牌，涵盖4.4至8.1的机型测试结果为，只要用户不主动杀死程序就不会死，但某些机型锁屏即断网的问题不是我能解决的。
### 第一步，在application中启动保活服务
```Java
        //定义前台服务的默认样式。即标题、描述和图标
        ForegroundNotification foregroundNotification = new ForegroundNotification("测试","描述", R.mipmap.ic_launcher,
                //定义前台服务的通知点击事件
                new ForegroundNotificationClickListener() {
                    @Override
                    public void foregroundNotificationClick() {
                    }
                });
        //启动保活服务
        KeepLive.startWork(this, foregroundNotification,
                //你需要保活的服务，如socket连接、定时任务等，建议不用匿名内部类的方式在这里写
                new KeepLiveService() {
                    /**
                     * 运行中
                     * 由于服务可能会多次自动启动，该方法可能重复调用
                     * @param context 所在服务上下文
                     */
                    @Override
                    public void onWorking(Context context) {

                    }
                    /**
                     * 服务终止
                     * 由于服务可能会被多次终止，该方法可能重复调用，需同onWorking配套使用，如注册和注销broadcast
                     * @param context 所在服务上下文
                     */
                    @Override
                    public void onStop(Context context) {
                        
                    }
                }
        );
```
### 第二步，在manifest中添加权限申请，可能会报过时，不必在意
```Xml
        <uses-permission android:name="android.permission.GET_TASKS"/>
```
### 第三步，在manifest中添加相关service及broadcast。原样copy，不要改动
```Xml
        <receiver android:name="com.fanjun.keeplive.receiver.NotificationClickReceiver"/>
        <activity android:name="com.fanjun.keeplive.activity.OnePixelActivity"/>
        <service android:name="com.fanjun.keeplive.service.LocalService"/>
        <service android:name="com.fanjun.keeplive.service.JobHandlerService" android:permission="android.permission.BIND_JOB_SERVICE"/>
        <service android:name="com.fanjun.keeplive.service.RemoteService" android:process=":remote"/>
```
### 依赖
#### Maven
```Xml
<dependency>
  <groupId>com.fanjun</groupId>
  <artifactId>keeplive</artifactId>
  <version>1.0.1</version>
  <type>pom</type>
</dependency>
```
#### Gradle
```Xml
implementation 'com.fanjun:keeplive:1.0.1'
```
