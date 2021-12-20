package com.lionelwang.library.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.lionelwang.library.R;
import com.lionelwang.library.bean.TextBean;
import com.lionelwang.library.click.OnItemClickListener;
import com.lionelwang.library.click.SelectedListener;
import com.lionelwang.library.utils.ToastUtil;
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.util.XPopupUtils;

import java.util.List;

/**
 * 多级选择弹窗
 */
public class MultiLevelPopup extends BottomPopupView {

    private Context context;
    private TextView titleView;
    private TabLayout tabLayout;
    private RecyclerView contentView;

    private String title;
    private List<TextBean> barTitles;
    private List<TextBean> contents;
    private SelectedListener listener;

    private BarContentAdapter barContentAdapter;
    //是否展示全部选项 默认false
    private boolean isShowAllSelect;

    public MultiLevelPopup(@NonNull Context context, Builder builder) {
        super(context);
        this.context = context;
        this.title = builder.title;
        this.barTitles = builder.barTitles;
        this.contents = builder.contents;
        this.listener = builder.listener;
        this.isShowAllSelect = builder.isShowAllSelect;
    }

    private void initView() {
        titleView = this.findViewById(R.id.tv_title);
        tabLayout = this.findViewById(R.id.tabLayout);
        contentView = this.findViewById(R.id.rcv_content);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.layout_bar_multi_level_selection;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        initView();
        if (!TextUtils.isEmpty(title)) {
            titleView.setText(title);
        }
        if(barTitles.size() != 0){
            for (int i = 0; i < barTitles.size(); i++){
                tabLayout.addTab(tabLayout.newTab().setId(Integer.parseInt(barTitles.get(i).getId())).setText(barTitles.get(i).getText()));
            }
            //设置最后一个为选中状态
            tabLayout.getTabAt(tabLayout.getTabCount() - 1).select();
        }else{
                tabLayout.addTab(tabLayout.newTab().setText("请选择"));
        }
        //内容添加全部选项数据
        if (isShowAllSelect){
            contents.add(0,new TextBean("9999999","全部",false,"全部"));
        }

        barContentAdapter = new BarContentAdapter(context, contents, listener);
        barContentAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int positions) {
                if (TextUtils.equals(tabLayout.getTabAt(tabLayout.getSelectedTabPosition()).getText(),"请选择")){
                    //设置当前Tab
                    tabLayout.getTabAt(tabLayout.getTabCount() - 1).setId(Integer.parseInt(contents.get(positions).getId())).setText(contents.get(positions).getText());
                    //新增一个Tab并设置选中
                    //设置barTitle
                    barTitles.add(tabLayout.getTabCount() - 1, new TextBean(contents.get(positions).getId(),
                            contents.get(positions).getText(),
                            false,
                            contents.get(positions).getType()));
                    tabLayout.addTab(tabLayout.newTab().setText("请选择"));
                }else {
                    //重新设置当前选中信息
                    tabLayout.getTabAt(tabLayout.getSelectedTabPosition()).setId(Integer.parseInt(contents.get(positions).getId())).setText(contents.get(positions).getText());
                }
                //设置最后一项为选中状态
                tabLayout.getTabAt(tabLayout.getTabCount() - 1).select();
            }
        });
        contentView.setLayoutManager(new LinearLayoutManager(context));
        contentView.setAdapter(barContentAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //选项卡点击
                int tabPosition = tab.getPosition();
                int tabCount = tabLayout.getTabCount();
                //设置最后一项名称为请选择
                tabLayout.getTabAt(tabCount-1).setText("请选择");
//                Log.e("test", "getPosition =" + tabPosition+",getTabCount = "+tabCount);
                //记录：从低位到高位正序删除Tab会报异常  原因：正序删除会导致position与数据错位
                //解决办法：从高位到低位倒序删除Tab正常
                for (int i = tabCount-2 ;i >tabPosition ;i--){
                    tabLayout.removeTabAt(i);
                    barTitles.remove(i);
//                    Log.e("test", "removeTabAt =" + i);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //上一个选中的选项卡
//                Log.e("test", "getPosition =" + tab.getPosition()+"getTabCount"+tabLayout.getTabCount());
//
//                Log.e("test", "onTabUnselected =" + tab.getText()+" = "+tab.getId());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //重复点击同一个选项卡
//                Log.e("test", "onTabReselected =" + tab.getText());
//                for (int i = tab.getPosition()+1 ;i<tabLayout.getTabCount()-1;i++){
//                    tabLayout.removeTabAt(i);
//                }
            }
        });
    }

    /**
     * 刷新适配器
     */
    public void notifyDataSetChanged() {
        barContentAdapter.notifyDataSetChanged();
    }

    //完全可见执行
    @Override
    protected void onShow() {
        super.onShow();
        Log.e("tag", "知乎评论 onShow");
    }

    //完全消失执行
    @Override
    protected void onDismiss() {
        Log.e("tag", "知乎评论 onDismiss");
    }

    @Override
    protected int getMaxHeight() {
        return (int) (XPopupUtils.getScreenHeight(getContext()) * .7f);
    }


    public static class Builder {

        private String title;
        private List<TextBean> barTitles;
        private List<TextBean> contents;
        private SelectedListener listener;
        //是否展示全部选项 默认false
        private boolean isShowAllSelect = false;

        public Builder setShowAllSelect(boolean isShowAllSelect) {
            this.isShowAllSelect = isShowAllSelect;
            return this;
        }

        public Builder setSelectedListener(SelectedListener selectedListener) {
            this.listener = selectedListener;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
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

        public MultiLevelPopup build(Context context) {
            return new MultiLevelPopup(context, this);
        }
    }
}
