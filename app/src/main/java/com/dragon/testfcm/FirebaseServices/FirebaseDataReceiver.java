package com.dragon.testfcm.FirebaseServices;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import com.rms.securityguard.Activity.LoginActivity;
import com.rms.securityguard.Activity.MainActivity;
import com.rms.securityguard.Activity.SplashActivity;
import com.rms.securityguard.R;
import com.rms.securityguard.Utils.ApiConstants;
import com.rms.securityguard.Utils.AudioPlay;
import com.rms.securityguard.Utils.CommonSharedPreference;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 3/13/2018.
 */

public class FirebaseDataReceiver extends BroadcastReceiver {
    private final String TAG = "FirebaseDataReceiver";
    private String title, message = "";
    public Context mFirebaseDataReceiverContext;
    public static Ringtone r,r1;
    private static int SPLASH_TIME_OUT = 5000;

    private CommonSharedPreference commonSharedPreference;
    public void onReceive(Context context, Intent intent) {
        commonSharedPreference = new CommonSharedPreference();
        mFirebaseDataReceiverContext = context;

        Log.d(TAG, "I'm in!!!");
        if (intent.getExtras() != null) {
            for (String key : intent.getExtras().keySet()) {
                Log.d(TAG, "I'm in!!!" + key);

                if (key.equals("gcm.notification.title")) {

                    Log.e(TAG, "onReceive title>>>>>: " + intent.getExtras().getString(key));
                    title = intent.getExtras().getString(key);
                    message = intent.getExtras().getString("gcm.notification.body");
                    NotificationBean notificationBean = new NotificationBean();
                    notificationBean.setmNotificatioType(title);
                    notificationBean.setmNotificationStatus(false);
                    notificationBean.setmNotificationMsg(message);

                    //set Sound for notification

                    if (title.equalsIgnoreCase(context.getResources().getString(R.string.security_alaram_notification))) {

                        try {
                            if(r == null)
                            {
                                Uri alarmSound=Uri.parse("android.resource://com.rms.securityguard/" + R.raw.finalcompress);
                                r = RingtoneManager.getRingtone(mFirebaseDataReceiverContext,alarmSound );
                            }

                            if(!isAppIsInBackground(mFirebaseDataReceiverContext))
                            {
//                                handleNotification();
                                r.play();
                            }else {
                                r.play();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }else if(title.equalsIgnoreCase(context.getResources().getString(R.string.notice_notification))) {

                        try {

                            if(r1 == null) {
                                Uri notificationUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                                r1 = RingtoneManager.getRingtone(mFirebaseDataReceiverContext, notificationUri);
                            }
                            if(!isAppIsInBackground(mFirebaseDataReceiverContext)) {

                                r1.play();

                            }else {

                                r1.play();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    Log.d("msg", title + "..." + message);
                    NotificationBeanListClass notificationBeanListClass=null;
                    if (title.equalsIgnoreCase(context.getResources().getString(R.string.notice_notification))){
                        notificationBeanListClass = commonSharedPreference.getAllPendingNotificationList(mFirebaseDataReceiverContext,ApiConstants.NotificationBeanNotice);
                    }else if (title.equalsIgnoreCase(context.getResources().getString(R.string.security_alaram_notification))) {
                        notificationBeanListClass = commonSharedPreference.getAllPendingNotificationList(mFirebaseDataReceiverContext, ApiConstants.NotificationBeanSecurity);
                    }
                    if (notificationBeanListClass != null) {
                        if (notificationBeanListClass.getNotificationBeanList() != null) {
                            List<NotificationBean> notificationBeanList = notificationBeanListClass.getNotificationBeanList();
                            notificationBeanList.add(notificationBean);
                            notificationBeanListClass.setNotificationBeanList(notificationBeanList);
                            //setNotificationData(notificationBeanListClass);

                            if (title.equalsIgnoreCase(context.getResources().getString(R.string.notice_notification))){
                                commonSharedPreference.setNotificationData(mFirebaseDataReceiverContext,notificationBeanListClass,ApiConstants.NotificationBeanNotice);
                            }else if (title.equalsIgnoreCase(context.getResources().getString(R.string.security_alaram_notification))){
                                commonSharedPreference.setNotificationData(mFirebaseDataReceiverContext,notificationBeanListClass,ApiConstants.NotificationBeanSecurity);
                            }

                        } else {
                            List<NotificationBean> notificationBeanList = new ArrayList<>();
                            notificationBeanList.add(notificationBean);
                            notificationBeanListClass.setNotificationBeanList(notificationBeanList);
                            //setNotificationData(notificationBeanListClass);
                            if (title.equalsIgnoreCase(context.getResources().getString(R.string.notice_notification))){
                                commonSharedPreference.setNotificationData(mFirebaseDataReceiverContext,notificationBeanListClass,ApiConstants.NotificationBeanNotice);
                            }else if (title.equalsIgnoreCase(context.getResources().getString(R.string.security_alaram_notification))){
                                commonSharedPreference.setNotificationData(mFirebaseDataReceiverContext,notificationBeanListClass,ApiConstants.NotificationBeanSecurity);
                            }
                        }
                    } else {
                        NotificationBeanListClass notificationBeanListClassNew = new NotificationBeanListClass();
                        List<NotificationBean> notificationBeanList = new ArrayList<>();
                        notificationBeanList.add(notificationBean);
                        notificationBeanListClassNew.setNotificationBeanList(notificationBeanList);
                        Log.e(TAG, "onReceive: Nullllllll>>>>>"+title );
                        Log.e(TAG, "onReceive: notificationBeanList>>>>>"+notificationBeanList.size() );
                        if (title.equalsIgnoreCase(context.getResources().getString(R.string.notice_notification))){
                            commonSharedPreference.setNotificationData(mFirebaseDataReceiverContext,notificationBeanListClassNew,ApiConstants.NotificationBeanNotice);
                        }else if (title.equalsIgnoreCase(context.getResources().getString(R.string.security_alaram_notification))){
                            commonSharedPreference.setNotificationData(mFirebaseDataReceiverContext,notificationBeanListClassNew,ApiConstants.NotificationBeanSecurity);
                        }

                    }
                }
            }
        }
    }

    public static boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }

        return isInBackground;
    }



    private void handleNotification()
    {
        r.play();

        Handler handler;
        Runnable runnable;

        handler = new Handler();
        runnable =  new Runnable() {
            @Override
            public void run() {
                r.stop();
            }
        };
        handler.postDelayed(runnable, SPLASH_TIME_OUT);

    }

}
