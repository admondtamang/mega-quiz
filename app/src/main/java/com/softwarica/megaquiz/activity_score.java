package com.softwarica.megaquiz;

import android.os.Bundle;
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

        score.setText(String.valueOf(getIntent().getIntExtra("score",0)));
        total.setText("/"+String.valueOf(getIntent().getIntExtra("total",0)));

    }
}