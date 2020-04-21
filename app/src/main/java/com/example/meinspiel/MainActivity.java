package com.example.meinspiel;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //android:background="@drawable/packers_bg"
    ImageView bg_away, bg_home;

    ImageView nfl100Logo, backgroundCorrect, backgroundWrong, backgroundSkip;
    ImageButton imageButtonAway, imageButtonHome;
    TextView textViewAway, textViewHome, textViewDate, textViewAwayscore, textViewColon, textViewHomescore, textViewAt;
    Button skipButton;

    LottieAnimationView av_correct;
    LottieAnimationView av_wrong;
    LottieAnimationView av_skip;

    final String databaseName = "NFL.db";
    final String databaseTableName = "season2019";
    final String prefNameFirstStart = "firstAppStart";
    final ArrayList<String> allUsedGames = new ArrayList<String>(256);
    final String prefGame = "currantGame";
    final int maxLevel = 2;

    String date, romannumber, hometeam, awayteam, homescore, awayscore;
    String currantGame;

    int numberOfPlayedGames;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewDate = findViewById(R.id.textViewDate);
        imageButtonAway = findViewById(R.id.imageButtonAway);
        imageButtonHome = findViewById(R.id.imageButtonHome);
        bg_away = findViewById(R.id.bg_away);
        bg_home = findViewById(R.id.bg_home);
        textViewAway = findViewById(R.id.textViewAway);
        textViewHome = findViewById(R.id.textViewHome);
        //textViewAwayscore = findViewById(R.id.textViewAwayscore);
        //textViewColon = findViewById(R.id.textViewColon);
        //textViewHomescore = findViewById(R.id.textViewHomescore);
        skipButton = findViewById(R.id.skipButton);
        //textViewAt = findViewById(R.id.textViewAt);
        //textViewTestausgabe = findViewById(R.id.textViewTestausgabe);
        //av_correct = findViewById(R.id.correct);
        //av_wrong = findViewById(R.id.wrong);
        //av_skip = findViewById(R.id.skip);
        //backgroundCorrect = findViewById(R.id.backgroundCorrect);
        //backgroundWrong = findViewById(R.id.backgroundWrong);
        //backgroundSkip = findViewById(R.id.backgroundSkip);

        //bears_bg = findViewById(R.id.bears_bg);
        //packers_bg = findViewById(R.id.packers_bg);
        //panthers_bg = findViewById(R.id.panthers_bg);
        //rams_bg = findViewById(R.id.rams_bg);

        imageButtonAway.setOnClickListener(this);
        imageButtonHome.setOnClickListener(this);
        skipButton.setOnClickListener(this);


        firstAppStart(); //createDatabase() inside
        testEinsetzen(); //setAllUsedGames() inside
        //safeLevel();
    }


    public void firstAppStart()  {
        SharedPreferences preferences = getSharedPreferences(prefNameFirstStart, MODE_PRIVATE);
        boolean fas = preferences.getBoolean(prefNameFirstStart, true);
        if(fas){
            createDatabase();
        }
    }


    //Test

    public void createDatabase(){
        SQLiteDatabase database = openOrCreateDatabase(databaseName, MODE_PRIVATE, null);
        //database.execSQL("DROP TABLE " + databaseTableName + " IF EXISTS;");
        database.execSQL("CREATE TABLE " + databaseTableName + " (id INTEGER PRIMARY KEY, date TEXT, awayteam TEXT, hometeam TEXT, awayscore TEXT, homescore TEXT)");
        database.execSQL("INSERT INTO " + databaseTableName + " VALUES('1','15. Jänner 1967','I','Packers','Chiefs','35','10')");
        database.execSQL("INSERT INTO " + databaseTableName + " VALUES('2','14. Jänner 1968','II','Packers','Raiders','33','14')");




        database.close();

        SharedPreferences preferences = getSharedPreferences(prefNameFirstStart, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(prefNameFirstStart, false);
        editor.apply();
    }

    public void testEinsetzen(){
        //nfl100Logo.setVisibility(View.VISIBLE);
        textViewDate.setVisibility(View.VISIBLE);
        imageButtonAway.setVisibility(View.VISIBLE);
        imageButtonHome.setVisibility(View.VISIBLE);
        textViewAway.setVisibility(View.VISIBLE);
        textViewHome.setVisibility(View.VISIBLE);

        //textViewAwayscore.setVisibility(View.INVISIBLE);
        //textViewColon.setVisibility(View.INVISIBLE);
        //textViewHomescore.setVisibility(View.INVISIBLE);

        skipButton.setVisibility(View.VISIBLE);
        //textViewAt.setVisibility(View.VISIBLE);

        //av_correct.setVisibility(View.INVISIBLE);
        //av_wrong.setVisibility(View.INVISIBLE);
        //av_skip.setVisibility(View.INVISIBLE);

        //backgroundCorrect.setVisibility(View.INVISIBLE);
        //backgroundWrong.setVisibility(View.INVISIBLE);
        //backgroundSkip.setVisibility(View.INVISIBLE);


        Random rand = new Random();
        int x = rand.nextInt(2);
        x++;

        if(numberOfPlayedGames<2) {
            while (allUsedGames.contains(Integer.toString(x))) {
                x = rand.nextInt(2);
                x++;
            }
        }

        currantGame = Integer.toString(x);



        if(numberOfPlayedGames<2) {
            SQLiteDatabase database = openOrCreateDatabase(databaseName, MODE_PRIVATE, null);
            Cursor cursor = database.rawQuery("SELECT * FROM " + databaseTableName + " WHERE id = '" + /*String.valueOf(*/ currantGame + "'", null);
            cursor.moveToNext();

            date = cursor.getString(1);
            romannumber = cursor.getString(2);
            hometeam = cursor.getString(3);
            awayteam = cursor.getString(4);
            homescore = cursor.getString(5);
            awayscore = cursor.getString(6);

            textViewDate.setText(date);
            textViewHome.setText(hometeam);
            textViewAway.setText(awayteam);
            //textViewHomescore.setText(homescore);
            //textViewAwayscore.setText(awayscore);

            //textViewAwayscore.setTextSize(70);
            //textViewHomescore.setTextSize(70);


            setLogos();
            setBackgrounds();


            cursor.close();
            testAusgabe();
            //Toast.makeText(this, Integer.toString(x),Toast.LENGTH_LONG).show();

        }else {
            Toast.makeText(this,"DU hast ALLE spiele durch! Danke fürs Spielen! :)",Toast.LENGTH_LONG).show();
            //testEinsetzen(); // muss dann weg
        }
    }


/*
    public void safeLevel(){
        SharedPreferences preferencesLevel = getSharedPreferences(prefGame, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencesLevel.edit();

        editor.putString(prefGame, currantGame);
        editor.apply();
    }
 */

    public void levelDoneAnimationCorrect(){
        backgroundCorrect.setVisibility(View.VISIBLE);

 /*       if(backgroundCorrect.getVisibility() == View.INVISIBLE){
            backgroundCorrect.setVisibility(View.VISIBLE);
        }
        if(backgroundWrong.getVisibility() == View.VISIBLE){
            backgroundWrong.setVisibility(View.INVISIBLE);
        }
*/
        nfl100Logo.setVisibility(View.INVISIBLE);
        textViewDate.setVisibility(View.INVISIBLE);
        imageButtonAway.setVisibility(View.INVISIBLE);
        imageButtonHome.setVisibility(View.INVISIBLE);
        //textViewAwayscore.setVisibility(View.INVISIBLE);
        //textViewColon.setVisibility(View.INVISIBLE);
        //textViewHomescore.setVisibility(View.INVISIBLE);
        textViewAway.setVisibility(View.INVISIBLE);
        textViewHome.setVisibility(View.INVISIBLE);
        skipButton.setVisibility(View.INVISIBLE);
        textViewAt.setVisibility(View.INVISIBLE);

        av_correct.setVisibility(View.VISIBLE);
        av_correct.playAnimation();


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                testEinsetzen();
            }
        }, 2000);
    }

    public void levelDoneAnimationWrong(){
        backgroundWrong.setVisibility(View.VISIBLE);

 /*       if(backgroundCorrect.getVisibility() == View.VISIBLE){
            backgroundCorrect.setVisibility(View.INVISIBLE);
        }
        if(backgroundWrong.getVisibility() == View.INVISIBLE){
            backgroundWrong.setVisibility(View.VISIBLE);
        }
*/
        nfl100Logo.setVisibility(View.INVISIBLE);
        textViewDate.setVisibility(View.INVISIBLE);
        imageButtonAway.setVisibility(View.INVISIBLE);
        imageButtonHome.setVisibility(View.INVISIBLE);
        //textViewAwayscore.setVisibility(View.INVISIBLE);
        //textViewColon.setVisibility(View.INVISIBLE);
        //textViewHomescore.setVisibility(View.INVISIBLE);
        textViewAway.setVisibility(View.INVISIBLE);
        textViewHome.setVisibility(View.INVISIBLE);
        skipButton.setVisibility(View.INVISIBLE);
        textViewAt.setVisibility(View.INVISIBLE);

        av_wrong.setVisibility(View.VISIBLE);
        av_wrong.playAnimation();


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                testEinsetzen();
            }
        }, 2000);
    }

    public void levelDoneAnimationSkip(){
        backgroundSkip.setVisibility(View.VISIBLE);

        nfl100Logo.setVisibility(View.INVISIBLE);
        textViewDate.setVisibility(View.INVISIBLE);
        imageButtonAway.setVisibility(View.INVISIBLE);
        imageButtonHome.setVisibility(View.INVISIBLE);
        textViewAwayscore.setVisibility(View.INVISIBLE);
        textViewColon.setVisibility(View.INVISIBLE);
        textViewHomescore.setVisibility(View.INVISIBLE);
        textViewAway.setVisibility(View.INVISIBLE);
        textViewHome.setVisibility(View.INVISIBLE);
        skipButton.setVisibility(View.INVISIBLE);
        textViewAt.setVisibility(View.INVISIBLE);

        av_skip.setVisibility(View.VISIBLE);
        av_skip.playAnimation();


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                testEinsetzen();
            }
        }, 2000);
    }


    public void setLogos(){
        String teamnameAway = awayteam.toLowerCase() + "_logo";
        int logoID1 = getResources().getIdentifier(teamnameAway, "drawable", getPackageName());
        imageButtonAway.setImageResource(logoID1);

        String teamnameHome = hometeam.toLowerCase() + "_logo";
        int logoID2 = getResources().getIdentifier(teamnameHome, "drawable", getPackageName());
        imageButtonHome.setImageResource(logoID2);
    }

    public void setBackgrounds(){
        String teamnameAway = awayteam.toLowerCase() + "_bg";
        int logoID1 = getResources().getIdentifier(teamnameAway, "drawable", getPackageName());
        bg_away.setImageResource(logoID1);

        String teamnameHome = hometeam.toLowerCase() + "_bg";
        int logoID2 = getResources().getIdentifier(teamnameHome, "drawable", getPackageName());
        bg_home.setImageResource(logoID2);
    }

    public void makeScoreVisible(String a, String h){
        textViewAwayscore.setTextColor(Color.WHITE);
        textViewHomescore.setTextColor(Color.WHITE);
        textViewColon.setTextColor(Color.WHITE);

        textViewAwayscore.setVisibility(View.VISIBLE);
        textViewColon.setVisibility(View.VISIBLE);
        textViewHomescore.setVisibility(View.VISIBLE);

        if(Integer.parseInt(a) > Integer.parseInt(h)){
            textViewAwayscore.setTextSize(85);
            textViewHomescore.setTextSize(60);
            //textViewAwayscore.setTextColor(12);
        }
        if(Integer.parseInt(a) < Integer.parseInt(h)) {
            textViewAwayscore.setTextSize(60);
            textViewHomescore.setTextSize(85);
            //textViewHomescore.setTextColor(12);
        }
    }




    public void testAusgabe(){
        new AlertDialog.Builder(this)
                .setTitle("Payed Level")
                .setMessage(allUsedGames.toString())
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create().show();

    }




    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imageButtonAway:
                if(Integer.parseInt(awayscore) > Integer.parseInt(homescore)){
                    //Toast.makeText(this,"RICHTIG!",Toast.LENGTH_LONG).show();
                    //makeScoreVisible(awayscore, homescore);
                    allUsedGames.add(currantGame);
                    numberOfPlayedGames = allUsedGames.size();
                    //levelDoneAnimationCorrect();
                    testEinsetzen(); //muss dann weg
                    break;
                }else{
                    //Toast.makeText(this,"FALSCH!",Toast.LENGTH_LONG).show();
                    //makeScoreVisible(awayscore, homescore);
                    allUsedGames.add(currantGame);
                    numberOfPlayedGames = allUsedGames.size();
                    //levelDoneAnimationWrong();
                    testEinsetzen(); //muss dann weg
                    break;
                }

            case R.id.imageButtonHome:
                if(Integer.parseInt(homescore) > Integer.parseInt(awayscore)){
                    //Toast.makeText(this,"RICHTIG!",Toast.LENGTH_LONG).show();
                    //makeScoreVisible(awayscore, homescore);
                    allUsedGames.add(currantGame);
                    numberOfPlayedGames = allUsedGames.size();
                    //levelDoneAnimationCorrect();
                    testEinsetzen(); //muss dann weg
                    break;
                }else{
                    //Toast.makeText(this,"FALSCH!",Toast.LENGTH_LONG).show();
                    //makeScoreVisible(awayscore, homescore);
                    allUsedGames.add(currantGame);
                    numberOfPlayedGames = allUsedGames.size();
                    //levelDoneAnimationWrong();
                    testEinsetzen(); //muss dann weg
                    break;
                }


            case R.id.skipButton:
                //levelDoneAnimationSkip();
                numberOfPlayedGames = allUsedGames.size();
                Toast.makeText(this,Integer.toString(numberOfPlayedGames),Toast.LENGTH_LONG).show();
                testEinsetzen(); // muss dann weg
                break;
        }
    }
}
