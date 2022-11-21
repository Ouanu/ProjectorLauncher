package com.android.projectorlauncher.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.projectorlauncher.R;
import com.android.projectorlauncher.bean.MusicModel;
import com.android.projectorlauncher.databinding.ActivityPlayBinding;
import com.android.projectorlauncher.databinding.ItemMusicBinding;
import com.android.projectorlauncher.utils.FileUtils;
import com.android.projectorlauncher.utils.MusicPlayerHelper;
import com.android.projectorlauncher.utils.ScanMusicUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class PlayActivity extends Activity {
    private ActivityPlayBinding playBinding;
    private MusicPlayerHelper helper;
    private List<MusicModel> musicList = new ArrayList<>();
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action != null) {
                switch (action) {
                    case Intent.ACTION_MEDIA_MOUNTED:
                        String mntPath = Objects.requireNonNull(intent.getData()).getPath();
                        if (!TextUtils.isEmpty(mntPath)) {
                            Toast.makeText(context, "U盘已挂载:" + mntPath, Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case Intent.ACTION_MEDIA_REMOVED:
                        Toast.makeText(context, "U盘取消挂载", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        playBinding = ActivityPlayBinding.inflate(getLayoutInflater());
        setContentView(playBinding.getRoot());

        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_MEDIA_MOUNTED);
        filter.addAction(Intent.ACTION_MEDIA_REMOVED);
        filter.addDataScheme("file");
        registerReceiver(receiver, filter);

        ScanMusicUtils.getMusicData(this);
        helper = new MusicPlayerHelper(this, playBinding.seekbar);
//        model.setResSrc("/storage/sdb1/南征北战NZBZ - 我的天空 (剧情版).mp3");
//        helper.playMusic(model, true);
        HashSet<String> usbDisk = FileUtils.getUsbDisk();
        for (String s : usbDisk) {
            musicList.addAll(ScanMusicUtils.getMusicList(s));
        }


        playBinding.musicRecyclerview.setAdapter(new ItemPlayAdapter());
        playBinding.musicRecyclerview.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.top = 5;
                outRect.bottom = 5;
                outRect.left = 10;
                outRect.right = 10;
            }
        });

    }



    class ItemPlayViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnFocusChangeListener {
        ItemMusicBinding musicBinding;
        int index = 0;
        public ItemPlayViewHolder(@NonNull View itemView) {
            super(itemView);
            musicBinding = ItemMusicBinding.bind(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnFocusChangeListener(this);
        }

        @SuppressLint("SetTextI18n")
        public void bind(int position) {
            index = position;
            StringBuilder builder = new StringBuilder();
            builder.append(position + 1);
            builder.append(musicList.get(position).getName());
            musicBinding.name.setText(builder.toString());
            musicBinding.author.setText(musicList.get(position).getAuthor());
            musicBinding.duration.setText(musicList.get(position).getDuration());

        }

        @Override
        public void onClick(View v) {
            helper.playMusic(musicList.get(index), true);
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                musicBinding.name.setTextColor(getColor(R.color.background_color));
                musicBinding.author.setTextColor(getColor(R.color.background_color));
                musicBinding.duration.setTextColor(getColor(R.color.background_color));
                ViewCompat.animate(v)
                        .scaleX(1.1f)
                        .scaleY(1.1f)
                        .setDuration(250)
                        .start();
            } else {
                musicBinding.name.setTextColor(getColor(R.color.self_4));
                musicBinding.author.setTextColor(getColor(R.color.self_4));
                musicBinding.duration.setTextColor(getColor(R.color.self_4));
                ViewCompat.animate(v)
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(250)
                        .start();
            }
        }
    }

    class ItemPlayAdapter extends RecyclerView.Adapter<ItemPlayViewHolder> {

        @NonNull
        @Override
        public ItemPlayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ItemMusicBinding musicBinding = ItemMusicBinding.inflate(getLayoutInflater(), parent, false);
            return new ItemPlayViewHolder(musicBinding.getRoot());
        }

        @Override
        public void onBindViewHolder(@NonNull ItemPlayViewHolder holder, int position) {
            holder.bind(position);
        }

        @Override
        public int getItemCount() {
            return musicList.size();
        }
    }
}
