package com.tldxdy.base.frame.view;

/**
 * 公共的View（可以添加公共方法）
 */
public interface BaseView {
    //显示dialog
    void showLoadingDialog(String msg);

    //取消dialog
    void dismissLoadingDialog();

}
