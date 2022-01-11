package com.lionelwang.filterbox.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.blankj.utilcode.util.BusUtils;
import com.lionelwang.filterbox.R;
import com.lionelwang.filterbox.resource.DemoDiyImpSource;
import com.lionelwang.filterbox.resource.DemoImpSource;
import com.lionelwang.library.base.BaseItemStyle;
import com.lionelwang.library.bean.TextBean;
import com.lionelwang.library.click.SelectedListener;
import com.lionelwang.library.click.SlideListener;
import com.lionelwang.library.mode.dialogmode.DialogMode;
import com.lionelwang.library.style.itemstyle.ItemClickListStyle;
import com.lionelwang.library.style.itemstyle.ItemClickPopupStyle;
import com.lionelwang.library.style.itemstyle.ItemDiyRangeStyle;
import com.lionelwang.library.style.itemstyle.ItemInputStyle;
import com.lionelwang.library.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;


public class DemoActivity extends AppCompatActivity {

    TextView common_popup;
    TextView common_popup_info;
    TextView diy_popup;
    TextView diy_popup_info;
    DemoImpSource demoImpSource;
    DemoDiyImpSource demoDiyImpSource;
    private String mode = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        ToastUtil.init(this);
        EventBus.getDefault().register(this);
        initView();
        initData();
        common_popup.setOnClickListener(view -> {
            mode = "common_popup";
            demoImpSource.show();
        });
        diy_popup.setOnClickListener(view -> {
            mode = "diy_popup";
            demoDiyImpSource.show();
        });
    }

    private void initView() {
        common_popup = this.findViewById(R.id.tv_common_popup);
        common_popup_info = this.findViewById(R.id.tv_common_popup_info);
        diy_popup = this.findViewById(R.id.tv_diy_popup);
        diy_popup_info = this.findViewById(R.id.tv_diy_popup_info);
    }

    private void initData() {
        demoImpSource = new DemoImpSource(this);
        demoDiyImpSource = new DemoDiyImpSource(this);
    }

    //更新界面
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateUI(String str){
        if(!TextUtils.isEmpty(str)){
            switch (mode) {
                case "common_popup":
                    common_popup_info.setMovementMethod(ScrollingMovementMethod.getInstance());//滚动
                    common_popup_info.setText(Html.fromHtml(str));
                    break;
                case "diy_popup":
                    diy_popup_info.setMovementMethod(ScrollingMovementMethod.getInstance());//滚动
                    diy_popup_info.setText(str);
                    break;
            }
        }
    }
}