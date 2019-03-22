package com.tldxdy.mydemo.retrofitMvp;

import com.tldxdy.base.frame.presenter.BasePresenter;
import com.tldxdy.base.frame.view.BaseView;
import com.tldxdy.mydemo.retrofitMvp.model.TestBean;

import java.util.List;

public interface TestContact {
    interface view extends BaseView {
        /**
         * 设置数据
         *
         * @param dataList
         */
        void setData(List<TestBean.StoriesBean> dataList);

    }

    interface presenter extends BasePresenter {
        /**
         * 获取数据
         */
        void getData();
    }
}
