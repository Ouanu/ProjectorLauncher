package com.android.projectorlauncher.presenter;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.android.projectorlauncher.bean.GameCard;
import com.android.projectorlauncher.bean.Tag;
import com.android.projectorlauncher.ui.view.GameView;
import com.android.projectorlauncher.utils.JsonUtils;
import com.android.projectorlauncher.utils.JumpToApplication;

import java.util.ArrayList;
import java.util.List;

public class GamePresenter {
    private final List<GameCard> cards = new ArrayList<>();
    private final Activity activity;
    private final ComicsHandler handler;
    private GameView view;
    public GamePresenter(Activity activity) {
        handler = new ComicsHandler(activity.getMainLooper());
        this.activity = activity;
    }

    // 初始化数据
    public void init() {
        JsonUtils.downloadJson(activity, Tag.COMICS, handler);
    }

    // 列表大小
    public int sizeOfCards() {
        return cards.size();
    }

    // 获得MovieFragment接口
    public void setView(GameView view) {
        this.view = view;
    }

    // 跳转到指定的视频详情页面
    public void turnToGame(int index) {
        if (index >= cards.size()) return;
        JumpToApplication.turnToGame(activity, cards.get(index));
    }

    public List<GameCard> getCards() {
        return cards;
    }


    private class ComicsHandler extends Handler {
        public ComicsHandler(@NonNull Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == JsonUtils.DOWNLOAD_SUCCESS) {
                cards.clear();
                cards.addAll(JsonUtils.readCards(activity, Tag.GAME, GameCard.class));
            } else {
                Toast.makeText(activity, "请检查网络", Toast.LENGTH_SHORT).show();
            }
//            view.update();
        }
    }
}
