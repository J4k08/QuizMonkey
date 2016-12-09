package com.example.jakobhaglof.quizapp;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {

    private final static String TAG = "GAME_ACTIVITY: ";
    private DBHelper db;
    private int rndNumber;
    private int playerScore = 0;
    private Player player;
    private String pName = "";
    private String isFromMenu = "Yes";
    private ArrayList<String> clickedCat;
    private ArrayList<Question> gameQuestions;
    private TextView que;
    private Button btn1, btn2, btn3, btn4;
    private Game game;
    private int timer;
    private TextView qTimer;
    private CountDownTimer countDownTimer;
    private Button btn;
    String guess = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        qTimer = (TextView) findViewById(R.id.setQuestionTime);

        db = new DBHelper(this);
        Intent i = getIntent();
        player = db.getPlayerFromDB(pName = i.getStringExtra("pName"));

        Log.d(TAG, player.getName());
        clickedCat = i.getStringArrayListExtra("clickedCat");

        game = new Game(this, 10000, clickedCat, player);
        gameQuestions = game.prepGame(clickedCat);
        rndNumber = 0;


        showQuestions();
        timer = game.getTimer();
        playGame(gameQuestions, game);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        MenuItem M1 = menu.getItem(0);

        M1.setTitle(player.getName());

        MenuItem M2 = menu.getItem(1);

        M2.setIcon(player.getMonkeyID());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.toolbarMonkey){
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
        }
        if (id == R.id.toolbarpName) {
            Intent intent = new Intent(this, PersonalProfileActivity.class);
            intent.putExtra("isFromMenu", isFromMenu);
            intent.putExtra("pName", pName);
            startActivity(intent);
        }
        if (id == R.id.settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            intent.putExtra("pName", pName);
            startActivity(intent);
        }
        if (id == R.id.quitApp) {
            this.finishAffinity();
        }

        return super.onOptionsItemSelected(item);
    }

    public void playGame(ArrayList<Question> questions, Game game) {

        timer = 11000;
        startTimer();

        que.setText(questions.get(rndNumber).getQuestion());
        btn1.setText(questions.get(rndNumber).getChoice1());
        btn2.setText(questions.get(rndNumber).getChoice2());
        btn3.setText(questions.get(rndNumber).getChoice3());
        btn4.setText(questions.get(rndNumber).getChoice4());

    }

    public void categoryBoxes(View view) {

        btn = (Button) view;
        guess = btn.getText().toString();

        playerScore += game.roundGuess(guess, gameQuestions.get(rndNumber), timer);
        btn.setEnabled(false);

        if(guess.equals(gameQuestions.get(rndNumber).getCorrectAnswer())) {
            btn.setBackgroundResource(R.drawable.btngreen);
        }
        else{
            btn.setBackgroundResource(R.drawable.btnred);
        }
        guessColor();

    }

    public void showQuestions() {
        que = (TextView) findViewById(R.id.setQuestion);
        btn1 = (Button) findViewById(R.id.btnChoice1);
        btn2 = (Button) findViewById(R.id.btnChoice2);
        btn3 = (Button) findViewById(R.id.btnChoice3);
        btn4 = (Button) findViewById(R.id.btnChoice4);
    }

    private void startTimer() {

        Log.d(TAG, "startTimer: " + timer);

        countDownTimer = new CountDownTimer(timer, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {

                qTimer.setText("" + millisUntilFinished / 1000);
                Log.d(TAG, "onTick: " + millisUntilFinished / 1000);
                timer = (int) millisUntilFinished;

            }

            public void onFinish() {

                qTimer.setText("0");
                timeOutMsg();
                //Handler
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "onFinish: " + qTimer);
                        rndNumber++;
                        playGame(gameQuestions, game);
                    }
                }, 2000);
            }

        }.start();
    }

    public void timeOutMsg() {

        Log.d(TAG, "timeOutMsg: Detta skrivs ut i onFinished");
        Toast.makeText(this, "Tiden tog slut!", Toast.LENGTH_SHORT).show();

    }

    public void guessColor(){

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                btn.setBackgroundResource(R.drawable.btn);
                rndNumber++;
                countDownTimer.cancel();
                btn.setEnabled(true);

                if(rndNumber == 10) {

                    Intent i = new Intent(GameActivity.this, PostGameActivity.class);

                    if(playerScore > player.getHighScore()) {
                        db.updateHighScore(playerScore, pName);
                        Log.d(TAG, "Player highscore: är högre och sparas!");
                    }
                    i.putExtra("pName", pName); i.putExtra("playerScore", playerScore);
                    i.putStringArrayListExtra("clickedCat", clickedCat);

                    startActivity(i);
                } else {
                    playGame(gameQuestions, game);
                }
            }
        },2000);
    }

}