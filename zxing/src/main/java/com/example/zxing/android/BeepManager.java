package com.example.zxing.android;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.util.Log;

import com.example.zxing.R;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author 53288
 * @description 管理声音和震动
 * @date 2021/5/27
 */
public class BeepManager implements MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener, Closeable {
    private static final String TAG = BeepManager.class.getSimpleName();
    private static final float BEEP_VOLUME = 0.10f;
    private static final long VIBRATE_DURATION = 200L;
    private final Activity activity;
    private MediaPlayer mediaPlayer;
    private boolean playBeep;
    private boolean vibrate;

    public BeepManager(Activity activity) {
        this.activity = activity;
        this.mediaPlayer = null;
        updatePrefs();
    }

    public void setPlayBeep(boolean playBeep) {
        this.playBeep = playBeep;
    }


    public void setVibrate(boolean vibrate) {
        this.vibrate = vibrate;
    }

    public synchronized void updatePrefs() {
        if (playBeep && mediaPlayer == null) {
            // The volume on STREAM_SYSTEM is not adjustable, and users found it
            // too loud,
            // so we now play on the music stream.
            // 设置activity音量控制键控制的音频流
            activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = buildMediaPlayer(activity);
        }
    }

    /**
     * 开启响铃和震动
     */
    public synchronized void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    /**
     * 创建MediaPlayer
     *
     * @param activity
     * @return
     */
    private MediaPlayer buildMediaPlayer(Context activity) {
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        // 监听是否播放完成
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnErrorListener(this);
        // 配置播放资源
        try {
            AssetFileDescriptor file = activity.getResources()
                    .openRawResourceFd(R.raw.beep);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(),
                        file.getStartOffset(), file.getLength());
            } finally {
                file.close();
            }
            // 设置音量
            mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
            mediaPlayer.prepare();
            return mediaPlayer;
        } catch (IOException ioe) {
            Log.w(TAG, ioe);
            mediaPlayer.release();
            return null;
        }
    }


    @Override
    public void onCompletion(MediaPlayer mp) {
        // 当哔哔声播放完毕后，倒带以排队等候另一个哔哔声
        mp.seekTo(0);
    }

    @Override
    public synchronized boolean onError(MediaPlayer mp, int what, int extra) {
        if (what == MediaPlayer.MEDIA_ERROR_SERVER_DIED) {
            // we are finished, so put up an appropriate error toast if required  and finish
            activity.finish();
        } else {
            // possibly media player error, so release and recreate
            mp.release();
            mediaPlayer = null;
            updatePrefs();
        }
        return true;
    }

    @Override
    public synchronized void close() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
