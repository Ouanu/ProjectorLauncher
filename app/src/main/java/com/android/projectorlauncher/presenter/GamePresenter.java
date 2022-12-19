package com.android.projectorlauncher.presenter;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import com.android.projectorlauncher.MainActivity;
import com.android.projectorlauncher.bean.GameCard;
import com.android.projectorlauncher.bean.Tag;
import com.android.projectorlauncher.ui.view.GameView;
import com.android.projectorlauncher.utils.JsonUtils;
import com.android.projectorlauncher.utils.JumpToApplication;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class GamePresenter {
    private final Activity activity;
    private final ComicsHandler handler;
    private GameView view;
    private final HashMap<Integer, ArrayList<GameCard>> map = new HashMap<>();
    private final MutableLiveData<List<GameCard>> mutableCards = new MutableLiveData<>(new ArrayList<>());
    public GamePresenter(MainActivity activity) {
        handler = new ComicsHandler(activity.getMainLooper());
        this.activity = activity;
        mutableCards.observe(activity, this::onChanged);
    }

    // 初始化数据
    public void init() {
        JsonUtils.downloadJson(activity, Tag.GAME, handler);
    }

    public void setView(GameView view) {
        this.view = view;
    }

    // 指定类型的游戏数量
    public int sizeOfCards(int tag) {
        return map.get(tag) == null ? 0 : Objects.requireNonNull(map.get(tag)).size();
    }

    //切换游戏类型
    public void setGameTag(int tag) {
        mutableCards.setValue(map.get(tag));
    }

    // 返回游戏
    public GameCard getCard(int index) {
        if (mutableCards.getValue() == null) {
            return null;
        }
        return mutableCards.getValue().get(index);
    }

    // 跳转到指定的视频详情页面
    public void turnToGame(GameCard card) {
        JumpToApplication.turnToGame(activity, card);
    }

    private void onChanged(List<GameCard> gameCards) {
        if (view == null) {
            return;
        }
        view.update();
    }

    private class ComicsHandler extends Handler {
        public ComicsHandler(@NonNull Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == JsonUtils.DOWNLOAD_SUCCESS) {
                List<GameCard> cards = new ArrayList<>(JsonUtils.readCards(activity, Tag.GAME, GameCard.class));
                cards.forEach(card -> {
                    map.computeIfAbsent(card.getTag(), k -> new ArrayList<>());
                    Objects.requireNonNull(map.get(card.getTag())).add(card);
                });
                mutableCards.setValue(map.get(0));
            } else {
                Toast.makeText(activity, "请检查网络", Toast.LENGTH_SHORT).show();
            }
        }
    }



}
