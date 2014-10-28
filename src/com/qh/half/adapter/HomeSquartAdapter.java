package com.qh.half.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.qh.half.R;
import com.qh.half.model.LeftPhoto;
import com.qh.half.model.RightPhoto;
import com.qh.half.util.ImageLoadUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2014/10/22.
 */
public class HomeSquartAdapter extends HalfBaseAdapter<LeftPhoto> {

    public HomeSquartAdapter(Context mContext, List<LeftPhoto> mList) {
        super(mContext, mList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LeftPhoto leftComment = getItem(position);
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.home_hot_list_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mLeftName.setText(leftComment.left_photo_user_name);
        holder.mLeftlocation.setText(leftComment.left_photo_user_address);
        ImageLoadUtil.displayImage(leftComment.left_photo_user_head, holder.mLeftAvatar);
        ImageLoadUtil.displayImage(leftComment.left_photo_URL, holder.mLeftPhoto);
        holder.mLikeCount.setText(mContext.getString(R.string.left_photo_notes, leftComment.left_vote_cout));
        holder.mLeftComment.setText(leftComment.left_photo_notes);
        holder.mCommentCount.setText(mContext.getString(R.string.left_photo_comments, leftComment.left_comment_count));
        if (leftComment.right_photo.size() > 0) {
            holder.mRightName.setText(leftComment.right_photo.get(0).right_photo_user_name);
            holder.mRightLocation.setText(leftComment.right_photo.get(0).right_photo_user_address);
            ImageLoadUtil.displayImage(leftComment.right_photo.get(0).right_photo_user_head, holder.mRightAvatar);
            RightPhotoPageAdapter adapter = new RightPhotoPageAdapter(leftComment.right_photo);
            holder.mRightViewpager.setAdapter(adapter);
            PageChangeListen pcl = new PageChangeListen(holder, leftComment.right_photo);
            holder.mRightViewpager.setOnPageChangeListener(pcl);
        }
        holder.mCellVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.mCellChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return convertView;
    }

    class RightPhotoPageAdapter extends PagerAdapter {
        private List<ImageView> viewList = new ArrayList<ImageView>();

        public RightPhotoPageAdapter(List<RightPhoto> photoList) {
            for (RightPhoto r : photoList) {
                ImageView imageView = new ImageView(mContext);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                ImageLoadUtil.displayImage(r.right_photo_URL, imageView);
                viewList.add(imageView);
            }
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(viewList.get(position));
        }


        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(viewList.get(position));
            return viewList.get(position);
        }

        @Override
        public int getCount() {
            return viewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }
    }

    class PageChangeListen implements ViewPager.OnPageChangeListener {
        private ViewHolder holder;
        private List<RightPhoto> rightPhotos;

        public PageChangeListen(ViewHolder h, List<RightPhoto> rlist) {
            holder = h;
            rightPhotos = rlist;
        }

        @Override
        public void onPageScrolled(int i, float v, int i2) {

        }

        @Override
        public void onPageSelected(int i) {
            RightPhoto rightPhoto = rightPhotos.get(i);
            holder.mRightName.setText(rightPhoto.right_photo_user_name);
            holder.mRightLocation.setText(rightPhoto.right_photo_user_address);
            ImageLoadUtil.displayImage(rightPhoto.right_photo_user_head, holder.mRightAvatar);
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    }


    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'home_hot_list_item.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Inmite Developers (http://inmite.github.io)
     */
    static class ViewHolder {
        @InjectView(R.id.leftAvatar)
        ImageView mLeftAvatar;
        @InjectView(R.id.leftName)
        TextView mLeftName;
        @InjectView(R.id.leftlocation)
        TextView mLeftlocation;
        @InjectView(R.id.rightName)
        TextView mRightName;
        @InjectView(R.id.rightLocation)
        TextView mRightLocation;
        @InjectView(R.id.rightAvatar)
        ImageView mRightAvatar;
        @InjectView(R.id.leftPhoto)
        ImageView mLeftPhoto;
        @InjectView(R.id.rightViewpager)
        ViewPager mRightViewpager;
        @InjectView(R.id.leftComment)
        TextView mLeftComment;
        @InjectView(R.id.rightComment)
        TextView mRightComment;
        @InjectView(R.id.likeCount)
        TextView mLikeCount;
        @InjectView(R.id.commentCount)
        TextView mCommentCount;
        @InjectView(R.id.cellVote)
        ImageView mCellVote;
        @InjectView(R.id.cellChat)
        ImageView mCellChat;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
