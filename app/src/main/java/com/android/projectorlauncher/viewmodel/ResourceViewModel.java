package com.android.projectorlauncher.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.projectorlauncher.bean.Model;
import com.android.projectorlauncher.bean.MusicModel;

import java.util.List;

public class ResourceViewModel extends ViewModel {
    private final MutableLiveData<List<Model>> videoList = new MutableLiveData<>();
    private final MutableLiveData<List<MusicModel>> musicList = new MutableLiveData<>();
    private final MutableLiveData<List<Model>> imageList = new MutableLiveData<>();

    public ResourceViewModel() {
    }

    public void setVideos(List<Model> videos) {
        videoList.setValue(videos);
    }

    public void setMusics(List<MusicModel> musics) {
        musicList.setValue(musics);
    }

    public void setImages(List<Model> images) {
        imageList.setValue(images);
    }

    public MutableLiveData<List<Model>> getVideos() {
        return videoList;
    }

    public MutableLiveData<List<MusicModel>> getMusics() {
        return musicList;
    }

    public MutableLiveData<List<Model>> getImages() {
        return imageList;
    }
}
