package com.yc.aeiou.view.activity;

import android.content.Intent;
import android.os.Handler;


import com.yc.aeiou.R;
import com.yc.aeiou.bean.InitBean;
import com.yc.aeiou.contract.InitContract;
import com.yc.aeiou.presenter.InitContractPresenter;

public class SplashActivity extends BaseActivity implements InitContract.InitContractView {


    private InitContractPresenter mInitPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initData() {

        mInitPresenter = new InitContractPresenter(this);
        mInitPresenter.attachView(this);
//        mInitPresenter.netInitData();

        startNext();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void initDataSuccess(InitBean data) {
        startNext();
    }

    private void startNext() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 10);
    }


    @Override
    public void showError(int code, String msg) {
        startNext();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mInitPresenter.detachView();
    }
}
