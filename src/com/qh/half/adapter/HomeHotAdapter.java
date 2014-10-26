package com.qh.half.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.qh.half.R;
import com.qh.half.model.LeftComment;
import com.qh.half.model.LeftPhoto;
import com.qh.half.model.RightPhoto;
import com.qh.half.ui.BaseActivity;
import com.qh.half.ui.fragment.BaseFragmentAdaper;
import com.qh.half.ui.fragment.SigleImageFragment;
import com.qh.half.util.ImageLoadUtil;

import java.util.List;

/**
 * Created by Administrator on 2014/10/22.
 */
public class HomeHotAdapter extends HalfBaseAdapter<LeftPhoto> {
    private FragmentManager fm ;
    public HomeHotAdapter(Context mContext, List<LeftPhoto> mList,FragmentManager fm) {
        super(mContext, mList);
        this.fm = fm     ;
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
        holder.mLikeCount.setText(mContext.getString(R.string.left_photo_notes, leftComment.left_photo_notes));
        holder.mCommentCount.setText(mContext.getString(R.string.left_photo_comments, leftComment.left_comment_count));
        RightPhotoPageAdapter adapter = new RightPhotoPageAdapter(fm,leftComment.right_photo) ;
        holder.mRightViewpager.setAdapter(adapter);
        return convertView;
    }

 class  RightPhotoPageAdapter extends BaseFragmentAdaper {
     private List<RightPhoto> rightPhotos  ;
     public RightPhotoPageAdapter(FragmentManager fm,List<RightPhoto> photoList) {
         super(fm);
         this.rightPhotos = photoList ;
     }

     @Override
     public Fragment getFragment(int i) {
        SigleImageFragment fragment = new SigleImageFragment();
         Bundle b = new Bundle() ;
         b.putString(SigleImageFragment.URL,rightPhotos.get(i).right_photo_URL);
         fragment.setArguments(b);
         return  fragment ;
     }

     @Override
     public int getCount() {
         return rightPhotos.size();
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
        @InjectView(R.id.rightAvatar)
        ImageView mRightAvatar;
        @InjectView(R.id.rightName)
        TextView mRightName;
        @InjectView(R.id.rightLocation)
        TextView mRightLocation;
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

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
