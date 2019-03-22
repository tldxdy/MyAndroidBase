package com.tldxdy.mydemo.retrofitMvp;

import android.annotation.SuppressLint;

import com.tldxdy.base.frame.presenter.BasePresenterImpl;
import com.tldxdy.base.frame.retroft.ExceptionHelper;
import com.tldxdy.mydemo.retrofitMvp.api.Api;
import com.tldxdy.mydemo.retrofitMvp.model.TestBean;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class TestPresenter extends BasePresenterImpl<TestContact.view> implements TestContact.presenter {
    public TestPresenter(TestContact.view view) {
        super(view);
    }

    /**
     * 获取数据
     */
    @SuppressLint("CheckResult")
    @Override
    public void getData() {
        Api.getInstance().test()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        addDisposable(disposable);
                        view.showLoadingDialog("");
                    }
                })
                .map(new Function<TestBean, List<TestBean.StoriesBean>>() {
                    @Override
                    public List<TestBean.StoriesBean> apply(@NonNull TestBean testBean) throws Exception {
                        return testBean.getStories();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<TestBean.StoriesBean>>() {
                    @Override
                    public void accept(@NonNull List<TestBean.StoriesBean> storiesBeen) throws Exception {
                        view.dismissLoadingDialog();
                        view.setData(storiesBeen);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        view.dismissLoadingDialog();
                        ExceptionHelper.handleException(throwable);
                    }
                });
    }
}
