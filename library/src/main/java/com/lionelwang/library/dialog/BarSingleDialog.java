package com.lionelwang.library.dialog;

import android.content.Context;

import com.lionelwang.library.base.BaseDialog;
import com.lionelwang.library.mode.dialogmode.DialogMode;

/**
 *
 */
public class BarSingleDialog extends BaseDialog {

    private Context context;
    private DialogMode dialogMode;

    public BarSingleDialog(Context context,Builder builder){
        this.context = context;
        this.dialogMode = builder.dialogMode;
        this.createDialog();
    }

    @Override
    public void createDialog(){
        //初始化Dialog

    }

    /**
     * 打开不同的二级弹窗
     * SINGLE_BAR_MODE 带Bar城市选择
     */
    public BarSingleDialog showDialog(){
        switch (dialogMode){
            case SINGLE_BAR_MODE:
                barSingleDialog();
                break;
        }
        return this;
    }

    /**
     * 带Bar单选弹窗
     */
    private BarSingleDialog barSingleDialog(){
        return this;
    }

    public static class Builder{

        private DialogMode dialogMode;

        public Builder setDialogMode(DialogMode dialogMode){
            this.dialogMode = dialogMode;
            return this;
        }
        public BarSingleDialog build(Context context){
            return new BarSingleDialog(context,this);
        }
    }
}
