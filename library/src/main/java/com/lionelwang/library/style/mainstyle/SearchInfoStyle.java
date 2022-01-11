package com.lionelwang.library.style.mainstyle;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lionelwang.library.R;
import com.lionelwang.library.base.BaseAdapter;
import com.lionelwang.library.base.BaseItemStyle;
import com.lionelwang.library.base.BaseMainStyle;
import com.lionelwang.library.click.OnRefreshAndLoadMoreListener;
import com.lionelwang.library.click.SelectedListener;
import com.lionelwang.library.click.TextChangedListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

/**
 * 检索信息样式
 */
public class SearchInfoStyle implements BaseMainStyle {

    private Context context;
    private String title;
    //设置主容器的布局样式(将xml布局转成view)
    private View layoutView;
    private TextView titleView;
    private EditText search;
    private RecyclerView recycle;
    private SmartRefreshLayout refresh;
    private BaseAdapter adapter;
    private List<BaseItemStyle> itemStyles;
    private TextChangedListener textChangedListener;
    private OnRefreshAndLoadMoreListener onRefreshAndLoadMoreListener;
    private int pageIndex = 0;

    public SearchInfoStyle(Context context,Builder builder){
        this.context = context;
        this.title = builder.title;
        this.itemStyles = builder.itemStyles;
        this.textChangedListener = builder.textChangedListener;
        this.onRefreshAndLoadMoreListener = builder.onRefreshAndLoadMoreListener;
        this.createStyle();
    }

    @Override
    public void createStyle(){
        if (layoutView == null){
            layoutView = LayoutInflater.from(context).inflate(R.layout.layout_base_search_info_popup,null,false);
            titleView = layoutView.findViewById(R.id.tv_title);
            search = layoutView.findViewById(R.id.search);
            recycle = layoutView.findViewById(R.id.recycle);
            refresh = layoutView.findViewById(R.id.refresh);
            recycle.setLayoutManager(new LinearLayoutManager(context));
            adapter = new com.lionelwang.library.base.BaseAdapter(context,itemStyles);
            recycle.setAdapter(adapter);
            //设置主标题
            if(TextUtils.isEmpty(title)){
                titleView.setVisibility(View.GONE);
            }else{
                titleView.setText(title);
            }
            search.addTextChangedListener(new TextWatcher(){
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s){
                    textChangedListener.onAfterTextChanged(search.getText().toString());
                }
            });
            //刷新监听
            refresh.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                    if (onRefreshAndLoadMoreListener != null){
                        pageIndex = 0;
                        onRefreshAndLoadMoreListener.onRefresh(refreshLayout,pageIndex);
                    }
                }
            });
            //加载更多监听
            refresh.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                    if (onRefreshAndLoadMoreListener != null){
                        pageIndex += 1;
                        onRefreshAndLoadMoreListener.onLoadMore(refreshLayout,pageIndex);
                    }
                }
            });
        }
    }


    /**
     * 数据改变,刷新界面
     */
    public void notifyDataSetChanged(){
        if (adapter != null)
        adapter.notifyDataSetChanged();
        Log.e("TAG","notifyDataSetChanged");
    }


    public void finishRefreshAndLoadMore(){
        refresh.finishRefresh(500);
        refresh.finishLoadMore(500);
        Log.e("TAG","finishRefreshAndLoadMore");
    }
    @Override
    public View getLayoutView() {
        return layoutView;
    }




    public static class Builder{
        private String title;
        private List<BaseItemStyle> itemStyles;
        private TextChangedListener textChangedListener;
        private boolean isShowSearch;
        private OnRefreshAndLoadMoreListener onRefreshAndLoadMoreListener;

        public Builder setOnRefreshLoadMoreListener(OnRefreshAndLoadMoreListener onRefreshAndLoadMoreListener){
            this.onRefreshAndLoadMoreListener = onRefreshAndLoadMoreListener;
            return this;
        }

        public Builder setTitle(String title){
            this.title = title;
            return this;
        }

        public Builder setItemStyles(List<BaseItemStyle> itemStyles){
            this.itemStyles = itemStyles;
            return this;
        }

        public Builder setTextChangedListener(TextChangedListener textChangedListener) {
            this.textChangedListener = textChangedListener;
            return this;
        }

        public Builder setShowSearch(boolean showSearch) {
            isShowSearch = showSearch;
            return this;
        }

        public SearchInfoStyle build(Context context){
            return new SearchInfoStyle(context,this);
        }
    }
}
