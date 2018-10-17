package com.dragon.testfcm.FirebaseServices;

import android.content.Intent;

/**
 * Created by Administrator on 3/12/2018.
 */

public interface OnNotificationReceiver {

    void onFCMReceiver(Intent intent);
}
