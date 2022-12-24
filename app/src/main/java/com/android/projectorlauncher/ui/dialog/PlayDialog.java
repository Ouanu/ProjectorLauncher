package com.android.projectorlauncher.ui.dialog;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;
import com.android.projectorlauncher.R;
import com.android.projectorlauncher.bean.MusicModel;
import com.android.projectorlauncher.databinding.ActivityPlayBinding;
import com.android.projectorlauncher.databinding.ItemMusicBinding;
import com.android.projectorlauncher.presenter.ResourcePresenter;
import com.android.projectorlauncher.utils.MusicPlayerHelper;
import java.util.Objects;

public class PlayDialog extends DialogFragment implements View.OnClickListener {
    private ActivityPlayBinding playBinding;
    private MusicPlayerHelper helper;
    private int chosenIndex = 0;
    private View chosenView = null;
    private ResourcePresenter presenter;
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, android.R.style.Theme_Light);
        registerBroadcast();
        Bundle bundle = getArguments();
        if (bundle != null) {
            presenter = (ResourcePresenter) bundle.getSerializable("presenter");
            chosenIndex = bundle.getInt("position");
        } else {
            dismiss();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        playBinding = ActivityPlayBinding.inflate(getLayoutInflater());
        MusicModel model = presenter.getMusics().get(chosenIndex);
        playBinding.title.setText(model.getName());
        playBinding.author.setText(model.getAuthor());
        return playBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        helper = new MusicPlayerHelper(requireActivity(), playBinding.seekbar, isPlay);
        helper.setCompletionListener(mp -> {
            if (presenter.getMusics().size() - 1 > chosenIndex) {
                chosenIndex++;
            } else {
                chosenIndex = 0;
            }
            helper.playMusic(presenter.getMusics().get(chosenIndex), false);

        });
        init();
        helper.playMusic(presenter.getMusics().get(chosenIndex), true);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (chosenView != null) {
            chosenView.requestFocus();
        } else {
            if (playBinding.musicRecyclerview.getLayoutManager() == null) return;
            playBinding.musicRecyclerview.getLayoutManager().scrollToPosition(chosenIndex);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        helper.stop();
        helper.destroy();
    }

    @Override
    public void onDestroy() {

        requireActivity().unregisterReceiver(receiver);
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
        requireActivity().registerReceiver(receiver, filter);
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
                chosenIndex--;
            } else {
                chosenIndex = presenter.getMusics().size() - 1;
            }
            helper.playMusic(presenter.getMusics().get(chosenIndex), true);
        } else if (v == playBinding.skipNext) {
            if (presenter.getMusics().size() - 1 > chosenIndex) {
                chosenIndex++;
            } else {
                chosenIndex = 0;
            }
            helper.playMusic(presenter.getMusics().get(chosenIndex), true);
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
            if (chosenIndex == position) {
                chosenView = itemView;
            }
            index = position;
            musicBinding.name.setText(presenter.getMusics().get(position).getName());
            musicBinding.author.setText(presenter.getMusics().get(position).getAuthor());
            musicBinding.duration.setText(presenter.getMusics().get(position).getDuration());

        }

        @Override
        public void onClick(View v) {
            helper.playMusic(presenter.getMusics().get(index), true);
            Log.d("PlayDialog", "onClick: " + presenter.getMusics().get(index).src);
            chosenIndex = index;
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                musicBinding.name.setTextColor(requireActivity().getColor(R.color.background_color));
                musicBinding.author.setTextColor(requireActivity().getColor(R.color.background_color));
                musicBinding.duration.setTextColor(requireActivity().getColor(R.color.background_color));
                ViewCompat.animate(v)
                        .scaleX(1.1f)
                        .scaleY(1.1f)
                        .setDuration(250)
                        .start();
            } else {
                musicBinding.name.setTextColor(requireActivity().getColor(R.color.self_4));
                musicBinding.author.setTextColor(requireActivity().getColor(R.color.self_4));
                musicBinding.duration.setTextColor(requireActivity().getColor(R.color.self_4));
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
            if (presenter == null) {
                return 0;
            }
            return presenter.getMusics().size();
        }
    }
}
