package com.tldxdy.mydemo.mvp;

import android.os.Bundle;
import android.widget.TextView;

import com.tldxdy.base.frame.activity.BaseActivity;
import com.tldxdy.mydemo.R;
import com.tldxdy.mydemo.mvp.model.TextModel;
import com.tldxdy.mydemo.mvp.presenter.TextPresenter;
import com.tldxdy.mydemo.mvp.view.IView;

import butterknife.BindView;
import butterknife.OnClick;

public class MvpTextActivity extends BaseActivity implements IView{

    TextPresenter presenter;

    @BindView(R.id.tv_name)
    TextView tv_name;

    @Override
    protected int getlayoutId() {
        return R.layout.activity_mvp_text;
    }

    @Override
    protected void initView() {
        presenter = new TextPresenter(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }
    @OnClick(R.id.button)
    protected void Click(){
        presenter.loadData();
    }


    @Override
    public void setData(TextModel model) {
        tv_name.setText(model.getName());
    }

    @Override
    public void setData(String str) {
        tv_name.setText(str);
    }
}
