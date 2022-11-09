package com.android.projectorlauncher.presenter;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.android.projectorlauncher.bean.Tag;
import com.android.projectorlauncher.bean.VideoCard;
import com.android.projectorlauncher.ui.view.ShowView;
import com.android.projectorlauncher.utils.JsonUtils;
import com.android.projectorlauncher.utils.JumpToApplication;

import java.util.ArrayList;
import java.util.List;

public class ShowPresenter {
    private final List<VideoCard> cards = new ArrayList<>();
    private final Activity activity;
    private final ShowHandler handler;
    private ShowView view;
    private final List<VideoCard> recommends = new ArrayList<>();
    public ShowPresenter(Activity activity) {
        handler = new ShowHandler(activity.getMainLooper());
        this.activity = activity;
        init();
    }

    // 初始化数据
    private void init() {
        JsonUtils.downloadJson(activity, Tag.SHOW, handler);
    }

    // 获得MovieFragment接口
    public void setView(ShowView view) {
        this.view = view;
    }

    // 跳转到指定的视频详情页面
    public void turnVideoDetailPage(int index) {
        if (index >= cards.size()) return;
        JumpToApplication.playVideo(activity, cards.get(index).getId());
    }

    // 跳转到搜索页面
    public void turnToSearchPage() {
        JumpToApplication.turnToSearch(activity);
    }

    // 跳转到电视剧分类频道
    public void turnToVarietyCategoryPage() {
        JumpToApplication.turnToCategory(activity, "variety");
    }

    // 获取视频封面地址
    public String getImage(int index) {
        if (index >= cards.size()) return "";
        return cards.get(index).getImgSrc();
    }

    // 获取推荐列表图片
    public String getRecommendsImage(int index) {
        return getImage(index + 4);
    }

    // 推荐列表跳转到指定的视频详情页面
    public void fromRecommendsToVideoDetailPage(int index) {
        turnVideoDetailPage(index + 4);
    }


    private class ShowHandler extends Handler {
        public ShowHandler(@NonNull Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == JsonUtils.DOWNLOAD_SUCCESS) {
                cards.addAll(JsonUtils.readVideoCards(activity, Tag.SHOW));
                recommends.addAll(cards.subList(4, cards.size()));
            } else {
                Toast.makeText(activity, "请检查网络", Toast.LENGTH_SHORT).show();
//                recommends.clear();
            }
            view.update(cards.get(0), cards.get(1), cards.get(2), cards.get(3), recommends);
        }
    }
}
