package com.lionelwang.library.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.lionelwang.library.R;
import com.lionelwang.library.base.BaseDialog;
import com.lionelwang.library.bean.TextBean;
import com.lionelwang.library.click.DialogActionListener;
import com.lionelwang.library.click.DialogSelectedChangeListener;
import com.lionelwang.library.click.DialogSelectedListener;
import com.lionelwang.library.click.OnItemContentClickListener;
import com.lionelwang.library.click.SelectedListener;
import com.lionelwang.library.mode.dialogmode.DialogMode;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;

import java.util.List;

/**
 * 带bar的多级选择
 */
public class BarSingleDialog extends BaseDialog {

    private Context context;
    private DialogMode dialogMode;
    private String title;
    private List<TextBean> barTitles;
    private List<TextBean> contents;
    private DialogSelectedListener dialogSelectedListener;
    private BasePopupView xPopup;
    //是否展示全部选项 默认false
    private boolean isShowAllSelect;
    private MultiLevelPopup multiLevelPopup;
    //SingleBarMode的选中数据监听
    private DialogSelectedChangeListener dialogSelectedChangeListener;

    public BarSingleDialog(Context context, Builder builder) {
        this.context = context;
        this.dialogMode = builder.dialogMode;
        this.title = builder.title;
        this.barTitles = builder.barTitles;
        this.contents = builder.contents;
        this.dialogSelectedListener = builder.dialogSelectedListener;
        this.isShowAllSelect = builder.isShowAllSelect;
        this.dialogSelectedChangeListener = builder.dialogSelectedChangeListener;
        this.createDialog();
    }

    @Override
    public void createDialog() {
        multiLevelPopup = new MultiLevelPopup.Builder()
                .setTitle(title)
                .setBarTitles(barTitles)
                .setContents(contents)
                .setDialogSelectedListener(dialogSelectedListener)
                .setShowAllSelect(isShowAllSelect)
                .setDialogSelectedChangeListener(dialogSelectedChangeListener)
                .build(context);
        xPopup = new XPopup.Builder(context)
//                .moveUpToKeyboard(false) //如果不加这个，评论弹窗会移动到软键盘上面
                .enableDrag(true)
//                .isDestroyOnDismiss(true) //对于只使用一次的弹窗，推荐设置这个
//                .isThreeDrag(true) //是否开启三阶拖拽，如果设置enableDrag(false)则无效
                .asCustom(multiLevelPopup);
    }

    /**
     * 打开不同的二级弹窗
     * SINGLE_BAR_MODE 带Bar多级选择
     */
    public BarSingleDialog showDialog() {
        switch (dialogMode) {
            case SINGLE_BAR_MODE:
                xPopup.show();
                break;
        }
        return this;
    }

    /**
     * 刷新适配器
     */
    public void refresh() {
        multiLevelPopup.notifyDataSetChanged();
    }

    public static class Builder {
        private DialogMode dialogMode;
        private String title;
        private List<TextBean> barTitles;
        private List<TextBean> contents;
        private DialogSelectedListener dialogSelectedListener;
        //是否展示全部选项 默认false
        private boolean isShowAllSelect = false;
        //SingleBarMode的选中数据监听
        private DialogSelectedChangeListener dialogSelectedChangeListener;

        public Builder setDialogSelectedChangeListener(DialogSelectedChangeListener dialogSelectedChangeListener) {
            this.dialogSelectedChangeListener = dialogSelectedChangeListener;
            return this;
        }

        public Builder setShowAllSelect(boolean isShowAllSelect) {
            this.isShowAllSelect = isShowAllSelect;
            return this;
        }


        public Builder setDialogSelectedListener(DialogSelectedListener dialogSelectedListener) {
            this.dialogSelectedListener = dialogSelectedListener;
            return this;
        }


        public Builder setDialogMode(DialogMode dialogMode) {
            this.dialogMode = dialogMode;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setBarTitles(List<TextBean> barTitles) {
            this.barTitles = barTitles;
            return this;
        }

        public Builder setContents(List<TextBean> contents) {
            this.contents = contents;
            return this;
        }

        public BarSingleDialog build(Context context) {
            return new BarSingleDialog(context, this);
        }
    }
}
