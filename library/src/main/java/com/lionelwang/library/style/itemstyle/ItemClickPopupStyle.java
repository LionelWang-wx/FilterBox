package com.lionelwang.library.style.itemstyle;

import android.app.Dialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.lionelwang.library.R;
import com.lionelwang.library.base.BaseFilterBoxFactory;
import com.lionelwang.library.base.BaseItemStyle;
import com.lionelwang.library.base.BaseViewHolder;
import com.lionelwang.library.bean.JsonBean;
import com.lionelwang.library.bean.TextBean;
import com.lionelwang.library.click.DialogActionListener;
import com.lionelwang.library.click.SlideListener;
import com.lionelwang.library.mode.dialogmode.DialogMode;
import com.lionelwang.library.mode.itemMode.ItemClickPopupMode;
import com.lionelwang.library.viewholder.ItemClickPopupViewHolder;

import java.util.List;
import java.util.Map;

/**
 * 点击弹出二级弹窗item样式
 */
public class ItemClickPopupStyle extends BaseItemStyle<String>{

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

    public ItemClickPopupStyle(Context context,Builder builder){
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
    public int getItemLayoutRes() {
        return R.layout.item_click_popup_style;
    }

    @Override
    public BaseViewHolder getItemViewHolder(View view){
        return new ItemClickPopupViewHolder.Builder()
                .setLabel(label)
                .setSelected(selected)
                .setDialogMode(dialogMode)
                .setDialogActionListener(actionListener)
                .setSlideListener(slideListener)
                .setLinkageCompleteData(false)
                .setNOptionsItems(nOptions1Items)
                .setNOptionsItems(nOptions1Items,nOptions2Items,nOptions3Items)
                .setOptionsItems(options1Items,options2Items,options3Items)
                .setLinkageCompleteData(isLinkageCompleteData)
                .build(view,context);
    }

    @Override
    public int getItemStyleMode(){
        return new ItemClickPopupMode().getItemStyleMode();
    }

    @Override
    public String getItemStyleData() {
        return selected;
    }

    @Override
    public String getItemLabel() {
        return label;
    }

    @Override
    public void clearSelectedItem() {
        selected = "";
    }


    public static class Builder {
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

        public ItemClickPopupStyle build(Context context) {
            return new ItemClickPopupStyle(context, this);
        }
    }
}
