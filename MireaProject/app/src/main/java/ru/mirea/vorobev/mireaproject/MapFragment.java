package ru.mirea.vorobev.mireaproject;


import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.Map;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.runtime.image.ImageProvider;


import ru.mirea.vorobev.mireaproject.databinding.FragmentMapBinding;


public class MapFragment extends AppCompatActivity {

    private FragmentMapBinding binding;
    private final String MAPKIT_API_KEY = "a84007cd-0b13-446c-8227-339ac0925864";
    private final Point ROUTE_START_LOCATION = new Point(55.584777, 37.903695);
    private final Point ROUTE_END_LOCATION = new Point(55.769008, 37.644612);
    private final Point SCREEN_CENTER = new Point(
            (ROUTE_START_LOCATION.getLatitude() + ROUTE_END_LOCATION.getLatitude()) / 2,
            (ROUTE_START_LOCATION.getLongitude() + ROUTE_END_LOCATION.getLongitude()) /
                    2);
    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MapKitFactory.setApiKey(MAPKIT_API_KEY);
        MapKitFactory.initialize(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_map);
        mapView = findViewById(R.id.mapView);
        Map map = mapView.getMap();


        PlacemarkMapObject redSquareMarker = map.getMapObjects().addPlacemark(new Point(55.753930, 37.620795));
        redSquareMarker.setIcon(ImageProvider.fromResource(this,  org.osmdroid.library.R.drawable.osm_ic_follow_me_on));
        redSquareMarker.setText("Красная площадь");

        PlacemarkMapObject vorobevyGoryMarker = map.getMapObjects().addPlacemark(new Point(55.710906,  37.553295));
        vorobevyGoryMarker.setIcon(ImageProvider.fromResource(this,  org.osmdroid.library.R.drawable.osm_ic_follow_me_on));
        vorobevyGoryMarker.setText("Воробьевы горы");

    }


    @Override
    protected void onStop() {
        mapView.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }
    @Override
    protected void onStart() {
        super.onStart();
        MapKitFactory.getInstance().onStart();
        mapView.onStart();
    }
}
//    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
//    private MapView mapView;
//    private IMapController mapController;
//    private static final GeoPoint KRASNAYA_PLOSHCHAD_LOCATION = new GeoPoint(55.753930, 37.620795);
//    private static final String KRASNAYA_PLOSHCHAD_TITLE = "Красная площадь";
//    private static final String KRASNAYA_PLOSHCHAD_DESCRIPTION = "Красная площадь — главная и наиболее известная площадь Москвы и России, арена многих важных событий русской истории и истории Советского государства, место массовых демонстраций трудящихся столицы и парадов Вооружённых Сил России.";
//
//    private static final GeoPoint VOROBEVY_GORY_LOCATION = new GeoPoint(55.710906, 37.553295);
//    private static final String VOROBEVY_GORY_TITLE = "Воробьевы горы";
//    private static final String VOROBEVY_GORY_DESCRIPTION = "Здесь очень красиво, тут я сделал своей девушке предложение";
//
//    public MapFragment() {
//        // Required empty public constructor
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_map, container, false);
//
//        // Initialize the map view
//        Configuration.getInstance().load(getContext(), PreferenceManager.getDefaultSharedPreferences(getContext()));
//        mapView = view.findViewById(R.id.mapView);
//        mapView.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE);
//        // mapView.setBuiltInZoomControls(true);
//        mapView.setMultiTouchControls(true);
//        mapController = mapView.getController();
//        mapController.setZoom(15.0);
//        GeoPoint startPoint = new GeoPoint(55.794229, 37.700772);
//        mapController.setCenter(startPoint);
//
//        // Add markers for establishments
//        addMarker(KRASNAYA_PLOSHCHAD_LOCATION, KRASNAYA_PLOSHCHAD_TITLE, KRASNAYA_PLOSHCHAD_DESCRIPTION);
//        addMarker(VOROBEVY_GORY_LOCATION, VOROBEVY_GORY_TITLE, VOROBEVY_GORY_DESCRIPTION);
//
//        // Check location permission
//        if (hasLocationPermission()) {
//            enableMyLocation();
//        } else {
//            requestLocationPermission();
//        }
//
//        return view;
//    }
//
//    private boolean hasLocationPermission() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            int result = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION);
//            return result == PackageManager.PERMISSION_GRANTED;
//        }
//        return true;
//    }
//
//    private void requestLocationPermission() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
//        }
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        mapView.onResume();
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        mapView.onPause();
//    }
//
//    private void addMarker(GeoPoint point, String title, String description) {
//        Marker marker = new Marker(mapView);
//        marker.setPosition(point);
//        marker.setTitle(title);
//        marker.setSnippet(description);
//        marker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
//            @Override
//            public boolean onMarkerClick(Marker marker, MapView mapView) {
//                Toast.makeText(requireContext(), marker.getTitle(), Toast.LENGTH_SHORT).show();
//                return true;
//            }
//        });
//        mapView.getOverlays().add(marker);
//    }
//
//    private void enableMyLocation() {
//        MyLocationNewOverlay locationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(requireContext()), mapView);
//        locationOverlay.enableMyLocation();
//        mapView.getOverlays().add(locationOverlay);
//        mapView.invalidate();
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                enableMyLocation();
//            } else {
//                Toast.makeText(requireContext(), "Location permission denied", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//

