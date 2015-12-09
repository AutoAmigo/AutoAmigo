package schnauzer.digital.autoamigo;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Erik on 11/23/2015.
 */
public class UserInterface extends AppCompatActivity implements View.OnClickListener {

    User user=null;

    //NAVIGATION VIEWS
    Button myProfileButton;

    TextView userNameText;

    private ListView rideListView,rideListView2;
    private ArrayList<Ride> userRides;

    private static final int USER_RIDE_REQUEST = 1;
    private int ridePosition;

    Button reglas;
    final String logTag ="UserInterface";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_interface);

        myProfileButton = (Button) findViewById(R.id.myProfileButton);
        userNameText = (TextView) findViewById(R.id.userNameTextInterface);
        TabHost tabHost = (TabHost)findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("Perfil");
        tabSpec.setContent(R.id.perfil);
        tabSpec.setIndicator("Perfil");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("Viajes");
        tabSpec.setContent(R.id.viajes);
        tabSpec.setIndicator("Conductor");

        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("Viajes2");
        tabSpec.setContent(R.id.viajes2);
        tabSpec.setIndicator("Pasajero");
        tabHost.addTab(tabSpec);


        reglas = (Button) findViewById(R.id.reglasBtn);
        rideListView = (ListView) findViewById(R.id.rideListView2);
        rideListView2 = (ListView) findViewById(R.id.rideListView3);

        // Listeners
        myProfileButton.setOnClickListener(this);
        reglas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reglasDialog();
            }
        });

        // Testing
        user = new User("Diego Padilla", "Ensenada", 51, 3.8f, 3.76f, 4.2f, 4.0f, "Gatos", new ArrayList<Post>(), new ArrayList<Ride>());

        user.addPost(new Post("Diego Padilla", 2015, 10, 5, 13, 35, "Hoy no voy a poder dar raite"));
        user.addPost(new Post("Rogelio Gonzalez", 2015, 10, 5, 13, 45, "Vales roña, Diego."));
        user.addPost(new Post("Diego Padilla", 2015, 10, 14, 13, 12, "Adivinen que\nno raite today"));
        user.addPost(new Post("Diego Padilla", 2015, 10, 15, 15, 37, "Ya pues hoy si puedo"));
        user.addPost(new Post("Diego Padilla", 2015, 10, 18, 9, 40, "lluvia lluvia"));
        user.addPost(new Post("Diego Padilla", 2015, 11, 3, 3, 35, "ya me quiero eslipear"));
        user.addPost(new Post("Diego Padilla", 2015, 11, 11, 14, 10, "Lo siento chicos pero hoy no podre proporcionales el glorioso raite que se que se merecen"));
        user.addPost(new Post("Diego Padilla", 2015, 11, 17, 14, 24, "give me the rait to be raited"));

        user.addRide(new Ride("Regreso a casa", new boolean[]{false, true, false, true, false, false, false}, 6, 0, 6, 30, true, true));
        user.addRide(new Ride("Al CETYS", new boolean[] {false, false, false, false, false, true, false}, 3, 20, 3, 55, true, true));
        user.addRide(new Ride("Regreso a casa", new boolean[] {false, false, true, false, true, false, false}, 9, 0, 9, 30, true, true));
        user.addRide(new Ride("Regreso a casa", new boolean[] {false, true, false, true, false, false, false}, 7, 0, 7, 30, true, true));
        user.addRide(new Ride("Al CETYS", new boolean[]{false, true, true, true, true, false, false}, 4, 20, 4, 55, true, true));

        setUser(user);
    }

    @Override
    public void onStart () {
        super.onStart();

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
    }

    public void onCheckboxClicked (View view){
        boolean checked = ((CheckBox) view).isChecked();

        switch(view.getId()) {
            case R.id.checkbox_noComer:
                if (checked)
                    Log.d(logTag,"NoComer" );
                else
                    //sdfdsf
                break;
            case R.id.checkbox_noDormir:
                if (checked)
                    Log.d(logTag,"NoDormir" );
                else

                break;
            case R.id.checkbox_noMusica:
                if (checked)
                    Log.d(logTag,"NoMusica" );
                else

                break;
            case R.id.checkbox_noHablar:
                if (checked)
                    Log.d(logTag,"NoHablar" );
                else

                break;


        }

    }

    public void reglasDialog (){
        final Dialog dialogReglas = new Dialog(this);
        dialogReglas.setContentView(R.layout.dialog_reglas);
        CheckBox noComer = (CheckBox)dialogReglas.findViewById(R.id.checkbox_noComer);
        CheckBox noDormir = (CheckBox)dialogReglas.findViewById(R.id.checkbox_noDormir);
        CheckBox noMusica = (CheckBox)dialogReglas.findViewById(R.id.checkbox_noMusica);
        CheckBox noHablar = (CheckBox)dialogReglas.findViewById(R.id.checkbox_noHablar);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCheckboxClicked(v);
            }
        };
        noComer.setOnClickListener(listener);
        noDormir.setOnClickListener(listener);
        noMusica.setOnClickListener(listener);
        noHablar.setOnClickListener(listener);
        dialogReglas.setTitle("Reglas");

        dialogReglas.show();
    }

    private void setUser (User user) {
        if (user==null)
            return;


        userNameText.setText(user.getName());

        userRides = user.getRides();

        if (!user.getRides().isEmpty()) {
            RideAdapter rideAdapter = new RideAdapter(this, R.layout.item_ride, userRides);

            rideListView.setAdapter(rideAdapter);
            rideListView2.setAdapter(rideAdapter);

            rideListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ridePosition = position;
                    Intent intent;
                    String item = userRides.get(position).getName();
                    Toast.makeText(getBaseContext(), item, Toast.LENGTH_SHORT).show();
                    intent = new Intent(getBaseContext(), RideActivity.class);
                    intent.putExtra("ride", userRides.get(position));
                    intent.putExtra("conductor", false);
                    startActivityForResult(intent, USER_RIDE_REQUEST);
                }
            });

            rideListView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ridePosition = position;
                    Intent intent;
                    String item = userRides.get(position).getName();
                    Toast.makeText(getBaseContext(), item, Toast.LENGTH_SHORT).show();
                    intent = new Intent(getBaseContext(), RideActivity.class);
                    intent.putExtra("ride", userRides.get(position));
                    intent.putExtra("conductor", true);
                    startActivityForResult(intent, USER_RIDE_REQUEST);
                }
            });


        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.myProfileButton:
                intent = new Intent(this, UserActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == USER_RIDE_REQUEST) {
            if (resultCode == RESULT_OK) {
                userRides.set(ridePosition, (Ride) data.getSerializableExtra("ride"));
                Log.wtf(getLocalClassName(), "Ride position: " + ridePosition);
                Log.wtf(getLocalClassName(), "Retrieved ride: "+data.getSerializableExtra("ride"));
                Log.wtf(getLocalClassName(), "User rides: "+userRides);
                Log.wtf(getLocalClassName(), "User: "+user);
                if (user!=null)
                    user.setRides(userRides);
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(UserInterface.this, "No ride returned", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
