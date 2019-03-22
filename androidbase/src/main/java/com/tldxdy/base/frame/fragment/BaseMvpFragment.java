package com.tldxdy.base.frame.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tldxdy.base.frame.presenter.BasePresenter;
import com.tldxdy.base.frame.view.BaseView;

public abstract class BaseMvpFragment<P extends BasePresenter> extends BaseFragment implements BaseView{

    public P presenter;
    protected abstract P initPresenter();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = initPresenter();
    }

    @Override
    public void onDestroyView() {
        if(null != presenter){
            presenter.detach();//释放presenter中解绑释放
            presenter = null;
        }
        super.onDestroyView();

    }

    @Override
    public void dismissLoadingDialog() {

    }

    @Override
    public void showLoadingDialog(String msg) {

    }

}
