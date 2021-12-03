package com.lionelwang.library.style.itemstyle;

import android.content.Context;
import android.view.View;
import com.lionelwang.library.R;
import com.lionelwang.library.base.BaseItemStyle;
import com.lionelwang.library.base.BaseViewHolder;
import com.lionelwang.library.bean.TextBean;
import com.lionelwang.library.mode.itemMode.ItemClickListMode;
import com.lionelwang.library.viewholder.ItemClickListViewHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 点击列表item样式
 */
public class ItemClickListStyle extends BaseItemStyle<List<TextBean>>{

    private Context context;
    private String label;
    private List<TextBean> selectedList;
    //item是否展示全部选项 默认true
    private boolean isShowAllSelect;
    //item是否展示选项列表 默认false
    private boolean isShowSelectList;
    private boolean isSingleChoice;

    public ItemClickListStyle(Context context,Builder builder) {
        this.context = context;
        this.label = builder.label;
        this.selectedList = builder.selectedList;
        this.isShowAllSelect = builder.isShowAllSelect;
        this.isShowSelectList = builder.isShowSelectList;
        this.isSingleChoice = builder.isSingleChoice;
    }

    @Override
    public int getItemLayoutRes() {
        return R.layout.item_click_list_style;
    }

    @Override
    public BaseViewHolder getItemViewHolder(View view){
        return new ItemClickListViewHolder.Builder()
                .setLabel(label)
                .setSelectedList(selectedList)
                .setShowSelectList(isShowSelectList)
                .setShowAllSelect(isShowAllSelect)
                .setSingleChoice(isSingleChoice)
                .build(view,context);
    }

    @Override
    public int getItemStyleMode(){
        return new ItemClickListMode().getItemStyleMode();
    }

    @Override
    public List<TextBean> getItemStyleData(){
        return selectedList;
    }

    @Override
    public String getItemLabel(){
        return label;
    }

    @Override
    public void clearSelectedItem() {
        for (TextBean bean : selectedList){
           bean.setSelected(false);
        }
    }

    /**
     * 建造者模式,实现ItemClickListStyle
     */
    public static class Builder{
        private String label;
        //选项数据
        private List<TextBean> selectedList;
        private boolean isShowAllSelect = true;
        private boolean isShowSelectList = false;
        private boolean isSingleChoice = true;
        public Builder setLabel(String label) {
            this.label = label;
            return this;
        }
        public Builder setSelectedList(List<TextBean> selectedList) {
            this.selectedList = selectedList;
            return this;
        }

        public Builder setShowAllSelect(boolean showAllSelect) {
            isShowAllSelect = showAllSelect;
            return this;
        }

        public Builder setShowSelectList(boolean showSelectList) {
            isShowSelectList = showSelectList;
            return this;
        }
        public Builder setSingleChoice(boolean isSingleChoice){
            this.isSingleChoice = isSingleChoice;
            return this;
        }

        public ItemClickListStyle build(Context context){
            return new ItemClickListStyle(context,this);
        }
    }
}
