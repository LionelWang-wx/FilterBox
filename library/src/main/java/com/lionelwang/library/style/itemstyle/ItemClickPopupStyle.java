package com.lionelwang.library.style.itemstyle;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lionelwang.library.R;
import com.lionelwang.library.base.BaseItemStyle;
import com.lionelwang.library.base.BaseViewHolder;
import com.lionelwang.library.bean.TextBean;
import com.lionelwang.library.click.DialogActionListener;
import com.lionelwang.library.click.DialogSelectedChangeListener;
import com.lionelwang.library.click.SelectedListener;
import com.lionelwang.library.click.SlideListener;
import com.lionelwang.library.mode.dialogmode.DialogMode;
import com.lionelwang.library.mode.itemMode.ItemClickPopupMode;
import com.lionelwang.library.viewholder.ItemClickPopupViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 点击弹出二级弹窗item样式
 */
public class ItemClickPopupStyle extends BaseItemStyle<List<TextBean>> {

    private Context context;
    private String label;
    private List<TextBean> resultSelectedList;
    private DialogActionListener actionListener;
    //第一列
    private List<TextBean> options1Items;
    //第二列
    private List<List<TextBean>> options2Items;
    //第三列
    private List<List<List<TextBean>>> options3Items;
    //不联动第一列
    private List<TextBean> nOptions1Items;
    //不联动第二列
    private List<TextBean> nOptions2Items;
    //不联动第三列
    private List<TextBean> nOptions3Items;
    //弹窗模式
    private DialogMode dialogMode;
    //联动数据是否完整传入
    private boolean isLinkageCompleteData;
    //滑动监听
    private SlideListener slideListener;
    //是否展示全部选项
    private boolean isShowAllSelect;
    //确认监听
    private SelectedListener selectedListener;
    //弹窗标题
    private String titleName;
    private ItemClickPopupViewHolder viewHolder;
    //bar标题栏
    private List<TextBean> barTitles;
    //多级选择内容
    private List<TextBean> contents;
    //SingleBarMode的选中数据监听
    private DialogSelectedChangeListener dialogSelectedChangeListener;


    public ItemClickPopupStyle(Context context, Builder builder) {
        this.context = context;
        this.label = builder.label;
//        this.selectedList = builder.selectedList;
        this.options1Items = builder.options1Items;
        this.options2Items = builder.options2Items;
        this.options3Items = builder.options3Items;

        this.nOptions1Items = builder.nOptions1Items;
        this.nOptions2Items = builder.nOptions2Items;
        this.nOptions3Items = builder.nOptions3Items;

        this.actionListener = builder.actionListener;
        this.isLinkageCompleteData = builder.isLinkageCompleteData;
        this.slideListener = builder.slideListener;
        this.isShowAllSelect = builder.isShowAllSelect;
        this.selectedListener = builder.selectedListener;
        this.titleName = builder.titleName;
        this.resultSelectedList = builder.resultSelectedList;

        this.dialogMode = builder.dialogMode;

        this.barTitles = builder.barTitles;
        this.contents = builder.contents;
        this.dialogSelectedChangeListener = builder.dialogSelectedChangeListener;
    }

    @Override
    public int getItemLayoutRes() {
        return R.layout.item_click_popup_style;
    }

    @Override
    public BaseViewHolder getItemViewHolder(View view) {
        if (viewHolder == null) {
            initViewHolder(view);
        }
        return viewHolder;
    }

    private void initViewHolder(View view) {
        viewHolder = new ItemClickPopupViewHolder.Builder()
                .setBarTitles(barTitles)
                .setContents(contents)
                .setLabel(label)
                .setResultSelectedList(resultSelectedList)
                .setDialogMode(dialogMode)
                .setDialogActionListener(actionListener)
                .setSlideListener(slideListener)
                .setSelectedListener(selectedListener)
                .setTitleName(titleName)
                .setNOptionsItems(nOptions1Items)
                .setNOptionsItems(nOptions1Items, nOptions2Items, nOptions3Items)
                .setOptionsItems(options1Items, options2Items, options3Items)
                .setLinkageCompleteData(isLinkageCompleteData)
                .setShowAllSelect(isShowAllSelect)
                .setDialogSelectedChangeListener(dialogSelectedChangeListener)
                .build(view, context);
    }

    @Override
    public int getItemStyleMode() {
        return new ItemClickPopupMode().getItemStyleMode();
    }

    @Override
    public List<TextBean> getItemStyleData() {
        return viewHolder.getItemStyleData();
    }

    @Override
    public String getItemLabel() {
        return label;
    }

    @Override
    public void clearSelectedItem() {
        //重置 1.有全部选项需要选中全部选项,其他取消选中
        //    2.无全部选项,全部取消选中
        switch (dialogMode) {
            case SINGLE_LEVEL_MODE:
                if (isShowAllSelect) {
                    for (TextBean bean : nOptions1Items) {
                        if (TextUtils.equals(bean.getText(), "全部") &&
                                TextUtils.equals(bean.getId(), "0")) {
                            bean.setSelected(true);
                        } else {
                            bean.setSelected(false);
                        }
                    }
                } else {
                    for (TextBean bean : nOptions1Items) {
                        bean.setSelected(false);
                    }
                }
                break;
            case THREE_LEVEL_MODE:
                for (TextBean bean1 : nOptions1Items) {
                    bean1.setSelected(false);
                }
                for (TextBean bean2 : nOptions2Items) {
                    bean2.setSelected(false);
                }
                for (TextBean bean3 : nOptions3Items) {
                    bean3.setSelected(false);
                }
                break;
            case THREE_LINKAGE_MODE:
                for (int i = 0; i < options1Items.size(); i++) {
                    options1Items.get(i).setSelected(false);
                    for (int j = 0; j < options2Items.size(); j++) {
                        options2Items.get(i).get(j).setSelected(false);
                        for (int k = 0; k < options3Items.size(); k++) {
                            options3Items.get(i).get(j).get(k).setSelected(false);
                        }
                    }
                }
                break;
            case SINGLE_BAR_MODE:

                break;
        }
    }

    /**
     * 刷新barSingleDialog适配器
     */
    public void refresh() {
        viewHolder.refresh();
    }

    /**
     * 刷新联动弹窗
     */
    public void refresh(@NonNull int option1, @Nullable int option2, @Nullable int option3) {
        viewHolder.refresh(option1, option2, option3);
    }


    public static class Builder {
        private String label;
        private DialogActionListener actionListener;


        //第一列
        private List<TextBean> options1Items;
        //第二列
        private List<List<TextBean>> options2Items;
        //第三列
        private List<List<List<TextBean>>> options3Items;

        //不联动第一列
        private List<TextBean> nOptions1Items;
        //不联动第二列
        private List<TextBean> nOptions2Items;
        //不联动第三列
        private List<TextBean> nOptions3Items;
        //弹窗模式
        private DialogMode dialogMode;
        //联动数据是否完整传入
        private boolean isLinkageCompleteData;
        //滑动监听
        private SlideListener slideListener;
        //是否展示全部选项
        private boolean isShowAllSelect;
        //确认监听
        private SelectedListener selectedListener;
        //弹窗标题
        private String titleName;
        //设置选中数据 默认内部生成,非必传
        private List<TextBean> resultSelectedList = new ArrayList<>();
        //bar标题栏
        private List<TextBean> barTitles;
        //多级选择内容
        private List<TextBean> contents;
        //SingleBarMode的选中数据监听
        private DialogSelectedChangeListener dialogSelectedChangeListener;

        public Builder setDialogSelectedChangeListener(DialogSelectedChangeListener dialogSelectedChangeListener) {
            this.dialogSelectedChangeListener = dialogSelectedChangeListener;
            return this;
        }

        public Builder setResultSelectedList(List<TextBean> resultSelectedList) {
            this.resultSelectedList = resultSelectedList;
            return this;
        }

        public Builder setTitleName(String titleName) {
            this.titleName = titleName;
            return this;
        }

        public Builder setSelectedListener(SelectedListener selectedListener) {
            this.selectedListener = selectedListener;
            return this;
        }

        public Builder setShowAllSelect(boolean showAllSelect) {
            this.isShowAllSelect = showAllSelect;
            return this;
        }

        public Builder setLinkageCompleteData(boolean isLinkageCompleteData) {
            this.isLinkageCompleteData = isLinkageCompleteData;
            return this;
        }

        /**
         * 三级联动
         *
         * @param options1Items
         * @param options2Items
         * @param options3Items
         * @return
         */
        public Builder setOptionsItems(List<TextBean> options1Items,
                                       List<List<TextBean>> options2Items,
                                       List<List<List<TextBean>>> options3Items) {
            this.options1Items = options1Items;
            this.options2Items = options2Items;
            this.options3Items = options3Items;
            return this;
        }

        /**
         * 三级不联动
         *
         * @param nOptions1Items
         * @param nOptions2Items
         * @param nOptions3Items
         * @return
         */
        public Builder setNOptionsItems(List<TextBean> nOptions1Items,
                                        List<TextBean> nOptions2Items,
                                        List<TextBean> nOptions3Items) {
            this.nOptions1Items = nOptions1Items;
            this.nOptions2Items = nOptions2Items;
            this.nOptions3Items = nOptions3Items;
            return this;
        }

        /**
         * 不联动的单行数据
         *
         * @param nOptions1Items
         * @return
         */
        public Builder setNOptionsItems(List<TextBean> nOptions1Items) {
            this.nOptions1Items = nOptions1Items;
            return this;
        }

        public Builder setDialogMode(DialogMode dialogMode) {
            this.dialogMode = dialogMode;
            return this;
        }

        public Builder setLabel(String label) {
            this.label = label;
            return this;
        }

        public Builder setDialogActionListener(DialogActionListener actionListener) {
            this.actionListener = actionListener;
            return this;
        }

        public Builder setSlideListener(SlideListener slideListener) {
            this.slideListener = slideListener;
            return this;
        }

        public Builder setBarTitles(List<TextBean> barTitles) {
            this.barTitles = barTitles;
            return this;
        }

        public Builder setContents(List<TextBean> contents) {
            this.contents = contents;
            return this;
        }

        public ItemClickPopupStyle build(Context context) {
            return new ItemClickPopupStyle(context, this);
        }
    }
}
