package com.lionelwang.library.utils;

import java.util.ArrayList;
import java.util.List;

public class DataUtils {
    //判断list为空
    public static boolean isEmpty(List list) {
        if (list == null || list.size() == 0) {
            return true;
        }
        return false;
    }

    public static List getData() {
        ArrayList list = new ArrayList<String>();
        for (int i = 0; i < 100; i++) {
            list.add(i + "");
        }
        return list;
    }
}
