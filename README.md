# LibKeepAliveProcess


# 调用 #


    KeepAliveProcessMain.init(this);

配置LockScreenActivity，如果LockScreenActivity和其它Activity在一个任务栈，导致LockScreenActivity销毁的时候，会将LockScreenActivity下的Activity显示到前台。如果当前任务栈在后台，这时候会自动显示到前台，造成用户体验影响

    <activity
            android:name="com.joyveb.lkap.LockScreenActivity"
            android:launchMode="singleInstance"
            android:taskAffinity="lap.joyveb.com.keepaliveprocess.back" />


## 手段一 利用 Activity 提升权限##

通过LockScreenActivity显示到锁屏界面，该界面透明，绘制1x1像素

注册ScreenReceiver监听锁屏事件，锁屏时显示LockScreenActivity，解锁后关闭


## 手段二 利用 Notification 提升权限 ##

Android 中 Service 的优先级为4，通过 setForeground 接口可以将后台 Service 设置为前台 Service，使进程的优先级由4提升为2，从而使进程的优先级仅仅低于用户当前正在交互的进程，与可见进程优先级一致，使进程被杀死的概率大大降低。

通过实现一个内部 Service，在 KeepAliveService 和其内部 GrayInnerService 中同时发送具有相同 ID 的 Notification，然后将内部 GrayInnerService 结束掉。随着内部 GrayInnerService 的结束，Notification 将会消失，但系统优先级依然保持为2。