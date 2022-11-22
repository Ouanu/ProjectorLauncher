package com.android.projectorlauncher.ui.activity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
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

public class PlayActivity extends FragmentActivity implements View.OnClickListener {
    private ActivityPlayBinding playBinding;
    private MusicPlayerHelper helper;
    private final List<MusicModel> musicList = new ArrayList<>();
    private int chosenIndex = 0;
    private final MutableLiveData<Boolean> isPlay = new MutableLiveData<>(false);
    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action != null) {
                switch (action) {
                    case Intent.ACTION_MEDIA_MOUNTED:
                        String mntPath = Objects.requireNonNull(intent.getData()).getPath();
                        if (!TextUtils.isEmpty(mntPath)) {
                            Toast.makeText(context, getString(R.string.mount) + mntPath, Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case Intent.ACTION_MEDIA_REMOVED:
                        Toast.makeText(context, getString(R.string.unmount), Toast.LENGTH_SHORT).show();
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

        registerBroadcast();
        helper = new MusicPlayerHelper(this, playBinding.seekbar, isPlay);
        helper.setCompletionListener(mp -> {
            if (musicList.size() - 1 > chosenIndex) {
                chosenIndex ++;
            } else {
                chosenIndex = 0;
            }
            helper.playMusic(musicList.get(chosenIndex), true);

        });
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        HashSet<String> usbDisk = FileUtils.getUsbDisk();
        musicList.clear();
        for (String s : usbDisk) {
            musicList.addAll(ScanMusicUtils.getMusicList(s));
        }
    }

    @Override
    protected void onDestroy() {
        helper.stop();
        helper.destroy();
        unregisterReceiver(receiver);
        super.onDestroy();
    }

    private void init() {
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

        playBinding.play.setOnClickListener(this);
        playBinding.fastRewind.setOnClickListener(this);
        playBinding.fastForward.setOnClickListener(this);
        playBinding.skipPrevious.setOnClickListener(this);
        playBinding.skipNext.setOnClickListener(this);
        isPlay.observe(this, aBoolean -> updatePlayState());

    }

    private void updatePlayState() {
        if (Boolean.TRUE.equals(isPlay.getValue())) {
            playBinding.play.setImageResource(R.drawable.pause);
        } else {
            playBinding.play.setImageResource(R.drawable.play_icon);
        }
    }

    private void registerBroadcast() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_MEDIA_MOUNTED);
        filter.addAction(Intent.ACTION_MEDIA_REMOVED);
        filter.addDataScheme("file");
        registerReceiver(receiver, filter);
    }

    @Override
    public void onClick(View v) {
        if (v == playBinding.play) {
            helper.autoPlay();
        } else if (v == playBinding.fastForward) {
            helper.fastForward();
        } else if (v == playBinding.fastRewind) {
            helper.fastRewind();
        } else if (v == playBinding.skipPrevious) {
            if (chosenIndex - 1 > 0) {
                chosenIndex --;
            } else {
                chosenIndex = musicList.size() - 1;
            }
            helper.playMusic(musicList.get(chosenIndex), true);
        } else if (v == playBinding.skipNext) {
            if (musicList.size() - 1 > chosenIndex) {
                chosenIndex ++;
            } else {
                chosenIndex = 0;
            }
            helper.playMusic(musicList.get(chosenIndex), true);
        }
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
            String builder = (position + 1) + musicList.get(position).getName();
            musicBinding.name.setText(builder);
            musicBinding.author.setText(musicList.get(position).getAuthor());
            musicBinding.duration.setText(musicList.get(position).getDuration());

        }

        @Override
        public void onClick(View v) {
            helper.playMusic(musicList.get(index), true);
            chosenIndex = index;
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
