package com.android.projectorlauncher.presenter;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.android.projectorlauncher.bean.Tag;
import com.android.projectorlauncher.bean.VideoCard;
import com.android.projectorlauncher.ui.view.ComicsView;
import com.android.projectorlauncher.utils.JsonUtils;
import com.android.projectorlauncher.utils.JumpToApplication;

import java.util.ArrayList;
import java.util.List;

public class ComicsPresenter {
    private final List<VideoCard> cards = new ArrayList<>();
    private final Activity activity;
    private final ComicHandler handler;
    private ComicsView view;
    private final List<VideoCard> recommends = new ArrayList<>();
    public ComicsPresenter(Activity activity) {
        handler = new ComicHandler(activity.getMainLooper());
        this.activity = activity;
    }

    // 初始化数据
    public void init() {
        JsonUtils.downloadJson(activity, Tag.COMICS, handler);
    }

    public int sizeOfCards() {
        return cards.size();
    }

    // 获得MovieFragment接口
    public void setView(ComicsView view) {
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
    public void turnToTvCategoryPage() {
        JumpToApplication.turnToCategory(activity, "cartoon");
    }

    // 获取视频封面地址
    public String getImage(int index) {
        if (index >= cards.size()) return "";
        return cards.get(index).getImgSrc();
    }

    // 获取推荐列表图片
    public String getRecommendsImage(int index) {
        return getImage(index + 3);
    }

    // 推荐列表跳转到指定的视频详情页面
    public void fromRecommendsToVideoDetailPage(int index) {
        turnVideoDetailPage(index + 3);
    }


    private class ComicHandler extends Handler {
        public ComicHandler(@NonNull Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == JsonUtils.DOWNLOAD_SUCCESS) {
                cards.clear();
                recommends.clear();
                cards.addAll(JsonUtils.readCards(activity, Tag.COMICS, VideoCard.class));
                recommends.addAll(cards.subList(3, cards.size()));
            } else {
                Toast.makeText(activity, "请检查网络", Toast.LENGTH_SHORT).show();
//                recommends.clear();
            }
            if (cards.size() > 3) {
                view.update(cards.get(0), cards.get(1), cards.get(2), recommends);
            }

        }
    }
}