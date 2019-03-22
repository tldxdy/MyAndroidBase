package com.tldxdy.mydemo.mvp.view;

import com.tldxdy.mydemo.mvp.model.TextModel;

public interface IView {
    void setData(TextModel model);
    void setData(String str);
}
