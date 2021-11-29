package com.lionelwang.library.dialog;


import android.content.Context;
import android.view.View;

import com.lionelwang.library.base.BaseDialog;
import com.lionelwang.library.bean.JsonBean;
import com.lionelwang.library.bean.TextBean;
import com.lionelwang.library.click.DialogActionListener;
import com.lionelwang.library.click.DialogSelectedListener;
import com.lionelwang.library.mode.dialogmode.DialogMode;

import java.util.List;

/**
 * 弹窗Manager
 **/
public class DialogManager{

    private Context context;
    //第一列
    private List<JsonBean> options1Items;
    //第二列
    private List<List<TextBean>> options2Items;
    //第三列
    private List<List<List<TextBean>>> options3Items;

    //不联动第一列
    private List<TextBean> nOptions1Items;
    //不联动第二列
    private List<TextBean> nOptions2Items;
    //不联动第三列
    private List<TextBean> nOptions3Items;

    //当前弹窗模式
    private DialogMode dialogMode;
    //二级弹窗选择回调
    private DialogSelectedListener selectedListener;
    //弹窗操作指令
    private DialogActionListener actionListener;
    private PickerDialog pickerDialog = null;
    private BarSingleDialog barSingleDialog = null;
    //联动数据是否完整传入
    private boolean isLinkageCompleteData;

    public DialogManager(Context context,Builder builder){
        this.context = context;
        this.options1Items = builder.options1Items;
        this.options2Items = builder.options2Items;
        this.options3Items = builder.options3Items;

        this.nOptions1Items = builder.nOptions1Items;
        this.nOptions2Items = builder.nOptions2Items;
        this.nOptions3Items = builder.nOptions3Items;

        this.selectedListener = builder.selectedListener;
        this.actionListener = builder.actionListener;

        this.dialogMode = builder.dialogMode;

        this.isLinkageCompleteData = builder.isLinkageCompleteData;
        initData();
    }

    private void initData() {
        switch (dialogMode){
            case SINGLE_LEVEL_MODE:
            case THREE_LEVEL_MODE:
            case THREE_LINKAGE_MODE:
                 pickerDialog = new PickerDialog.Builder()
                        .setDialogMode(dialogMode)
                        .setNOptionsItems(nOptions1Items)
                        .setNOptionsItems(nOptions1Items,nOptions2Items,nOptions3Items)
                        .setOptionsItems(options1Items,options2Items,options3Items)
                        .setDialogActionListener(actionListener)
                        .setDialogSelectedListener(selectedListener)
                        .setLinkageCompleteData(isLinkageCompleteData)
                        .build(context).showDialog();
                break;
            case SINGLE_BAR_MODE:
                barSingleDialog = new BarSingleDialog.Builder()
                        .build(context);
                break;
        }
    }

    //根据弹窗模式,打开不同样式的弹窗
    public DialogManager show(){
        switch (dialogMode){
            case SINGLE_LEVEL_MODE:
            case THREE_LEVEL_MODE:
            case THREE_LINKAGE_MODE:
                if (pickerDialog != null){
                    pickerDialog.showDialog();
                }
                break;
            case SINGLE_BAR_MODE:
                if (barSingleDialog != null){
                    barSingleDialog.showDialog();
                }
                break;
        }
        return this;
    }


    public static class Builder{
        //第一列
        private List<JsonBean> options1Items;
        //第二列
        private List<List<TextBean>> options2Items;
        //第三列
        private List<List<List<TextBean>>> options3Items;

        //不联动第一列
        private List<TextBean> nOptions1Items;
        //不联动第二列
        private List<TextBean> nOptions2Items;
        //不联动第三列
        private List<TextBean> nOptions3Items;

        //当前弹窗模式
        private DialogMode dialogMode;
        //二级弹窗选择回调
        private DialogSelectedListener selectedListener;
        //弹窗操作指令
        private DialogActionListener actionListener;

        //联动数据是否完整传入
        private boolean isLinkageCompleteData;

        public Builder setLinkageCompleteData(boolean isLinkageCompleteData){
            this.isLinkageCompleteData = isLinkageCompleteData;
            return this;
        }

        /**
         * 三级联动
         * @param options1Items
         * @param options2Items
         * @param options3Items
         * @return
         */
        public Builder setOptionsItems(List<JsonBean> options1Items,
                                                    List<List<TextBean>> options2Items,
                                                    List<List<List<TextBean>>> options3Items){
            this.options1Items = options1Items;
            this.options2Items = options2Items;
            this.options3Items = options3Items;
            return this;
        }

        /**
         * 三级不联动
         * @param nOptions1Items
         * @param nOptions2Items
         * @param nOptions3Items
         * @return
         */
        public Builder setNOptionsItems(List<TextBean> nOptions1Items,
                                                     List<TextBean> nOptions2Items,
                                                     List<TextBean> nOptions3Items){
            this.nOptions1Items = nOptions1Items;
            this.nOptions2Items = nOptions2Items;
            this.nOptions3Items = nOptions3Items;
            return this;
        }

        /**
         * 不联动的单行数据
         * @param nOptions1Items
         * @return
         */
        public Builder setNOptionsItems(List<TextBean> nOptions1Items){
            this.nOptions1Items = nOptions1Items;
            return this;
        }

        public Builder setDialogMode(DialogMode dialogMode){
            this.dialogMode = dialogMode;
            return this;
        }

        public Builder setDialogSelectedListener(DialogSelectedListener selectedListener){
            this.selectedListener = selectedListener;
            return this;
        }

        public Builder setDialogActionListener(DialogActionListener actionListener){
            this.actionListener = actionListener;
            return this;
        }

        public DialogManager build(Context context){
            return new DialogManager(context,this);
        }
    }
}
