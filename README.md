# FilterBox
一、主容器
   1.有一种可选择的固定模式
   2.固定模式的布局可使用默认布局,也可通过setLayoutRes()自定义布局
   3.主容器一个模式只能对应一种样式
#主容器模式(展示的形式) ---------- 主容器样式(展示的具体布局) ------>不同与item对应关系
    vs                   vs
#item模式(展示的具体布局)  ---------- item样式(展示的具体布局)

//item是否展示全部选项 默认true
private boolean isShowAllSelect;
//item是否展示选项列表 默认false
private boolean isShowSelectList;

#ItemClickList 选中情况1.有全部选项 单选 多选  2.没有全部选项 单选 多选
#当前弹窗不是主弹窗时需要设置DialogActionListener
#setLinkageCompleteData 设置联动数据是否传入完成      false->需要进一步处理联动数据变化
