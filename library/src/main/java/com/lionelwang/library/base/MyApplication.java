package com.lionelwang.library.base;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

/**
 * 初始化类
 */
public class MyApplication{

    public MyApplication() {
        initSmartRefresh();
    }

    //全局初始化刷新布局
    private void initSmartRefresh() {
        //设置全局的Header构建器
        //指定为经典Header，默认是 贝塞尔雷达Header
        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> new ClassicsHeader(context).setSpinnerStyle(SpinnerStyle.Translate));
        //设置全局的Footer构建器
        //指定为经典Footer，默认是 BallPulseFooter
        SmartRefreshLayout.setDefaultRefreshFooterCreator((context, layout) -> new ClassicsFooter(context).setSpinnerStyle(SpinnerStyle.Translate));
    }
}
