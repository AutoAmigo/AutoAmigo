package schnauzer.digital.autoamigo;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class RideActivity extends AppCompatActivity implements DialogInterface.OnCancelListener, View.OnClickListener, OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private static final int RIDE_MAP_REQUEST = 10;
    private GoogleMap mMap;

    private Dialog daysDialog;
    private Boolean raiteFlag;

    private Intent intent;
    private Ride ride;


    private EditText rideNameEditText;
    private Button editMapButton;
    private View departTimeButton;
    private View arriveTimeButton;
    private View daysButton;
    private TextView departTimeText;
    private TextView arriveTimeText;
    private TextView monday;
    private TextView tuesday;
    private TextView wednesday;
    private TextView thursday;
    private TextView friday;
    private TextView saturday;
    private TextView sunday;

    //NAVIGATION VIEWS
    Button myProfileButton;

    //RIDE data
    private String rideName;
    private boolean[] days = new boolean[7];
    private int departHour;
    private int departMinutes;
    private int arriveHour;
    private int arriveMinutes;
    private boolean arrivePm;
    private boolean departPm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        intent = getIntent();
        ride = (Ride) intent.getSerializableExtra("ride");
        raiteFlag = (Boolean) intent.getBooleanExtra("conductor",false);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.rideMap);
        mapFragment.getMapAsync(this);

        if (ride == null)
            ride = new Ride();
        days = ride.getDays();

        myProfileButton = (Button) findViewById(R.id.myProfileButton);
        rideNameEditText = (EditText) findViewById(R.id.rideNameEditText);
        editMapButton = (Button) findViewById(R.id.editMapButton);
        departTimeButton = findViewById(R.id.departTimeButton);
        arriveTimeButton = findViewById(R.id.arriveTimeButton);
        daysButton = findViewById(R.id.daysButton);
        departTimeText = (TextView) findViewById(R.id.departTimeText);
        arriveTimeText = (TextView) findViewById(R.id.arriveTimeText);
        monday = (TextView) findViewById(R.id.monday);
        tuesday = (TextView) findViewById(R.id.tuesday);
        wednesday = (TextView) findViewById(R.id.wednesday);
        thursday = (TextView) findViewById(R.id.thursday);
        friday = (TextView) findViewById(R.id.friday);
        saturday = (TextView) findViewById(R.id.saturday);
        sunday = (TextView) findViewById(R.id.sunday);

        if (ride.getName().length()>0)
            rideNameEditText.setText(ride.getName());

        myProfileButton.setOnClickListener(this);
        editMapButton.setOnClickListener(this);
        departTimeButton.setOnClickListener(this);
        arriveTimeButton.setOnClickListener(this);
        daysButton.setOnClickListener(this);
    }

    @Override
    protected void onStart () {
        super.onStart();

        setHoursDisplay();
        setDaysDisplay();
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
                ride.setName(rideNameEditText.getText().toString());
                intent.putExtra("ride", ride);
                setResult(RESULT_OK, intent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void daysDialog (){
        daysDialog = new Dialog(this);

        if (ride == null)
            return;

        daysDialog.setContentView(R.layout.dialog_days);
        CheckBox monday = (CheckBox)daysDialog.findViewById(R.id.checkbox_monday);
        CheckBox tuesday = (CheckBox)daysDialog.findViewById(R.id.checkbox_tuesday);
        CheckBox wednesday = (CheckBox)daysDialog.findViewById(R.id.checkbox_wednesday);
        CheckBox thursday = (CheckBox)daysDialog.findViewById(R.id.checkbox_thursday);
        CheckBox friday = (CheckBox)daysDialog.findViewById(R.id.checkbox_friday);
        CheckBox saturday = (CheckBox)daysDialog.findViewById(R.id.checkbox_saturday);
        CheckBox sunday = (CheckBox)daysDialog.findViewById(R.id.checkbox_sunday);

        View.OnClickListener checkBoxListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCheckboxClicked(v);
            }
        };
        monday.setOnClickListener(checkBoxListener);
        tuesday.setOnClickListener(checkBoxListener);
        wednesday.setOnClickListener(checkBoxListener);
        thursday.setOnClickListener(checkBoxListener);
        friday.setOnClickListener(checkBoxListener);
        saturday.setOnClickListener(checkBoxListener);
        sunday.setOnClickListener(checkBoxListener);

        Button cancel = (Button) daysDialog.findViewById(R.id.cancelButton);
        Button accept = (Button) daysDialog.findViewById(R.id.acceptButton);
        View.OnClickListener buttonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDialogButtonClicked(v);
            }
        };
        cancel.setOnClickListener(buttonListener);
        accept.setOnClickListener(buttonListener);

        daysDialog.setTitle(R.string.days);
        daysDialog.setOnCancelListener(this);

        daysDialog.show();

        monday.setChecked(days[0]);
        tuesday.setChecked(days[1]);
        wednesday.setChecked(days[2]);
        thursday.setChecked(days[3]);
        friday.setChecked(days[4]);
        saturday.setChecked(days[5]);
        sunday.setChecked(days[6]);
    }

    public void onCheckboxClicked (View view){
        boolean checked = ((CheckBox) view).isChecked();

        switch(view.getId()) {
            case R.id.checkbox_monday:
                if (checked)
                    days[0] = true;
                else
                    days[0] = false;
                    break;
            case R.id.checkbox_tuesday:
                if (checked)
                    days[1] = true;
                else
                    days[1] = false;
                break;
            case R.id.checkbox_wednesday:
                if (checked)
                    days[2] = true;
                else
                    days[2] = false;
                break;
            case R.id.checkbox_thursday:
                if (checked)
                    days[3] = true;
                else
                    days[3] = false;
                break;
            case R.id.checkbox_friday:
                if (checked)
                    days[4] = true;
                else
                    days[4] = false;
                break;
            case R.id.checkbox_saturday:
                if (checked)
                    days[5] = true;
                else
                    days[5] = false;
                break;
            case R.id.checkbox_sunday:
                if (checked)
                    days[6] = true;
                else
                    days[6] = false;
                break;
        }
    }

    public void onDialogButtonClicked(View view){
        switch(view.getId()) {
            case R.id.acceptButton:
                ride.setDays(days);
                setDaysDisplay();
                break;
            case R.id.cancelButton:
                days = ride.getDays();
                break;
        }
        daysDialog.dismiss();

    }

    @Override
    public void onCancel(DialogInterface dialog) {
        days = ride.getDays();
    }

    private void setHoursDisplay() {
        departTimeText.setText(ride.getDepartTime());
        arriveTimeText.setText(ride.getArriveTime());
    }

    private void setDaysDisplay() {
        final String ACTIVE_COLOR = "#FF3FB541";
        final String INACTIVE_COLOR = "#000000";

        if (ride.getDays()[0]) {monday.setTextColor(Color.parseColor(ACTIVE_COLOR)); monday.setTypeface(Typeface.DEFAULT_BOLD);} else {monday.setTextColor(Color.parseColor(INACTIVE_COLOR)); monday.setTypeface(Typeface.DEFAULT);}
        if (ride.getDays()[1]) {tuesday.setTextColor(Color.parseColor(ACTIVE_COLOR)); tuesday.setTypeface(Typeface.DEFAULT_BOLD);} else {tuesday.setTextColor(Color.parseColor(INACTIVE_COLOR)); tuesday.setTypeface(Typeface.DEFAULT);}
        if (ride.getDays()[2]) {wednesday.setTextColor(Color.parseColor(ACTIVE_COLOR)); wednesday.setTypeface(Typeface.DEFAULT_BOLD);} else {wednesday.setTextColor(Color.parseColor(INACTIVE_COLOR)); wednesday.setTypeface(Typeface.DEFAULT);}
        if (ride.getDays()[3]) {thursday.setTextColor(Color.parseColor(ACTIVE_COLOR)); thursday.setTypeface(Typeface.DEFAULT_BOLD);} else {thursday.setTextColor(Color.parseColor(INACTIVE_COLOR)); thursday.setTypeface(Typeface.DEFAULT);}
        if (ride.getDays()[4]) {friday.setTextColor(Color.parseColor(ACTIVE_COLOR)); friday.setTypeface(Typeface.DEFAULT_BOLD);} else {friday.setTextColor(Color.parseColor(INACTIVE_COLOR)); friday.setTypeface(Typeface.DEFAULT);}
        if (ride.getDays()[5]) {saturday.setTextColor(Color.parseColor(ACTIVE_COLOR)); saturday.setTypeface(Typeface.DEFAULT_BOLD);} else {saturday.setTextColor(Color.parseColor(INACTIVE_COLOR)); saturday.setTypeface(Typeface.DEFAULT);}
        if (ride.getDays()[6]) {sunday.setTextColor(Color.parseColor(ACTIVE_COLOR)); sunday.setTypeface(Typeface.DEFAULT_BOLD);} else {sunday.setTextColor(Color.parseColor(INACTIVE_COLOR)); sunday.setTypeface(Typeface.DEFAULT);}
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.myProfileButton:
                intent = new Intent(this, UserActivity.class);
                startActivity(intent);
                break;
            case R.id.editMapButton:
                intent = new Intent(this, MapsActivity.class);
                intent.putExtra("points", ride.getPoints());
                intent.putExtra("bounds", ride.getBounds());
                startActivityForResult(intent, RIDE_MAP_REQUEST);
                break;
            case R.id.departTimeButton:
                break;
            case R.id.arriveTimeButton:
                break;
            case R.id.daysButton:
                daysDialog();
                break;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng defaultPoint = new LatLng(31.854842158455835,-116.60555005073549);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultPoint, 14));

        drawPoints(ride.getPoints());
        mMap.setOnMapClickListener(this);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        Log.wtf(this.getLocalClassName(), "Map clicked");
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("points", ride.getPoints());
        intent.putExtra("bounds", ride.getBounds());
        if(raiteFlag==true){
            intent.putExtra("conductor",true);
        }else{
            intent.putExtra("conductor",false);
        }
        startActivityForResult(intent, RIDE_MAP_REQUEST);
    }

    public void drawPoints (ArrayList<LatLng> points) {
        if (points.size()>0) {
            mMap.addMarker(new MarkerOptions().position(points.get(0)).title(getResources().getString(R.string.start)));
        }
                if (points.size() < 2)
                    return;
            if (raiteFlag == false) {
                for (int i = 1; i < points.size(); i++)
                    mMap.addPolyline((new PolylineOptions()).add(points.get(i - 1), points.get(i)).width(5).color(Color.BLUE).geodesic(true));
            }
                mMap.addMarker(new MarkerOptions().position(points.get(points.size() - 1)).title(getResources().getString(R.string.destiny)));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RIDE_MAP_REQUEST) {
            if (resultCode == RESULT_OK) {
                mMap.clear();
                ArrayList<LatLng> points = ((ArrayList<LatLng>) data.getSerializableExtra("points"));
                ride.setPoints(points);
                drawPoints(ride.getPoints());
                ArrayList<LatLng> bounds = ((ArrayList<LatLng>) data.getSerializableExtra("bounds"));
                if (points.size()>0) {
                    ride.setBounds(bounds);
                    mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(new LatLngBounds(
                            bounds.get(0),
                            bounds.get(1)
                    ), 150));
                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(RideActivity.this, "No route returned", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
