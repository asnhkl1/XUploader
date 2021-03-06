package com.mrlee.library;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cbman.roundimageview.RoundImageView;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.XPopupImageLoader;
import com.mrlee.mylibrary.R;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImgAdapter extends BaseMultiItemQuickAdapter<ImageBean, BaseViewHolder> {
    private Context mContext;
    private int width;
    private List<ImageBean> imgLists;
    private OnItemClickListener onItemClickListener;

    private boolean canEdit = false;

    public ImgAdapter(Context context, List<ImageBean> imgLists) {
        super(imgLists);
        this.mContext = context;
        this.imgLists = imgLists;
        width = (ScreenUtil.getScreenWidth(mContext)) / 3 - ScreenUtil.dip2px(mContext, 14);
        addItemType(ImageBean.IMAGE, R.layout.item_img_layout);
        addItemType(ImageBean.ADD,R.layout.item_img_add_layout);
    }
    @Override
    protected void convert(@NotNull BaseViewHolder helper, ImageBean item) {
        int itemViewType = helper.getItemViewType();

        helper.itemView.setLayoutParams(new RecyclerView.LayoutParams(width, width));
        switch (itemViewType){
            case ImageBean.ADD:
                if(canEdit){
                    helper.setVisible(R.id.add,true);
                    helper.getView(R.id.add).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onItemClickListener.onAddClick();
                        }
                    });
                }else{
                    helper.setVisible(R.id.add,false);
                }

                break;
            case ImageBean.IMAGE:
                RoundImageView img = helper.getView(R.id.img);
                ImageView del = helper.getView(R.id.bt_del);
                if(canEdit){
                    del.setVisibility(View.VISIBLE);
                }else{
                    del.setVisibility(View.GONE);
                }
                if (item.getThumbnail().startsWith("http")){
                    Glide.with(img).load(item.getThumbnail()).into(img);
                }else{
                    Glide.with(img).load(new File(item.getThumbnail())).into(img);
                }

                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        List<Object> imgs = new ArrayList<>();
                        for (int i = 0; i < imgLists.size()-1; i++) {
                            imgs.add(imgLists.get(i).getThumbnail());
                        }
                        new XPopup.Builder(ImgAdapter.this.getContext())
                                .asImageViewer(img, helper.getLayoutPosition(), imgs,
                                        (popupView, position) -> {
                                            RecyclerView rv = (RecyclerView) helper.itemView.getParent();
                                            View Liner = (RelativeLayout)rv.getChildAt(position);
                                            popupView.updateSrcView(Liner.findViewById(R.id.img));
                                        }, new ImageLoader())
                                .show();
                    }
                });
                del.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onDelClick(helper.getLayoutPosition());
                    }
                });
                break;
            default:
                break;
        }

    }

    // ??????????????????XPopup?????????????????????????????????????????????????????????????????????????????????Glide???????????????????????????????????????????????????????????????????????????????????????????????????XPopup???????????????????????????
    class ImageLoader implements XPopupImageLoader {

        @Override
        public void loadImage(int position, @NonNull Object uri, @NonNull ImageView imageView) {
            Glide.with(imageView).load(uri).into(imageView);
        }

        //?????????????????????????????????uri??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????null???
        @Override
        public File getImageFile(@NonNull Context context, @NonNull Object uri) {
            try {
                return Glide.with(context).downloadOnly().load(uri).submit().get();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
    public interface OnItemClickListener {

        void onDelClick(int position);

        void onAddClick();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setCanEdit(boolean hide) {
        canEdit = hide;
        notifyDataSetChanged();
    }
}
