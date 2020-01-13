package com.sample.sampleaudioplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String url = "https://file-examples.com/wp-content/uploads/2017/11/file_example_MP3_700KB.mp3";

        if (Player.player == null){
            new Player();
            Player.player.playStream(url);
        }
    }
}
