package com.lionelwang.filterbox;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.lionelwang.library.base.BaseItemStyle;
import com.lionelwang.library.bean.JsonBean;
import com.lionelwang.library.bean.TextBean;
import com.lionelwang.library.click.DialogActionListener;
import com.lionelwang.library.click.SlideListener;
import com.lionelwang.library.factory.mainfactory.PopupFilterBoxFactory;
import com.lionelwang.library.mode.dialogmode.DialogMode;
import com.lionelwang.library.style.itemstyle.ItemClickListStyle;
import com.lionelwang.library.style.itemstyle.ItemClickPopupStyle;
import com.lionelwang.library.style.mainstyle.DefaultStyle;
import com.lionelwang.library.utils.ToastUtil;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView tv_action;
    PopupFilterBoxFactory factory;
    //第一列(三级联动的情况第二列、第三列需要通过第一列数据组装而成)
    private List<JsonBean> options1Items = new ArrayList<>();
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
                    .setSelected("四川省")
                    .setSlideListener(new SlideListener(){
                        @Override
                        public void onSlideChange(Object data) {
                            ToastUtil.show("数据");
                            if(data instanceof JsonBean){
                                ((JsonBean)data).getId();
                                //请求数据
                                ToastUtil.show("请求省数据");
                            }else if(data instanceof TextBean){
                                ((TextBean)data).getId();
                                //请求数据
                                ToastUtil.show("请求市数据");
                            }
                        }
                    })
                    //当前弹窗不是主弹窗时需要设置DialogActionListener
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
                    .setLinkageCompleteData(false)
                    .setOptionsItems(options1Items,options2Items,options3Items)
                    .build(this));

            //1.先创建主容器样式
            //2.在将样式View传到主容器模式中显示
            DefaultStyle defaultStyle = new DefaultStyle.Builder()
                    .setTitle("默认弹窗样式")
                    .setItemStyles(itemStyles)
                    .setCallback(selectedList -> {
                         //确认选中回调
                         List<TextBean> bList = (List<TextBean>) selectedList.get("性别");
                         ToastUtil.show(bList.get(2).getText());
                        factory.dismiss();
                    })
                    .build(this);
            factory = new PopupFilterBoxFactory.Builder()
                    .setLayoutView(defaultStyle.getLayoutView())
                    .build(this)
                    .show();
        });

    }

    private void initData() {
        //省市区三级联动
        List<JsonBean.CityBean> cityBeanList = new ArrayList<>();
        JsonBean jsonBean = new JsonBean();
        jsonBean.setId("1");
        jsonBean.setName("四川");
        JsonBean.CityBean cityBean = new JsonBean.CityBean();
        cityBean.setId("1");
        cityBean.setName("成都");
        //下面所有区添加到成都市
        List<TextBean> area = new ArrayList<>();
        area.add(new TextBean("1","锦江",false));
        area.add(new TextBean("2","金牛",false));
        area.add(new TextBean("3","高兴",false));
        cityBean.setArea(area);

        JsonBean.CityBean cityBean2 = new JsonBean.CityBean();
        cityBean2.setId("2");
        cityBean2.setName("广元");
        List<TextBean> a = new ArrayList<>();
        a.add(new TextBean("","",false));
        cityBean2.setArea(a);

        cityBeanList.add(cityBean);
        cityBeanList.add(cityBean2);
        jsonBean.setCityList(cityBeanList);
       //添加省
        List<JsonBean.CityBean> cityBeanList1 = new ArrayList<>();
        JsonBean jsonBean1 = new JsonBean();
        jsonBean1.setId("2");
        jsonBean1.setName("湖南");
        JsonBean.CityBean cityBean1 = new JsonBean.CityBean();
        cityBean1.setId("");
        cityBean1.setName("");
        List<TextBean> area1 = new ArrayList<>();
        area1.add(new TextBean("","",false));
        cityBean1.setArea(area1);
//        JsonBean.CityBean cityBean1 = new JsonBean.CityBean();
//        cityBean1.setName("北京");
//        List<String> area1 = new ArrayList<>();
//        area1.add("朝阳");
//        area1.add("大兴");
//        area1.add("通州");
//        cityBean1.setArea(area1);
//        cityBeanList1.add(cityBean1);
        cityBeanList1.add(cityBean1);
        jsonBean1.setCityList(cityBeanList1);
        options1Items.add(jsonBean);
        options1Items.add(jsonBean1);
        for(int i = 0; i < options1Items.size(); i++){//遍历省份
            ArrayList<TextBean> cityList = new ArrayList<>();//该省的城市列表（第二级）
            List<List<TextBean>> province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）
            ArrayList<TextBean> city_AreaList = new ArrayList<>();//该城市的所有地区列表
            for (int c = 0; c < options1Items.get(i).getCityList().size(); c++){//遍历该省份的所有城市
//                if (options1Items.get(i).getCityList().size() ==0 ||
//                        options1Items.get(i).getCityList().get(c) == null){
//                    cityList.add(new TextBean("","",false));//添加城市
//                    city_AreaList.add(new TextBean("","",false));//添加区
//                }else{
                    String id = options1Items.get(i).getCityList().get(c).getId();
                    String cityName = options1Items.get(i).getCityList().get(c).getName();
                    cityList.add(new TextBean(id,cityName,false));//添加城市
                    //如果无地区数据,建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                    if (options1Items.get(i).getCityList().get(c).getArea().get(0).getId().equals("")
                            || options1Items.get(i).getCityList().get(c).getArea().get(0).getId().equals("")) {
                        city_AreaList.add(new TextBean("","",false));
                    } else {
                        city_AreaList.addAll(options1Items.get(i).getCityList().get(c).getArea());
                    }
//                }
//                    city_AreaList.addAll(options1Items.get(i).getCityList().get(c).getArea());
                    province_AreaList.add(city_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(cityList);

            /**
             * 添加地区数据
             */
            options3Items.add(province_AreaList);
        }
//        nOptions1Items.add(new TextBean(0,"四川0",false));
//        nOptions1Items.add(new TextBean(1,"四川1",false));
//        nOptions1Items.add(new TextBean(2,"四川2",false));
//        nOptions1Items.add(new TextBean(3,"四川3",false));
    }
}