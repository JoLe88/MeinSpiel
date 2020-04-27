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

    ImageView nfl100Logo, backgroundCorrect, backgroundWrong, backgroundSkip, imageViewSuperBowl;
    ImageButton imageButtonAway, imageButtonHome;
    TextView textViewAway, textViewHome, textViewDate, textViewAwayscore, textViewColon, textViewHomescore, textViewAt, textViewSuperBowl;
    Button skipButton;

    LottieAnimationView av_correct;
    LottieAnimationView av_wrong;
    LottieAnimationView av_skip;

    final String databaseName = "NFL.db";
    final String databaseTableName = "season2019";
    final String prefNameFirstStart = "firstAppStart";
    final ArrayList<String> allUsedGames = new ArrayList<String>(256);
    final String prefGame = "currantGame";
    final int maxLevel = 52;

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
        imageViewSuperBowl = findViewById(R.id.imageViewSuperBowl);
        textViewSuperBowl = findViewById(R.id.textViewSuperBowl);
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

        //createDatabase();
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

    public void createDatabase(){
        SQLiteDatabase database = openOrCreateDatabase(databaseName, MODE_PRIVATE, null);
        //database.execSQL("DROP TABLE " + databaseTableName + " IF EXISTS;");
        database.execSQL("CREATE TABLE " + databaseTableName + " (id INTEGER PRIMARY KEY, date TEXT, romnnumber TEXT, awayteam TEXT, hometeam TEXT, awayscore TEXT, homescore TEXT)");
        database.execSQL("INSERT INTO " + databaseTableName + " VALUES('1','15. Jänner 1967','I','Packers','Chiefs','35','10')");
        database.execSQL("INSERT INTO " + databaseTableName + " VALUES('2','14. Jänner 1968','II','Packers','Raiders','33','14')");
        database.execSQL("INSERT INTO " + databaseTableName + " VALUES('3','12. Jänner 1969','III','Jets','Colts','16','7')");
        database.execSQL("INSERT INTO " + databaseTableName + " VALUES('4','11. Jänner 1970','IV','Chiefs','Vikings','23','7')");
        database.execSQL("INSERT INTO " + databaseTableName + " VALUES('5','17. Jänner 1971','V','Colts','Cowboys','16','13')");
        database.execSQL("INSERT INTO " + databaseTableName + " VALUES('6','16. Jänner 1972','VI','Cowboys','Dolphins','24','3')");
        database.execSQL("INSERT INTO " + databaseTableName + " VALUES('7','14. Jänner 1973','VII','Dolphins','Redskins','14','7')");
        database.execSQL("INSERT INTO " + databaseTableName + " VALUES('8','13. Jänner 1974','VIII','Dolphins','Vikings','24','7')");
        database.execSQL("INSERT INTO " + databaseTableName + " VALUES('9','12. Jänner 1975','IX','Steelers','Vikings','16','6')");
        database.execSQL("INSERT INTO " + databaseTableName + " VALUES('10','18. Jänner 1976','X','Steelers','Cowboys','21','17')");
        database.execSQL("INSERT INTO " + databaseTableName + " VALUES('11','9. Jänner 1977','XI','Raiders','Vikings','32','14')");
        database.execSQL("INSERT INTO " + databaseTableName + " VALUES('12','15. Jänner 1978','XII','Cowboys','Broncos','27','10')");
        database.execSQL("INSERT INTO " + databaseTableName + " VALUES('13','21. Jänner 1979','XIII','Steelers','Cowboys','35','31')");
        database.execSQL("INSERT INTO " + databaseTableName + " VALUES('14','20. Jänner 1980','XIV','Steelers','Rams','31','19')");
        database.execSQL("INSERT INTO " + databaseTableName + " VALUES('15','25. Jänner 1981','XV','Raiders','Eagles','27','10')");
        database.execSQL("INSERT INTO " + databaseTableName + " VALUES('16','24. Jänner 1982','XVI','Sf49ers','Bengals','26','21')");
        database.execSQL("INSERT INTO " + databaseTableName + " VALUES('17','30. Jänner 1983','XVII','Redskins','Dolphins','27','17')");
        database.execSQL("INSERT INTO " + databaseTableName + " VALUES('18','22. Jänner 1984','XVIII','Raiders','Redskins','38','9')");
        database.execSQL("INSERT INTO " + databaseTableName + " VALUES('19','20. Jänner 1985','XIX','Sf49ers','Dolphins','38','16')");
        database.execSQL("INSERT INTO " + databaseTableName + " VALUES('20','26. Jänner 1986','XX','Bears','Patriots','46','10')");
        database.execSQL("INSERT INTO " + databaseTableName + " VALUES('21','25. Jänner 1987','XXI','Giants','Broncos','39','20')");
        database.execSQL("INSERT INTO " + databaseTableName + " VALUES('22','31. Jänner 1988','XXII','Redskins','Broncos','42','10')");
        database.execSQL("INSERT INTO " + databaseTableName + " VALUES('23','22. Jänner 1989','XXIII','Sf49ers','Bengals','20','16')");
        database.execSQL("INSERT INTO " + databaseTableName + " VALUES('24','28. Jänner 1990','XXIV','Sf49ers','Broncos','55','10')");
        database.execSQL("INSERT INTO " + databaseTableName + " VALUES('25','27. Jänner 1991','XXV','Giants','Bills','20','19')");
        database.execSQL("INSERT INTO " + databaseTableName + " VALUES('26','26. Jänner 1992','XXVI','Redskins','Bills','37','24')");
        database.execSQL("INSERT INTO " + databaseTableName + " VALUES('27','31. Jänner 1993','XXVII','Cowboys','Bills','52','17')");
        database.execSQL("INSERT INTO " + databaseTableName + " VALUES('28','30. Jänner 1994','XXVIII','Cowboys','Bills','30','13')");
        database.execSQL("INSERT INTO " + databaseTableName + " VALUES('29','29. Jänner 1995','XXIX','Sf49ers','Chargers','49','26')");
        database.execSQL("INSERT INTO " + databaseTableName + " VALUES('30','28. Jänner 1996','XXX','Cowboys','Steelers','27','17')");
        database.execSQL("INSERT INTO " + databaseTableName + " VALUES('31','26. Jänner 1997','XXXI','Packers','Patriots','35','21')");
        database.execSQL("INSERT INTO " + databaseTableName + " VALUES('32','25. Jänner 1998','XXXII','Broncos','Packers','31','24')");
        database.execSQL("INSERT INTO " + databaseTableName + " VALUES('33','31. Jänner 1999','XXXIII','Broncos','Falcons','34','19')");
        database.execSQL("INSERT INTO " + databaseTableName + " VALUES('34','30. Jänner 2000','XXXIV','Rams','Titans','23','16')");
        database.execSQL("INSERT INTO " + databaseTableName + " VALUES('35','28. Jänner 2001','XXXV','Ravens','Giants','34','7')");
        database.execSQL("INSERT INTO " + databaseTableName + " VALUES('36','3. Februar 2002','XXXVI','Patriots','Rams','20','17')");
        database.execSQL("INSERT INTO " + databaseTableName + " VALUES('37','26. Jänner 2003','XXXVII','Buccaneers','Raiders','48','21')");
        database.execSQL("INSERT INTO " + databaseTableName + " VALUES('38','1. Februar 2004','XXXVIII','Patriots','Panthers','32','29')");
        database.execSQL("INSERT INTO " + databaseTableName + " VALUES('39','6. Februar 2005','XXXIX','Patriots','Eagles','24','21')");
        database.execSQL("INSERT INTO " + databaseTableName + " VALUES('40','5. Februar 2006','XL','Steelers','Seahawks','21','10')");
        database.execSQL("INSERT INTO " + databaseTableName + " VALUES('41','4. Februar 2007','XLI','Colts','Bears','29','17')");
        database.execSQL("INSERT INTO " + databaseTableName + " VALUES('42','3. Februar 2008','XLII','Giants','Patriots','17','14')");
        database.execSQL("INSERT INTO " + databaseTableName + " VALUES('43','1. Februar 2009','XLIII','Steelers','Cardinals','27','23')");
        database.execSQL("INSERT INTO " + databaseTableName + " VALUES('44','7. Februar 2010','XLIV','Saints','Colts','31','17')");
        database.execSQL("INSERT INTO " + databaseTableName + " VALUES('45','6. Februar 2011','XLV','Packers','Steelers','31','25')");
        database.execSQL("INSERT INTO " + databaseTableName + " VALUES('46','5. Februar 2012','XLVI','Giants','Patriots','21','17')");
        database.execSQL("INSERT INTO " + databaseTableName + " VALUES('47','3. Februar 2013','XLVII','Ravens','Sf49ers','34','31')");
        database.execSQL("INSERT INTO " + databaseTableName + " VALUES('48','2. Februar 2014','XLVIII','Seahawks','Broncos','43','8')");
        database.execSQL("INSERT INTO " + databaseTableName + " VALUES('49','1. Februar 2015','XLIX','Patriots','Seahawks','28','24')");
        database.execSQL("INSERT INTO " + databaseTableName + " VALUES('50','7. Februar 2016','L','Broncos','Panthers','24','10')");
        database.execSQL("INSERT INTO " + databaseTableName + " VALUES('51','5. Februar 2017','LI','Patriots','Falcons','34','28')");
        database.execSQL("INSERT INTO " + databaseTableName + " VALUES('52','4. Februar 2018','LII','Eagles','Patriots','41','33')");
        database.execSQL("INSERT INTO " + databaseTableName + " VALUES('53','4. Februar 2019','LIII','Rams','Patriots','3','13')");
        database.execSQL("INSERT INTO " + databaseTableName + " VALUES('54','2. Februar 2020','LIV','Chiefs','Sf49ers','31','20')");






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
        int x = rand.nextInt(maxLevel);
        x++;

        if(numberOfPlayedGames<maxLevel) {
            while (allUsedGames.contains(Integer.toString(x))) {
                x = rand.nextInt(maxLevel);
                x++;
            }
        }

        currantGame = Integer.toString(x);


        if (numberOfPlayedGames < maxLevel) {
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
            String sbtext = "Super Bowl " + romannumber;
            textViewSuperBowl.setText(sbtext);
            //textViewHomescore.setText(homescore);
            //textViewAwayscore.setText(awayscore);

            //textViewAwayscore.setTextSize(70);
            //textViewHomescore.setTextSize(70);

            setSuperBowlLogo();
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

    public void setSuperBowlLogo(){
        String sbLogo = "sb_" + romannumber.toLowerCase();
        int logoID1 = getResources().getIdentifier(sbLogo, "drawable", getPackageName());
        imageViewSuperBowl.setImageResource(logoID1);
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