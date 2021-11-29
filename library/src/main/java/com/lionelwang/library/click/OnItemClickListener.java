package com.lionelwang.library.click;

import java.util.List;

/**
 * item的点击
 */
public interface OnItemClickListener<T> {

    void onItemClick(List<T> positions);
}
