package com.example.hung.homelessproject;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import android.util.Log;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class LocationsMapActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;

    // mapped data from csv file
    private Map<Integer, String> servicesMap = new HashMap<>();
    private Map<Integer, String> firstNameMap = new HashMap<>();
    private Map<Integer, String> lastNameMap = new HashMap<>();
    private Map<Integer, String> agencyMap = new HashMap<>();
    private Map<Integer, String> titleMap = new HashMap<>();
    private Map<Integer, String> serviceTypeMap = new HashMap<>();
    private Map<Integer, String> populationMap = new HashMap<>();
    private Map<Integer, String> hoursOfOperationMap = new HashMap<>();
    private Map<Integer, String> addressMap = new HashMap<>();
    private Map<Integer, String> addressMap2 = new HashMap<>();
    private Map<Integer, String> stateMap = new HashMap<>();
    private Map<Integer, String> cityMap = new HashMap<>();
    private Map<Integer, String> zipMap = new HashMap<>();
    private Map<Integer, String> phoneMap = new HashMap<>();
    private Map<Integer, String> emailMap = new HashMap<>();
    private Map<Integer, String> websiteMap = new HashMap<>();

    // coordinates map derived from csv data
    private Map<Integer, String> coordinatesMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations_map);

        // generate data first
        generateData();

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
     * This app is made by Hong Luu
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Los Angeles and move the camera
        LatLng cityLA = new LatLng(34.0522, -118.2437);
//        mMap.addMarker(new MarkerOptions().position(cityLA).title("Marker in Los Angeles"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cityLA, 10.0f) );

        // now to map coordinates from coordinates csv file
        mapCoordinates();

    }

    private void generateData(){
        InputStream in = getResources().openRawResource(R.raw.provider_locations01);
        int record_num = 1;
        try{
            Reader reader = new InputStreamReader(in);
            Iterable<CSVRecord> records = CSVFormat.EXCEL.withHeader().parse(reader);
            String record_string = "";
            for (CSVRecord record : records)
            {
                record_string = "";
                for (int i = 0; i < record.size(); i++){
                    record_string += record.get(i).toString() + " ";
                    switch(i){
                        case 0:
                            servicesMap.put(record_num, record.get(i).toString());
                            break;
                        case 1:
                            firstNameMap.put(record_num, record.get(i).toString());
                            break;
                        case 2:
                            lastNameMap.put(record_num, record.get(i).toString());
                            break;
                        case 3:
                            agencyMap.put(record_num, record.get(i).toString());
                            break;
                        case 4:
                            titleMap.put(record_num, record.get(i).toString());
                            break;
                        case 5:
                            serviceTypeMap.put(record_num, record.get(i).toString());
                            break;
                        case 6:
                            populationMap.put(record_num, record.get(i).toString());
                            break;
                        case 7:
                            hoursOfOperationMap.put(record_num, record.get(i).toString());
                            break;
                        case 8:
                            addressMap.put(record_num, record.get(i).toString());
                            break;
                        case 9:
                            addressMap2.put(record_num, record.get(i).toString());
                            break;
                        case 10:
                            stateMap.put(record_num, record.get(i).toString());
                            break;
                        case 11:
                            cityMap.put(record_num, record.get(i).toString());
                            break;
                        case 12:
                            zipMap.put(record_num, record.get(i).toString());
                            break;
                        case 13:
                            phoneMap.put(record_num, record.get(i).toString());
                            break;
                        case 14:
                            emailMap.put(record_num, record.get(i).toString());
                            break;
                        case 15:
                            websiteMap.put(record_num, record.get(i).toString());
                            break;
                        default:
                            // do nothing because some headers are empty
                    }
                }
                //Log.d("csv record " + record_num, record_string);
                record_num++;
            }
            Log.d("record_num ", record_num+"");
        }catch(IOException e){
            Log.e("Parser Error","");
        }

        // test by displaying data
//        for (int i = 1; i < record_num; i++){
//            Log.d("record num " + i,
//                    servicesMap.get(i) + " " +
//                    firstNameMap.get(i) + " " +
//                    lastNameMap.get(i) + " " +
//                    agencyMap.get(i) + " " +
//                    titleMap.get(i) + " " +
//                    serviceTypeMap.get(i) + " " +
//                    populationMap.get(i) + " " +
//                    hoursOfOperationMap.get(i) + " " +
//                    addressMap.get(i) + " " +
//                    addressMap2.get(i) + " " +
//                    stateMap.get(i) + " " +
//                    cityMap.get(i) + " " +
//                    zipMap.get(i) + " " +
//                    phoneMap.get(i) + " " +
//                    emailMap.get(i) + " " +
//                    websiteMap.get(i)
//            );
//        }

    }

    private void mapCoordinates(){
        InputStream in = getResources().openRawResource(R.raw.provider_locations_coordinates);
        int record_num = 1;
        try{
            Reader reader = new InputStreamReader(in);
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader().parse(reader);
            Iterator<CSVRecord> iterator = records.iterator();
            String record_string = "";
            String lat = "";
            String lng = "";
//            CSVRecord record;
//            for (int i = 1; i < 10; i++){
//                if (iterator.hasNext()){
//                    record = iterator.next();
//                    lat = record.get("latitude");
//                    lng = record.get("longitude");
//                    if (!lat.equals("unknown") && !lng.equals("unknown")){
//
//                        LatLng coordinates = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
//                        Log.d("coordinates:", coordinates.toString());
//                        Log.d("agency", agencyMap.get(record_num));
//                        mMap.addMarker(new MarkerOptions().position(coordinates).title(agencyMap.get(record_num)));
//                    }
//                    record_num++;
//                }
//            }
            for (CSVRecord record : records) {
                lat = record.get("latitude");
                lng = record.get("longitude");
                if (!lat.equals("unknown") && !lng.equals("unknown")){

                    LatLng coordinates = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
                    mMap.addMarker(new MarkerOptions().position(coordinates).title(agencyMap.get(record_num) +
                        "\n" + addressMap.get(record_num) +
                        "\n" + phoneMap.get(record_num)));
                }
                record_num++;
            }
            Log.d("record_num ", record_num+"");
        }catch(IOException e){
            Log.e("Parser Error","");
        }
    }
}
