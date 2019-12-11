package com.softwarica.megaquiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
public class activity_score extends AppCompatActivity {
    private TextView score,total;
    private Button btnDone;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

//         For fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        score=findViewById(R.id.tvScore);
        total=findViewById(R.id.tvTotal);
        btnDone=findViewById(R.id.btnDone);

        Intent intent = getIntent();
        score.setText(intent.getStringExtra("score"));
        total.setText(intent.getStringExtra("total"));
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(activity_score.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }
}