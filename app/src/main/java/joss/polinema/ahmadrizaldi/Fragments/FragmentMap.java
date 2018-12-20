package joss.polinema.ahmadrizaldi.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import joss.polinema.ahmadrizaldi.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMap extends Fragment implements OnMapReadyCallback{


    GoogleMap mGoogleMap;
    MapView mMapView;
    View mView;

    double lat, lang;

    String namahotel;


    public FragmentMap() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_fragment_map, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMapView = (MapView)mView.findViewById(R.id.peta);

        if (mMapView != null){
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //Inisialisasi maos
        MapsInitializer.initialize(getContext());

        //Set tipe map yang akan digunakan
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        //menset langtitude dan longtitude
        LatLng polinema = new LatLng(-7.9465289, 112.6133481);

        //menambahkan marker pada langtitude dan longtitude diatas
        googleMap.addMarker(new MarkerOptions().position(polinema).title(namahotel).snippet("Ini rumahku"));

        //menset posisi kamera/ fokus pada lokasi
        CameraPosition lokasi = CameraPosition.builder().target(polinema).zoom(16).bearing(0).tilt(0 ).build();
        //mengarahkan kamera pada lokasi yang telah di mark
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(lokasi));
    }
}
