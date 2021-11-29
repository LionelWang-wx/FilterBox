package com.lionelwang.library.bean;


import com.contrarywind.interfaces.IPickerViewData;

import java.util.List;


public class JsonBean implements IPickerViewData{



    private String id;
    private String name;
    private List<CityBean> city;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CityBean> getCityList() {
        return city;
    }

    public void setCityList(List<CityBean> city) {
        this.city = city;
    }

    // 实现 IPickerViewData接口
    // 这个用来显示在PickerView上面的字符串
    // PickerView会过IPickerViewData获取getPickerViewText方法显示出来
    @Override
    public String getPickerViewText() {
        return this.name;
    }


    public static class CityBean {

        private String id;
        private String name;
        private List<TextBean> area;


        public String getId(){
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<TextBean> getArea() {
            return area;
        }

        public void setArea(List<TextBean> area) {
            this.area = area;
        }
    }
}
