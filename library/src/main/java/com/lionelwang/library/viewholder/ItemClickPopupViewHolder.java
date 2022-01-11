package com.lionelwang.library.viewholder;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lionelwang.library.R;
import com.lionelwang.library.base.BaseViewHolder;
import com.lionelwang.library.bean.TextBean;
import com.lionelwang.library.click.DialogActionListener;
import com.lionelwang.library.click.DialogSelectedChangeListener;
import com.lionelwang.library.click.DialogSelectedListener;
import com.lionelwang.library.click.SelectedListener;
import com.lionelwang.library.click.SlideListener;
import com.lionelwang.library.dialog.DialogManager;
import com.lionelwang.library.mode.dialogmode.DialogMode;
import com.lionelwang.library.utils.DataUtils;

import java.util.List;

/**
 * 点击弹出二级弹窗
 */
public class ItemClickPopupViewHolder extends BaseViewHolder<List<TextBean>> {

    private TextView labelView;
    private TextView selectorView;
    private Context context;

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
    //返回选中的所有数据
    private List<TextBean> resultSelectedList;
    private DialogManager manager;
    //确认监听
    private SelectedListener selectedListener;
    //弹窗标题
    private String titleName;
    //bar标题栏
    private List<TextBean> barTitles;
    //多级选择内容
    private List<TextBean> contents;
    //SingleBarMode的选中数据监听
    private DialogSelectedChangeListener dialogSelectedChangeListener;


    public ItemClickPopupViewHolder(@NonNull View itemView, Context context, Builder builder) {
        super(itemView);
        initView(itemView);
        this.context = context;
        this.label = builder.label;
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
        this.resultSelectedList = builder.resultSelectedList;
        this.selectedListener = builder.selectedListener;
        this.titleName = builder.titleName;
        this.dialogMode = builder.dialogMode;
        this.barTitles = builder.barTitles;
        this.contents = builder.contents;
        this.dialogSelectedChangeListener = builder.dialogSelectedChangeListener;
    }

    @Override
    public void bindViewHolder(BaseViewHolder holder, int position) {
        if (holder instanceof ItemClickPopupViewHolder) {
            //设置标签
            labelView.setText(label);
            //根据当前模式设置选中数据
            if (DataUtils.isEmpty(resultSelectedList)) {
                selectorView.setText("全部");
            } else {
                switch (dialogMode) {
                    case SINGLE_LEVEL_MODE:
                        selectorView.setText(resultSelectedList.get(0).getText());
                        break;
                    case THREE_LEVEL_MODE:
                    case THREE_LINKAGE_MODE:
                        selectorView.setText(resultSelectedList.get(0).getText() + "-" +
                                resultSelectedList.get(1).getText() + "-" +
                                resultSelectedList.get(2).getText());
                        break;
                    case SINGLE_BAR_MODE:
                        StringBuilder sb = new StringBuilder();
                            for (int i = 0; i < resultSelectedList.size(); i++) {
                                if (resultSelectedList.size() - 1 == i) {
                                    sb.append(resultSelectedList.get(i).getText());
                                } else {
                                    sb.append(resultSelectedList.get(i).getText() + "-");
                                }
                            }
                        selectorView.setText(sb.toString());
                        break;
                }
            }

            //打开二级弹窗
            selectorView.setOnClickListener(v -> {
                if (manager == null) {
                    initDialogManager();
                }
                manager.show();
            });
        }
    }

    @Override
    public List<TextBean> getItemStyleData() {
        return resultSelectedList;
    }

    private void initDialogManager(){
        manager = new DialogManager.Builder()
                .setBarTitles(barTitles)
                .setContents(contents)
                .setDialogMode(dialogMode)
                .setNOptionsItems(nOptions1Items)
                .setNOptionsItems(nOptions1Items, nOptions2Items, nOptions3Items)
                .setOptionsItems(options1Items, options2Items, options3Items)
                .setDialogActionListener(actionListener)
                .setShowAllSelect(isShowAllSelect)
                .setTitleName(titleName)
                .setLinkageCompleteData(isLinkageCompleteData)
                .setDialogSelectedChangeListener(dialogSelectedChangeListener)
                .setDialogSelectedListener(new DialogSelectedListener() {
                    @Override
                    public void onDialogSelect(int position1, int position2, int position3, View view, DialogMode dialogMode) {
                        String str = "";
                        resultSelectedList.clear();
                        switch (dialogMode) {
                            case SINGLE_LEVEL_MODE:
                                str = nOptions1Items.get(position1).getText();
                                nOptions1Items.get(position1).setSelected(true);
                                resultSelectedList.addAll(nOptions1Items);
                                //记录选中的位置,再次打开弹窗直接展示上次选中的位置
                                refresh(position1,0,0);
//                                //副弹窗确认回调监听
//                                if (selectedListener != null) {
//                                    selectedListener.onSelected(nOptions1Items.get(position1));
//                                }
                                break;
                            case THREE_LEVEL_MODE:
                                str = nOptions1Items.get(position1).getText() + "-" +
                                        nOptions2Items.get(position2).getText() + "-" +
                                        nOptions3Items.get(position3).getText();
                                resultSelectedList.add(0, nOptions1Items.get(position1));
                                resultSelectedList.add(1, nOptions2Items.get(position2));
                                resultSelectedList.add(2, nOptions3Items.get(position3));
                                refresh(position1,position2,position3);
                                break;
                            case THREE_LINKAGE_MODE:
                                str = options1Items.get(position1).getText() + "-" +
                                        options2Items.get(position1).get(position2).getText() + "-" +
                                        options3Items.get(position1).get(position2).get(position3).getText();
                                resultSelectedList.add(0, options1Items.get(position1));
                                resultSelectedList.add(1, options2Items.get(position1).get(position2));
                                resultSelectedList.add(2, options3Items.get(position1).get(position2).get(position3));
                                refresh(position1,position2,position3);
                                break;
                            case SINGLE_BAR_MODE:
                                StringBuilder sb = new StringBuilder();
                                for (int i = 0; i < barTitles.size(); i++) {
                                    if (barTitles.size() - 1 == i) {
                                        sb.append(barTitles.get(i).getText());
                                    } else {
                                        sb.append(barTitles.get(i).getText() + "-");
                                    }
                                }
                                str = sb.toString();
                                resultSelectedList.addAll(barTitles);
                                break;
                        }
                        selectorView.setText(str);
                    }

                    @Override
                    public void onSelectChanged(int position1, int position2, int position3, DialogMode dialogMode, boolean isLinkageCompleteDa) {
                        switch (dialogMode){
                            case THREE_LINKAGE_MODE:
                                //联动数据不是完整传入,需要处理联动数据变化
                                if (!isLinkageCompleteDa) {
                                    if (TextUtils.isEmpty(options2Items.get(position1).get(position2).getId()) &&
                                            TextUtils.isEmpty(options2Items.get(position1).get(position2).getText())) {
                                        //情况二：第二个item数据为空 滑动的为第一列
                                        if (slideListener != null)
                                            slideListener.onSlideChange(options1Items.get(position1), position1, position2, position3);
                                    } else if (TextUtils.isEmpty(options3Items.get(position1).get(position2).get(position3).getId()) &&
                                            TextUtils.isEmpty(options3Items.get(position1).get(position2).get(position3).getText())) {
                                        //情况一：第三个item数据为空 滑动的为第二列
                                        if (slideListener != null)
                                            slideListener.onSlideChange(options2Items.get(position1).get(position2), position1, position2, position3);
                                    }
                                }
                                break;
                            case SINGLE_BAR_MODE:
                                //未实现
                                break;
                        }
                    }
                })
                .build(context);
    }

    private void initView(View itemView) {
        labelView = itemView.findViewById(R.id.popup_label);
        selectorView = itemView.findViewById(R.id.popup_selector);
    }

    /**
     * 刷新barSingleDialog适配器
     */
    public void refresh() {
        manager.refresh();
    }

    /**
     * 刷新
     * @return
     */
    public void refresh(@NonNull int option1, @Nullable int option2, @Nullable int option3){
        manager.refresh(option1, option2, option3);
    }

    public static class Builder{
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
        //返回选中的所有数据
        private List<TextBean> resultSelectedList;
        //确认监听
        private SelectedListener selectedListener;
        //弹窗标题
        private String titleName;
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

        public Builder setTitleName(String titleName) {
            this.titleName = titleName;
            return this;
        }

        public Builder setSelectedListener(SelectedListener selectedListener) {
            this.selectedListener = selectedListener;
            return this;
        }

        public Builder setResultSelectedList(List<TextBean> resultSelectedList) {
            this.resultSelectedList = resultSelectedList;
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

        public ItemClickPopupViewHolder build(View view, Context context) {
            return new ItemClickPopupViewHolder(view, context, this);
        }
    }
}
