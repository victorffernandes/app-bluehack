package com.example.user.app_bluehack;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Home extends AppCompatActivity {

    Button btt_mãe;
    Button btt_filho;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        btt_filho = (Button)findViewById(R.id.btt_filho);
        btt_mãe = (Button)findViewById(R.id.btt_pais);
        btt_filho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent IntentSon = new Intent(Home.this, SonActivity.class);
                startActivity(IntentSon);
            }
        });

        btt_mãe.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent IntentSon = new Intent(Home.this, MomActivity.class);
                startActivity(IntentSon);
            }
        });
    }
}
