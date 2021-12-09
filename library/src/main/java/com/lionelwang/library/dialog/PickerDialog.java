package com.lionelwang.library.dialog;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnDismissListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.lionelwang.library.base.BaseDialog;
import com.lionelwang.library.bean.TextBean;
import com.lionelwang.library.click.DialogActionListener;
import com.lionelwang.library.click.DialogSelectedListener;
import com.lionelwang.library.mode.dialogmode.DialogMode;
import com.lionelwang.library.utils.DataUtils;
import com.lionelwang.library.utils.ToastUtil;

import java.util.List;

/**
 * PickerView二次封装
 */
public class PickerDialog extends BaseDialog {

    private Context context;
    //第一列
    private List<TextBean> options1Items;
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
    //选择器
    private OptionsPickerView pvOptions;
    //选择器Builder
    private OptionsPickerBuilder pvOptionsBuilder;
    //弹窗选择回调
    private DialogSelectedListener selectedListener;
    //弹窗操作指令
    private DialogActionListener actionListener;
    //当前弹窗模式
    private DialogMode dialogMode;
    //联动数据是否完整传入
    private boolean isLinkageCompleteData;
    //是否展示全部选项
    private boolean isShowAllSelect;
    //弹窗标题
    private String titleName;


    public PickerDialog(Context context, Builder builder) {
        this.context = context;
        this.options1Items = builder.options1Items;
        this.options2Items = builder.options2Items;
        this.options3Items = builder.options3Items;

        this.nOptions1Items = builder.nOptions1Items;
        this.nOptions2Items = builder.nOptions2Items;
        this.nOptions3Items = builder.nOptions3Items;

        this.selectedListener = builder.selectedListener;
        this.actionListener = builder.actionListener;
        this.isShowAllSelect = builder.isShowAllSelect;
        this.titleName = builder.titleName;

        this.dialogMode = builder.dialogMode;
        this.isLinkageCompleteData = builder.isLinkageCompleteData;
        this.createDialog();
    }

    @Override
    public void createDialog() {
        initPickerView();
    }


    /**
     * 初始化PickerView
     */
    private void initPickerView() {
        //弹出选择
        pvOptionsBuilder = new OptionsPickerBuilder(context, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //这里是确认监听
                selectedListener.onDialogSelect(options1, options2, options3, v, dialogMode);
            }
        }).setTitleText(titleName)
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜
                .setContentTextSize(20)
                .setSelectOptions(0,0,0)//设置默认选中
                .setOptionsSelectChangeListener(new OnOptionsSelectChangeListener(){
                    @Override
                    public void onOptionsSelectChanged(int options1, int options2, int options3){
                        //item滑动监听
//                        0=1=0
//                        四川-广元-
//                        0=0=0
//                        四川-成都-锦江
                        //options无法区分
//                        Log.e("TAG","options1 = "+options1);
                        selectedListener.onSelectChanged(options1, options2, options3, dialogMode, isLinkageCompleteData);
                    }
                });
        pvOptions = pvOptionsBuilder.build();

        pvOptions.setOnDismissListener(new OnDismissListener(){
            @Override
            public void onDismiss(Object o){
                //二级弹窗销毁时设置主弹窗展示   如何为空说明不需要执行此操作
                if (actionListener != null){
                    actionListener.show();
                }
            }
        });
        switch (dialogMode){
            case SINGLE_LEVEL_MODE:
                if (isShowAllSelect) {
                    nOptions1Items.add(0, new TextBean("0", "全部", false));
                }
                break;
            case THREE_LEVEL_MODE:
                if (isShowAllSelect) {
                    nOptions1Items.add(0, new TextBean("0", "全部", false));
                    nOptions2Items.add(0, new TextBean("0", "全部", false));
                    nOptions3Items.add(0, new TextBean("0", "全部", false));
                }
                break;
            case THREE_LINKAGE_MODE:
//暂时未实现
//                if (isShowAllSelect) {
//                    options1Items.add(0, new TextBean("0", "全部", false));
//                    options2Items.add(0, new TextBean("0", "全部", false));
//                    options3Items.add(0, new TextBean("0", "全部", false));
//                }
                break;
        }
    }


    /**
     * 打开不同的二级弹窗
     * SINGLE_LEVEL_MODE(0)//单行滚动
     * THREE_LEVEL_MODE(1)//三级联动
     * SINGLE_BAR_MODE(2)//带Bar城市选择
     */
    public PickerDialog showDialog() {
        switch (dialogMode) {
            case SINGLE_LEVEL_MODE:
                singleDialog();
                break;
            case THREE_LEVEL_MODE:
                threeDialog();
                break;
            case THREE_LINKAGE_MODE:
                threeLinkageDialog();
                break;
        }
        return this;
    }


    /**
     * 单行滚动
     */
    private PickerDialog singleDialog() {
        if (DataUtils.isEmpty(nOptions1Items)) {
            ToastUtil.show("单行滚动无数据");
            return this;
        }
        pvOptions.setPicker(nOptions1Items);
        pvOptions.show();
        if (actionListener != null)
            actionListener.hide();
        return this;
    }

    /**
     * 三级不联动
     */
    private PickerDialog threeDialog() {
        if (DataUtils.isEmpty(nOptions1Items) &&
                DataUtils.isEmpty(nOptions2Items) &&
                DataUtils.isEmpty(nOptions3Items)) {
            ToastUtil.show("三级不联动无数据");
            return this;
        }
        pvOptions.setNPicker(nOptions1Items, nOptions2Items, nOptions3Items);
        pvOptions.show();
        if (actionListener != null)
            actionListener.hide();
        return this;
    }


    /**
     * 三级联动
     */
    private PickerDialog threeLinkageDialog() {
        if (DataUtils.isEmpty(options1Items) &&
                DataUtils.isEmpty(options2Items) &&
                DataUtils.isEmpty(options3Items)){
            ToastUtil.show("三级联动无数据");
            return this;
        }
        pvOptions.setPicker(options1Items, options2Items, options3Items);
        pvOptions.show();
        if (actionListener != null)
            actionListener.hide();
        return this;
    }

    /**
     * 刷新
     * 先设置选中位置,在设置数据
     */
    public void refresh(int option1,int option2,int option3){
        pvOptionsBuilder.setSelectOptions(option1,option2,option3);
        pvOptions.setPicker(options1Items, options2Items, options3Items);
        Log.e("option","option1="+option1+"    option2="+option2+"  option3="+option3);
    }

    public static class Builder {
        //第一列
        private List<TextBean> options1Items;
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
        //是否展示全部选项
        private boolean isShowAllSelect;
        //弹窗标题
        private String titleName;

        public Builder setTitleName(String titleName) {
            this.titleName = titleName;
            return this;
        }

        public Builder setShowAllSelect(boolean showAllSelect) {
            this.isShowAllSelect = showAllSelect;
            return this;
        }


        public Builder setLinkageCompleteData(boolean isLinkageCompleteData) {
            this.isLinkageCompleteData = isLinkageCompleteData;
            return this;
        }

        /**
         * 三级联动
         *
         * @param options1Items
         * @param options2Items
         * @param options3Items
         * @return
         */
        public Builder setOptionsItems(List<TextBean> options1Items,
                                       List<List<TextBean>> options2Items,
                                       List<List<List<TextBean>>> options3Items) {
            this.options1Items = options1Items;
            this.options2Items = options2Items;
            this.options3Items = options3Items;
            return this;
        }

        /**
         * 三级不联动
         *
         * @param nOptions1Items
         * @param nOptions2Items
         * @param nOptions3Items
         * @return
         */
        public Builder setNOptionsItems(List<TextBean> nOptions1Items,
                                        List<TextBean> nOptions2Items,
                                        List<TextBean> nOptions3Items) {
            this.nOptions1Items = nOptions1Items;
            this.nOptions2Items = nOptions2Items;
            this.nOptions3Items = nOptions3Items;
            return this;
        }

        /**
         * 不联动的单行数据
         *
         * @param nOptions1Items
         * @return
         */
        public Builder setNOptionsItems(List<TextBean> nOptions1Items) {
            this.nOptions1Items = nOptions1Items;
            return this;
        }

        public Builder setDialogMode(DialogMode dialogMode) {
            this.dialogMode = dialogMode;
            return this;
        }

        public Builder setDialogSelectedListener(DialogSelectedListener selectedListener) {
            this.selectedListener = selectedListener;
            return this;
        }

        public Builder setDialogActionListener(DialogActionListener actionListener) {
            this.actionListener = actionListener;
            return this;
        }

        public PickerDialog build(Context context) {
            return new PickerDialog(context, this);
        }
    }
}
