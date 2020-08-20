package fr.pcmaintenance.healthy;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import fr.pcmaintenance.healthy.Helper.DatabaseHelper;
import fr.pcmaintenance.healthy.Modele.User;

public class LoginActivity extends Activity {

    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DatabaseHelper(getApplicationContext());
        setContentView(R.layout.activity_login);
    }

    public void createUser(View view) {
        EditText name = findViewById(R.id.pseudo);
        EditText jour = findViewById(R.id.birthdayJour);
        EditText mois = findViewById(R.id.birthdayMois);
        EditText annee = findViewById(R.id.birthdayAnnee);
        Switch sexe = findViewById(R.id.sexe);
        EditText taille = findViewById(R.id.taille);
        EditText poids = findViewById(R.id.poids);
        Spinner activitySpinner = findViewById(R.id.activity);
        Spinner objectifSpinner = findViewById(R.id.objectif);


        if (name.getText().equals("") || jour.getText().equals("") || mois.getText().equals("") || annee.getText().equals("") || taille.getText().equals("")
                || poids.getText().equals("") || activitySpinner.getSelectedItemPosition() == 0 || objectifSpinner.getSelectedItemPosition() == 0){

            Toast toast1 = Toast.makeText(getApplicationContext(),"Merci de remplir tout les champs.",Toast.LENGTH_SHORT);
            toast1.show();
        }else{
            int j,m,a;
            j = Integer.parseInt(jour.getText().toString());
            m = Integer.parseInt(mois.getText().toString());
            a = Integer.parseInt(annee.getText().toString());
            if (j <= 0 || j > 31 || m <=0 || m > 12 || a <= 1900){
                Toast toast2 = Toast.makeText(getApplicationContext(),"Merci de renter une date valide.",Toast.LENGTH_SHORT);
                toast2.show();
            }else{
                User user = new User();
                user.setName(name.getText().toString());
                user.setSexe(sexe.isChecked());
                user.setPoids(Float.parseFloat(poids.getText().toString()));
                user.setTaille(Integer.parseInt(taille.getText().toString()));
                user.setActivité(activitySpinner.getSelectedItemPosition());
                user.setObjectif(objectifSpinner.getSelectedItemPosition());
                String date = "" + a + "-" + m + "-" + j;
                user.setBirthday(date);
                db.addUser(user);
                finish();
            }
        }
    }

    @Override
    public void onBackPressed(){
        return;
    }
}