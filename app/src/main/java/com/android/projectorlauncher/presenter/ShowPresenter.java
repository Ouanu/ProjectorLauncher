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
import com.android.projectorlauncher.utils.JsonUtils;
import com.android.projectorlauncher.utils.JumpToApplication;

import java.util.ArrayList;
import java.util.List;

public class ShowPresenter {
    private final List<VideoCard> cards = new ArrayList<>();
    private final Activity activity;
    private final ShowHandler handler;
    private MovieView view;
    public ShowPresenter(Activity activity) {
        handler = new ShowHandler(activity.getMainLooper());
        this.activity = activity;
    }

    // 初始化数据
    public void init() {
        JsonUtils.downloadJson(activity, Tag.SHOW, handler);
    }

    // 列表大小
    public int sizeOfCards() {
        return cards.size();
    }

    // 获得MovieFragment接口
    public void setView(MovieView view) {
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

    // 跳转到综艺分类频道
    public void turnToShowCategoryPage() {
        JumpToApplication.turnToCategory(activity, "variety");
    }

    //跳转到最近观看
    public void turnToRecentWatch() {
        JumpToApplication.turnToHistory(activity);
    }

    // 获取视频封面地址
    public String getImage(int index) {
        if (index >= cards.size()) return "";
        return cards.get(index).getImgSrc();
    }

    private class ShowHandler extends Handler {
        public ShowHandler(@NonNull Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == JsonUtils.DOWNLOAD_SUCCESS) {
                cards.clear();
                cards.addAll(JsonUtils.readCards(activity, Tag.SHOW, VideoCard.class));
            } else {
                Toast.makeText(activity, "请检查网络", Toast.LENGTH_SHORT).show();
            }
            view.update();
        }
    }
}
