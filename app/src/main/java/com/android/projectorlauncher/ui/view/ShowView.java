package com.android.projectorlauncher.ui.view;

import com.android.projectorlauncher.bean.VideoCard;

import java.util.List;

public interface ShowView {
    void update(VideoCard r1, VideoCard r2, VideoCard r3, VideoCard r4, List<VideoCard> cards);
}
