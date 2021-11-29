package com.lionelwang.library.click;

import com.lionelwang.library.bean.TextBean;

import java.util.List;
import java.util.Map;

public interface SelectedCallBack<T> {

    //所有item选中的数据
    void selected(Map<String,T> selectedList);
}
