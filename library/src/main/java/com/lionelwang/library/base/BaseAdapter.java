package com.lionelwang.library.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lionelwang.library.R;
import com.lionelwang.library.click.SelectedListener;
import com.lionelwang.library.viewholder.ItemClickPopupViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Base级主容器的Adapter  万能
 */
public class BaseAdapter extends RecyclerView.Adapter<BaseViewHolder>{

    private List<BaseItemStyle> itemStyles;
    private Context context;
    private SelectedListener selectedListener;

    public BaseAdapter(Context context,List<BaseItemStyle> itemStyles) {
        this.itemStyles = itemStyles;
        this.context = context;
    }


    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view;
        BaseViewHolder viewHolder;
        view = LayoutInflater.from(parent.getContext()).inflate(itemStyles.get(viewType).getItemLayoutRes(),parent,false);
        viewHolder = itemStyles.get(viewType).getItemViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position){
        holder.bindViewHolder(holder,position);
    }

    @Override
    public int getItemCount(){
        return itemStyles.size();
    }

    @Override
    public int getItemViewType(int position){
        return position;
    }
}
