package com.lionelwang.library.dialog;

import android.content.Context;
import android.os.Build;
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
import com.lionelwang.library.click.OnItemContentClickListener;
import com.lionelwang.library.click.SelectedListener;
import com.lionelwang.library.utils.ToastUtil;

import java.util.List;

/**
 * 多级选择弹窗：内容选择适配器
 */
public class BarContentAdapter extends RecyclerView.Adapter<BarContentAdapter.BarContentViewHolder>{

    private Context context;
    private List<TextBean> contents;
    //之前选中的Item Position
    private int oldSelectedPosition = -1;
    private OnItemClickListener itemClickListener;

    public BarContentAdapter(Context context, List<TextBean> contents){
        this.context = context;
        this.contents = contents;
    }


    @NonNull
    @Override
    public BarContentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_bar_multi_level_selection_content,parent,false);
        return new BarContentViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull BarContentViewHolder holder, int position){
        //设置内容
        holder.content.setText(contents.get(position).getText());
        if (contents.get(position).isSelected()){
            oldSelectedPosition = position;
            holder.content.setTextColor(context.getResources().getColor(R.color.common_base_color,null));
        }else {
            holder.content.setTextColor(context.getResources().getColor(R.color.app_gray,null));
        }
        //内容监听
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                itemClickListener.onItemClick(position);
                //设置当前item为选中状态
                contents.get(position).setSelected(true);
                //设置取消之前的选中
                if (oldSelectedPosition != -1){
                    if (contents.get(oldSelectedPosition).isSelected()){
                        contents.get(oldSelectedPosition).setSelected(false);
                    }
                }
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return contents.size();
    }

    public class BarContentViewHolder extends RecyclerView.ViewHolder{
        TextView content;
        public BarContentViewHolder(@NonNull View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.tv_content);
        }
    }

    /**
     * 设置item监听
     * @param itemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }
}
