package com.lionelwang.library.base;

import android.view.View;

import com.lionelwang.library.bean.TextBean;

import java.util.List;
import java.util.Map;

/**
 *
 */
public abstract class BaseItemStyle<T> {
    public abstract int getItemLayoutRes();
    public abstract BaseViewHolder getItemViewHolder(View view);
    public abstract int getItemStyleMode();
    public abstract T getItemStyleData();
    public abstract String getItemLabel();
    public abstract void clearSelectedItem();
}
