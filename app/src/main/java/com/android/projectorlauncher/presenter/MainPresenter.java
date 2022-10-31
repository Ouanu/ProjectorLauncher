package com.android.projectorlauncher.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.android.projectorlauncher.bean.VideoCard;
import com.android.projectorlauncher.ui.view.PagerFragment;
import com.android.projectorlauncher.utils.JsonUtils;

import java.util.List;

public class MainPresenter {
    private final Context context;
    private static final int SUCCESS = 100;
    private static final int FAILURE = 101;
    private final MainHandler handler;
    private final List<PagerFragment> fragments;
    public MainPresenter(Context context, List<PagerFragment> fragments) {
        this.context = context;
        handler = new MainHandler(context.getMainLooper());
        this.fragments = fragments;
        init();
    }

    private void init() {
        JsonUtils.writeVideoCards(context, handler);
    }

    class MainHandler extends Handler {
        public MainHandler(@NonNull Looper looper) {
            super(looper);
        }
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case SUCCESS:
                    Toast.makeText(context, "写入成功", Toast.LENGTH_SHORT).show();
                    List<VideoCard> cards = JsonUtils.readVideoCards(context, "Movie.json");
                    fragments.get(0).setCards(cards);
                    break;
                case FAILURE:
                    Toast.makeText(context, "写入失败", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    }


}
