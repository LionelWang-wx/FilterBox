package com.lionelwang.library.viewholder;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.KeyboardUtils;
import com.lionelwang.library.R;
import com.lionelwang.library.base.BaseViewHolder;

import java.util.HashMap;
import java.util.Map;

/**
 * 自定义范围
 */
public class ItemDiyRangeViewHolder extends BaseViewHolder<Map<String, String>> {

    private Context context;
    private EditText minValueView;
    private EditText maxValueView;
    private TextView labelView;
    private String label;
    private String minHint;
    private String minValue;
    private String maxHint;
    private String maxValue;
    private Map<String, String> values = new HashMap<>();

    public ItemDiyRangeViewHolder(@NonNull View itemView, Context context, Builder builder) {
        super(itemView);
        initView(itemView);
        this.context = context;
        this.label = builder.label;
        this.minHint = builder.minHint;
        this.minValue = builder.minValue;
        this.maxHint = builder.maxHint;
        this.maxValue = builder.maxValue;
        initData();
    }

    private void initData() {
        //注册软键盘的监听
        KeyboardUtils.registerSoftInputChangedListener((Activity)context, height -> {
            if (height > 0 && KeyboardUtils.isSoftInputVisible((Activity)context)) return;
            //收起软键盘时,校验最大最小值
            minValue = minValueView.getText().toString();
            maxValue = maxValueView.getText().toString();
            if (!TextUtils.isEmpty(minValue) && !TextUtils.isEmpty(maxValue)) {
                //校验minValue是否大于maxValue
                //校验maxValue是否小于minValue
                //出现上述情况需要对调minValue与maxValue
                if (Integer.parseInt(minValue) > Integer.parseInt(maxValue)) {
                    minValue = maxValueView.getText().toString();
                    maxValue = minValueView.getText().toString();
                    minValueView.setText(minValue);
                    maxValueView.setText(maxValue);
                }
            }
        });
    }

    private void initView(View itemView) {
        minValueView = itemView.findViewById(R.id.edt_input_min);
        maxValueView = itemView.findViewById(R.id.edt_input_max);
        labelView = itemView.findViewById(R.id.tv_label);
    }

    @Override
    public void bindViewHolder(BaseViewHolder holder,int position) {
        if (holder instanceof ItemDiyRangeViewHolder) {
            ((ItemDiyRangeViewHolder) holder).labelView.setText(label);
            ((ItemDiyRangeViewHolder) holder).minValueView.setText(minValue);
            ((ItemDiyRangeViewHolder) holder).maxValueView.setText(maxValue);
            ((ItemDiyRangeViewHolder) holder).minValueView.setHint(minHint);
            ((ItemDiyRangeViewHolder) holder).maxValueView.setHint(maxHint);
        }
    }

    @Override
    public Map<String, String> getItemStyleData(){
        values.put("minValue", minValue);
        values.put("maxValue", maxValue);
        return values;
    }

    public static class Builder {
        private String label;
        private String minHint;
        private String minValue;
        private String maxHint;
        private String maxValue;

        public Builder setLabel(String label) {
            this.label = label;
            return this;
        }

        public Builder setMinHint(String minHint) {
            this.minHint = minHint;
            return this;
        }

        public Builder setMinValue(String minValue) {
            this.minValue = minValue;
            return this;
        }

        public Builder setMaxHint(String maxHint) {
            this.maxHint = maxHint;
            return this;
        }

        public Builder setMaxValue(String maxValue) {
            this.maxValue = maxValue;
            return this;
        }

        public ItemDiyRangeViewHolder build(View view, Context context) {
            return new ItemDiyRangeViewHolder(view, context, this);
        }
    }
}
