package com.android.projectorlauncher.presenter;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.android.projectorlauncher.bean.Tag;
import com.android.projectorlauncher.bean.VideoCard;
import com.android.projectorlauncher.ui.view.MovieView;
import com.android.projectorlauncher.utils.CacheUtil;
import com.android.projectorlauncher.utils.JsonUtils;
import com.android.projectorlauncher.utils.JumpToApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ComicsPresenter {
    private final List<VideoCard> cards = new ArrayList<>();
    private final Activity activity;
    private final ComicsHandler handler;
    private MovieView view;
    private final HashMap<Integer, String> map;
    public ComicsPresenter(Activity activity) {
        handler = new ComicsHandler(activity.getMainLooper());
        this.activity = activity;
        map = new HashMap<>();
    }

    // 初始化数据
    public void init() {
        JsonUtils.downloadJson(activity, Tag.COMICS, handler);
    }

    // 获得MovieFragment接口
    public void setView(MovieView view) {
        this.view = view;
    }

    // 跳转到指定的视频详情页面
    public void turnVideoDetailPage(int index) {
        if (index >= cards.size()) {
            Toast.makeText(activity, "请连接网络后重试", Toast.LENGTH_SHORT).show();
            return;
        }
        JumpToApplication.playVideo(activity, cards.get(index).getId());
    }

    // 跳转到搜索页面
    public void turnToSearchPage() {
        JumpToApplication.turnToSearch(activity);
    }

    // 跳转到动画分类频道
    public void turnToComicsCategoryPage() {
        JumpToApplication.turnToCategory(activity, "cartoon");
    }

    //跳转到最近观看
    public void turnToRecentWatch() {
        JumpToApplication.turnToHistory(activity);
    }

    // 获取视频封面地址
    public String getImage(int index) {
        if (index >= cards.size()) return "";
        return map.getOrDefault(index, null);
    }

    private class ComicsHandler extends Handler {
        public ComicsHandler(@NonNull Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == JsonUtils.DOWNLOAD_SUCCESS) {
                cards.clear();
                cards.addAll(JsonUtils.readCards(activity, Tag.COMICS, VideoCard.class));
                for (int i = 0; i < 12; i++) {
                    CacheUtil.downloadImage(activity, cards.get(i).getImgSrc(), i, map, handler);
                }
            } else if (msg.what == JsonUtils.NO_NETWORK_CACHE) {
                cards.clear();
                cards.addAll(JsonUtils.readCards(activity, Tag.COMICS, VideoCard.class));
                view.updateAll();
            } else if (msg.what == CacheUtil.IMAGE_PREPARED) {
                view.updateIndex(msg.getData().getInt("INDEX", -1));
            } else {
                Toast.makeText(activity, "请检查网络", Toast.LENGTH_SHORT).show();
                view.updateAll();
            }
        }
    }
}
