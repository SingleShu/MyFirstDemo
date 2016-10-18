package com.abings.baby.ui.finder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.abings.baby.R;
import com.abings.baby.data.model.NewsListItem;
import com.abings.baby.data.remote.RetrofitUtils;
import com.abings.baby.ui.adapter.ABRecyclerViewAdapter;
import com.abings.baby.ui.adapter.ABViewHolderHelper;
import com.abings.baby.utils.DateUtil;
import com.abings.baby.utils.StringUtils;
import com.bumptech.glide.Glide;
import com.socks.library.KLog;

import java.util.Date;

/**
 * Created by Administrator on 2016/2/1.
 */
public class FinderRecyclerViewAdapter extends ABRecyclerViewAdapter<NewsListItem> {

    private boolean mIsIgnoreChange = true;
    private NewsListItem newsListItemBefore;
    private NewsListItem newsListItemAfter;

    public FinderRecyclerViewAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.recyler_item_newinfo);
    }

    @Override
    protected void fillData(ABViewHolderHelper viewHolderHelper, int position, NewsListItem model) {

        Date date = DateUtil.getTimeFromString(model.getCreate_datetime(), "yyyy/MM/dd HH:mm:ss");
        if (DateUtil.isToday(date.getTime())) {
            viewHolderHelper.setVisibility(R.id.date_month, View.GONE);
            viewHolderHelper.setVisibility(R.id.date_day, View.GONE);
            viewHolderHelper.setVisibility(R.id.today, View.VISIBLE);
            if (position > 0) {
                viewHolderHelper.setText(R.id.today, "");
            } else {
                viewHolderHelper.setText(R.id.today, "今天");
            }
        } else {
            viewHolderHelper.setVisibility(R.id.date_month, View.VISIBLE);
            viewHolderHelper.setVisibility(R.id.date_day, View.VISIBLE);
            viewHolderHelper.setVisibility(R.id.today, View.GONE);
            viewHolderHelper.setText(R.id.date_month,DateUtil.getCNdate(DateUtil.getMonth(date.getTime())));
            viewHolderHelper.setText(R.id.date_day, DateUtil.getDay(date.getTime()) + "");

            if (position > 0){
                newsListItemBefore = getItemObj(position - 1);
            }
            if (newsListItemBefore == null || position == 0){
                viewHolderHelper.setText(R.id.date_month, DateUtil.getCNdate(DateUtil.getMonth(date.getTime())));
                viewHolderHelper.setText(R.id.date_day, DateUtil.getDay(date.getTime()) + "");
            }else{
                Date beforedate = DateUtil.getTimeFromString(newsListItemBefore.getCreate_datetime(), "yyyy/MM/dd HH:mm:ss");
                if (DateUtil.getMonth(beforedate.getTime()) == DateUtil.getMonth(date.getTime()) && DateUtil.getDay(beforedate.getTime()) == DateUtil.getDay(date.getTime())){
                    viewHolderHelper.setText(R.id.date_month, "");
                    viewHolderHelper.setText(R.id.date_day, "");
                }else{
                    viewHolderHelper.setText(R.id.date_month, DateUtil.getCNdate(DateUtil.getMonth(date.getTime())));
                    viewHolderHelper.setText(R.id.date_day, DateUtil.getDay(date.getTime()) + "");
                }
            }
        }

        //图片展示的显示
        if (Integer.valueOf(model.getImage_count()) > 0) {
            viewHolderHelper.setVisibility(R.id.imageGroup, View.VISIBLE);
            viewHolderHelper.setVisibility(R.id.video, View.GONE);
            switch (Integer.valueOf(model.getImage_count())) {
                case 0:
                    viewHolderHelper.setVisibility(R.id.imageGroup, View.GONE);
                    break;
                case 1:
                    viewHolderHelper.setVisibility(R.id.image0, View.VISIBLE);
                    viewHolderHelper.setVisibility(R.id.imageOne, View.GONE);
                    viewHolderHelper.setVisibility(R.id.imageTow, View.GONE);
                    KLog.e(RetrofitUtils.BASE_NEWS_SMALL_IMG_URL + model.getImages().get(0));
                    if ("video".equals(model.getImages().get(0).getType())){
                        viewHolderHelper.setVisibility(R.id.video, View.VISIBLE);
                    }else{
                        viewHolderHelper.setVisibility(R.id.video, View.GONE);
                    }
                    Glide.with(mContext).load(RetrofitUtils.BASE_NEWS_SMALL_IMG_URL + model.getImages().get(0).getImage()).placeholder(R.drawable.image_onload).error(R.drawable.a).into(viewHolderHelper.getImageView(R.id.image0));
                    break;
                case 2:
                    viewHolderHelper.setVisibility(R.id.image0, View.GONE);
                    viewHolderHelper.setVisibility(R.id.imageTow, View.VISIBLE);
                    viewHolderHelper.setVisibility(R.id.imageOne, View.VISIBLE);
                    viewHolderHelper.setVisibility(R.id.image_1, View.INVISIBLE);
                    viewHolderHelper.setVisibility(R.id.image_4, View.INVISIBLE);
                    viewHolderHelper.setVisibility(R.id.image_2, View.VISIBLE);
                    viewHolderHelper.setVisibility(R.id.image_3, View.VISIBLE);
                    Glide.with(mContext).load(RetrofitUtils.BASE_NEWS_SMALL_IMG_URL + model.getImages().get(0).getImage()).placeholder(R.drawable.image_onload).error(R.drawable.a).into(viewHolderHelper.getImageView(R.id.image_2));

                    Glide.with(mContext).load(RetrofitUtils.BASE_NEWS_SMALL_IMG_URL + model.getImages().get(1).getImage()).placeholder(R.drawable.image_onload).error(R.drawable.a).into(viewHolderHelper.getImageView(R.id.image_3));
                    break;
                case 3:
                    viewHolderHelper.setVisibility(R.id.image0, View.GONE);
                    viewHolderHelper.setVisibility(R.id.imageTow, View.VISIBLE);
                    viewHolderHelper.setVisibility(R.id.imageOne, View.VISIBLE);
                    viewHolderHelper.setVisibility(R.id.image_1, View.VISIBLE);
                    viewHolderHelper.setVisibility(R.id.image_4, View.INVISIBLE);
                    viewHolderHelper.setVisibility(R.id.image_2, View.VISIBLE);
                    viewHolderHelper.setVisibility(R.id.image_3, View.VISIBLE);
                    Glide.with(mContext).load(RetrofitUtils.BASE_NEWS_SMALL_IMG_URL + model.getImages().get(0).getImage()).placeholder(R.drawable.image_onload).error(R.drawable.a).into(viewHolderHelper.getImageView(R.id.image_1));

                    Glide.with(mContext).load(RetrofitUtils.BASE_NEWS_SMALL_IMG_URL + model.getImages().get(1).getImage()).placeholder(R.drawable.image_onload).error(R.drawable.a).into(viewHolderHelper.getImageView(R.id.image_2));

                    Glide.with(mContext).load(RetrofitUtils.BASE_NEWS_SMALL_IMG_URL + model.getImages().get(2).getImage()).placeholder(R.drawable.image_onload).error(R.drawable.a).into(viewHolderHelper.getImageView(R.id.image_3));
                    break;
                default:
                    viewHolderHelper.setVisibility(R.id.image0, View.GONE);
                    viewHolderHelper.setVisibility(R.id.imageTow, View.VISIBLE);
                    viewHolderHelper.setVisibility(R.id.imageOne, View.VISIBLE);
                    viewHolderHelper.setVisibility(R.id.image_1, View.VISIBLE);
                    viewHolderHelper.setVisibility(R.id.image_4, View.VISIBLE);
                    viewHolderHelper.setVisibility(R.id.image_2, View.VISIBLE);
                    viewHolderHelper.setVisibility(R.id.image_3, View.VISIBLE);
                    Glide.with(mContext).load(RetrofitUtils.BASE_NEWS_SMALL_IMG_URL + model.getImages().get(0).getImage()).placeholder(R.drawable.image_onload).error(R.drawable.a).into(viewHolderHelper.getImageView(R.id.image_1));

                    Glide.with(mContext).load(RetrofitUtils.BASE_NEWS_SMALL_IMG_URL + model.getImages().get(1).getImage()).placeholder(R.drawable.image_onload).error(R.drawable.a).into(viewHolderHelper.getImageView(R.id.image_2));

                    Glide.with(mContext).load(RetrofitUtils.BASE_NEWS_SMALL_IMG_URL + model.getImages().get(2).getImage()).placeholder(R.drawable.image_onload).error(R.drawable.a).into(viewHolderHelper.getImageView(R.id.image_3));
                    Glide.with(mContext).load(RetrofitUtils.BASE_NEWS_SMALL_IMG_URL + model.getImages().get(3).getImage()).placeholder(R.drawable.image_onload).error(R.drawable.a).into(viewHolderHelper.getImageView(R.id.image_4));
                    break;
            }
        } else {
            viewHolderHelper.setVisibility(R.id.imageGroup, View.GONE);
        }

        if (!StringUtils.isEmpty(model.getLike_count()) && Integer.valueOf(model.getLike_count()) > 0) {
            viewHolderHelper.setText(R.id.favorite_num, model.getLike_count());
            viewHolderHelper.setImageResource(R.id.favorite, R.mipmap.icon_product_collect_checked);
        } else {
            viewHolderHelper.setImageResource(R.id.favorite, R.mipmap.icon_product_collect);
            viewHolderHelper.setText(R.id.favorite_num, "");
        }

        viewHolderHelper.setText(R.id.context, model.getContent());
        newsListItemAfter = model;
    }

    public  NewsListItem getItemObj(int position){
        if (mDatas.size() <= position){
            return newsListItemAfter;
        }
        return mDatas.get(position);
    }

    public boolean isIgnoreChange() {
        return mIsIgnoreChange;
    }

}
