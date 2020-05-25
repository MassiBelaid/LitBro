package com.LilBro.LitBro.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.LilBro.LitBro.Activity.ConnectionActivity;
import com.LilBro.LitBro.Models.Local;
import com.LilBro.LitBro.Models.Utilisateur;
import com.LilBro.LitBro.R;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;

import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;
import java.util.List;


public class MapFragment extends Fragment implements  OnMapReadyCallback{

    MapView mapView;
    private GoogleMap googleMap;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private Utilisateur user;
    String idUserLocal;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private List<Local> ll;


    public MapFragment(Utilisateur user) {
        this.user = user;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View result = inflater.inflate(R.layout.fragment_map, container, false);
        //Toast.makeText(getActivity(),"ON CREATEDView", Toast.LENGTH_LONG).show();
        //fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());



        return result;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView = (MapView) view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        if(this.user.getUtilisateurType().equals(ConnectionActivity.UTILISATEUR_AUGMENTE)){
            this.idUserLocal = user.getLogin();
        }else{
            this.idUserLocal = user.getUserSup();
        }



        mapView.getMapAsync(this);
        mapView.onResume();
        //Toast.makeText(getActivity(),"ON FViewCreate", Toast.LENGTH_LONG).show();

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        }catch (Exception e){
            Toast.makeText(getActivity(),"Exception" + e, Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        fetchLocation();

    }

    private void fetchLocation (){
        //Toast.makeText(getActivity(),"ON FETCH LOCATION", Toast.LENGTH_LONG).show();
        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.ACCESS_COARSE_LOCATION)){
                Toast.makeText(getActivity(),"PERMISSIONS NOT ACCEPTED", Toast.LENGTH_LONG).show();
            }else{
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},PERMISSION_REQUEST_CODE);
            }
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
        mapView.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case PERMISSION_REQUEST_CODE :
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    googleMap.setMyLocationEnabled(true);
                }else{
                    googleMap.setMyLocationEnabled(false);
                }
        }
    }

    @Override
    public void onMapReady(GoogleMap gMap) {

        this.ll = new ArrayList<>();

        CollectionReference locaux = db.collection("local");
        locaux.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(QueryDocumentSnapshot document : queryDocumentSnapshots){
                            Local loc = document.toObject(Local.class);
                            if(loc.getUtilisateurProp().equals( idUserLocal))
                            {
                                ll.add(loc);
                            }
                        }


                    }
                }).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                //Toast.makeText(getActivity(),"ON MAPREADY"+this.ll.size(), Toast.LENGTH_LONG).show();
                googleMap = gMap;
                LatLng point = new LatLng(43.60, 3.87);
                //googleMap.addMarker(new MarkerOptions().position(point).title("LA FOUINE").snippet("snipeset que j'ai mis"));
                for(Local l : ll){
                    googleMap.addMarker(new MarkerOptions().position(new LatLng(l.getLocalisation().getLatitude(),l.getLocalisation().getLongitude())).title(l.getNomLocal()).snippet(l.getZone()));
                }

                CameraPosition cameraPosition = new CameraPosition.Builder().target(point).zoom(11).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(),getResources().getString(R.string.bddEchec),Toast.LENGTH_LONG).show();
                    }
                });

        //Toast.makeText(getActivity(),"ON MAPREADY", Toast.LENGTH_LONG).show();



    }
}
