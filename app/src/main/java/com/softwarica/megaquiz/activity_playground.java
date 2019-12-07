package com.softwarica.megaquiz;

import android.animation.Animator;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.softwarica.megaquiz.Model.Question;

import java.util.ArrayList;
import java.util.List;

public class activity_playground extends AppCompatActivity {
    private TextView tvQuestion;
    private Button next;
    private LinearLayout optionConatiner;
    List<Question> list=new ArrayList<>();
    private int position=0;
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

        list.add(new Question("Where do you live?", "jungle", "house", "room", "earth","earth"));
        list.add(new Question("Where do you live in?", "jungle", "house", "room", "earth","room"));
        list.add(new Question("Where do you monkey live?", "jungle", "house", "room", "earth","earth"));
        list.add(new Question("Where do you put your cloth?", "daraz", "house", "room", "earth","earth"));


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count=0;
                playAnim(tvQuestion,0,list.get(position).getQuestion());
            }
        });
    }

    private void playAnim(final View view, final int value, final String data){
        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500).setStartDelay(100)
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
                    playAnim(optionConatiner.getChildAt(count),0,option);
                    count++;
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //data change
                if(value==0){
                    ((TextView) view).setText(data);
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
