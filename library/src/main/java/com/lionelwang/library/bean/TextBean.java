package com.lionelwang.library.bean;

import com.contrarywind.interfaces.IPickerViewData;

/**
 * TextBean
 */
public class TextBean implements IPickerViewData {
    String id;
    String text;
    //是否选中
    boolean isSelected;
    //标签
    String type;

    public TextBean(){

    }


    public TextBean(String id, String text, boolean isSelected) {
        this.id = id;
        this.text = text;
        this.isSelected = isSelected;
    }

    public TextBean(String id, String text, boolean isSelected, String type) {
        this.id = id;
        this.text = text;
        this.isSelected = isSelected;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String getPickerViewText() {
        return this.text;
    }
}
