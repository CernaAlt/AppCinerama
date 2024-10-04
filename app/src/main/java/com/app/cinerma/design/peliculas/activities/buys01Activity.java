package com.app.cinerma.design.peliculas.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.app.cinerma.R;

public class buys01Activity extends AppCompatActivity {

    Button btn_01, btn_02, btn_03;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_buys01);

        btn_01=findViewById(R.id.btn_Time01);
        btn_02=findViewById(R.id.btn_Time02);
        btn_03=findViewById(R.id.btn_Time03);

        btn_01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(buys01Activity.this, buys02Activity.class));

            }
        });

        btn_02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(buys01Activity.this, buys02Activity.class));

            }
        });

        btn_03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(buys01Activity.this, buys02Activity.class));
            }
        });
    }
}