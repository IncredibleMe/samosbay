package com.example.jack.samost;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.nio.file.Files;

public class MainActivity extends AppCompatActivity {

    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = (Button) findViewById(R.id.button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, MainCatalogue.class);
                MainActivity.this.startActivity(myIntent);
            }
        });

    }
}
