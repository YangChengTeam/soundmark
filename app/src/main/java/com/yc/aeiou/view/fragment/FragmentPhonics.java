package com.yc.aeiou.view.fragment;

import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import com.yc.aeiou.R;
import com.yc.aeiou.bean.ClassNetBean;
import com.yc.aeiou.contract.ClassContract;
import com.yc.aeiou.presenter.ClassContractPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;

/**
 * Created by caokun on 2019/11/5 11:51.
 */

public class FragmentPhonics extends BaseMainFragment implements ClassContract.ClassContractView {


    @BindViews({R.id.tv_phonics_des01, R.id.tv_phonics_des02})
    public List<TextView> tvDes;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_phonics;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initData() {
        ClassContractPresenter classContractPresenter = new ClassContractPresenter(mMainActivity);
        classContractPresenter.attachView(this);
        ClassNetBean classNetBean = (ClassNetBean) classContractPresenter.loadDataClass();


        tvDes.get(0).setText(Html.fromHtml("<b><tt>课程简介：</tt></b>趣味音标课共12节，利用趣味的英语三字经、绕口令朗朗上口的韵律，连贯的语义帮助记忆及巩固音标读法，同时帮助记忆单词拼写及意思。"));
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void showError(int code, String msg) {

    }

    @Override
    public void loadDataClassSuccess(ClassNetBean data) {

//        {info=InfoBean{id='2', app_id='5', title='微课', type=null, alias='', price='89.00', real_price='49.00', desp='',
//        status='0', sort='3', attr='0', field1='1', field2='0', icon='http://tic.upkao.com/Upload/icon/good_info_num3.png',
//        sub_title='24节原创微课协助记忆音标！'},
//            list=[ListBean{id='9', cover='http://voice.wk2.com/img/2017091801.jpg',
//        video='http://voice.wk2.com/video/2017091801.mp4	', title='说说英语趣味音标课        ', desp='共十二节微课，包教包会！',
//        sort='500', add_time='0', app_id='5'},
//        ListBean{id='10', cover='http://voice.wk2.com/img/2017091802.jpg',
//        video='http://voice.wk2.com/video/2017091802.mp4	', title='说说英语趣味音标课
//        ', desp='第二课：前元音', sort='500', add_time='0', app_id='5'},
//    ListBean{id='11', cover='http://voice.wk2.com/img/2017091803.jpg',
//        video='http://voice.wk2.com/video/2017091803.mp4	', title='说说英语趣味音标课        ', desp='第三课：爆破音', sort='500',
//        add_time='0', app_id='5'},
//        ListBean{id='12', cover='http://voice.wk2.com/img/2017091804.jpg', video='http://voice.wk2.com/video/2017091804.mp4
//        ', title='说说英语趣味音标课        ', desp='第四课：中元音和后元音', sort='500', add_time='0', app_id='5'},
//        ListBean{id='13', cover='http://voice.wk2.com/img/2017091805.jpg', video='http://voice.wk2.com/video/2017091805.mp4
//        ', title='说说英语趣味音标课        ', desp='第五课：摩擦音', sort='500', add_time='0', app_id='5'},
//    ListBean{id='14',
//        cover='http://voice.wk2.com/img/2017091806.jpg', video='http://voice.wk2.com/video/2017091806.mp4	', title='说说英语趣味音标课
//        ', desp='第六课：后元音', sort='500', add_time='0', app_id='5'},
//    ListBean{id='15', cover='http://voice.wk2.com/img/2017091807.jpg',
//        video='http://voice.wk2.com/video/2017091807.mp4	', title='说说英语趣味音标课        ', desp='第七课：摩擦音和半元音', sort='500',
//        add_time='0', app_id='5'},
//        ListBean{id='16', cover='http://voice.wk2.com/img/2017091808.jpg', video='http://voice.wk2.com/video/2017091808.mp4
//        ', title='说说英语趣味音标课        ', desp='第八课：合口双元音', sort='500', add_time='0', app_id='5'},
//        ListBean{id='17', cover='http://voice.wk2.com/img/2017091809.jpg', video='http://voice.wk2.com/video/2017091809.mp4	',
//        title='说说英语趣味音标课        ', desp='第九课：破擦音', sort='500', add_time='0', app_id='5'},
//        ListBean{id='18',
//        cover='http://voice.wk2.com/img/2017091810.jpg', video='http://voice.wk2.com/video/2017091810.mp4	', title='说说英语趣味音标课
//        ', desp='第十课：集中双元音', sort='500', add_time='0', app_id='5'},
//    ListBean{id='19', cover='http://voice.wk2.com/img/2017091811.jpg',
//        video='http://voice.wk2.com/video/2017091811.mp4	', title='说说英语趣味音标课        ', desp='第十一课：鼻音和舌侧音', sort='500', add_time='0',
//        app_id='5'},
//        ListBean{id='20', cover='http://voice.wk2.com/img/2017091812.jpg', video='http://voice.wk2.com/video/2017091812.mp4	',
//        title='说说英语趣味音标课        ', desp='第十二课：音标复习', sort='500', add_time='0', app_id='5'}]}

        Log.d("ssssss", "loadDataClassSuccess: data " + data.toString());
    }

}
