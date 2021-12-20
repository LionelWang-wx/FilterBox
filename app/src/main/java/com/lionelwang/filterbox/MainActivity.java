package com.lionelwang.filterbox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lionelwang.library.base.BaseItemStyle;
import com.lionelwang.library.bean.TextBean;
import com.lionelwang.library.click.DialogActionListener;
import com.lionelwang.library.click.OnRefreshAndLoadMoreListener;
import com.lionelwang.library.click.SelectedCallBack;
import com.lionelwang.library.click.SelectedListener;
import com.lionelwang.library.click.SlideListener;
import com.lionelwang.library.click.TextChangedListener;
import com.lionelwang.library.factory.mainfactory.PopupFilterBoxFactory;
import com.lionelwang.library.mode.dialogmode.DialogMode;
import com.lionelwang.library.style.itemstyle.ItemClickListStyle;
import com.lionelwang.library.style.itemstyle.ItemClickPopupStyle;
import com.lionelwang.library.style.itemstyle.ItemDiyRangeStyle;
import com.lionelwang.library.style.itemstyle.ItemInputStyle;
import com.lionelwang.library.style.mainstyle.DefaultStyle;
import com.lionelwang.library.style.mainstyle.SearchInfoStyle;
import com.lionelwang.library.utils.ToastUtil;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

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
    //列表信息数据 ——>单个布局
    private List<String> nameList = new ArrayList<>();
    //列表信息数据 ——>多个布局
    private List<String> ageList = new ArrayList<>();
    //bar标题栏
    private List<TextBean> barTitles = new ArrayList<>();
    //多级选择内容
    private List<TextBean> contents = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ToastUtil.init(this);
        tv_action = this.findViewById(R.id.tv_action);
        tv_dialog = this.findViewById(R.id.tv_dialog);
        initData();
        tv_action.setOnClickListener(v -> {

            List<BaseItemStyle> itemStyles = new ArrayList<>();

            List<TextBean> strList = new ArrayList<>();
            strList.add(new TextBean("1", "男", false));
            strList.add(new TextBean("2", "女", false));
            strList.add(new TextBean("3", "男", false));
            strList.add(new TextBean("4", "女", false));
            itemStyles.add(new ItemClickListStyle.Builder()
                    .setLabel("性别")
                    .setSingleChoice(false)
                    .setShowAllSelect(true)
                    .setSelectedList(strList)
                    .setShowSelectList(true)
                    .build(this));

            itemStyles.add(new ItemInputStyle.Builder()
                    .setLabel("车牌")
                    .setHint("请输入车牌号")
                    .setContent("")
                    .build(this));

            itemStyles.add(new ItemDiyRangeStyle.Builder()
                    .setLabel("价格")
                    .setMinHint("自定最低价")
                    .setMinValue("")
                    .setMaxHint("自定最高价")
                    .setMaxValue("")
                    .build(this));

            //范围
            itemStyles.add(new ItemClickPopupStyle.Builder()
                    .setBarTitles(barTitles)
                    .setContents(contents)
                    .setLabel("生源地")
                    .setDialogMode(DialogMode.SINGLE_BAR_MODE)
                    //设置副弹窗的确认监听
                    //三级联动滑动item监听
//                    .setSlideListener(new SlideListener<TextBean>() {
//                        @Override
//                        public void onSlideChange(TextBean data, int options1, int options2, int options3) {
//                            switch (data.getType()) {
//                                case "省":
//                                    ToastUtil.show("请求省数据");
//                                    break;
//                                case "市":
//                                    ToastUtil.show("请求市数据");
//                                    break;
//                            }
//                        }
//                    })
//                    //当前弹窗不是主弹窗时需要设置DialogActionListener  控制主弹窗与副弹窗的交互
//                    .setDialogActionListener(new DialogActionListener() {
//                        @Override
//                        public void show() {
//                            factory.show();
//                        }
//
//                        @Override
//                        public void dismiss() {
//                            factory.dismiss();
//                        }
//
//                        @Override
//                        public void hide() {
//                            factory.hide();
//                        }
//                    })
//                    .setSelectedListener(new SelectedListener<TextBean>() {
//                        @Override
//                        public void onSelected(TextBean data) {
//                            ToastUtil.show("确认监听" + data.getText());
//                        }
//                    })
//                    .setLinkageCompleteData(false)
                    .setShowAllSelect(true)//是否展示全部选项
                    .setTitleName("请选择生源地")//副弹窗标题
                    .setSelectedListener(new SelectedListener<TextBean>() {
                        @Override
                        public void onSelected(TextBean data) {
                            ToastUtil.show("请选择生源地"+data.getText());
                        }
                    })
//                    .setNOptionsItems(options1Items)
//                    .setOptionsItems(options1Items, options2Items, options3Items)
                    .build(this));


            //1.先创建主容器样式
            //2.在将样式传到主容器模式中显示
            factory = new PopupFilterBoxFactory.Builder()
                    //这里传入主样式 可使用默认样式DefaultStyle
                    .setMainStyle(new DefaultStyle.Builder()
                            .setTitle("主样式标题")//主样式标题
                            .setItemStyles(itemStyles)//主样式的item样式
                            //选择确认,数据回调
                            .setCallback(new SelectedCallBack() {
                                @Override
                                public void selected(Map selectedList) {
                                    //确认选中回调
                                    //通过key获取对应的数据
                                    //车牌
//                                    String str = (String) selectedList.get("车牌");
//                                    ToastUtil.show(str);

                                    //价格
                                    Map<String,String> priceList = (Map<String,String>)selectedList.get("价格");
                                    ToastUtil.show(priceList.get("maxValue"));

                                    //性别
//                                    List<TextBean> sexList = (List<TextBean>)selectedList.get("性别");
//                                    ToastUtil.show(sexList.get(0).getText());
                                }
                            })
                            .build(this))
                    .build(this)
                    .show();
        });


        /**
         * 1.同一个布局对应多个数据
         *
         * 2.不同的布局对应不同的数据
         */
        tv_dialog.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                List<BaseItemStyle> itemStyles = new ArrayList<>();
                for (int i = 0; i < nameList.size();i++){
                itemStyles.add(new ItemListInfoStyle.Builder()
                        .setLabel("人员信息")
                        .setData(nameList.get(i))
                        .setCallBack(new SelectedCallBack(){
                            @Override
                            public void selected(Map selectedList){
                                String name = (String)selectedList.get("人员信息");
                                ToastUtil.show(name);
                            }
                        })
                        .build(MainActivity.this));

                //实现不同的布局对应不同的数据
//                itemStyles.add(new ItemListInfoStyle.Builder()
//                        .setLabel("年龄")
//                        .setData(ageList.get(i))
//                        .build(MainActivity.this));
                }
                //1.先创建主容器样式
                //2.在将样式传到主容器模式中显示
                factory = new PopupFilterBoxFactory.Builder()
                        //这里传入主样式 可使用默认样式DefaultStyle
                        .setMainStyle(new SearchInfoStyle.Builder()
                                .setTitle("默认主样式")
                                .setItemStyles(itemStyles)
                                .setShowSearch(true)//是否展示搜索功能
                                .setOnRefreshLoadMoreListener(new OnRefreshAndLoadMoreListener(){
                                    @Override
                                    public void onRefresh(@NonNull RefreshLayout refreshLayout, int pageIndex) {
                                        ToastUtil.show("onRefresh");
                                    }

                                    @Override
                                    public void onLoadMore(@NonNull RefreshLayout refreshLayout, int pageIndex) {
                                        ToastUtil.show("onLoadMore");
                                    }
                                })
                                .setTextChangedListener(new TextChangedListener(){
                                    @Override
                                    public void onAfterTextChanged(String changedText) {
                                        ToastUtil.show("搜索"+changedText);
                                    }
                                })
                                .setSelectedListener(new SelectedListener<Integer>(){

                                    @Override
                                    public void onSelected(Integer position){
                                        ToastUtil.show("onSelected");
                                    }
                                })
                                .build(MainActivity.this))
                        .build(MainActivity.this)
                        .show();
            }
        });
    }

    private void initData() {
        /**
         * 测试
         */
        options1Items.add(new TextBean("1", "四川", false, "省"));

        //四川省
        List<TextBean> itemCList = new ArrayList<>();
        itemCList.add(new TextBean("1", "广元", false, "市"));
        itemCList.add(new TextBean("2", "成都", false, "市"));
        itemCList.add(new TextBean("3", "绵阳", false, "市"));
        options2Items.add(itemCList);

        List<List<TextBean>> gyiAList = new ArrayList<>();
        //广元
        List<TextBean> gyAList = new ArrayList<>();
        gyAList.add(new TextBean("1", "利州区", false, "区"));
        gyAList.add(new TextBean("2", "昭化区", false, "区"));
        gyAList.add(new TextBean("3", "剑阁区", false, "区"));
        //成都
        List<TextBean> cdAList = new ArrayList<>();
        cdAList.add(new TextBean("1", "高新区", false, "区"));
        cdAList.add(new TextBean("2", "武侯区", false, "区"));
        cdAList.add(new TextBean("3", "锦江区", false, "区"));

        gyiAList.add(gyAList);
        gyiAList.add(cdAList);
        options3Items.add(gyiAList);

        nameList.add("张三");
        nameList.add("李四");
        nameList.add("王五");
        nameList.add("小明");
        nameList.add("小红");
        nameList.add("小王");

        ageList.add("10");
        ageList.add("24");
        ageList.add("53");
        ageList.add("24");
        ageList.add("12");
        ageList.add("11");

//        barTitles.add(new TextBean("1","请选择",true,"省"));
//        barTitles.add(new TextBean("1","请选择",true,"省"));
//        barTitles.add(new TextBean("1","请选择",true,"省"));
//        barTitles.add(new TextBean("1","请选择",true,"省"));
//        barTitles.add(new TextBean("1","请选择",true,"省"));
//        barTitles.add(new TextBean("1","请选择",true,"省"));
//        barTitles.add(new TextBean("1","请选择",true,"省"));
//        barTitles.add(new TextBean("1","请选择",true,"省"));
//        barTitles.add(new TextBean("1","请选择",true,"省"));
//        barTitles.add(new TextBean("1","请选择",true,"省"));
//        barTitles.add(new TextBean("1","请选择",true,"省"));
//        barTitles.add(new TextBean("1","请选择",true,"省"));
//        barTitles.add(new TextBean("1","请选择",true,"省"));

        contents.add(new TextBean("1","广元",false,"内容"));
        contents.add(new TextBean("2","成都",false,"内容"));
        contents.add(new TextBean("3","绵阳",false,"内容"));
        contents.add(new TextBean("4","德阳",false,"内容"));
        contents.add(new TextBean("5","南充",false,"内容"));
    }



}