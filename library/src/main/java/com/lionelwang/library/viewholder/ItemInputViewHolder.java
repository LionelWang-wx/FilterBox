package com.lionelwang.library.viewholder;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.lionelwang.library.R;
import com.lionelwang.library.base.BaseViewHolder;
import com.lionelwang.library.utils.ToastUtil;

/**
 * 输入框
 */
public class ItemInputViewHolder extends BaseViewHolder<String> {

    private EditText inputText;
    private TextView labelText;
    private Context context;
    private String label;
    private String hint;
    private String content;

    public ItemInputViewHolder(@NonNull View itemView,Context context,Builder builder){
        super(itemView);
        initView(itemView);
        this.context = context;
        this.label = builder.label;
        this.hint = builder.hint;
        this.content = builder.content;
        initData();
    }

    private void initData(){
        inputText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                content = inputText.getText().toString();
            }
        });
    }

    private void initView(View itemView){
        inputText = itemView.findViewById(R.id.edt_input);
        labelText = itemView.findViewById(R.id.tv_label);
    }

    public String getItemStyleData(){
        return content;
    }

    @Override
    public void bindViewHolder(BaseViewHolder holder,int position){
        if(holder instanceof ItemInputViewHolder){
            ((ItemInputViewHolder)holder).labelText.setText(label);
            ((ItemInputViewHolder)holder).inputText.setText(content);
            ((ItemInputViewHolder)holder).inputText.setHint(hint);
        }
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

        public ItemInputViewHolder build(View view, Context context){
            return new ItemInputViewHolder(view,context,this);
        }
    }
}
