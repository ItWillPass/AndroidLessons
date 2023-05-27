package ru.mirea.vorobev.osmmaps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.preference.PreferenceManager;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import ru.mirea.vorobev.osmmaps.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private MapView mapView;
    private MyLocationNewOverlay locationNewOverlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);


        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        mapView = binding.mapView;
        mapView.setMultiTouchControls(true);
        mapView.setZoomRounding(true);


        mapView.getController().setZoom(15.0);
        GeoPoint startPoint = new GeoPoint(55.794229, 37.700772);
        mapView.getController().setCenter(startPoint);


        Marker marker = new Marker(mapView);
        marker.setPosition(startPoint);
        marker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker, MapView mapView) {
                Toast.makeText(getApplicationContext(), "Очень красивое место!", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        marker.setIcon(ResourcesCompat.getDrawable(getResources(), org.osmdroid.library.R.drawable.osm_ic_follow_me_on, null));
        marker.setTitle("Title");
        mapView.getOverlays().add(marker);


        if (hasLocationPermission()) {
            locationNewOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(getApplicationContext()), mapView);
            locationNewOverlay.enableMyLocation();
            mapView.getOverlays().add(locationNewOverlay);
        } else {
            requestLocationPermission();
        }

        CompassOverlay compassOverlay = new CompassOverlay(getApplicationContext(), mapView);
        compassOverlay.enableCompass();
        mapView.getOverlays().add(compassOverlay);

        ScaleBarOverlay scaleBarOverlay = new ScaleBarOverlay(mapView);
        scaleBarOverlay.setCentred(true);
        scaleBarOverlay.setScaleBarOffset(mapView.getContext().getResources().getDisplayMetrics().widthPixels / 2, 10);
        mapView.getOverlays().add(scaleBarOverlay);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    private boolean hasLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int result = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
            return result == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }

    private void requestLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationNewOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(getApplicationContext()), mapView);
                locationNewOverlay.enableMyLocation();
                mapView.getOverlays().add(locationNewOverlay);
            } else {
                Toast.makeText(getApplicationContext(), "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}


