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

//异常修复
//副弹窗添加全部选项
//副弹窗全部选项组装重复问题
//添加副弹窗确认监听
//三级联动清除选中状态功能实现逻辑变化
//监听的空异常处理
//优化主弹窗默认样式标题
//添加弹窗选中数据传递
//添加外部主弹窗刷新功能

//三级联动不完整数据传入,滑动切换数据

//新增关键字和自定范围两种样式
//新增列表信息选择弹窗,实现搜索和自定义布局
//新增仿京东选择器

[![](https://jitpack.io/v/LionelWang-wx/FilterBox.svg)](https://jitpack.io/#LionelWang-wx/FilterBox)

Add it in your root build.gradle at the end of repositories:
1.
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
	
	
2.
   dependencies {
	        implementation 'com.github.LionelWang-wx:FilterBox:Tag'
	}

