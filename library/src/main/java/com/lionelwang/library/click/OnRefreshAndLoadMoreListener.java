package com.lionelwang.library.click;

import androidx.annotation.NonNull;

import com.scwang.smartrefresh.layout.api.RefreshLayout;

public interface OnRefreshAndLoadMoreListener {
    void onRefresh(@NonNull RefreshLayout refreshLayout, int pageIndex);
    void onLoadMore(@NonNull RefreshLayout refreshLayout, int pageIndex);
}
