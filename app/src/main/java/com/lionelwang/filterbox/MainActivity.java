package com.lionelwang.filterbox;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.lionelwang.library.base.BaseItemStyle;
import com.lionelwang.library.bean.TextBean;
import com.lionelwang.library.click.DialogActionListener;
import com.lionelwang.library.click.SelectedCallBack;
import com.lionelwang.library.click.SelectedListener;
import com.lionelwang.library.click.SlideListener;
import com.lionelwang.library.factory.mainfactory.PopupFilterBoxFactory;
import com.lionelwang.library.mode.dialogmode.DialogMode;
import com.lionelwang.library.style.itemstyle.ItemClickListStyle;
import com.lionelwang.library.style.itemstyle.ItemClickPopupStyle;
import com.lionelwang.library.style.mainstyle.DefaultStyle;
import com.lionelwang.library.utils.ToastUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    TextView tv_action;
    TextView tv_dialog;
    PopupFilterBoxFactory factory;
    //第一列(三级联动的情况第二列、第三列需要通过第一列数据组装而成)
    private List<TextBean> options1Items = new ArrayList<>();
    //第二列
    private List<List<TextBean>> options2Items = new ArrayList<>();
    //第三列
    private List<List<List<TextBean>>> options3Items = new ArrayList<>();

    //不联动第一列
    private List<TextBean> nOptions1Items = new ArrayList<>();
    //不联动第二列
    private List<TextBean> nOptions2Items = new ArrayList<>();
    //不联动第三列
    private List<TextBean> nOptions3Items = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ToastUtil.init(this);
        tv_action = this.findViewById(R.id.tv_action);
        tv_dialog = this.findViewById(R.id.tv_dialog);
        initData();
        tv_action.setOnClickListener(v -> {

            List<BaseItemStyle> itemStyles = new ArrayList<>();

            List<TextBean> strList = new ArrayList<>();
            strList.add(new TextBean("1","男",false));
            strList.add(new TextBean("2","女",false));
            strList.add(new TextBean("3","男",false));
            strList.add(new TextBean("4","女",false));
            itemStyles.add(new ItemClickListStyle.Builder()
                    .setLabel("性别")
                    .setSingleChoice(false)
                    .setShowAllSelect(true)
                    .setSelectedList(strList)
                    .setShowSelectList(true)
                    .build(this));
            itemStyles.add(new ItemClickPopupStyle.Builder()
                    .setLabel("生源地")
                    .setDialogMode(DialogMode.THREE_LINKAGE_MODE)
                    //设置副弹窗的确认监听
                    //三级联动滑动item监听
                    .setSlideListener(new SlideListener<TextBean>() {
                        @Override
                        public void onSlideChange(TextBean data, int options1, int options2, int options3) {
                            switch(data.getType()){
                                case "省":
                                    ToastUtil.show("请求省数据");
                                    break;
                                case "市":
                                    ToastUtil.show("请求市数据");
                                    break;
                            }
                        }
                    })
                    //当前弹窗不是主弹窗时需要设置DialogActionListener  控制主弹窗与副弹窗的交互
                    .setDialogActionListener(new DialogActionListener(){
                        @Override
                        public void show() {
                            factory.show();
                        }

                        @Override
                        public void dismiss() {
                            factory.dismiss();
                        }

                        @Override
                        public void hide() {
                            factory.hide();
                        }
                    })
                    .setSelectedListener(new SelectedListener<TextBean>(){
                        @Override
                        public void onSelected(TextBean data){
                            ToastUtil.show("确认监听"+data.getText());
                        }
                    })
                    .setLinkageCompleteData(false)
                    .setShowAllSelect(true)//是否展示全部选项
                    .setTitleName("请选择生源地")//副弹窗标题
//                    .setNOptionsItems(options1Items)
                    .setOptionsItems(options1Items,options2Items,options3Items)
                    .build(this));

            //1.先创建主容器样式
            //2.在将样式传到主容器模式中显示
            factory = new PopupFilterBoxFactory.Builder()
                    //这里传入主样式 可使用默认样式DefaultStyle
                    .setMainStyle(new DefaultStyle.Builder()
                            .setTitle("主样式标题")//主样式标题
                            .setItemStyles(itemStyles)//主样式的item样式
                            //选择确认,数据回调
                            .setCallback(new SelectedCallBack<List<TextBean>>(){
                                @Override
                                public void selected(Map<String,List<TextBean>> selectedList){
                                    //确认选中回调
                                    //通过key获取对应的数据
                                    List<TextBean> sexList = selectedList.get("性别");
                                    ToastUtil.show(sexList.get(2).getText());
                                }
                            })
                            .build(this))
                    .build(this)
                    .show();
        });
    }

    private void initData() {
        /**
         * 测试
         */
        options1Items.add(new TextBean("1","四川",false,"省"));

        //四川省
        List<TextBean> itemCList = new ArrayList<>();
        itemCList.add(new TextBean("1","广元",false,"市"));
        itemCList.add(new TextBean("2","成都",false,"市"));
        itemCList.add(new TextBean("3","绵阳",false,"市"));
        options2Items.add(itemCList);

        List<List<TextBean>> gyiAList = new ArrayList<>();
        //广元
        List<TextBean> gyAList = new ArrayList<>();
        gyAList.add(new TextBean("1","利州区",false,"区"));
        gyAList.add(new TextBean("2","昭化区",false,"区"));
        gyAList.add(new TextBean("3","剑阁区",false,"区"));
        //成都
        List<TextBean> cdAList = new ArrayList<>();
        cdAList.add(new TextBean("1","高新区",false,"区"));
        cdAList.add(new TextBean("2","武侯区",false,"区"));
        cdAList.add(new TextBean("3","锦江区",false,"区"));

        gyiAList.add(gyAList);
        gyiAList.add(cdAList);
        options3Items.add(gyiAList);
    }
}