package com.dragon.testfcm.FirebaseServices;

/**
 * Created by Administrator on 3/12/2018.
 */

public class NotificationBean {
    private String mNotificatioType,mNotificationMsg;
    private boolean mNotificationStatus;

    public String getmNotificatioType() {
        return mNotificatioType;
    }

    public void setmNotificatioType(String mNotificatioType) {
        this.mNotificatioType = mNotificatioType;
    }

    public String getmNotificationMsg() {
        return mNotificationMsg;
    }

    public void setmNotificationMsg(String mNotificationMsg) {
        this.mNotificationMsg = mNotificationMsg;
    }

    public boolean ismNotificationStatus() {
        return mNotificationStatus;
    }

    public void setmNotificationStatus(boolean mNotificationStatus) {
        this.mNotificationStatus = mNotificationStatus;
    }
}
