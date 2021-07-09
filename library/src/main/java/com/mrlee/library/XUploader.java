package com.mrlee.library;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.mrlee.mylibrary.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class XUploader extends LinearLayout {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<ImageBean> imageBeans = new ArrayList<ImageBean>();
    private ImgAdapter adapter;

    private boolean canEdit;
    private int maxNum;

    public XUploader(Context context) {
        super(context);
    }

    {
        ImageBean imageBean = new ImageBean(2);
        imageBean.setUrl("https://p9-dy.byteimg.com/large/tos-cn-p-0015/1212b5ed8a8243c3a2982552b35af051_1575196092.jpeg?from=2563711402_large");
        imageBean.setThumbnail("https://p9-dy.byteimg.com/large/tos-cn-p-0015/1212b5ed8a8243c3a2982552b35af051_1575196092.jpeg?from=2563711402_large");
        imageBeans.add(imageBean);

        imageBeans.add(new ImageBean(1));
        imageBeans.removeAll(Collections.singleton(null));
    }

    public XUploader(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;


        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.XUploader);
        canEdit = ta.getBoolean(R.styleable.XUploader_canEdit, false);
        maxNum = ta.getInteger(R.styleable.XUploader_maxSelectNum, 0);

        initView();
        ta.recycle();
    }

    private void initView() {

        setGravity(VERTICAL);
        mInflater = LayoutInflater.from(getContext());

        RecyclerView rcvImg = new RecyclerView(mContext);
        adapter = new ImgAdapter(mContext, imageBeans);
        rcvImg.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        rcvImg.setAdapter(adapter);
        adapter.setCanEdit(canEdit);
        adapter.setNewInstance(imageBeans);
        adapter.setOnItemClickListener(new ImgAdapter.OnItemClickListener() {

            @Override
            public void onDelClick(int position) {
                new XPopup.Builder(mContext).asConfirm("", "确定删除？", new OnConfirmListener() {
                    @Override
                    public void onConfirm() {
                        imageBeans.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                }).show();
            }

            @Override
            public void onAddClick() {
                PictureSelector.create((Activity) mContext)
                        .openGallery(PictureMimeType.ofImage())
                        .selectionMode(PictureConfig.MULTIPLE)
                        .imageEngine(GlideEngine.createGlideEngine())
                        .theme(R.style.picture_WeChat_style)
                        .isWeChatStyle(true)
                        .isCompress(true)
                        .maxSelectNum(maxNum)
                        .forResult(new OnResultCallbackListener<LocalMedia>() {
                            @RequiresApi(api = Build.VERSION_CODES.N)
                            @Override
                            public void onResult(List<LocalMedia> result) {
                                List<ImageBean> res = new ArrayList<>();
                                for (int i = 0; i < result.size(); i++) {
                                    ImageBean imageBean = new ImageBean(2);
                                    imageBean.setUrl(result.get(i).getRealPath());
                                    imageBean.setThumbnail(result.get(i).getCompressPath());
                                    res.add(imageBean);
                                }
                                imageBeans.addAll(res);
                                adapter.notifyDataSetChanged();
                                sortList(imageBeans);
                                adapter.setNewInstance(imageBeans);
                            }

                            @Override
                            public void onCancel() {

                            }
                        });
            }
        });
        addView(rcvImg);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void sortList(List<ImageBean> images) {
        images.sort((o1, o2) -> o2.getItemType() - o1.getItemType());
    }


    public List<ResultData> getImageData() {
        List<ResultData> resultData = new ArrayList<>();
        for (int i = 0; i < imageBeans.size() - 1; i++) {
            ResultData resultData1 = new ResultData();
            resultData1.setUrl(imageBeans.get(i).getUrl());
            resultData1.setThumbnail(imageBeans.get(i).getThumbnail());
            resultData.add(resultData1);
        }
        return resultData;
    }
}
