package com.tldxdy.base.frame.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.tldxdy.base.frame.presenter.BasePresenter;
import com.tldxdy.base.frame.view.BaseView;

public abstract class BaseMvpActivity<P extends BasePresenter> extends BaseActivity implements BaseView{
    public P presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        presenter = initPresenter();
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onDestroy() {
        if(null != presenter){
            presenter.detach();//释放presenter中解绑释放
            presenter = null;
        }
        super.onDestroy();

    }

    protected abstract P initPresenter();

    @Override
    public void dismissLoadingDialog() {

    }

    @Override
    public void showLoadingDialog(String msg) {

    }

}
