package com.lionelwang.filterbox.resource;

import android.content.Context;

import androidx.annotation.NonNull;

import com.lionelwang.filterbox.ItemListInfoStyle;
import com.lionelwang.filterbox.MainActivity;
import com.lionelwang.library.base.BaseItemStyle;
import com.lionelwang.library.base.BaseSource;
import com.lionelwang.library.click.OnRefreshAndLoadMoreListener;
import com.lionelwang.library.click.SelectedCallBack;
import com.lionelwang.library.click.SelectedListener;
import com.lionelwang.library.click.TextChangedListener;
import com.lionelwang.library.factory.mainfactory.PopupFilterBoxFactory;
import com.lionelwang.library.style.mainstyle.SearchInfoStyle;
import com.lionelwang.library.utils.ToastUtil;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 自定义筛选框资源
 */
public class DemoDiyImpSource extends BaseSource<BaseItemStyle> {

    private Context context;
    //人员信息
    private List<String> nameList = new ArrayList<>();
    private PopupFilterBoxFactory factory;

    public DemoDiyImpSource(Context context) {
        this.context = context;
        initData();
        //创建ItemStyles
        onCreateItemStyles();
        //初始化弹窗工厂
        initFactory();
    }

    private void onCreateItemStyles() {
        /**
         * 1.同一个布局对应多个数据
         * 2.不同的布局对应不同的数据
         * 3.可自己组装交叉布局
         */
        for (int i = 0; i < nameList.size();i++){
            addItemStyle(new ItemListInfoStyle.Builder()
                    .setLabel("人员信息")//item标题
                    .setData(nameList.get(i))//item数据
                    //item选择监听
                    .setCallBack(new SelectedCallBack<String>(){
                        @Override
                        public void selected(Map<String, String> selectedList) {
                                String name = selectedList.get("人员信息");
                                ToastUtil.show(name);
                            EventBus.getDefault().post(name);
                            factory.dismiss();
                        }
                    })
                    .build(context));
        }
    }

    private void initFactory() {
        //1.先创建主容器样式
        //2.在将样式传到主容器模式中显示
        factory = new PopupFilterBoxFactory.Builder()
                //这里传入主样式 可使用默认样式DefaultStyle
                .setMainStyle(new SearchInfoStyle.Builder()
                        .setTitle("默认主样式")//主样式标题
                        .setItemStyles(itemStyles)//设置item样式数据
                        .setShowSearch(false)//是否开启搜索功能
                        //上拉加载,下拉刷新监听
                        .setOnRefreshLoadMoreListener(new OnRefreshAndLoadMoreListener(){
                            @Override
                            public void onRefresh(@NonNull RefreshLayout refreshLayout, int pageIndex) {
                                ToastUtil.show("onRefresh");
                                refreshLayout.finishRefresh(500);
                            }

                            @Override
                            public void onLoadMore(@NonNull RefreshLayout refreshLayout, int pageIndex) {
                                ToastUtil.show("onLoadMore");
                                refreshLayout.finishLoadMore(500);
                            }
                        })
                        //搜索监听
                        .setTextChangedListener(new TextChangedListener(){
                            @Override
                            public void onAfterTextChanged(String changedText) {
                                ToastUtil.show("搜索"+changedText);
                            }
                        })
                        .build(context))
                .build(context);
    }

    //展示主弹窗
    public void show(){
        factory.show();
    }

    private void initData() {
        nameList.add("张三");
        nameList.add("李四");
        nameList.add("王五");
        nameList.add("小明");
        nameList.add("小红");
        nameList.add("小王");
    }
}
