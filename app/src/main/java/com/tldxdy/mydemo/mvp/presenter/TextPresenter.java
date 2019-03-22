package com.tldxdy.mydemo.mvp.presenter;

import com.tldxdy.mydemo.mvp.model.TextModel;
import com.tldxdy.mydemo.mvp.view.IView;

public class TextPresenter {
    IView view;
    TextModel model;

    public TextPresenter(IView view){
        this.view = view;
        model = new TextModel();
    }
    public void loadData(){
        //模拟加载网络数据
        model.setName("你的名字叫张三");

        //加载好了执行
        setData(model);
    }

    public void setData(TextModel model){
        view.setData(model);
    }
}
