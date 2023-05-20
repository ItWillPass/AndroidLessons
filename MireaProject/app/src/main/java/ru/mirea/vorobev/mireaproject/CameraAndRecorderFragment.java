package ru.mirea.vorobev.mireaproject;

import static androidx.core.content.ContextCompat.getSystemService;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CameraAndRecorderFragment extends Fragment implements SensorEventListener {
    private static final int REQUEST_CODE_PERMISSION_CAMERA = 100;
    private static final int REQUEST_CODE_CAMERA = 101;
    private static final int REQUEST_CODE_PERMISSION_RECORD_AUDIO = 101;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 101;
    private ImageView imageView;
    private Uri imageUri;

    private TextView azimuthTextView;
    private TextView pitchTextView;
    private TextView rollTextView;
    private SensorManager sensorManager;
    private Sensor accelerometerSensor;

    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;

    private String outputFile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camera, container, false);
        imageView = view.findViewById(R.id.imageView);

        // Проверка наличия разрешения на использование камеры
        if (ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // Разрешение не было получено, запрашиваем его у пользователя
            requestCameraPermission();
        }
        // проверка наличия разрешения на использование аудио
        if (ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){
            reguestAudioPermission();
        }
        // Проверка разрешения
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Разрешение не предоставлено, запросите его у пользователя
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
        } else {
            // Разрешение уже предоставлено, выполните необходимые действия
            startRecording();
        }

        sensorManager = (SensorManager)getActivity().getSystemService(Context.SENSOR_SERVICE);
        accelerometerSensor = sensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        azimuthTextView = view.findViewById(R.id.azimuthTextView);
        pitchTextView = view.findViewById(R.id.pitchTextView);
        rollTextView = view.findViewById(R.id.rollTextView);
        ImageButton imageButtonStart=view.findViewById(R.id.imageButtonStart);
        imageButtonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRecording();
            }
        });
        ImageButton imageButtonStop = view.findViewById(R.id.imageButtonStop);
        imageButtonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopRecording();
            }
        });
        Button ButtonPlay = view.findViewById(R.id.buttonListen);
        ButtonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playRecording();
            }
        });


        // Обработка нажатия на кнопку для запуска камеры
        Button cameraButton = view.findViewById(R.id.button);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePicture();
            }
        });

        return view;

    }

    private void reguestAudioPermission() {
        ActivityCompat.requestPermissions(requireActivity(),
                new String[]{Manifest.permission.RECORD_AUDIO},
                REQUEST_CODE_PERMISSION_RECORD_AUDIO);
    }

    private void startRecording() {
        outputFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC)
                .getAbsolutePath() + "/recording.3gp";

        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        mediaRecorder.setOutputFile(outputFile);

        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stopRecording() {
        if (mediaRecorder != null) {
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
        }
    }

    private void playRecording() {
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(outputFile);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometerSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    // Метод для запроса разрешения на использование камеры
    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(requireActivity(),
                new String[]{Manifest.permission.CAMERA},
                REQUEST_CODE_PERMISSION_CAMERA);
    }

    // Обработка результата запроса разрешения на использование камеры
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if ((requestCode == REQUEST_CODE_PERMISSION_CAMERA) || (requestCode == MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE)) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Разрешение получено, можно использовать камеру
                startRecording();
            } else {
                // Разрешение не получено, выводим сообщение об ошибке
                Toast.makeText(requireContext(), "Не удалось получить разрешение на использование камеры и аудио",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }



    // Метод для запуска камеры и получения фотографии
    private void takePicture() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Создаем временный файл для сохранения фотографии
        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (photoFile != null) {
            imageUri = FileProvider.getUriForFile(requireContext(),
                    BuildConfig.APPLICATION_ID + ".fileprovider", photoFile);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(cameraIntent, REQUEST_CODE_CAMERA);
        }
    }

    // Метод для создания временного файла для сохранения фотографии
    private File createImageFile() throws IOException {
        // Создаем уникальное имя файла на основе времени съемки
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imageFileName, ".jpg", storageDir);
    }

    // Обработка результата запуска камеры и получения фотографии
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CAMERA && resultCode == Activity.RESULT_OK) {
            // Фотография успешно получена, отображаем ее на ImageView
            imageView.setImageURI(imageUri);
            // Сохраняем фотографию в галерее
            savePhotoToGallery();
        }
    }

    // Метод для сохранения фотографии в галерее
    private void savePhotoToGallery() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File file = new File(imageUri.getPath());
        Uri contentUri = Uri.fromFile(file);
        mediaScanIntent.setData(contentUri);
        requireContext().sendBroadcast(mediaScanIntent);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float valueAzimuth = event.values[0];
            float valuePitch = event.values[1];
            float valueRoll = event.values[2];
            azimuthTextView.setText("Azimuth: " + valueAzimuth);
            pitchTextView.setText("Pitch: " + valuePitch);
            rollTextView.setText("Roll: " + valueRoll);
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
