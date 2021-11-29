package com.lionelwang.library.viewholder;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.lionelwang.library.R;
import com.lionelwang.library.base.BaseViewHolder;
import com.lionelwang.library.bean.JsonBean;
import com.lionelwang.library.bean.TextBean;
import com.lionelwang.library.click.DialogActionListener;
import com.lionelwang.library.click.DialogSelectedListener;
import com.lionelwang.library.click.SlideListener;
import com.lionelwang.library.dialog.DialogManager;
import com.lionelwang.library.dialog.PickerDialog;
import com.lionelwang.library.mode.dialogmode.DialogMode;
import com.lionelwang.library.utils.DataUtils;
import com.lionelwang.library.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 点击弹出二级弹窗
 */
public class ItemClickPopupViewHolder extends BaseViewHolder {

    private TextView labelView;
    private TextView selectorView;
    private Context context;

    private String label;
    private String selected;
    private DialogActionListener actionListener;

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
    //弹窗模式
    private DialogMode dialogMode;
    //联动数据是否完整传入
    private boolean isLinkageCompleteData;
    //滑动监听
    private SlideListener slideListener;

    public ItemClickPopupViewHolder(@NonNull View itemView,Context context,Builder builder){
        super(itemView);
        initView(itemView);
        this.context = context;
        this.label = builder.label;
        this.selected = builder.selected;

        this.options1Items = builder.options1Items;
        this.options2Items = builder.options2Items;
        this.options3Items = builder.options3Items;

        this.nOptions1Items = builder.nOptions1Items;
        this.nOptions2Items = builder.nOptions2Items;
        this.nOptions3Items = builder.nOptions3Items;

        this.actionListener = builder.actionListener;
        this.isLinkageCompleteData = builder.isLinkageCompleteData;
        this.slideListener = builder.slideListener;

        this.dialogMode = builder.dialogMode;
    }

    @Override
    public void bindViewHolder(BaseViewHolder holder){
        if(holder instanceof ItemClickPopupViewHolder){
            labelView.setText(label);
            selectorView.setText(selected);
            //打开二级弹窗
            selectorView.setOnClickListener(v -> {
                DialogManager pickerDialog = new DialogManager.Builder()
                        .setDialogMode(dialogMode)
                        .setNOptionsItems(nOptions1Items)
                        .setNOptionsItems(nOptions1Items,nOptions2Items,nOptions3Items)
                        .setOptionsItems(options1Items,options2Items,options3Items)
                        .setDialogActionListener(actionListener)
                        .setLinkageCompleteData(isLinkageCompleteData)
                        .setDialogSelectedListener(new DialogSelectedListener(){
                            @Override
                            public void onDialogSelect(int position1, int position2, int position3, View view,DialogMode dialogMode){
                                String str = "";
                                switch (dialogMode){
                                    case SINGLE_LEVEL_MODE:
                                        str = nOptions1Items.get(position1).getText();
                                        break;
                                    case THREE_LEVEL_MODE:
                                        str = nOptions1Items.get(position1).getText()+"-"+
                                                nOptions2Items.get(position2).getText()+"-"+
                                                nOptions3Items.get(position3).getText();
                                        break;
                                    case THREE_LINKAGE_MODE:
                                        str = options1Items.get(position1).getName()+"-"+
                                                options2Items.get(position1).get(position2)+"-"+
                                                options3Items.get(position1).get(position2).get(position3);
                                        break;
//                                    case SINGLE_BAR_MODE:
//
//                                        break;
                                }
                                selectorView.setText(str);
                            }

                            @Override
                            public void onSelectChanged(int position1, int position2, int position3,DialogMode dialogMode,boolean isLinkageCompleteDa){
                                switch (dialogMode){
                                    case THREE_LINKAGE_MODE:
                                        //联动数据不是完整传入,需要处理联动数据变化
                                        if(!isLinkageCompleteDa){
                                            if(TextUtils.isEmpty(options2Items.get(position1).get(position2).getId()) &&
                                                    TextUtils.isEmpty(options2Items.get(position1).get(position2).getText())){
                                                //情况二：第二个item数据为空 滑动的为第一列
                                                slideListener.onSlideChange(options1Items.get(position1));
                                            }else if(TextUtils.isEmpty(options3Items.get(position1).get(position2).get(position3).getId()) &&
                                                    TextUtils.isEmpty(options3Items.get(position1).get(position2).get(position3).getText())){
                                                //情况一：第三个item数据为空 滑动的为第二列
                                                slideListener.onSlideChange(options2Items.get(position1).get(position2));
                                            }
                                        }
                                        break;
//                                    case SINGLE_BAR_MODE:
//
//                                        break;
                                }
                            }
                        })
                        .build(context);
                pickerDialog.show();
            });
        }
    }

    private void initView(View itemView){
        labelView = itemView.findViewById(R.id.popup_label);
        selectorView = itemView.findViewById(R.id.popup_selector);
    }

    public static class Builder{
        private String label;
        private String selected;
        private DialogActionListener actionListener;
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
        //弹窗模式
        private DialogMode dialogMode;
        //联动数据是否完整传入
        private boolean isLinkageCompleteData;
        //滑动监听
        private SlideListener slideListener;

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
        public Builder setLabel(String label) {
            this.label = label;
            return this;
        }

        public Builder setSelected(String selected) {
            this.selected = selected;
            return this;
        }

        public Builder setDialogActionListener(DialogActionListener actionListener){
            this.actionListener = actionListener;
            return this;
        }

        public Builder setSlideListener(SlideListener slideListener){
            this.slideListener = slideListener;
            return this;
        }


        public ItemClickPopupViewHolder build(View view, Context context){
            return new ItemClickPopupViewHolder(view,context,this);
        }
    }
}
