package com.lionelwang.library.factory.mainfactory;

import android.content.Context;
import com.lionelwang.library.base.BaseFilterBoxFactory;
import com.lionelwang.library.base.BaseMainStyle;
import com.lionelwang.library.mode.mainmode.PopupMode;

/**
 * 主容器Factory：弹窗实现工厂
 */
public class PopupFilterBoxFactory extends BaseFilterBoxFactory {

    private Context context;
    private PopupMode popupMode;
    //主样式
    private BaseMainStyle mainStyle;

    public PopupFilterBoxFactory(Context context,Builder builder){
           this.context = context;
           this.mainStyle = builder.mainStyle;
           this.getMainMode();
    }

    @Override
    public void getMainMode(){
        popupMode = new PopupMode.Builder().setLayoutRes(mainStyle.getLayoutView()).build(context);
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

        //主样式
        private BaseMainStyle mainStyle;

        public Builder setMainStyle(BaseMainStyle mainStyle){
            this.mainStyle = mainStyle;
            return this;
        }

        public PopupFilterBoxFactory build(Context context){
            return new PopupFilterBoxFactory(context,this);
        }
    }
}
