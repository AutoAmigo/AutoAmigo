package schnauzer.digital.autoamigo;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, View.OnClickListener {

    private GoogleMap mMap;
    private Intent intent;
    private boolean raiteFlag;

    private Button undoButton;
    private Button clearButton;

    private ArrayList<LatLng> points = new ArrayList<LatLng>();
    private double north = -85;
    private double east = -180;
    private double south = 85;
    private double west = 180;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        intent = getIntent();
        points = (ArrayList<LatLng>) intent.getSerializableExtra("points");
        raiteFlag = (Boolean) intent.getBooleanExtra("conductor", false);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        undoButton = (Button) findViewById(R.id.undoButton);
        clearButton = (Button) findViewById(R.id.clearButton);
        undoButton.setOnClickListener(this);
        clearButton.setOnClickListener(this);
        if(raiteFlag==true){
            View v = this.findViewById(android.R.id.content);
            undoButton.setVisibility(v.INVISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.rides_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.menuSaveButton:
                intent.putExtra("points", points);
                ArrayList<LatLng> bounds = new ArrayList<LatLng>();
                bounds.add(new LatLng(south, west));
                bounds.add(new LatLng(north, east));
                intent.putExtra("bounds", bounds);
                setResult(RESULT_OK, intent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
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
        LatLng defaultPoint = new LatLng(31.854842158455835,-116.60555005073549);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultPoint, 14));

        drawPoints(points);

        mMap.setOnMapClickListener(this);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        if (latLng.latitude < south)
            south = latLng.latitude;
        if (latLng.latitude > north)
            north = latLng.latitude;
        if (latLng.longitude < west)
            west = latLng.longitude;
        if (latLng.longitude > east)
            east = latLng.longitude;
        Log.wtf(this.getLocalClassName(), "Click on: "+latLng.toString());
        if(raiteFlag==false||(raiteFlag==true&&points.size()<2)){
            points.add(latLng);
        }else{
            Log.wtf("ASD","SALIEND");
            return;
        }

        int size = points.size();
        if (size<2) {
            mMap.addMarker(new MarkerOptions().position(points.get(0)).title(getResources().getString(R.string.start)));
            return;
        }
        if(raiteFlag==false) {
            mMap.addPolyline((new PolylineOptions()).add(points.get(size - 2), points.get(size - 1)).width(5).color(Color.BLUE).geodesic(true));
        }else{
            if(points.size()==2){
                mMap.addMarker(new MarkerOptions().position(points.get(1)).title(getResources().getString(R.string.destiny)));
            }
        }
    }

    public void drawPoints (ArrayList<LatLng> points) {
        if (points.size()>0)
            mMap.addMarker(new MarkerOptions().position(points.get(0)).title(getResources().getString(R.string.start)));
        if(raiteFlag==false) {
            for (int i = 1; i < points.size(); i++)
                mMap.addPolyline((new PolylineOptions()).add(points.get(i - 1), points.get(i)).width(5).color(Color.BLUE).geodesic(true));
        }else{
            if(points.size()==2){
                mMap.addMarker(new MarkerOptions().position(points.get(1)).title(getResources().getString(R.string.destiny)));
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.clearButton:
                mMap.clear();
                points.clear();
                break;
            case R.id.undoButton:
                mMap.clear();
                points.remove(points.size()-1);
                drawPoints(points);
                break;
        }
    }
}
