package com.android.projectorlauncher.presenter;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.android.projectorlauncher.bean.MatchCard;
import com.android.projectorlauncher.bean.Tag;
import com.android.projectorlauncher.ui.view.MatchView;
import com.android.projectorlauncher.utils.JsonUtils;
import com.android.projectorlauncher.utils.JumpToApplication;

import java.util.ArrayList;
import java.util.List;

public class MatchPresenter {
    private final List<MatchCard> cards = new ArrayList<>();
    private final Activity activity;
    private final MatchHandler handler;
    private MatchView view;
    public MatchPresenter(Activity activity) {
        handler = new MatchHandler(activity.getMainLooper());
        this.activity = activity;
    }

    // 初始化数据
    public void init() {
        JsonUtils.downloadJson(activity, Tag.MATCH, handler);
    }

    // 获得MovieFragment接口
    public void setView(MatchView view) {
        this.view = view;
    }

    // 跳转到指定的体育比赛界面
    public void turnToChannel(int index) {
        if (index >= cards.size()) return;
        JumpToApplication.turnToChannel(activity, cards.get(index).getId());
    }

    // 获取列表数据大小
    public int sizeOfCards() {
        return cards.size();
    }

    private class MatchHandler extends Handler {
        public MatchHandler(@NonNull Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == JsonUtils.DOWNLOAD_SUCCESS) {
                cards.clear();
                cards.addAll(JsonUtils.readCards(activity, Tag.MATCH, MatchCard.class));
            } else {
                Toast.makeText(activity, "请检查网络", Toast.LENGTH_SHORT).show();
            }
            view.update(cards);
        }
    }
}
