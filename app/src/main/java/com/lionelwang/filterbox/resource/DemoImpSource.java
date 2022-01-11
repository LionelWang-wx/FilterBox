package com.lionelwang.filterbox.resource;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.util.Log;

import androidx.core.text.HtmlCompat;

import com.lionelwang.library.base.BaseItemStyle;
import com.lionelwang.library.base.BaseSource;
import com.lionelwang.library.bean.TextBean;
import com.lionelwang.library.click.DialogActionListener;
import com.lionelwang.library.click.DialogSelectedChangeListener;
import com.lionelwang.library.click.OnItemContentClickListener;
import com.lionelwang.library.click.SelectedCallBack;
import com.lionelwang.library.click.SelectedListener;
import com.lionelwang.library.click.SlideListener;
import com.lionelwang.library.factory.mainfactory.PopupFilterBoxFactory;
import com.lionelwang.library.mode.dialogmode.DialogMode;
import com.lionelwang.library.style.itemstyle.ItemClickListStyle;
import com.lionelwang.library.style.itemstyle.ItemClickPopupStyle;
import com.lionelwang.library.style.itemstyle.ItemDiyRangeStyle;
import com.lionelwang.library.style.itemstyle.ItemInputStyle;
import com.lionelwang.library.style.mainstyle.DefaultStyle;
import com.lionelwang.library.utils.DataUtils;
import com.lionelwang.library.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Source实现类
 */
public class DemoImpSource extends BaseSource<BaseItemStyle> {

    private Context context;
    //弹窗工厂
    private PopupFilterBoxFactory factory;
    //主容器样式
    private DefaultStyle defaultStyle;
    //性别
    List<TextBean> sexList = new ArrayList<>();
    String sexSelected = "";//选中的性别
    //商品类型
    List<TextBean> goodsTypeList = new ArrayList<>();
    String goodsTypeSelected = "";//选中的商品类型
    //民族
    List<TextBean> nationalityList = new ArrayList<>();
    String nationalitySelected = "";//选中的民族
    //发货地址
    List<TextBean> shipAddressItem1 = new ArrayList<>();
    List<List<TextBean>> shipAddressItem2 = new ArrayList<>();
    List<List<List<TextBean>>> shipAddressItem3 = new ArrayList<>();
    String shipAddressSelected = "";//选中的发货地址
    //中转地址
    List<TextBean> transitAddressItem1 = new ArrayList<>();
    List<List<TextBean>> transitAddressItem2 = new ArrayList<>();
    List<List<List<TextBean>>> transitAddressItem3 = new ArrayList<>();
    String transitAddressSelected = "";//选中的中转地址
    //送货地址
    List<TextBean> deliverGoodBarTitles = new ArrayList<>();
    List<TextBean> deliverGoodBarContents = new ArrayList<>();
    String deliverGoodSelected = "";//选中的送货地址
    //筛选框选中数据
    String selectedDataHtml = "";

    public DemoImpSource(Context context) {
        this.context = context;
        initData();
        //创建ItemStyles
        onCreateItemStyles();
        //初始化弹窗工厂
        initFactory();
    }

    /**
     * 本地模拟数据,仅供参考
     */
    private void initData() {
        //性别
        sexList.add(new TextBean("1", "男", false, "性别"));
        sexList.add(new TextBean("2", "女", false, "性别"));
        //商品类型
        goodsTypeList.add(new TextBean("1", "零食", false, "商品类型"));
        goodsTypeList.add(new TextBean("2", "化妆品", false, "商品类型"));
        goodsTypeList.add(new TextBean("3", "电器", false, "商品类型"));

        nationalityList.add(new TextBean("1", "汉族", false, "民族"));
        nationalityList.add(new TextBean("2", "蒙古族", false, "民族"));
        nationalityList.add(new TextBean("3", "回族", false, "民族"));
        //发货地址三级联动完整数据
        shipAddressItem1.add(new TextBean("1", "四川", false, "省"));
        //四川省
        List<TextBean> itemCList = new ArrayList<>();
        itemCList.add(new TextBean("1", "广元", false, "市"));
        itemCList.add(new TextBean("2", "成都", false, "市"));
        shipAddressItem2.add(itemCList);

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
        shipAddressItem3.add(gyiAList);

        /**
         * 中转地址三级联动传入不完整数据 这里只模拟滑动第二列的数据,原理相同
         */
        transitAddressItem1.add(new TextBean("1", "四川", false, "省"));
        //四川省
        List<TextBean> itemCList1 = new ArrayList<>();
        itemCList1.add(new TextBean("1", "广元", false, "市"));
        itemCList1.add(new TextBean("2", "成都", false, "市"));
        transitAddressItem2.add(itemCList1);

        List<List<TextBean>> gyiAList1 = new ArrayList<>();
        //广元
        List<TextBean> gyAList1 = new ArrayList<>();
        gyAList1.add(new TextBean("1", "利州区", false, "区"));
        gyAList1.add(new TextBean("2", "昭化区", false, "区"));
        gyAList1.add(new TextBean("3", "剑阁区", false, "区"));
        //成都
        List<TextBean> cdAList1 = new ArrayList<>();
        cdAList1.add(new TextBean("", "", false, "区"));

        gyiAList1.add(gyAList1);
        gyiAList1.add(cdAList1);
        transitAddressItem3.add(gyiAList1);


        //送货地址多级选择
        deliverGoodBarTitles.add(new TextBean("1", "请选择", true, "标题"));

        deliverGoodBarContents.add(new TextBean("1", "四川", false, "内容"));
        deliverGoodBarContents.add(new TextBean("2", "湖北", false, "内容"));
        deliverGoodBarContents.add(new TextBean("3", "湖南", false, "内容"));
        deliverGoodBarContents.add(new TextBean("4", "河北", false, "内容"));
        deliverGoodBarContents.add(new TextBean("5", "河南", false, "内容"));
        deliverGoodBarContents.add(new TextBean("6", "广元", false, "内容"));
        deliverGoodBarContents.add(new TextBean("7", "成都", false, "内容"));
        deliverGoodBarContents.add(new TextBean("8", "绵阳", false, "内容"));
        deliverGoodBarContents.add(new TextBean("9", "德阳", false, "内容"));
        deliverGoodBarContents.add(new TextBean("10", "南充", false, "内容"));
    }

    private void initFactory() {
        //1.先创建主容器样式
        defaultStyle = new DefaultStyle.Builder()
                .setTitle("筛选框")//主样式标题
                .setItemStyles(itemStyles)//主容器所有的item样式
                //选择确认,数据回调
                .setCallback(new SelectedCallBack() {
                    @Override
                    public void selected(Map selectedList) {
                        String goodsName = (String) selectedList.get("商品名");
                        Map<String, String> diyValues = (Map<String, String>) selectedList.get("价格");
                        String minValue = diyValues.get("minValue");
                        String maxValue = diyValues.get("maxValue");
                        //性别(单选)
                        List<TextBean> sexList = (List<TextBean>) selectedList.get("性别(单选)");
                        if (!DataUtils.isEmpty(sexList)) {
                            for (TextBean sexBean : sexList) {
                                if (sexBean.isSelected()) {
                                    sexSelected = sexBean.getText();
                                    Log.e("TAG", "sex = " + sexBean.getText());
                                }
                            }
                        }
                        //商品类型(多选)
                        List<TextBean> goodsTypeList = (List<TextBean>) selectedList.get("商品类型(多选)");
                        if (!DataUtils.isEmpty(goodsTypeList)) {
                            StringBuilder str = new StringBuilder();
                            for (TextBean goodsTypeBean : goodsTypeList) {
                                if (goodsTypeBean.isSelected()) {
                                    Log.e("TAG", "goodsType = " + goodsTypeBean.getText());
                                    str.append(goodsTypeBean.getText()+"、");
                                }
                            }
                            str.deleteCharAt(str.length()-1);
                            goodsTypeSelected = str.toString();
                        }
                        //民族
                        List<TextBean> nationalityList = (List<TextBean>) selectedList.get("民族");
                        if (!DataUtils.isEmpty(nationalityList)){
                            for (TextBean nationalityBean : nationalityList) {
                                if (nationalityBean.isSelected()) {
                                    Log.e("TAG", "nationalityBean = " + nationalityBean.getText());
                                    nationalitySelected = nationalityBean.getText();
                                }
                            }
                        }

                        //发货地址
                        List<TextBean> shipAddressList = (List<TextBean>) selectedList.get("发货地址(三级联动完整传入数据)");
                        StringBuilder sb = new StringBuilder();
                        if (!DataUtils.isEmpty(shipAddressList)) {
                            for (int i = 0; i < shipAddressList.size(); i++) {
                                //最后一个不需要拼接短横杆
                                if (shipAddressList.size() - 1 == i) {
                                    sb.append(shipAddressList.get(i).getText());
                                } else {
                                    sb.append(shipAddressList.get(i).getText() + "-");
                                }
                            }
                            shipAddressSelected = sb.toString();
                            Log.e("TAG", "shipAddress = " + sb.toString());
                        }
                        //中转地址
                        List<TextBean> transitAddressList = (List<TextBean>) selectedList.get("中转地址(三级联动传入数据不完整)");
                        StringBuilder sb1 = new StringBuilder();
                        if (!DataUtils.isEmpty(transitAddressList)) {
                            for (int i = 0; i < transitAddressList.size(); i++) {
                                //最后一个不需要拼接短横杆
                                if (transitAddressList.size() - 1 == i) {
                                    sb1.append(transitAddressList.get(i).getText());
                                } else {
                                    sb1.append(transitAddressList.get(i).getText() + "-");
                                }
                            }
                            transitAddressSelected = sb1.toString();
                            Log.e("TAG", "transitAddress = " + sb1.toString());
                        }
                        //送货地址
                        List<TextBean> shippingAddressList = (List<TextBean>) selectedList.get("送货地址");
                        StringBuilder sb2 = new StringBuilder();
                        if (!DataUtils.isEmpty(shippingAddressList)) {
                            for (int i = 0; i < shippingAddressList.size(); i++) {
                                //最后一个不需要拼接短横杆
                                if (shippingAddressList.size() - 1 == i) {
                                    sb2.append(shippingAddressList.get(i).getText());
                                } else {
                                    sb2.append(shippingAddressList.get(i).getText() + "-");
                                }
                            }
                            deliverGoodSelected = sb2.toString();
                            Log.e("TAG", "shippingAddressList = " + sb2.toString());
                        }

                        Log.e("TAG", "goodsName = " + goodsName);
                        Log.e("TAG", "minValue = " + minValue);
                        Log.e("TAG", "maxValue = " + maxValue);

                        //组装选中数据
                        selectedDataHtml = "<html><head><h1>常用筛选框选中结果</h1></head><body>"
                                +"<p><strong>商品名："+goodsName+"</strong></p>"
                                +"<p><strong>价格范围："+minValue+"--"+maxValue+"</strong></p>"
                                +"<p><strong>性别："+sexSelected+"</strong></p>"
                                +"<p><strong>商品类型(多选)："+goodsTypeSelected+"</strong></p>"
                                +"<p><strong>民族："+nationalitySelected+"</strong></p>"
                                +"<p><strong>发货地址："+shipAddressSelected+"</strong></p>"
                                +"<p><strong>中转地址："+transitAddressSelected+"</strong></p>"
                                +"<p><strong>送货地址："+deliverGoodSelected+"</strong></p>"
                                +"</body></html>";
                        EventBus.getDefault().post(selectedDataHtml);
                        factory.hide();
                    }
                })
                .build(context);
        //2.在将样式传到主容器模式中显示
        factory = new PopupFilterBoxFactory.Builder()
                //这里传入主样式 可使用默认样式DefaultStyle
                .setMainStyle(defaultStyle)//设置主容器样式
                .build(context);
    }


    private void onCreateItemStyles() {
        //关键字
        addItemStyle(new ItemInputStyle.Builder()
                .setLabel("商品名")//item标题
                .setHint("请输入商品名")//输入框提示
                .setContent("")//输入框内容
                .build(context));
        //自定义范围
        addItemStyle(new ItemDiyRangeStyle.Builder()
                .setLabel("价格")//item标题
                .setMinHint("自定最低价")//最小值输入框提示
                .setMinValue("")//最小值输入框内容
                .setMaxHint("自定最高价")//最大值输入框提示
                .setMaxValue("")//最大值输入框内容
                .build(context));
        //性别样式(单选)
        addItemStyle(new ItemClickListStyle.Builder()
                .setLabel("性别(单选)")//item标题
                .setSingleChoice(true)//是否单选
                .setShowAllSelect(true)//是否展示全部选项
                .setSelectedList(sexList)//选项数据
                .setShowSelectList(true)//是否直接展示列表
                .build(context));
        //商品类型样式(多选)
        addItemStyle(new ItemClickListStyle.Builder()
                .setLabel("商品类型(多选)")//item标题
                .setSingleChoice(false)//是否单选
                .setShowAllSelect(true)//是否展示全部选项
                .setSelectedList(goodsTypeList)//选项数据
                .setShowSelectList(true)//是否直接展示列表
                .build(context));
        //民族
        addItemStyle(new ItemClickPopupStyle.Builder()
                .setLabel("民族")//item标题
                .setDialogMode(DialogMode.SINGLE_LEVEL_MODE)//item模式
                .setShowAllSelect(true)//是否展示全部选项
                .setTitleName("请选择民族")//item弹窗标题
                .setNOptionsItems(nationalityList)//设置数据
                //对item弹窗监听,控制主弹窗的状态
                .setDialogActionListener(new DialogActionListener() {
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
                .build(context));

        //发货地址(三级联动完整传入数据)
        addItemStyle(new ItemClickPopupStyle.Builder()
                .setLabel("发货地址(三级联动完整传入数据)")//item标题
                .setDialogMode(DialogMode.THREE_LINKAGE_MODE)//item模式
                .setLinkageCompleteData(true)//是否完整传入联动数据
//                .setShowAllSelect(false)//是否展示全部选项 未实现
                .setTitleName("请选择发货地址")//item弹窗标题
                .setOptionsItems(shipAddressItem1, shipAddressItem2, shipAddressItem3)//三级联动数据
                //对item弹窗监听,控制主弹窗的状态
                .setDialogActionListener(new DialogActionListener() {
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
                .build(context));
        //中转地址(三级联动传入不完整数据,这里模拟接口请求的方式变更数据)
        addItemStyle(new ItemClickPopupStyle.Builder()
                .setLabel("中转地址(三级联动传入数据不完整)")//item标题
                .setDialogMode(DialogMode.THREE_LINKAGE_MODE)//item模式
                .setLinkageCompleteData(false)//是否完整传入联动数据
                .setShowAllSelect(true)//是否展示全部选项
                .setTitleName("请选择中转地址")//item弹窗标题
                .setOptionsItems(transitAddressItem1, transitAddressItem2, transitAddressItem3)//三级联动数据
                //对item弹窗监听,控制主弹窗的状态
                .setDialogActionListener(new DialogActionListener() {
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
                //滑动监听
                .setSlideListener(new SlideListener<TextBean>() {
                    @Override
                    public void onSlideChange(TextBean data, int options1, int options2, int options3) {
                        ToastUtil.show("滑动了" + data.getText());
                    }
                })
                .build(context));
        //送货地址(多级选择)
        addItemStyle(new ItemClickPopupStyle.Builder()
                .setLabel("送货地址")//item标题
                .setBarTitles(deliverGoodBarTitles)//bar标题栏
                .setContents(deliverGoodBarContents)//多级选择内容
                .setDialogMode(DialogMode.SINGLE_BAR_MODE)//item模式
                .setShowAllSelect(false)//是否展示全部选项,逻辑还存在问题,不建议使用
                .setTitleName("请选择送货地址")//item弹窗标题
                //SingBarMode弹窗标题和内容的监听
                .setDialogSelectedChangeListener(new DialogSelectedChangeListener() {
                    @Override
                    public void selectedChange(TextBean textBean) {
                        ToastUtil.show("返回 = " + textBean.getText());
                    }
                })
                .build(context));
    }


    /**
     * 展示
     *
     * @return
     */
    public DemoImpSource show() {
        factory.show();
        return this;
    }
}
