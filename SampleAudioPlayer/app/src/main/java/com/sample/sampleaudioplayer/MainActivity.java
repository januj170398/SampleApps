package com.sample.sampleaudioplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    static FloatingActionButton playPauseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playPauseButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);

        playPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Player.player.togglePlayer();
            }
        });

        String url = "https://file-examples.com/wp-content/uploads/2017/11/file_example_MP3_700KB.mp3";

        if (Player.player == null){
            new Player();
            Player.player.playStream(url);
        }
    }

    public static void flipPlayPauseButton (boolean isPlaying){
        if (isPlaying){
            playPauseButton.setImageResource(android.R.drawable.ic_media_pause);
        } else {
            playPauseButton.setImageResource(android.R.drawable.ic_media_play);
        }
    }
}
