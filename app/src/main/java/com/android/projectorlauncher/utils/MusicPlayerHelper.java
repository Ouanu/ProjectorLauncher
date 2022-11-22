package com.android.projectorlauncher.utils;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.android.projectorlauncher.bean.MusicModel;

import java.io.IOException;
import java.lang.ref.WeakReference;

public class MusicPlayerHelper implements MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, SeekBar.OnSeekBarChangeListener {

    private static final int MSG_CODE = 0x01;
    private static final long MSG_TIME = 1_000L;
    private final MediaPlayer player;
    private final SeekBar seekBar;
    private MediaPlayer.OnCompletionListener completionListener;
    private final MusicHelperHandler musicHandler;
    private final MutableLiveData<Boolean> isPlay;

    @SuppressWarnings("deprecation")
    public MusicPlayerHelper(Context context, SeekBar seekBar, MutableLiveData<Boolean> isPlay) {
        this.isPlay = isPlay;
        musicHandler = new MusicHelperHandler(context.getMainLooper(), this);
        player = new MediaPlayer();
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        player.setOnBufferingUpdateListener(this);
        player.setOnPreparedListener(this);
        player.setOnCompletionListener(this);

        this.seekBar = seekBar;
        this.seekBar.setOnSeekBarChangeListener(this);
    }

    public void setCompletionListener(MediaPlayer.OnCompletionListener listener) {
        completionListener = listener;
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        seekBar.setProgress(percent);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if (completionListener != null) {
            completionListener.onCompletion(mp);
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }

    public void playMusic(MusicModel musicModel, Boolean isRestPlayer) {
        if (isRestPlayer) {
            player.reset();
            if (!TextUtils.isEmpty(musicModel.getResSrc())) {
                try {
                    player.setDataSource(musicModel.getResSrc());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                player.prepareAsync();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        } else {
            player.start();
        }
        musicHandler.sendEmptyMessage(MSG_CODE);
    }

    public void autoPlay() {
        if (player.isPlaying()) {
            player.pause();
            isPlay.setValue(false);
            musicHandler.removeMessages(MSG_CODE);
        } else {
            player.start();
            musicHandler.sendEmptyMessage(MSG_CODE);
        }
    }

    public void stop() {
        player.stop();
        seekBar.setProgress(0);
        musicHandler.removeMessages(MSG_CODE);
    }

    public void destroy() {
        player.release();
        musicHandler.removeCallbacksAndMessages(null);
    }

    public void fastForward() {
        int progress = seekBar.getProgress();
        int duration = player.getDuration();
        int max = seekBar.getMax();
        if (max > progress + 5) {
            progress += 5;
        } else {
            progress = max;
        }
        float msec = progress / (max * 1.0f) * duration;
        player.seekTo((int) msec);
    }

    public void fastRewind() {
        int progress = seekBar.getProgress();
        int duration = player.getDuration();
        int max = seekBar.getMax();
        if (0 < progress - 5) {
            progress -= 5;
        } else {
            progress = 0;
        }
        float msec = progress / (max * 1.0f) * duration;
        player.seekTo((int) msec);
    }

    class MusicHelperHandler extends Handler {
        WeakReference<MusicPlayerHelper> weakReference;

        public MusicHelperHandler(@NonNull Looper looper, MusicPlayerHelper helper) {
            super(looper);
            this.weakReference = new WeakReference<>(helper);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == MSG_CODE) {
                int position = 0;
                if (weakReference.get().player.isPlaying() && !weakReference.get().seekBar.isPressed()) {
                    if (weakReference.get().player.getDuration() > 0) {
                        int current = weakReference.get().player.getCurrentPosition();
                        int duration = weakReference.get().player.getDuration();
                        position = (int) (weakReference.get().seekBar.getMax() * current / duration * 1.0f);
                    }
                    Log.d("MusicPlayerHelper", "handleMessage: " + position);
                }
                weakReference.get().seekBar.setProgress(position);
                isPlay.setValue(true);
                sendEmptyMessageDelayed(MSG_CODE, MSG_TIME);
            }
        }
    }

    /**
     * SeekBar settings
     */
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        musicHandler.removeMessages(MSG_CODE);
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        int progress = seekBar.getProgress();
        int max = player.getDuration();
        int seekBarMax = seekBar.getMax();
        float msec = progress / (seekBarMax * 1.0f) * max;
        player.seekTo((int) msec);
        musicHandler.sendEmptyMessageDelayed(MSG_CODE, MSG_TIME);
    }
}
