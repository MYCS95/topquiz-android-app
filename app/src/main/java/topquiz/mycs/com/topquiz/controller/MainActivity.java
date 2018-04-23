package topquiz.mycs.com.topquiz.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import model.User;
import topquiz.mycs.com.topquiz.R;

public class MainActivity extends AppCompatActivity {

    private TextView mBienvenueText;
    private EditText mPrenomInput;
    private Button mJouerBtn;

    private User mUser;

    private static final int GAME_ACTIVITY_REQUEST_CODE = 42;
    private static final String PREF_KEY_SCORE = "PREF_KEY_SCORE";
    private static final String PREF_KEY_FIRSTNAME = "PREF_KEY_FIRSTNAME";

    private SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        System.out.println("MainActivity::onCreate()");

        mPreferences = getPreferences(MODE_PRIVATE);
        mUser = new User();

        // Branchement des widgets et des variables
        mBienvenueText = findViewById(R.id.activity_main_bienvenue_text);
        mPrenomInput = findViewById(R.id.activity_main_prenom_input);
        mJouerBtn = findViewById(R.id.activity_main_jouer_btn);

        greetUser();
        mJouerBtn.setEnabled(false);

        mPrenomInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mJouerBtn.setEnabled(s.toString().length() != 0);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mJouerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // On sauvegarde le nom de l'utilisateur dans le modèle
                mUser.setFirstName(mPrenomInput.getText().toString());

                mPreferences.edit().putString(PREF_KEY_FIRSTNAME, mUser.getFirstName()).apply();

                Intent gameActivity = new Intent(MainActivity.this, GameActivity.class);
                startActivityForResult(gameActivity, GAME_ACTIVITY_REQUEST_CODE);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (GAME_ACTIVITY_REQUEST_CODE == requestCode && RESULT_OK == resultCode) {

            // Fetch the score from the Intent
            int score = data.getIntExtra(GameActivity.BUNDLE_EXTRA_SCORE, 0);
            mPreferences.edit().putInt(PREF_KEY_SCORE, score).apply();
            greetUser();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("MainActivity::onStart()");
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        System.out.println("MainActivity::onPostResume()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("MainActivity::onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("MainActivity::onPause()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("MainActivity::onDestroy()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("MainActivity::onStop()");
    }

    public void greetUser() {
        // Gestion de la session précédente
        String firstname = mPreferences.getString(PREF_KEY_FIRSTNAME, null);

        if(firstname != null) {
            // Ce n'est pas le premier lancement de l'application

            int score = mPreferences.getInt(PREF_KEY_SCORE, 0);

            String fulltext = "Welcome back, " + firstname
                    + "!\nYour last score was " + score
                    + ", will you do better this time?";
            mBienvenueText.setText(fulltext);
            mPrenomInput.setText(firstname);
            mPrenomInput.setSelection(firstname.length());
            mJouerBtn.setEnabled(true);
        }
    }
}
