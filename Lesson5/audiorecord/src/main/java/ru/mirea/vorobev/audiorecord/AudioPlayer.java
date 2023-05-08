package ru.mirea.vorobev.audiorecord;

import android.media.MediaPlayer;

import java.io.IOException;

public class AudioPlayer {
    private MediaPlayer mediaPlayer;

    public void startPlaying(String fileName) throws IOException {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setDataSource(fileName);
        mediaPlayer.prepare();
        mediaPlayer.start();
    }
    public void stopPlaying() {
        mediaPlayer.release();
        mediaPlayer = null;
    }


}
