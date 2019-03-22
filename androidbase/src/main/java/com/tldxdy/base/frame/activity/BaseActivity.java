package com.tldxdy.base.frame.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.tldxdy.base.util.AppManager;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity{


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.create().addActivity(this);
        /**
         *      强制竖屏
         */
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(getlayoutId());
        ButterKnife.bind(this);

        initView();
        initData(savedInstanceState);

    }

    protected abstract int getlayoutId();

    protected abstract void initView();

    protected abstract void initData(Bundle savedInstanceState);


    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.create().finishActivity(this);
    }

    /**
     *                  页面跳转
     * @param activityClass
     * @param finish
     */
    public void gotoActivity(Class<? extends Activity> activityClass,boolean finish){
        Intent intent = new Intent(this,activityClass);
        startActivity(intent);
        if(finish){
            finish();
        }
    }

    /**
     *              带参数跳转
     * @param activityClass
     * @param bundle
     * @param finish
     */
    public void gotoActivity(Class<? extends Activity> activityClass,Bundle bundle,boolean finish){
        Intent intent = new Intent(this,activityClass);
        if (null != bundle){
            intent.putExtras(bundle);
        }
        startActivity(intent);
        if(finish){
            finish();
        }
    }

    /**
     *                  带参数跳转(带跳转页面的启动模式)
     * @param activityClass
     * @param bundle
     * @param flags
     * @param finish
     */
    public void gotoActivity(Class<? extends Activity> activityClass, Bundle bundle,int flags, boolean finish){
        Intent intent = new Intent(this,activityClass);
        if (null != bundle){
            intent.putExtras(bundle);
        }
        intent.setFlags(flags);
        startActivity(intent);
        if(finish){
            finish();
        }
    }

}
