package com.android.projectorlauncher.ui.activity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.projectorlauncher.R;
import com.android.projectorlauncher.bean.Model;
import com.android.projectorlauncher.bean.MusicModel;
import com.android.projectorlauncher.databinding.ActivityResourceBinding;
import com.android.projectorlauncher.databinding.ItemCardCategoryBinding;
import com.android.projectorlauncher.databinding.ItemCategoryMusicBinding;
import com.android.projectorlauncher.presenter.ResourcePresenter;
import com.android.projectorlauncher.ui.dialog.ImageDialog;
import com.android.projectorlauncher.ui.dialog.PlayDialog;
import com.android.projectorlauncher.ui.view.ResourceView;
import com.bumptech.glide.Glide;
import java.util.List;
import java.util.Objects;

public class ResourceActivity extends FragmentActivity implements ResourceView {
    public static final int TYPE_ERROR = -1;
    public static final int TYPE_VIDEO = 0;
    public static final int TYPE_AUDIO = 1;
    public static final int TYPE_IMAGE = 2;
    private ActivityResourceBinding resourceBinding;
    private int type = 1;
    private ResourcePresenter presenter;
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
                    case Intent.ACTION_MEDIA_UNMOUNTED:
                        Toast.makeText(context, getString(R.string.unmount), Toast.LENGTH_SHORT).show();
                        break;
                }
                refreshData();
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resourceBinding = ActivityResourceBinding.inflate(getLayoutInflater());
        setContentView(resourceBinding.getRoot());
        presenter = new ResourcePresenter(this);
        if (getIntent() != null) {
            type = getIntent().getIntExtra("TYPE", 1);
        }
        resourceBinding.mediaRecyclerView.setLayoutManager(new GridLayoutManager(this, 5));
        resourceBinding.mediaRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.top = 20;
                outRect.left = 15;
                outRect.right = 15;
                outRect.bottom = 20;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_MEDIA_MOUNTED);
        filter.addAction(Intent.ACTION_MEDIA_REMOVED);
        filter.addAction(Intent.ACTION_MEDIA_UNMOUNTED);
        filter.addDataScheme("file");
        registerReceiver(receiver, filter);

        refreshData();
    }

    private void refreshData() {
        if (type == TYPE_ERROR) {
            resourceBinding.error.setVisibility(View.VISIBLE);
            resourceBinding.mediaRecyclerView.setVisibility(View.GONE);
        } else if (type == TYPE_VIDEO) {
            resourceBinding.category.setText(R.string.video);
            resourceBinding.mediaRecyclerView.setVisibility(View.VISIBLE);
            resourceBinding.error.setVisibility(View.GONE);
            presenter.loadVideos();
            resourceBinding.mediaRecyclerView.setAdapter(new CardAdapter(presenter.getVideos()));
        } else if (type == TYPE_AUDIO) {
            resourceBinding.category.setText(R.string.audio);
            resourceBinding.mediaRecyclerView.setVisibility(View.VISIBLE);
            resourceBinding.error.setVisibility(View.GONE);
            presenter.loadAudios();
            resourceBinding.mediaRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            resourceBinding.mediaRecyclerView.setAdapter(new LinearAdapter(presenter.getMusics()));

        } else if (type == TYPE_IMAGE) {
            resourceBinding.error.setVisibility(View.GONE);
            resourceBinding.category.setText(R.string.picture);
            resourceBinding.mediaRecyclerView.setVisibility(View.VISIBLE);
            presenter.loadImages();
            resourceBinding.mediaRecyclerView.setAdapter(new CardAdapter(presenter.getImages()));
        }
    }

    @Override
    public void updateUI() {

    }

    static class LinearViewHolder extends RecyclerView.ViewHolder {
        ItemCategoryMusicBinding musicBinding;

        public LinearViewHolder(@NonNull View itemView) {
            super(itemView);
            musicBinding = ItemCategoryMusicBinding.bind(itemView);
        }

        public void bind(MusicModel model, View.OnClickListener listener) {
            musicBinding.name.setText(model.getName());
            musicBinding.author.setText(model.getAuthor());
            musicBinding.duration.setText(model.getDuration());
            itemView.setOnClickListener(listener);
        }
    }

    class LinearAdapter extends RecyclerView.Adapter<LinearViewHolder> {

        private final List<MusicModel> musicModels;

        public LinearAdapter(List<MusicModel> musicModels) {
            this.musicModels = musicModels;
        }

        @NonNull
        @Override
        public LinearViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ItemCategoryMusicBinding musicBinding = ItemCategoryMusicBinding.inflate(getLayoutInflater(), parent, false);
            return new LinearViewHolder(musicBinding.getRoot());
        }

        @Override
        public void onBindViewHolder(@NonNull LinearViewHolder holder, int position) {
            holder.bind(musicModels.get(position), v -> {
                PlayDialog dialog = new PlayDialog();
                Bundle bundle = new Bundle();
                bundle.putInt("position", position);
                bundle.putSerializable("presenter", presenter);
                dialog.setArguments(bundle);
                dialog.show(getSupportFragmentManager(), "play-tag");
            });
        }

        @Override
        public int getItemCount() {
            return musicModels.size();
        }
    }


    class CardViewHolder extends RecyclerView.ViewHolder {

        ItemCardCategoryBinding categoryBinding;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryBinding = ItemCardCategoryBinding.bind(itemView);
        }

        public void bind(Model model, View.OnClickListener clickListener) {
            Glide.with(categoryBinding.image)
                    .load(model.src)
                    .error(R.drawable.error_cover_can_t_found)
                    .centerCrop()
                    .into(categoryBinding.image);
            categoryBinding.title.setText(model.name);
            categoryBinding.image.setOnClickListener(clickListener);
        }


    }

    class CardAdapter extends RecyclerView.Adapter<CardViewHolder> {

        private final List<Model> models;

        public CardAdapter(List<Model> models) {
            this.models = models;
        }

        @NonNull
        @Override
        public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ItemCardCategoryBinding categoryBinding = ItemCardCategoryBinding.inflate(getLayoutInflater(), parent, false);
            return new CardViewHolder(categoryBinding.getRoot());
        }

        @Override
        public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
            holder.bind(models.get(position), v -> {
                // 跳转
                if (type == 0) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.parse(models.get(position).getSrc()), "video/*");
                    ComponentName comp = new ComponentName("org.xbmc.kodi", "org.xbmc.kodi.Splash");
                    intent.setComponent(comp);
                    startActivity(intent);
                } else if (type == 2) {
                    ImageDialog dialog = new ImageDialog();
                    Bundle bundle = new Bundle();
                    bundle.putString("PATH", models.get(position).getSrc());
                    dialog.setArguments(bundle);
                    dialog.show(getSupportFragmentManager(), "image-tag");
                }
            });
        }

        @Override
        public int getItemCount() {
            return models.size();
        }
    }
}
