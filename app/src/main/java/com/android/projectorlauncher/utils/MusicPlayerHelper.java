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

import com.android.projectorlauncher.bean.MusicModel;

import java.io.IOException;
import java.lang.ref.WeakReference;

public class MusicPlayerHelper implements MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, SeekBar.OnSeekBarChangeListener {

    private static int MSG_CODE = 0x01;
    private static long MSG_TIME = 1_000L;
    private MediaPlayer player;
    private SeekBar seekBar;
    private MusicModel musicModel;
    private MediaPlayer.OnCompletionListener completionListener;
    private MusicHelperHandler musicHandler;

    public MusicPlayerHelper(Context context, SeekBar seekBar) {
        musicHandler = new MusicHelperHandler(context.getMainLooper(), this);
        player = new MediaPlayer();
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        player.setOnBufferingUpdateListener(this);
        player.setOnPreparedListener(this);
        player.setOnCompletionListener(this);

        this.seekBar = seekBar;
        this.seekBar.setOnSeekBarChangeListener(this);
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
        this.musicModel = musicModel;
        if (isRestPlayer) {
            player.reset();
            if (!TextUtils.isEmpty(musicModel.getResSrc())) {
                try {
                    player.setDataSource(musicModel.getResSrc());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            player.prepareAsync();
        } else {
            player.start();
        }
        musicHandler.sendEmptyMessage(MSG_CODE);
    }

    public void pause() {
        if (player.isPlaying()) {
            player.pause();
        }
        musicHandler.removeMessages(MSG_CODE);
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
                        position = (int) (weakReference.get().seekBar.getMax() * weakReference.get().player.getCurrentPosition() / weakReference.get().player.getDuration() * 1.0f);
                    }
                    Log.d("MusicPlayerHelper", "handleMessage: " + position);
                }
                weakReference.get().seekBar.setProgress(position);
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
