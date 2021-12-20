package com.lionelwang.library.style.itemstyle;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.lionelwang.library.R;
import com.lionelwang.library.base.BaseItemStyle;
import com.lionelwang.library.base.BaseViewHolder;
import com.lionelwang.library.viewholder.ItemDiyRangeViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 自定义范围样式
 */
public class ItemDiyRangeStyle extends BaseItemStyle<Map<String,String>> {

    private Context context;
    private String label;
    private String minHint;
    private String minValue;
    private String maxHint;
    private String maxValue;
    private ItemDiyRangeViewHolder viewHolder;

    public ItemDiyRangeStyle(Context context,Builder builder){
        this.context = context;
        this.minHint = builder.minHint;
        this.maxHint = builder.maxHint;
        this.label = builder.label;
        this.minValue = builder.minValue;
        this.maxValue = builder.maxValue;
    }

    @Override
    public int getItemLayoutRes() {
        return R.layout.item_diy_range_style;
    }

    @Override
    public BaseViewHolder getItemViewHolder(View view){
        return viewHolder = new ItemDiyRangeViewHolder.Builder()
                .setLabel(label)
                .setMaxHint(maxHint)
                .setMaxValue(maxValue)
                .setMinHint(minHint)
                .setMinValue(minValue)
                .build(view,context);
    }

    @Override
    public int getItemStyleMode() {
        return 0;
    }

    @Override
    public Map<String,String> getItemStyleData(){
        return viewHolder.getItemStyleData();
    }

    @Override
    public String getItemLabel(){
        return label;
    }

    @Override
    public void clearSelectedItem(){
        minValue = "";
        maxValue = "";
    }

    public static class Builder{

        private String label;
        private String minHint;
        private String minValue;
        private String maxHint;
        private String maxValue;

        public Builder setLabel(String label){
            this.label = label;
            return this;
        }

        public Builder setMinHint(String minHint){
            this.minHint = minHint;
            return this;
        }

        public Builder setMinValue(String minValue){
            this.minValue = minValue;
            return this;
        }

        public Builder setMaxHint(String maxHint){
            this.maxHint = maxHint;
            return this;
        }

        public Builder setMaxValue(String maxValue){
            this.maxValue = maxValue;
            return this;
        }

        public ItemDiyRangeStyle build(Context context) {
            return new ItemDiyRangeStyle(context,this);
        }
    }
}
