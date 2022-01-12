package com.lionelwang.library.base;

import java.util.ArrayList;
import java.util.List;

/**
 * 资源整理类
 */
public abstract class BaseSource<T> {
    //装载itemStyles的集合
    public List<T> itemStyles;

    public BaseSource() {
        itemStyles = new ArrayList<>();
    }

    /**
     * 添加ItemStyle
     */
    public void addItemStyle(T itemStyle) {
        itemStyles.add(itemStyle);
    }
}
