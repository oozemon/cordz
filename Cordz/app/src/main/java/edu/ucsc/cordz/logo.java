package edu.ucsc.cordz;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class logo extends AppCompatActivity {
    private static int LOGO_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent loginIntent = new Intent(logo.this, LoginActivity.class);
                startActivity(loginIntent);
                finish();
            }
        },LOGO_TIME_OUT);
    }
}
