package com.yc.aeiou.view.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.kk.utils.LogUtil;
import com.yc.aeiou.R;
import com.yc.aeiou.adaper.PagerAdapterMain;
import com.yc.aeiou.bean.ListNetBean;
import com.yc.aeiou.bean.ListNetListBean;
import com.yc.aeiou.contract.ListContract;
import com.yc.aeiou.presenter.ListContractPresenter;
import com.yc.aeiou.presenter.MainContractPresenter;
import com.yc.music.bean.MusicPlayerStatusBean;
import com.yc.music.listener.MusicInitializeCallBack;
import com.yc.music.listener.MusicPlayerCallBack;
import com.yc.music.listener.MusicPlayerObservable;
import com.yc.music.manager.MusicPlayerManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import butterknife.OnPageChange;


public class MainActivity extends BasePermissionActivity implements ListContract.ListContractView  /*MainContract.MainContractView */ {


    private MainContractPresenter mMainPresenter;


    @BindViews({R.id.iv_main_home, R.id.iv_main_learn, R.id.iv_main_read, R.id.iv_main_phonics, R.id.iv_main_icon, R.id.iv_main_share})
    public List<ImageView> tabImageViews;

    @BindView(R.id.tv_main_foreign_teacher)
    TextView tvForeignTeacher;

    @BindView(R.id.view_pager_main)
    ViewPager viewPager;
    private ListContractPresenter mListContractPresenter;
    private PagerAdapterMain mPagerAdapterMain;


    @OnPageChange(R.id.view_pager_main)
    public void onPageSelected(int position) {
        switchTab(position, true);
    }


    @OnClick({R.id.iv_main_icon, R.id.iv_main_home, R.id.iv_main_learn, R.id.iv_main_read, R.id.iv_main_phonics, R.id.iv_main_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_main_icon:

                break;
            case R.id.iv_main_home:
                switchTab(0, false);
                break;
            case R.id.iv_main_learn:
                switchTab(1, false);
                break;
            case R.id.iv_main_read:
                switchTab(2, false);
                break;
            case R.id.iv_main_phonics:
                switchTab(3, false);
                break;
            case R.id.iv_main_share:

                break;
            default:
                LogUtil.msg(" --> view.getId() " + view.getId());
                break;
        }
    }

    private void switchTab(int position, boolean isPage) {
        int currentItem = viewPager.getCurrentItem();
        if (!isPage && position == currentItem) {
            return;
        }
        cleanAllTab();
        ImageView imageView = tabImageViews.get(position);
        switch (position) {
            case 0:
                Glide.with(this).load(R.mipmap.main_index_selected).into(imageView);
                break;
            case 1:
                Glide.with(this).load(R.mipmap.main_learn_selected).into(imageView);
                break;
            case 2:
                Glide.with(this).load(R.mipmap.main_read_to_me_selected).into(imageView);
                break;
            case 3:
                Glide.with(this).load(R.mipmap.main_phonics_selected).into(imageView);
                break;
        }
        viewPager.setCurrentItem(position);
    }

    private void cleanAllTab() {
        Glide.with(this).load(R.mipmap.main_index).into(tabImageViews.get(0));
        Glide.with(this).load(R.mipmap.main_learn).into(tabImageViews.get(1));
        Glide.with(this).load(R.mipmap.main_read_to_me).into(tabImageViews.get(2));
        Glide.with(this).load(R.mipmap.main_phonics).into(tabImageViews.get(3));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }


    @Override
    protected void initViews() {
//        tvForeignTeacher.setText("1234565");

//        checkSdPermissions();
    }

    @Override
    protected void onRequestPermissionSuccess() {

    }

    @Override
    protected void initData() {

        //初始化媒体播放器
        MusicPlayerManager.getInstance().bindService(this, new MusicInitializeCallBack() {
            @Override
            public void onSuccess() {
                MusicPlayerManager.getInstance().addOnPlayerEventListener(new MusicPlayerCallBack() {
                    @Override
                    public void onMusicPlayerPrepared(long totalDurtion) {
                        MusicPlayerStatusBean musicPlayerStatusBean = new MusicPlayerStatusBean(totalDurtion);
                        musicPlayerStatusBean.setStatus(musicPlayerStatusBean.STATUS_PREPARED);
                        MusicPlayerObservable.getInstance().updataSubjectObserivce(musicPlayerStatusBean);
                    }

                    @Override
                    public void onMusicPlayerError(int event, int extra, String content) {
                        MusicPlayerStatusBean musicPlayerStatusBean = new MusicPlayerStatusBean(event, extra, content);
                        musicPlayerStatusBean.setStatus(musicPlayerStatusBean.STATUS_ERROR);
                        MusicPlayerObservable.getInstance().updataSubjectObserivce(musicPlayerStatusBean);
                    }
                });

            }
        });

        //视频播放器初始化
//        VideoPlayerManager.getInstance()
//                //循环模式
////                .setLoop(true)
//                //悬浮窗中打开播放器的绝对路径
//                .setPlayerActivityClassName(MainActivity.class.getCanonicalName());


        mListContractPresenter = new ListContractPresenter(this);
        mListContractPresenter.attachView(this);

        ListNetBean listNetBean = (ListNetBean) mListContractPresenter.loadDataList();
        if (listNetBean != null) {
            ArrayList<ListNetListBean> list = listNetBean.list;
            mPagerAdapterMain = new PagerAdapterMain(getSupportFragmentManager(), 0, list);
            viewPager.setAdapter(mPagerAdapterMain);
        }


//        mMainPresenter = new MainContractPresenter(this);
//        mMainPresenter.attachView(this);
//        mMainPresenter.loadDataClass();
//        mMainPresenter.loadDataList();


    }


    @Override
    public void showLoading() {
        LogUtil.msg("showLoading -->  ");
    }

    @Override
    public void showError(int code, String msg) {
//        int codeClass = mMainPresenter.mEnginBase.NET_ERROR_CODE_CLASS;
//        int codeList = mMainPresenter.mEnginBase.NET_ERROR_CODE_LIST;
        LogUtil.msg("showError --> code " + code + " msg " + msg);
    }


    @Override
    public void loadDataListSuccess(ListNetBean data) {
        ArrayList<ListNetListBean> list = data.list;
        mPagerAdapterMain = new PagerAdapterMain(getSupportFragmentManager(), 0, list);
        viewPager.setAdapter(mPagerAdapterMain);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mListContractPresenter.detachView();
    }

}
