package com.lionelwang.library.viewholder;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lionelwang.library.R;
import com.lionelwang.library.base.BaseViewHolder;
import com.lionelwang.library.bean.TextBean;
import com.lionelwang.library.item.itemadpter.FilterClickListAdapter;
import com.lionelwang.library.utils.DataUtils;
import com.lionelwang.library.utils.ToastUtil;

import java.util.List;
import java.util.Map;

/**
 * 点击列表item样式的ViewHolder
 */
public class ItemClickListViewHolder extends BaseViewHolder<List<TextBean>>{
    private RelativeLayout bar;
    private TextView tv_label;
    private ImageView iconOrientation;
    private RecyclerView recycle;
    private View bottomLine;
    private Context context;
    //数据
    private String label;
    private List<TextBean> selectedList;
    private boolean isShowAllSelect;
    private boolean isShowSelectList;
    private FilterClickListAdapter adapter;
    private boolean isSingleChoice;

    public ItemClickListViewHolder(@NonNull View itemView,Context context,Builder builder){
        super(itemView);
        initView(itemView);
        this.context = context;
        this.label = builder.label;
        this.selectedList = builder.selectedList;
        this.isShowAllSelect = builder.isShowAllSelect;
        this.isShowSelectList = builder.isShowSelectList;
        this.isSingleChoice = builder.isSingleChoice;
        initData();
    }

    private void initData(){
            //判断是否需要添加全部选项
            if (isShowAllSelect){
                //将全部选项添加到第一位   全部选项id要避免用户使用
                selectedList.add(0,new TextBean("99999","全部",true));
            }
    }

    @Override
    public void bindViewHolder(BaseViewHolder holder,int position){
        if(holder instanceof ItemClickListViewHolder){
            ((ItemClickListViewHolder)holder).tv_label.setText(label);
            //设置点击Bar展示Item
            ((ItemClickListViewHolder)holder).recycle.setVisibility(isShowSelectList ? View.VISIBLE : View.GONE);
            ((ItemClickListViewHolder)holder).bottomLine.setVisibility(isShowSelectList ? View.GONE : View.VISIBLE);
            ((ItemClickListViewHolder)holder).bar.setOnClickListener(v -> {
                if (((ItemClickListViewHolder)holder).recycle.getVisibility() == View.VISIBLE){
                    ((ItemClickListViewHolder)holder).recycle.setVisibility(View.GONE);
                    ((ItemClickListViewHolder)holder).iconOrientation.setImageResource(R.mipmap.icon_bottom_arrow);
                    ((ItemClickListViewHolder)holder).bottomLine.setVisibility(View.VISIBLE);
                }else{
                    ((ItemClickListViewHolder)holder).recycle.setVisibility(View.VISIBLE);
                    ((ItemClickListViewHolder)holder).iconOrientation.setImageResource(R.mipmap.icon_top_arrow);
                    ((ItemClickListViewHolder)holder).bottomLine.setVisibility(View.GONE);
                }
            });
            if (!DataUtils.isEmpty(selectedList)){
                //有数据就要装载到适配器中
                if (adapter == null){
                    adapter = new FilterClickListAdapter.Builder()
                            .setSingleChoice(isSingleChoice)
                            .setSelectedList(selectedList)
                            .setShowAllSelect(isShowAllSelect)
                            .build(context);
                    ((ItemClickListViewHolder)holder).recycle.setLayoutManager(new GridLayoutManager(context,3));
                    ((ItemClickListViewHolder)holder).recycle.setAdapter(adapter);
                }else {
                    adapter.notifyDataSetChanged();
                }
            }else {
                ToastUtil.show("无数据");
            }
        }
    }

    @Override
    public List<TextBean> getItemStyleData() {
        return selectedList;
    }

    private void initView(View itemView){
        bar = itemView.findViewById(R.id.rlt_bar);
        tv_label = itemView.findViewById(R.id.click_label);
        iconOrientation = itemView.findViewById(R.id.iv_orientation);
        recycle = itemView.findViewById(R.id.click_recycle);
        bottomLine = itemView.findViewById(R.id.bottom_line);
    }


    public static class Builder{

        private String label;
        private List<TextBean> selectedList;
        private boolean isShowAllSelect = true;
        private boolean isShowSelectList = false;
        private boolean isSingleChoice = true;


        public Builder setLabel(String label) {
            this.label = label;
            return this;
        }
        public Builder setSelectedList(List<TextBean> selectedList) {
            this.selectedList = selectedList;
            return this;
        }

        public Builder setShowAllSelect(boolean showAllSelect) {
            isShowAllSelect = showAllSelect;
            return this;
        }

        public Builder setShowSelectList(boolean showSelectList) {
            isShowSelectList = showSelectList;
            return this;
        }
        public Builder setSingleChoice(boolean isSingleChoice){
            this.isSingleChoice = isSingleChoice;
            return this;
        }

        public ItemClickListViewHolder build(View view,Context context) {
            return new ItemClickListViewHolder(view,context,this);
        }
    }
}
