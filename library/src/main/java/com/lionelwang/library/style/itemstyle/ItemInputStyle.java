package com.lionelwang.library.style.itemstyle;

import android.content.Context;
import android.view.View;
import com.lionelwang.library.R;
import com.lionelwang.library.base.BaseItemStyle;
import com.lionelwang.library.base.BaseViewHolder;
import com.lionelwang.library.viewholder.ItemInputViewHolder;
/**
 * 输入框样式
 */
public class ItemInputStyle extends BaseItemStyle<String>{

    private Context context;
    private String label;
    private String hint;
    private String content;
    private ItemInputViewHolder viewHolder;

    public ItemInputStyle(Context context,Builder builder){
        this.context = context;
        this.label = builder.label;
        this.hint = builder.hint;
        this.content = builder.content;
    }

    @Override
    public int getItemLayoutRes(){
        return R.layout.item_input_style;
    }

    @Override
    public BaseViewHolder getItemViewHolder(View view){
        return viewHolder = new ItemInputViewHolder.Builder()
                .setLabel(label)
                .setHint(hint)
                .setContent(content)
                .build(view,context);
    }

    @Override
    public int getItemStyleMode(){
        return 0;
    }

    @Override
    public String getItemStyleData(){
        return viewHolder.getItemStyleData();
    }

    @Override
    public String getItemLabel(){
        return label;
    }

    @Override
    public void clearSelectedItem(){
        content = "";
    }

    public static class Builder{

        private String label;
        private String hint;
        private String content;

        public Builder setLabel(String label){
            this.label = label;
            return this;
        }

        public Builder setHint(String hint){
            this.hint = hint;
            return this;
        }

        public Builder setContent(String content){
            this.content = content;
            return this;
        }

        public ItemInputStyle build(Context context){
               return new ItemInputStyle(context,this);
        }
    }
}
