package ru.mirea.vorobev.musicplayeractivity;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.SeekBar;

import ru.mirea.vorobev.musicplayeractivity.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private MediaPlayer mediaPlayer;
    private boolean isPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // Инициализация MediaPlayer
        mediaPlayer = MediaPlayer.create(this, R.raw.song);

        // Назначение обработчиков для кнопок
        binding.playButton.setOnClickListener(v -> {
            if (isPlaying) {
                mediaPlayer.pause();
                isPlaying = false;
                binding.playButton.setImageResource(R.drawable.ic_media_play);
            } else {
                mediaPlayer.start();
                isPlaying = true;
                binding.playButton.setImageResource(R.drawable.ic_media_pause);
            }
        });

        binding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // Обновление SeekBar
        new Thread(() -> {
            while (mediaPlayer != null) {
                try {
                    if (mediaPlayer.isPlaying()) {
                        Message msg = new Message();
                        msg.what = mediaPlayer.getCurrentPosition();
                        handler.sendMessage(msg);
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private Handler handler = new Handler(msg -> {
        binding.seekBar.setProgress(msg.what);
        return false;
    });

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
