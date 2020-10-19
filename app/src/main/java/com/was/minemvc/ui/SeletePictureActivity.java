package com.was.minemvc.ui;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.decoration.GridSpacingItemDecoration;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.luck.picture.lib.tools.ScreenUtils;
import com.was.minemvc.R;
import com.was.minemvc.adapter.GridImageAdapter;
import com.was.minemvc.common.base.BaseActivity;
import com.was.minemvc.helper.PictureSeleteHelper;
import com.was.minemvc.widget.FullyGridLayoutManager;

import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SeletePictureActivity extends BaseActivity {

    private static final int REQUEST_PICTURE = 0x006;

    @BindView(R.id.rv_picture)
    RecyclerView rvPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selete_picture);
        ButterKnife.bind(this);

        setBack();
        setTitleText("选择图片");
        initView();
    }

    private GridImageAdapter mAdapter;

    private void initView() {

        FullyGridLayoutManager manager = new FullyGridLayoutManager(this,
                4, GridLayoutManager.VERTICAL, false);
        rvPicture.setLayoutManager(manager);

        rvPicture.addItemDecoration(new GridSpacingItemDecoration(4,
                ScreenUtils.dip2px(this, 8), false));

        mAdapter = new GridImageAdapter(this, onAddPicClickListener);
        mAdapter.setSelectMax(9);
        rvPicture.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener((v, position) -> {
            List<LocalMedia> selectList = mAdapter.getData();
            if (selectList.size() > 0) {
                LocalMedia media = selectList.get(position);
                String mimeType = media.getMimeType();
                int mediaType = PictureMimeType.getMimeType(mimeType);
                switch (mediaType) {
                    case PictureConfig.TYPE_VIDEO:
                        PictureSeleteHelper.previewVideo(SeletePictureActivity.this, media);
                        break;
//                    case PictureConfig.TYPE_AUDIO:
//                        // 预览音频
//                        PictureSelector.create(SeletePictureActivity.this)
//                                .externalPictureAudio(PictureMimeType.isContent(media.getPath()) ? media.getAndroidQToPath() : media.getPath());
//                        break;
                    default:
                        PictureSeleteHelper.previewPictures(SeletePictureActivity.this, selectList, position);
                        break;
                }
            }
        });

    }


    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
//            PictureSeleteHelper.selectSinglePicture(SeletePictureActivity.this, new MyResultCallback(mAdapter));
            PictureSeleteHelper.selectMultiplePicture(SeletePictureActivity.this, null, 4, new MyResultCallback(mAdapter));
//            PictureSeleteHelper.selectMultipleVideo(SeletePictureActivity.this, null, 4, new MyResultCallback(mAdapter));
        }
    };

    private static class MyResultCallback implements OnResultCallbackListener<LocalMedia> {
        private WeakReference<GridImageAdapter> mAdapterWeakReference;

        public MyResultCallback(GridImageAdapter adapter) {
            super();
            this.mAdapterWeakReference = new WeakReference<>(adapter);
        }

        @Override
        public void onResult(List<LocalMedia> result) {
            for (LocalMedia media : result) {
                Log.i(TAG, "是否压缩:" + media.isCompressed());
                Log.i(TAG, "压缩:" + media.getCompressPath());
                Log.i(TAG, "原图:" + media.getPath());
                Log.i(TAG, "是否裁剪:" + media.isCut());
                Log.i(TAG, "裁剪:" + media.getCutPath());
                Log.i(TAG, "是否开启原图:" + media.isOriginal());
                Log.i(TAG, "原图路径:" + media.getOriginalPath());
                Log.i(TAG, "Android Q 特有Path:" + media.getAndroidQToPath());
                Log.i(TAG, "宽高: " + media.getWidth() + "x" + media.getHeight());
                Log.i(TAG, "Size: " + media.getSize());
                // TODO 可以通过PictureSelectorExternalUtils.getExifInterface();方法获取一些额外的资源信息，如旋转角度、经纬度等信息
            }
            if (mAdapterWeakReference.get() != null) {
                mAdapterWeakReference.get().setList(result);
                mAdapterWeakReference.get().notifyDataSetChanged();
            }
        }

        @Override
        public void onCancel() {
            Log.i(TAG, "PictureSelector Cancel");
        }
    }

    public static final String TAG = "PictureSelector";
}