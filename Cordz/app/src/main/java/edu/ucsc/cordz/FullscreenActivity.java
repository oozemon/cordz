package edu.ucsc.cordz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class FullscreenActivity extends AppCompatActivity {

    public Button b2;
    public Button b3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);

        b2 = findViewById(R.id.button2);
        b3 = findViewById(R.id.button3);


        b2.setOnClickListener(new View.OnClickListener() { //set onClick listener
            @Override
            public void onClick(View view) {
                Intent PianoIntent = new Intent(FullscreenActivity.this,PianoView.class);
                startActivity(PianoIntent);

            }
        });



        b3.setOnClickListener(new View.OnClickListener() { //set onClick listener
            public void onClick(View view) {

            }
        });


    }



}
