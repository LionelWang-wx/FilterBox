package com.lionelwang.library.click;

/**
 * 滑动监听
 */
public interface SlideListener<T> {
    void onSlideChange(T data,int options1,int options2,int options3);
}
