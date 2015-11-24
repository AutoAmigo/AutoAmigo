package schnauzer.digital.autoamigo;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TabHost;

import java.util.ArrayList;

/**
 * Created by Erik on 11/23/2015.
 */
public class UserInterface extends AppCompatActivity {
    Button reglas;
    final String logTag ="UserInterface";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_interface);

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
        reglas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reglasDialog();
            }
        });

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




}
