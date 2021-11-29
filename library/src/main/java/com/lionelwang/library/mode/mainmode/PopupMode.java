package com.lionelwang.library.mode.mainmode;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.gyf.immersionbar.ImmersionBar;
import com.lionelwang.library.R;
import com.lionelwang.library.base.BaseMainMode;
import com.lionelwang.library.style.mainstyle.DefaultStyle;
import com.lionelwang.library.utils.Utils;

/**
 * 主容器模式：弹窗模式
 * Dialog样式
 */
public class PopupMode implements BaseMainMode{

    private Context context;
    //设置主容器的布局
    private View layoutView;
    private Dialog dialog;

    public PopupMode(Context context,Builder builder) {
           this.context = context;
           this.layoutView = builder.layoutView;
           this.createMainMode();
    }

    @Override
    public void createMainMode() {
           initPopupDialog();
    }



    /**
     * 初始化弹窗模式
     */
    private void initPopupDialog(){
            //创建dialog
            if (dialog == null || !dialog.isShowing()){
                dialog = new Dialog(context,R.style.AlertDialogStyle);
                dialog.setContentView(layoutView);
                initHeight(dialog);
            }
    }

    /**
     * 设置Dialog宽高
     * @param dialog
     */
    private void initHeight(Dialog dialog) {
        Window mDialogWindow = dialog.getWindow();
        //计算屏幕宽高
        Integer[] widthAndHeight = Utils.getWidthAndHeight(((Activity) context).getWindow());
        mDialogWindow.setGravity(Gravity.BOTTOM);
        mDialogWindow.setWindowAnimations(R.style.BottomAnimation);
        mDialogWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, widthAndHeight[1] / 3 * 2);
        ImmersionBar.with((Activity) context, dialog)
                .navigationBarColor(R.color.white)
                .navigationBarDarkIcon(true, 0.2f)
                .init();
    }

    /**
     * 展示弹窗
     */
    public PopupMode show(){
        dialog.show();
        return this;
    }

    /**
     * 隐藏弹窗
     */
    public PopupMode hide(){
        dialog.hide();
        return this;
    }
    /**
     * 销毁弹窗
     */
    public PopupMode dismiss(){
        dialog.dismiss();
        return this;
    }

    /**
     * 建造者模式实现PopupMode
     */
    public static class Builder{
        //设置主容器的布局
        private View layoutView;

        public Builder setLayoutRes(View layoutView){
            this.layoutView = layoutView;
            return this;
        }
        public PopupMode build(Context context){
            return new PopupMode(context,this);
        }
    }
}
