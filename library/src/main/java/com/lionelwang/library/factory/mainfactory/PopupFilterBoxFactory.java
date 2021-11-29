package com.lionelwang.library.factory.mainfactory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.lionelwang.library.R;
import com.lionelwang.library.base.BaseFilterBoxFactory;
import com.lionelwang.library.base.BaseMainMode;
import com.lionelwang.library.base.BaseMainStyle;
import com.lionelwang.library.mode.mainmode.PopupMode;

/**
 * 主容器Factory：弹窗实现工厂
 */
public class PopupFilterBoxFactory extends BaseFilterBoxFactory {

    private Context context;
    //设置主容器的布局:弹窗样式默认布局--->layout_base_popup,支持修改
    private View layoutView;
    private PopupMode popupMode;

    public PopupFilterBoxFactory(Context context,Builder builder){
           this.context = context;
           this.layoutView = builder.layoutView;
           this.getMainMode();
    }

    @Override
    public void getMainMode(){
        popupMode = new PopupMode.Builder().setLayoutRes(layoutView).build(context);
    }

    /**
     * 展示主弹窗
     * @return
     */
    public PopupFilterBoxFactory show(){
        popupMode.show();
        return this;
    }

    /**
     * 隐藏主弹窗
     * @return
     */
    public PopupFilterBoxFactory hide(){
        popupMode.hide();
        return this;
    }

    /**
     * 销毁主弹窗
     * @return
     */
    public PopupFilterBoxFactory dismiss(){
        popupMode.dismiss();
        return this;
    }

    /**
     * 使用建造者模式,实现PopupFilterBoxFactory
     */
    public static class Builder{

        //设置主容器的布局:弹窗样式默认布局--->layout_base_popup,支持修改
        private View layoutView;

        public Builder setLayoutView(View layoutView){
            this.layoutView = layoutView;
            return this;
        }

        public PopupFilterBoxFactory build(Context context){
            return new PopupFilterBoxFactory(context,this);
        }
    }
}
