package com.lionelwang.library.click;

/**
 * item的点击
 */
public interface OnItemContentClickListener<T> {

    /**
     * 带回item信息
     * @param data
     */
    void onItemSelectedContent(T data);

    /**
     * 带回bar信息
     */
    void onItemSelectedBar(T data);
}
