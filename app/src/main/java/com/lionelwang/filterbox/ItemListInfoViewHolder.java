package com.lionelwang.filterbox;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import com.lionelwang.library.base.BaseViewHolder;
import com.lionelwang.library.click.SelectedCallBack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ItemListInfoViewHolder extends BaseViewHolder{

    private Context context;
    private String label;
    private TextView nameView;
    private String data;
    private SelectedCallBack selectedCallBack;

    public ItemListInfoViewHolder(@NonNull View itemView,Context context,Builder builder){
        super(itemView);
        initView(itemView);
        this.context = context;
        this.label = builder.label;
        this.data = builder.data;
        this.selectedCallBack = builder.selectedCallBack;
    }

    private void initView(View itemView) {
        nameView = itemView.findViewById(R.id.tv_name);
    }

    @Override
    public void bindViewHolder(BaseViewHolder holder,int position){
        if (holder instanceof ItemListInfoViewHolder){
            nameView.setText(data);
            //item监听
            holder.itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    Map<String,Object> datas = new HashMap<>();
                    datas.put(label,data);
                    selectedCallBack.selected(datas);
                }
            });
        }
    }

    @Override
    public Object getItemStyleData(){
        return null;
    }

    public static class Builder{

        private String label;
        private String data;
        private SelectedCallBack selectedCallBack;

        public Builder setData(String data){
            this.data = data;
            return this;
        }
        public Builder setLabel(String label){
            this.label = label;
            return this;
        }

        public Builder setCallBack(SelectedCallBack selectedCallBack) {
            this.selectedCallBack = selectedCallBack;
            return this;
        }

        public ItemListInfoViewHolder build(View view,Context context){
            return new ItemListInfoViewHolder(view,context,this);
        }
    }
}
