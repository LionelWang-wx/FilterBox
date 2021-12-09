package com.lionelwang.library.base;

import android.view.View;

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
