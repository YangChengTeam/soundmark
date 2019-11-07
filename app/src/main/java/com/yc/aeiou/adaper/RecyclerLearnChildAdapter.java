package com.yc.aeiou.adaper;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.aeiou.R;
import com.yc.aeiou.bean.ListNetListExampleBean;

import java.util.ArrayList;

/**
 * Created by caokun on 2019/11/6 18:19.
 */

public class RecyclerLearnChildAdapter extends BaseQuickAdapter<ListNetListExampleBean, BaseViewHolder> {


    private final Context context;
    private String name;
    private OnClickItemListent onClickItemListent;

    public RecyclerLearnChildAdapter(Context context, ArrayList<ListNetListExampleBean> datas, String name) {
        super(R.layout.layout_recycler_learn_child, datas);
        this.name = name;
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, ListNetListExampleBean item) {


        TextView tvTitle = holder.getView(R.id.tv_recycler_learn_child_title);
        TextView tvDes = holder.getView(R.id.tv_recycler_learn_child_des);

        String emphasisTitle = item.letter;
        String tempTitle = "<font color='#FF0000'>" + emphasisTitle + "</font>";
        String s = item.word.replaceAll(emphasisTitle, tempTitle);
        tvTitle.setText(Html.fromHtml(s));

        String emphasisDes = name.substring(1, name.length() - 1);
        String tempDes = "<font color='#FF0000'>" + emphasisDes + "</font>";
        String s2 = item.word_phonetic.replaceAll(emphasisDes, tempDes);
        tvDes.setText(Html.fromHtml(s2));

        LinearLayout llContainer = holder.getView(R.id.ll_recycler_learn_container);
        ProgressBar pb = holder.getView(R.id.pb_recycler_learn_child);



//        holder.addOnClickListener(R.id.ll_recycler_learn_container);

        llContainer.setOnClickListener(v -> onClickItemListent.clickItem(llContainer,pb, getClickPosition(holder)));
    }

    public interface OnClickItemListent {
        void clickItem(LinearLayout itemView,ProgressBar pb, int position);
    }

    public void setOnClickItemListent(OnClickItemListent onClickItemListent) {
        this.onClickItemListent = onClickItemListent;
    }

    private int getClickPosition(BaseViewHolder holder) {
        if (holder.getLayoutPosition() >= getHeaderLayoutCount()) {
            return holder.getLayoutPosition() - getHeaderLayoutCount();
        }
        return 0;
    }
}
