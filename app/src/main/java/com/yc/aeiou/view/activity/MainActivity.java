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
import com.qw.soul.permission.SoulPermission;
import com.qw.soul.permission.bean.Permission;
import com.qw.soul.permission.callbcak.CheckRequestPermissionListener;
import com.yc.aeiou.R;
import com.yc.aeiou.adaper.PagerAdapterMain;
import com.yc.aeiou.bean.ClassNetBean;
import com.yc.aeiou.bean.ListNetBean;
import com.yc.aeiou.bean.ListNetListBean;
import com.yc.aeiou.contract.ListContract;
import com.yc.aeiou.contract.MainContract;
import com.yc.aeiou.presenter.ListContractPresenter;
import com.yc.aeiou.presenter.MainContractPresenter;

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

        mListContractPresenter = new ListContractPresenter(this);
        mListContractPresenter.attachView(this);

        ListNetBean listNetBean = (ListNetBean) mListContractPresenter.loadDataList();
        if (listNetBean != null) {
            ArrayList<ListNetListBean> list = listNetBean.list;
            LogUtil.msg("initData --> " + list.size());

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
        for (ListNetListBean l : list
        ) {
            LogUtil.msg("loadDataListSuccess list --> " + l.toString());
        }

//        ListNetListBean listNetListBean = list.get(0);
//        listNetListBean.desp = "0001";
        mPagerAdapterMain = new PagerAdapterMain(getSupportFragmentManager(), 0, list);
        viewPager.setAdapter(mPagerAdapterMain);

    }

/*    @Override
    public void loadDataClassSuccess(ClassNetBean data) {
        ClassNetBean.InfoBean info = data.info;

//        {id='2', app_id='5', title='微课', type=null, alias='', price='89.00', real_price='49.00', desp='', status='0', sort='3', attr='0',
//        field1='1', field2='0', icon='http://tic.upkao.com/Upload/icon/good_info_num3.png', sub_title='24节原创微课协助记忆音标！'}
        LogUtil.msg("loadDataClassSuccess info --> " + info.toString());

        List<ClassNetBean.ListBean> list = data.list;
        for (ClassNetBean.ListBean l : list
        ) {
//            {id='9', cover='http://voice.wk2.com/img/2017091801.jpg', video='http://voice.wk2.com/video/2017091801.mp4	'
//                    , title='说说英语趣味音标课        ', desp='共十二节微课，包教包会！', sort='500', add_time='0', app_id='5'}
            LogUtil.msg("loadDataClassSuccess list --> " + l.toString());
        }
    }*/

    private void checkSdPermissions() {
        SoulPermission.getInstance().checkAndRequestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                //if you want do noting or no need all the callbacks you may use SimplePermissionAdapter instead
                new CheckRequestPermissionListener() {
                    @Override
                    public void onPermissionOk(Permission permission) {
//                        PhotoPicker.builder()
//                                .setPhotoCount(1)
//                                .setShowCamera(true)
//                                .setShowGif(true)
//                                .setPreviewEnabled(false)
//                                .start(CreateBeforeActivity.this, PhotoPicker.REQUEST_CODE);
                    }

                    @Override
                    public void onPermissionDenied(Permission permission) {
//                                Activity activity = SoulPermission.getInstance().getTopActivity();
                                /*if (null == activity) {
                                    return;
                                }*/
                        //绿色框中的流程
                        //用户第一次拒绝了权限、并且没有勾选"不再提示"这个值为true，此时告诉用户为什么需要这个权限。
                        /*if (permission.shouldRationale()) {
                            showToastShort("未获取到相机权限");
                        } else {
                            //此时请求权限会直接报未授予，需要用户手动去权限设置页，所以弹框引导用户跳转去设置页
                            String permissionDesc = permission.getPermissionNameDesc();
                            new AlertDialog.Builder(CreateBeforeActivity.this)
                                    .setTitle("提示")
                                    .setMessage(permissionDesc + "异常，请前往设置－>权限管理，打开" + permissionDesc + "。")
                                    .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            //去设置页
                                            SoulPermission.getInstance().goPermissionSettings();
                                        }
                                    }).create().show();
                        }*/
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mListContractPresenter.detachView();
    }

}
