package com.example.qa;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.app.AlertDialog;

import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    TextView qu;
    TextView t_qu;
    Button ans1,ans2, ans3,ans4, submit;
    Button restart ;
    int score=0;
    int total_qu = QuestionAnswer.question.length;
    int currentQuIndex=0;
    String selectedAnswer="";



    @SuppressLint({"SetTextI18n", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        t_qu=findViewById(R.id.total_question);
        qu=findViewById(R.id.question);
        ans1=findViewById(R.id.ans_1);
        ans2=findViewById(R.id.ans_2);
        ans3=findViewById(R.id.ans_3);
        ans4=findViewById(R.id.ans_4);
        submit=findViewById(R.id.submit);
        restart=findViewById(R.id.restart);

        ans1.setOnClickListener(this);
        ans2.setOnClickListener(this);
        ans3.setOnClickListener(this);
        ans4.setOnClickListener(this);
        submit.setOnClickListener(this);
        restart.setOnClickListener(this);

        t_qu.setText("total questions: "+total_qu);

    loudNewQuestion();


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void loudNewQuestion() {
        if (currentQuIndex == total_qu){
            finishQuiz();
            return;
        }
        qu.setText(QuestionAnswer.question[currentQuIndex]);
        ans1.setText(QuestionAnswer.choices[currentQuIndex][0]);
        ans2.setText(QuestionAnswer.choices[currentQuIndex][1]);
        ans3.setText(QuestionAnswer.choices[currentQuIndex][2]);
        ans4.setText(QuestionAnswer.choices[currentQuIndex][3]);

        selectedAnswer = "";



    }

    private void finishQuiz() {
        String passState;
        if (score>=total_qu*0.6){
            passState = "passed";

        }
        else passState="failed";
        new AlertDialog.Builder(this).setTitle(passState)
                .setMessage("your score is "+score+"out of"+total_qu)
                .setPositiveButton("restart",((dialog, i) -> restartQuiz()))
                .setCancelable(false)
                .show();




    }

    private void restartQuiz() {
        score =0;
        currentQuIndex=0;
        loudNewQuestion();
    }


    @Override
    public void onClick(View v) {
        ans1.setBackgroundColor(Color.WHITE);
        ans2.setBackgroundColor(Color.WHITE);
        ans3.setBackgroundColor(Color.WHITE);
        ans4.setBackgroundColor(Color.WHITE);

        Button clickedButton=(Button) v;
        if (clickedButton.getId() == R.id.submit){
            if (!selectedAnswer.isEmpty()){
                if(selectedAnswer.equals(QuestionAnswer.correctAnswer[currentQuIndex])){
                    score ++;
                    new AlertDialog.Builder(this).setTitle("Correct")
                            .setMessage("your score is "+score+"out of "+total_qu)
                            .show();
                }else {
                    clickedButton.setBackgroundColor(Color.MAGENTA);
                    new AlertDialog.Builder(this).setTitle("Wrong")
                            .setMessage("correct answer is: \n"+QuestionAnswer.correctAnswer[currentQuIndex])
                            .show();

                }
                currentQuIndex++;
                loudNewQuestion();
            }
        }else {
            selectedAnswer = clickedButton.getText().toString();
            clickedButton.setBackgroundColor(Color.YELLOW);

        }
        if (clickedButton == restart){
            restartQuiz();
        }



    }
}

