package com.android.projectorlauncher.ui.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.projectorlauncher.R;
import com.android.projectorlauncher.bean.Model;
import com.android.projectorlauncher.bean.MusicModel;
import com.android.projectorlauncher.databinding.ActivityResourceBinding;
import com.android.projectorlauncher.databinding.ItemCardCategoryBinding;
import com.android.projectorlauncher.utils.FileUtils;
import com.android.projectorlauncher.utils.ScanMusicUtils;
import com.android.projectorlauncher.utils.ScanVideoUtils;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class ResourceActivity extends Activity {
    public static final int TYPE_ERROR = -1;
    public static final int TYPE_VIDEO = 0;
    public static final int TYPE_AUDIO = 1;
    public static final int TYPE_IMAGE = 2;
    private ActivityResourceBinding resourceBinding;
    private int type = 0;
    private final MutableLiveData<List<Model>> videoList = new MutableLiveData<List<Model>>();
    private final MutableLiveData<List<MusicModel>> musicList = new MutableLiveData<>();
    private final MutableLiveData<List<Model>> imageList = new MutableLiveData<>();
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
        resourceBinding = ActivityResourceBinding.inflate(getLayoutInflater());
        setContentView(resourceBinding.getRoot());
        if (getIntent() != null) {
            type = getIntent().getIntExtra("TYPE", 0);
        }
        resourceBinding.mediaRecyclerView.setLayoutManager(new GridLayoutManager(this, 5));
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_MEDIA_MOUNTED);
        filter.addAction(Intent.ACTION_MEDIA_REMOVED);
        filter.addDataScheme("file");
        registerReceiver(receiver, filter);

        if (type == TYPE_ERROR) {
            resourceBinding.error.setVisibility(View.VISIBLE);
            resourceBinding.mediaRecyclerView.setVisibility(View.GONE);
        } else if (type == TYPE_VIDEO) {
            resourceBinding.category.setText(R.string.video);
            resourceBinding.mediaRecyclerView.setVisibility(View.VISIBLE);
            resourceBinding.error.setVisibility(View.GONE);
            List<Model> videoModels = new ArrayList<>();
            HashSet<String> paths = FileUtils.getUsbDisk();
            for (String path : paths) {
                Log.d("ResourceActivity", "onStart: " + path);
                videoModels.addAll(ScanVideoUtils.getVideoList(path));
            }
            videoList.setValue(videoModels);
            Log.d("ResourceActivity", "onStart: " + Objects.requireNonNull(videoList.getValue()).size());
            resourceBinding.mediaRecyclerView.setAdapter(new CardAdapter(videoList.getValue()));
            resourceBinding.mediaRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                @Override
                public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                    outRect.top = 10;
                    outRect.left = 10;
                    outRect.right = 10;
                    outRect.bottom = 10;
                }
            });
        } else if (type == TYPE_AUDIO) {
            resourceBinding.category.setText(R.string.audio);
            resourceBinding.mediaRecyclerView.setVisibility(View.VISIBLE);
            resourceBinding.error.setVisibility(View.GONE);
            List<MusicModel> audioModels = new ArrayList<>();
            HashSet<String> paths = FileUtils.getUsbDisk();
            for (String path : paths) {
                audioModels.addAll(ScanMusicUtils.getMusicList(path));
            }
            musicList.setValue(audioModels);
        } else if (type == TYPE_IMAGE) {
            resourceBinding.error.setVisibility(View.GONE);
            resourceBinding.category.setText(R.string.picture);
            resourceBinding.mediaRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    class CardViewHolder extends RecyclerView.ViewHolder {

        ItemCardCategoryBinding categoryBinding;
        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryBinding = ItemCardCategoryBinding.bind(itemView);

        }

        public void bind(Model model, View.OnClickListener listener){
            Glide.with(categoryBinding.image)
                    .load(model.src)
                    .centerCrop()
                    .into(categoryBinding.image);
            categoryBinding.title.setText(model.name);
        }



    }

    class CardAdapter extends RecyclerView.Adapter<CardViewHolder> {

        private List<Model> models;

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
                    startActivity(intent);
                } else if (type == 2) {

                }
            });
        }

        @Override
        public int getItemCount() {
            return models.size();
        }
    }
}
