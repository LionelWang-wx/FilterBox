package com.lionelwang.library.base;

/**
 * base级主容器Factory
 * 1.负责调度不同的主容器
 * 2.对扩展开放,对修改关闭(支持自定义主容器)
 */
public abstract class BaseFilterBoxFactory{

       public BaseFilterBoxFactory() {
              init();
       }
       void init(){
              new MyApplication();
       }

       public abstract void getMainMode();
}
