package com.lionelwang.library.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * author:Teck
 * date: 2021/5/17 15:12
 * description:
 */
public class ToastUtil {

    private static Toast mToast;
    @SuppressLint("StaticFieldLeak")
    private static Context context;
    public static void init(Context context) {
        ToastUtil.context = context;
    }

    public static void show(String msg) {
        try {
            if (context != null && !TextUtils.isEmpty(msg)) {
                if(mToast != null){
                    mToast.cancel();
                }
                mToast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
                mToast.setText(msg);
                mToast.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
