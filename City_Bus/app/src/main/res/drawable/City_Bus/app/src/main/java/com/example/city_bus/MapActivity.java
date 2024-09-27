package com.example.city_bus;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;

import com.example.city_bus.R;
import com.example.city_bus.databinding.ActivityMapBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;

import java.io.IOException;
import java.util.ArrayList;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapBinding binding;
    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        Intent intent = getIntent();
        double latitude = Double.parseDouble(intent.getStringExtra("latitude"));
        double longitude = Double.parseDouble(intent.getStringExtra("longitude"));
        Log.d("Tagy","A"+latitude+" "+longitude);
        LatLng latlng = new LatLng(latitude,longitude);
//        LatLng latlng = new LatLng(23.0225,72.5714);
        MarkerOptions markerOptions = new MarkerOptions().position(latlng).title("Ahmedabad");
        mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng,16f));

        // circleS
//        mMap.addCircle(new CircleOptions()
//                .center(latlng)
//                .radius(500)
//                .fillColor(Color.DKGRAY)
//                .strokeColor(Color.DKGRAY));


        // polygon
//        mMap.addPolygon(new PolygonOptions().add(new LatLng(23.0225,72.5714),
//                new LatLng(24.0225,74.5714),
//                new LatLng(25.0225,74.5714),
//                new LatLng(26.0225,73.5714),
//                new LatLng(23.0225,72.5714)).fillColor(Color.YELLOW).strokeColor(Color.BLUE));

        // Image Overlay
//        mMap.addGroundOverlay(new GroundOverlayOptions()
//                .position(latlng,1000f,1000f)
//                .image(BitmapDescriptorFactory.fromResource(R.drawable.i9))
//                .clickable(true));
//        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);


//        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//
//            @Override
//            public void onMapClick(@NonNull LatLng latLng) {
////                MarkerOptions markerOptions = (new MarkerOptions().position(latLng).title("Click here"));
//              mMap.addMarker(new MarkerOptions().position(latlng).title("Clicked here")); // clicked no karavu hoy to aam j use karvu je location par javi ema
//                // and clicked karava mate upar ni line ma markeroptions ne call karavo
//                Geocoder geocoder = new Geocoder(MapsActivity.this);
//
//                try {
//                     ArrayList<Address>arradr =  (ArrayList<Address>) geocoder.getFromLocation(latLng.latitude,latLng.longitude,1);
//                     Log.d("Addr",arradr.get(0).getAddressLine(0));
//                }
//                catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });

        // information mate

        // AA CLICK KARYA VAGAER ADDRESS MALE TYA KARVU
//        Geocoder geocoder = new Geocoder(this);
//        try {
//           ArrayList<Address>arradr =  (ArrayList<Address>) geocoder.getFromLocation(23.0225,72.5714,1);// list of address
//           Log.d("Addr",arradr.get(0).getAddressLine(0));         // app ma jya user click kare tyathi address layne avine edittext ma fit kari sakay
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }



    }
}