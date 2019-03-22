package com.tldxdy.mydemo.retrofitMvp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.tldxdy.base.frame.activity.BaseMvpActivity;
import com.tldxdy.mydemo.R;
import com.tldxdy.mydemo.retrofitMvp.model.TestBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class TestActivity extends BaseMvpActivity<TestContact.presenter> implements TestContact.view{

    @BindView(R.id.tv_data)
    TextView tv_data;

    private List<TestBean.StoriesBean> list = new ArrayList<>();//数据

    @Override
    protected TestContact.presenter initPresenter() {
        return new TestPresenter(this);
    }

    @Override
    protected int getlayoutId() {
        return R.layout.activity_test;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        presenter.getData();
    }

    @Override
    public void setData(List<TestBean.StoriesBean> dataList) {
        tv_data.setText(dataList.toString());
    }
}
