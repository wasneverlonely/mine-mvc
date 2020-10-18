package com.was.minemvc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.luck.picture.lib.entity.LocalMedia;
import com.was.core.common.image.ImageLoader;
import com.was.minemvc.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 选择图片
 */
public class SelectPictureAdapter extends RecyclerView.Adapter<SelectPictureAdapter.ViewHolder> {

    public final int TYPE_ADD = 1; //图片添加item
    public final int TYPE_PICTURE = 2; //图片item

    public static final int CLICK_ADD = 1; //  添加点击
    public static final int CLICK_DELETE = 2; // 删除点击

    private LayoutInflater mInflater;
    private List<LocalMedia> mData = new ArrayList<>();
    private int mMaxCount = 9;
    /**
     * 点击添加图片跳转
     */
    private OnAddPicClickListener mOnAddPicClickListener;
    protected OnItemClickListener mItemClickListener;

    public int getMaxCount() {
        return mMaxCount;
    }

    public void setMaxCount(int maxCount) {
        this.mMaxCount = maxCount;
    }

    public List<LocalMedia> getData() {
        return mData;
    }

    public void setData(List<LocalMedia> list) {
        this.mData = list;
    }

    public SelectPictureAdapter(Context context, OnAddPicClickListener onAddPicClickListener) {
        mInflater = LayoutInflater.from(context);
        this.mOnAddPicClickListener = onAddPicClickListener;
    }


    /**
     * 清理 页面
     */
    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        int size = mData.size();
        return size < mMaxCount ? ++size : size; // 图片没有选择满 就多返回一个加图片的
    }

    @Override
    public int getItemViewType(int position) {
        return isAddItem(position) ? TYPE_ADD : TYPE_PICTURE;
    }

    /**
     * 创建ViewHolder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.item_picture,
                viewGroup, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        //itemView 的点击事件
        if (mItemClickListener != null) {
            viewHolder.itemView.setOnClickListener(v -> {
                mItemClickListener.onItemClick(0, viewHolder.getAdapterPosition(), v);
            });
        }
        return viewHolder;
    }

    /**
     * 判断 给item 是否是添加图片的item
     *
     * @param position
     * @return
     */
    private boolean isAddItem(int position) {
        return position == mData.size();
    }


    /**
     * 设置值
     */
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

        if (getItemViewType(position) == TYPE_ADD) { //
            viewHolder.mImg.setImageResource(R.drawable.ic_picture_add);
            viewHolder.mImg.setScaleType(ImageView.ScaleType.FIT_XY);

            viewHolder.mImg.setOnClickListener((v) -> {
                mOnAddPicClickListener.onAddPicClick(CLICK_ADD, viewHolder.getAdapterPosition());
            });
            viewHolder.ll_del.setVisibility(View.GONE);
        } else {
            LocalMedia media = mData.get(position);
//            String picturePath = CommUtils.getPicturePath(media);
            String picturePath = null;
            ImageLoader.loadListImager(picturePath, viewHolder.mImg);
            viewHolder.ll_del.setVisibility(View.VISIBLE);

            viewHolder.ll_del.setOnClickListener((v -> {
                mOnAddPicClickListener.onAddPicClick(CLICK_DELETE, viewHolder.getAdapterPosition());
            }));
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mImg;
        LinearLayout ll_del;

        public ViewHolder(View view) {
            super(view);
            mImg = view.findViewById(R.id.iv_content);
            ll_del = view.findViewById(R.id.ll_delete);
        }
    }


    public interface OnItemClickListener {
        void onItemClick(int type, int position, View v);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    public interface OnAddPicClickListener {
        void onAddPicClick(int type, int position);
    }

}
