package com.lionelwang.filterbox;

import android.content.Context;
import android.view.View;
import com.lionelwang.library.base.BaseItemStyle;
import com.lionelwang.library.base.BaseViewHolder;
import com.lionelwang.library.click.SelectedCallBack;
import com.lionelwang.library.viewholder.ItemInputViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 列表信息样式
 */
public class ItemListInfoStyle extends BaseItemStyle<String>{

    private Context context;
    private String label;
    private String data;
    private SelectedCallBack selectedCallBack;

    public ItemListInfoStyle(Context context,Builder builder){
        this.context = context;
        this.label = builder.label;
        this.data = builder.data;
        this.selectedCallBack = builder.selectedCallBack;
    }

    @Override
    public int getItemLayoutRes(){
        return R.layout.item_test;
    }

    @Override
    public BaseViewHolder getItemViewHolder(View view){
        return new ItemListInfoViewHolder.Builder()
                .setLabel(label)
                .setData(data)
                .setCallBack(selectedCallBack)
                .build(view,context);
    }

    @Override
    public int getItemStyleMode(){
        return 0;
    }

    @Override
    public String getItemStyleData(){
        return null;
    }

    @Override
    public String getItemLabel(){
        return label;
    }

    @Override
    public void clearSelectedItem(){

    }

    public static class Builder{

        private String label;
        private String data;
        private SelectedCallBack selectedCallBack;

        public Builder setLabel(String label){
            this.label = label;
            return this;
        }

        public Builder setData(String data){
            this.data = data;
            return this;
        }

        public Builder setCallBack(SelectedCallBack selectedCallBack) {
            this.selectedCallBack = selectedCallBack;
            return this;
        }

        public ItemListInfoStyle build(Context context){
            return new ItemListInfoStyle(context,this);
        }
    }
}
