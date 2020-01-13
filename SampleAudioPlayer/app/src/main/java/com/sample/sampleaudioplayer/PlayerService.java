package com.sample.sampleaudioplayer;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.io.IOException;

public class PlayerService extends Service {

    private final IBinder mBinder = new MyBinder();

    public class MyBinder extends Binder{
        PlayerService getService(){
            return PlayerService.this;
        }
    }

    MediaPlayer mediaPlayer = new MediaPlayer();

    public PlayerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getStringExtra("url") != null){
            playStream(intent.getStringExtra("url"));
        }

        return START_NOT_STICKY; // service will only run when it is processing commands
    }

    public void playStream (String url){
        if (mediaPlayer != null){
            try {
                mediaPlayer.stop();
            } catch (Exception e){
                e.printStackTrace();
            }
            mediaPlayer = null;
        }

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    // deleted  mediaPlayer.start();
                    playPlayer();
                }
            });
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                  //  MainActivity.flipPlayPauseButton(false);
                    flipPlayPauseButton(false);
                }
            });
            mediaPlayer.prepareAsync();

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void pausePlayer(){
        try {
            mediaPlayer.pause();
           // MainActivity.flipPlayPauseButton(false);
            flipPlayPauseButton(false);

        } catch (Exception e){
            Log.d("PLAYER", "pausePlayer: failed to pause");
        }
    }

    public void playPlayer(){
        try {
            mediaPlayer.start();
            // MainActivity.flipPlayPauseButton(true);
            flipPlayPauseButton(true);

        } catch (Exception e){
            Log.d("PLAYER", "pausePlayer: failed to pause");
        }
    }

    public void flipPlayPauseButton(boolean isPlaying){
        Intent intent = new Intent("changePlayButton");
        intent.putExtra("isPlaying", isPlaying);

        // Broadcast receiver
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    public void togglePlayer(){
        try {
            if (mediaPlayer.isPlaying()){
                pausePlayer();
            } else {
                playPlayer();
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
