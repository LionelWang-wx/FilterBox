package com.lionelwang.library.item.itemadpter;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import com.lionelwang.library.R;
import com.lionelwang.library.bean.TextBean;
import com.lionelwang.library.click.OnItemClickListener;
import com.lionelwang.library.utils.DataUtils;
import com.lionelwang.library.viewholder.ItemClickListViewHolder;

import java.util.List;
import java.util.Map;

public class FilterClickListAdapter extends RecyclerView.Adapter<FilterClickListAdapter.FilterClickListViewHolder>{

    private Context context;
    private List<TextBean> selectedList;
    private boolean isSingleChoice;
    private boolean isShowAllSelect;

    public FilterClickListAdapter(Context context,Builder builder){
        this.context = context;
        this.selectedList = builder.selectedList;
        this.isSingleChoice = builder.isSingleChoice;
        this.isShowAllSelect = builder.isShowAllSelect;
    }

    @NonNull
    @Override
    public FilterClickListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        FilterClickListViewHolder viewHolder;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_click_list_item_text,parent,false);
        viewHolder = new FilterClickListViewHolder(view);
        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull FilterClickListViewHolder holder, int position){
        //设置item数据
        TextBean bean = selectedList.get(position);
        holder.selectedStr.setText(bean.getText());
        if (bean.isSelected()) {
            //选中
            holder.selectedStr.setTextColor(0xFF777CE3);
            holder.selectedStr.setBackgroundResource(R.drawable.shape_common_transparent_purple_bg);
        } else {
            //未选中
            holder.selectedStr.setTextColor(0xFF333333);
            holder.selectedStr.setBackgroundResource(R.drawable.shape_common_gray_white_bg);
        }

        //选中回调,更改选中状态
        holder.selectedStr.setOnClickListener(v->{
            //状态变更
            TextBean b = selectedList.get(position);
            if (!isSingleChoice){
                //多选处理
                if (TextUtils.equals(b.getId(),"99999")){
                    if (b.isSelected()){
                        //未选中
                        b.setSelected(false);
                    }else {
                        //选中
                        b.setSelected(true);
                        //多选 选中全部时,其他item取消选中
                        for (TextBean b1 : selectedList){
                            if (!TextUtils.equals(b1.getId(),"99999")){
                                b1.setSelected(false);
                            }
                        }
                    }
                }else{
                    if (b.isSelected()){
                        b.setSelected(false);
                    }else{
                        b.setSelected(true);
                        for (TextBean b1 : selectedList){
                            if (TextUtils.equals(b1.getId(),"99999")){
                                b1.setSelected(false);
                            }
                        }
                    }
                }
            }else{
                //单选处理
                if(b.isSelected()){
                    b.setSelected(false);
                }else {
                    b.setSelected(true);
                }
                for (TextBean b1 : selectedList){
                    if (b.getId() != b1.getId()){
                        b1.setSelected(false);
                    }
                }
            }
            this.notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount(){
         return selectedList.size();
    }

    class FilterClickListViewHolder extends RecyclerView.ViewHolder{
        private TextView selectedStr;
        public FilterClickListViewHolder(@NonNull View itemView) {
            super(itemView);
            selectedStr = itemView.findViewById(R.id.item_sizer_select_click_text);
        }
    }

    public static class Builder{

        private List<TextBean> selectedList;
        private boolean isSingleChoice = true;
        private boolean isShowAllSelect = true;

        public Builder setSelectedList(List<TextBean> selectedList){
            this.selectedList = selectedList;
            return this;
        }
        public Builder setSingleChoice(boolean isSingleChoice){
            this.isSingleChoice = isSingleChoice;
            return this;
        }
        public Builder setShowAllSelect(boolean isShowAllSelect) {
            this.isShowAllSelect = isShowAllSelect;
            return this;
        }

        public FilterClickListAdapter build(Context context) {
            return new FilterClickListAdapter(context,this);
        }
    }
}
