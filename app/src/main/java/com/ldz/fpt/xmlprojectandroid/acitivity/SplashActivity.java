package com.ldz.fpt.xmlprojectandroid.acitivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.ldz.fpt.xmlprojectandroid.R;
import com.ldz.fpt.xmlprojectandroid.database.DBContext;
import com.ldz.fpt.xmlprojectandroid.model.User;

public class SplashActivity extends AppCompatActivity {
    //database
    private DBContext dbContext;
    //
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hide action bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        //
        setContentView(R.layout.activity_splash);
        //databse
        dbContext = new DBContext(this);
        //
        new MakeDelay().execute();
    }

    private class MakeDelay extends AsyncTask<Void, Void, Void> {
        //
        private Intent intent;

        @Override
        protected void onPreExecute() {
            user = dbContext.checkLoginSuccess();
            if (user!=null) {
                intent = new Intent(SplashActivity.this, HomeActivity.class);
            } else {
                intent = new Intent(SplashActivity.this, LoginActivity.class);
            }
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();
        }
    }
}
