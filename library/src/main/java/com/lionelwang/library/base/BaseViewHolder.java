package com.lionelwang.library.base;

import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
/**
 * Base级ViewHolder
 */
public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder{

    public BaseViewHolder(@NonNull View itemView){
        super(itemView);
    }

    public abstract void bindViewHolder(BaseViewHolder holder,int position);
    public abstract T getItemStyleData();
}
