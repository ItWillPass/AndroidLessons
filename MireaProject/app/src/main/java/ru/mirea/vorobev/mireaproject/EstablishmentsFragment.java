package ru.mirea.vorobev.mireaproject;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;
import android.preference.PreferenceManager;

public class EstablishmentsFragment extends Fragment {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private MapView mapView;
    private IMapController mapController;
    private static final GeoPoint KRASNAYA_PLOSHCHAD_LOCATION = new GeoPoint(55.753930, 37.620795);
    private static final String KRASNAYA_PLOSHCHAD_TITLE = "Красная площадь";
    private static final String KRASNAYA_PLOSHCHAD_DESCRIPTION = "Красная площадь — главная и наиболее известная площадь Москвы и России, арена многих важных событий русской истории и истории Советского государства, место массовых демонстраций трудящихся столицы и парадов Вооружённых Сил России.";

    private static final GeoPoint VOROBEVY_GORY_LOCATION = new GeoPoint(55.710906, 37.553295);
    private static final String VOROBEVY_GORY_TITLE = "Воробьевы горы";
    private static final String VOROBEVY_GORY_DESCRIPTION = "Здесь очень красиво, тут я сделал своей девушке предложение";

    public EstablishmentsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_establishments, container, false);

        // Initialize the map view
        Configuration.getInstance().load(getContext(), PreferenceManager.getDefaultSharedPreferences(getContext()));
        mapView = view.findViewById(R.id.mapView);
        mapView.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE);
        // mapView.setBuiltInZoomControls(true);
        mapView.setMultiTouchControls(true);
        mapController = mapView.getController();
        mapController.setZoom(15.0);
        GeoPoint startPoint = new GeoPoint(55.794229, 37.700772);
        mapController.setCenter(startPoint);

        // Add markers for establishments
        addMarker(KRASNAYA_PLOSHCHAD_LOCATION, KRASNAYA_PLOSHCHAD_TITLE, KRASNAYA_PLOSHCHAD_DESCRIPTION);
        addMarker(VOROBEVY_GORY_LOCATION, VOROBEVY_GORY_TITLE, VOROBEVY_GORY_DESCRIPTION);

        // Check location permission
        if (hasLocationPermission()) {
            enableMyLocation();
        } else {
            requestLocationPermission();
        }

        return view;
    }

    private boolean hasLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int result = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION);
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
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    private void addMarker(GeoPoint point, String title, String description) {
        Marker marker = new Marker(mapView);
        marker.setPosition(point);
        marker.setTitle(title);
        marker.setSnippet(description);
        marker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker, MapView mapView) {
                Toast.makeText(requireContext(), marker.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        mapView.getOverlays().add(marker);
    }

    private void enableMyLocation() {
        MyLocationNewOverlay locationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(requireContext()), mapView);
        locationOverlay.enableMyLocation();
        mapView.getOverlays().add(locationOverlay);
        mapView.invalidate();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                enableMyLocation();
            } else {
                Toast.makeText(requireContext(), "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
