package ru.mirea.vorobev.audiorecord;
import static android.content.ContentValues.TAG;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "AudioRecordTest";
    private static String outputFile = null;

    private Button startRecordButton;
    private Button stopRecordButton;
    private Button playButton;
    private File audioFile;

    private MediaRecorder mediaRecorder;
    private MediaPlayer player = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startRecordButton = findViewById(R.id.start_record_button);
        stopRecordButton = findViewById(R.id.stop_record_button);
        playButton = findViewById(R.id.play_button);

        stopRecordButton.setEnabled(false);
        playButton.setEnabled(false);

        outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording.3gp";
    }

    // Обработчик нажатия на кнопку "Start Record"
    public void onRecordStart(View view) {
        try {
            startRecordButton.setEnabled(false);
            stopRecordButton.setEnabled(true);
            stopRecordButton.requestFocus();
            startRecording();
        } catch (Exception e) {
            Log.e(LOG_TAG, "Caught io exception " + e.getMessage());
        }
    }

    // Обработчик нажатия на кнопку "Stop Record"
    public void onStopRecord(View view) {
        startRecordButton.setEnabled(true);
        stopRecordButton.setEnabled(false);
        startRecordButton.requestFocus();
        stopRecording();

    }

    // Обработчик нажатия на кнопку "Play"
    public void onPlay(View view) {
        if (outputFile != null) {
            try {
                playRecording();
            } catch (IOException e) {
                Log.e(TAG, "Caught io exception " + e.getMessage());
            }
        }
    }


    private void playRecording() throws IOException {
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setDataSource(outputFile);
        mediaPlayer.prepare();
        mediaPlayer.start();
    }
    private void stopRecording() {
        if (player != null) {
            player.release();
            player = null;
        }
    }
    private void startRecording() throws IOException {
        // проверка доступности sd - карты
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            Log.d(TAG, "sd-card success");
            // выбор источника звука
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            // выбор формата данных
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            // выбор кодека
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            if (audioFile == null) {
                // создание файла
                audioFile = new File(this.getExternalFilesDir(
                        Environment.DIRECTORY_MUSIC), "mirea.3gp");
            }
            mediaRecorder.setOutputFile(audioFile.getAbsolutePath());
            mediaRecorder.prepare();
            mediaRecorder.start();
            Toast.makeText(this, "Recording started!", Toast.LENGTH_SHORT).show();
        }
    }

}


