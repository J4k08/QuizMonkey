package com.example.jakobhaglof.quizapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    List<Player> playersList;
    ArrayAdapter adapter;
    ArrayList<String> playerNames;
    DBHelper db;
    EditText et;
    Button ib;
    private final static String TAG = "PROFILE_ACTIVITY: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        db = new DBHelper(this);

        playerNames = getNameOfPlayer();
        listProfiles(playerNames);

    }

    public void sendToGamesettings(View view) {

        Intent intent = new Intent(this,GameSettingsActivity.class);
        startActivity(intent);
    }

    public ArrayList<String> getNameOfPlayer() {

        playersList = db.getAllPlayers();

        ArrayList<String> playerNames = new ArrayList<String>();

        for(int i = 0; i < playersList.size(); i++) {

            playerNames.add(playersList.get(i).getName());
        }

        return playerNames;

    }

    public void saveProfile(View view) {

        et = (EditText)findViewById(R.id.add_name);

        String name = et.getText().toString();
        db = new DBHelper(getApplicationContext());

        Log.d(TAG, name);

        Player p1 = new Player(0, name, 0);

        db.addPlayer(p1);
        playerNames = getNameOfPlayer();

        listProfiles(playerNames);

    }

    public void listProfiles(ArrayList<String> playerNames) {

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, playerNames);

        ListView lw = (ListView)findViewById(R.id.item_list);

        lw.setAdapter(adapter);

    }


    public void sendToMain(View view) {

        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);
    }

    public void saveMonkey(View view) {

        if (view.equals(findViewById(R.id.monkey1))){
            ib = (Button) findViewById(R.id.monkey1);
        }
        else if (view.equals(findViewById(R.id.monkey2))){
            ib = (Button) findViewById(R.id.monkey2);
        }
        else if (view.equals(findViewById(R.id.monkey3))){
            ib = (Button) findViewById(R.id.monkey3);
        }
    }

}
