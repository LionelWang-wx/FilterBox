package com.lionelwang.library.click;

import android.view.View;

import com.lionelwang.library.mode.dialogmode.DialogMode;

public interface DialogSelectedListener {
    //确认选择
    void onDialogSelect(int position1, int position2, int position3, View view, DialogMode dialogMode);
    //item滑动监听
    void onSelectChanged(int position1, int position2, int position3,DialogMode dialogMode,boolean isLinkageCompleteData);
}
