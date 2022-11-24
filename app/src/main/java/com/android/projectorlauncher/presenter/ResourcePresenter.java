package com.android.projectorlauncher.presenter;

import androidx.lifecycle.ViewModelProvider;
import com.android.projectorlauncher.bean.Model;
import com.android.projectorlauncher.bean.MusicModel;
import com.android.projectorlauncher.ui.activity.ResourceActivity;
import com.android.projectorlauncher.ui.view.ResourceView;
import com.android.projectorlauncher.utils.FileUtils;
import com.android.projectorlauncher.utils.ScanImageUtils;
import com.android.projectorlauncher.utils.ScanMusicUtils;
import com.android.projectorlauncher.utils.ScanVideoUtils;
import com.android.projectorlauncher.viewmodel.ResourceViewModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ResourcePresenter implements Serializable {
    private final ResourceViewModel vm;
    private ResourceView view;

    public ResourcePresenter(ResourceActivity activity) {
        vm = new ViewModelProvider(activity).get(ResourceViewModel.class);
        view = activity;
        vm.getVideos().observe(activity, models -> {
            //更新操作
            view.updateUI();
        });

        vm.getMusics().observe(activity, musicModels -> {
            //更新操作
            view.updateUI();
        });

        vm.getImages().observe(activity, models -> {
            //更新操作
            view.updateUI();
        });
    }

    public void loadVideos() {
        List<Model> videoModels = new ArrayList<>();
        HashSet<String> paths = FileUtils.getUsbDisk();
        for (String path : paths) {
            videoModels.addAll(ScanVideoUtils.getVideoList(path));
        }
        vm.setVideos(videoModels);
    }

    public void loadAudios() {
        List<MusicModel> audioModels = new ArrayList<>();
        HashSet<String> paths = FileUtils.getUsbDisk();
        for (String path : paths) {
            audioModels.addAll(ScanMusicUtils.getMusicList(path));
        }
        vm.setMusics(audioModels);
    }

    public void loadImages() {
        List<Model> imageModels = new ArrayList<>();
        HashSet<String> paths = FileUtils.getUsbDisk();
        for (String path : paths) {
            imageModels.addAll(ScanImageUtils.getImageList(path));
        }
        vm.setImages(imageModels);
    }

    public List<Model> getVideos() {
        return vm.getVideos().getValue();
    }

    public List<MusicModel> getMusics() {
        return vm.getMusics().getValue();
    }

    public List<Model> getImages() {
        return vm.getImages().getValue();
    }
}
