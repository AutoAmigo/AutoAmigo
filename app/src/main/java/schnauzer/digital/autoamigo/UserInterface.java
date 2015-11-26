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

    private ListView rideListView;
    private ArrayList<Ride> userRides;

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
        tabSpec.setIndicator("Viajes");
        tabHost.addTab(tabSpec);


        reglas = (Button) findViewById(R.id.reglasBtn);
        rideListView = (ListView) findViewById(R.id.rideListView2);

        // Listeners
        myProfileButton.setOnClickListener(this);
        reglas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reglasDialog();
            }
        });

        setUser(user);
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
        userNameText.setText(user.getName());

        userRides = user.getRides();

        if (!user.getRides().isEmpty()) {
            RideAdapter rideAdapter = new RideAdapter(this, R.layout.item_ride, userRides);

            rideListView.setAdapter(rideAdapter);

            rideListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String item = userRides.get(position).getName();

                    Toast.makeText(getBaseContext(), item, Toast.LENGTH_SHORT).show();
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

}
