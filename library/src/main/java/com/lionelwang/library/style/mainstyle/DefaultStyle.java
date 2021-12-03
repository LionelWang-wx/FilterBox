package com.lionelwang.library.style.mainstyle;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.lionelwang.library.R;
import com.lionelwang.library.base.BaseAdapter;
import com.lionelwang.library.base.BaseItemStyle;
import com.lionelwang.library.base.BaseMainStyle;
import com.lionelwang.library.bean.TextBean;
import com.lionelwang.library.click.SelectedCallBack;
import com.lionelwang.library.style.itemstyle.ItemClickListStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 主容器默认样式:layout_base_popup
 */
public class DefaultStyle implements BaseMainStyle{
    
    private Context context;
    //设置主容器的布局样式(将xml布局转成view)
    private View layoutView;
    private BaseAdapter adapter;
    private List<BaseItemStyle> itemStyles;
    private String title;
    private SelectedCallBack callback;
    public DefaultStyle(Context context,Builder builder){
            this.context = context;
            this.title = builder.title;
            this.itemStyles = builder.itemStyles;
            this.callback = builder.callback;
            this.createStyle();
    }

    @Override
    public void createStyle(){
        if (layoutView == null){
            layoutView = LayoutInflater.from(context).inflate(R.layout.layout_base_popup,null,false);
            RecyclerView recyclerView = layoutView.findViewById(R.id.sizer_recycle);
            TextView reset = layoutView.findViewById(R.id.sizer_reset);
            TextView sure = layoutView.findViewById(R.id.sizer_sure);
            TextView titleView = layoutView.findViewById(R.id.tv_title);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            adapter = new com.lionelwang.library.base.BaseAdapter(context,itemStyles);
            recyclerView.setAdapter(adapter);
            //设置主标题
            if (TextUtils.isEmpty(title)){
                titleView.setVisibility(View.GONE);
            }else {
                titleView.setText(title);
            }

            //重置
            reset.setOnClickListener(v ->{
                for (BaseItemStyle itemStyle : itemStyles){
                    itemStyle.clearSelectedItem();
                }
                adapter.notifyDataSetChanged();
            });
            //确认
            sure.setOnClickListener(v -> {
                Map<String,Object> data = new HashMap<>();
                for(BaseItemStyle itemStyle : itemStyles){
                    data.put(itemStyle.getItemLabel(), itemStyle.getItemStyleData());
                }
                callback.selected(data);
            });
        }
    }

    /**
     * 返回布局View
     * @return
     */
    @Override
    public View getLayoutView(){
        return layoutView;
    }

    /**
     * 获取主适配器
     * @return
     */
    public void refreshAdapter(){
        adapter.notifyDataSetChanged();
    }

    public static class Builder{
        private List<BaseItemStyle> itemStyles;
        private String title;
        private SelectedCallBack callback;

        public Builder setTitle(String title){
            this.title = title;
            return this;
        }

        public Builder setItemStyles(List<BaseItemStyle> itemStyles){
            this.itemStyles = itemStyles;
            return this;
        }

        public Builder setCallback(SelectedCallBack callback){
            this.callback = callback;
            return this;
        }
        public DefaultStyle build(Context context){
            return new DefaultStyle(context,this);
        }
    }
}
