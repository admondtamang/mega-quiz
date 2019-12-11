package com.softwarica.megaquiz;

import android.animation.Animator;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.softwarica.megaquiz.Model.Question;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class activity_playground extends AppCompatActivity {
    private TextView tvQuestion,noOfQuestion;
    private Button next;
    private LinearLayout optionConatiner;
    List<Question> list=new ArrayList<>();
    private int position=0;
    private int score=0;
    private int count = 0; // Total no. of button/ option in question

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playground);

//         For fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        tvQuestion=findViewById(R.id.tvQuestion);
        next=findViewById(R.id.btnNext);
        optionConatiner=findViewById(R.id.optionContainer);
        noOfQuestion=findViewById(R.id.noOfQuestion);

        // Gets all data from database
        DbHelper db=new DbHelper(this);
        list=db.getAllQuestions();
        Collections.shuffle(list);

        // Option button event listner
        for(int i=0;i<4;i++){
            optionConatiner.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkAnswer((Button)v);
                }
            });
        }

        // Add first question on screen
        playAnim(tvQuestion,0,list.get(position).getQuestion());

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next.setEnabled(true);
                next.setAlpha(0.7f);
                enableOption(true);
                position++;

                String size, actual;
                actual = score + "/";
                size = list.size() + "";
                if(position==list.size()){
                    Intent intent=new Intent(activity_playground.this,activity_score.class);
                    intent.putExtra("score", actual);
                    intent.putExtra("total", size);
                    startActivity(intent);
                    overridePendingTransition(R.anim.goup, R.anim.godown);
                    return;
                }

                count=0;
                playAnim(tvQuestion,0,list.get(position).getQuestion());
            }
        });
    }

    // Check if answer is correct or not

    public void checkAnswer(Button selectedOption){
        enableOption(false);
        next.setEnabled(true);
        next.setAlpha(1);
        if(selectedOption.getText().toString().equals(list.get(position).getAns())){
            //correct
            selectedOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00ff00")));
            score++;
        }else{
            //Incorrect
            selectedOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ff0000")));
            Button correctOption=(Button) optionConatiner.findViewWithTag(list.get(position).getAns());
            correctOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00ff00")));
        }
    }
    // Make button disable after answer is selected
    public void enableOption(boolean enable){
        for(int i=0;i<4;i++){
            optionConatiner.getChildAt(i).setEnabled(enable);
            if(enable){
                optionConatiner.getChildAt(i).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#fdd835")));

            }
        }
    }


    // Animation start
    private void playAnim(final View view, final int value, final String data){
        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500).setStartDelay(10)
                .setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                if(value==0 && count<4){
                String option="";
                    if(count==0){
                        option=list.get(position).getOption1();
                    }else if(count==1){
                        option=list.get(position).getOption2();
                    }else if(count==2){
                        option=list.get(position).getOption3();
                    }else if(count==3) {
                        option=list.get(position).getOption4();
                    }
                    next.setEnabled(false);
                    playAnim(optionConatiner.getChildAt(count),0,option);
                    count++;
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //data change
                if(value==0){
                    try{
                        ((TextView) view).setText(data);
                        noOfQuestion.setText(position+1 +"/"+list.size());
                    }catch (ClassCastException e){
                        ((Button) view).setText(data);
                    }
                    view.setTag(data);
                    playAnim(view,1,data);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }
}
