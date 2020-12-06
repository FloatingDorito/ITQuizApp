package student.inti.itquizapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import student.inti.itquizapp.Model.Question;

public class BeginQuiz extends AppCompatActivity {

    private Button option1,option2,option3,option4;
    private TextView mQuestion,timer;
    private DatabaseReference mReference;
    int total = 0;
    int correct = 0;
    int wrong = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_begin_quiz);

        option1 = (Button) findViewById(R.id.option_one);
        option2 = (Button) findViewById(R.id.option_two);
        option3 = (Button) findViewById(R.id.option_three);
        option4 = (Button) findViewById(R.id.option_four);

        mQuestion = (TextView) findViewById(R.id.txtQuestion);
        timer = (TextView) findViewById(R.id.txtTimer);

        updateQuestion();
        reverseTimer(40,timer);
    }

    private void updateQuestion() {

        //Depends on how many questions the Firebase is stored
        //Loop until all questions are answered
        total++;
        if(total>5){
            //View Result
            total--;
            Intent i = new Intent (BeginQuiz.this,QuizResult.class);
            i.putExtra("total",String.valueOf(total));
            i.putExtra("correct",String.valueOf(correct));
            i.putExtra("wrong",String.valueOf(wrong));
            startActivity(i);

        }else{
            mReference = FirebaseDatabase.getInstance().getReference().child("Questions").child(String.valueOf(total));
            mReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    //Get the values from Firebase and set into the TextView and Buttons
                    Question question = snapshot.getValue(Question.class);

                    mQuestion.setText(question.getQuestion());
                    option1.setText(question.getOption1());
                    option2.setText(question.getOption2());
                    option3.setText(question.getOption3());
                    option4.setText(question.getOption4());

                    option1.setOnClickListener(view -> {
                        //If the Answer is Correct
                        if (option1.getText().toString().equals(question.getAnswer())){
                            option1.setBackgroundColor(Color.GREEN);
                            Handler handler = new Handler();
                            handler.postDelayed(() -> {
                                correct++;
                                option1.setBackgroundColor(Color.parseColor("#41436A"));
                                updateQuestion();
                            },1500);
                        } else{
                            //If the Answer is Incorrect
                            // Red for Incorrect and Show the Correct Answer in Green
                            wrong++;
                            option1.setBackgroundColor(Color.RED);
                            if (option2.getText().toString().equals(question.getAnswer())){
                                option2.setBackgroundColor(Color.GREEN);
                            } else if (option3.getText().toString().equals(question.getAnswer())){
                                option3.setBackgroundColor(Color.GREEN);
                            } else if (option4.getText().toString().equals(question.getAnswer())){
                                option4.setBackgroundColor(Color.GREEN);
                            }

                            Handler handler = new Handler();
                            handler.postDelayed(() -> {
                                option1.setBackgroundColor(Color.parseColor("#41436A"));
                                option2.setBackgroundColor(Color.parseColor("#41436A"));
                                option3.setBackgroundColor(Color.parseColor("#41436A"));
                                option4.setBackgroundColor(Color.parseColor("#41436A"));
                                    updateQuestion();
                            },1500);

                        }
                    });

                    option2.setOnClickListener(view -> {
                        //If the Answer is Correct
                        if (option2.getText().toString().equals(question.getAnswer())){
                            option2.setBackgroundColor(Color.GREEN);
                            Handler handler = new Handler();
                            handler.postDelayed(() -> {
                                correct++;
                                option2.setBackgroundColor(Color.parseColor("#41436A"));
                                updateQuestion();
                            },1500);
                        } else{
                            //If the Answer is Incorrect
                            // Red for Incorrect and Show the Correct Answer in Green
                            wrong++;
                            option2.setBackgroundColor(Color.RED);
                            if (option1.getText().toString().equals(question.getAnswer())){
                                option1.setBackgroundColor(Color.GREEN);
                            } else if (option3.getText().toString().equals(question.getAnswer())){
                                option3.setBackgroundColor(Color.GREEN);
                            } else if (option4.getText().toString().equals(question.getAnswer())){
                                option4.setBackgroundColor(Color.GREEN);
                            }

                            Handler handler = new Handler();
                            handler.postDelayed(() -> {
                                option1.setBackgroundColor(Color.parseColor("#41436A"));
                                option2.setBackgroundColor(Color.parseColor("#41436A"));
                                option3.setBackgroundColor(Color.parseColor("#41436A"));
                                option4.setBackgroundColor(Color.parseColor("#41436A"));
                                updateQuestion();
                            },1500);

                        }
                    });

                    option3.setOnClickListener(view -> {
                        //If the Answer is Correct
                        if (option3.getText().toString().equals(question.getAnswer())){
                            option3.setBackgroundColor(Color.GREEN);
                            Handler handler = new Handler();
                            handler.postDelayed(() -> {
                                correct++;
                                option3.setBackgroundColor(Color.parseColor("#41436A"));
                                updateQuestion();
                            },1500);
                        } else{
                            //If the Answer is Incorrect
                            // Red for Incorrect and Show the Correct Answer in Green
                            wrong++;
                            option3.setBackgroundColor(Color.RED);
                            if (option2.getText().toString().equals(question.getAnswer())){
                                option2.setBackgroundColor(Color.GREEN);
                            } else if (option1.getText().toString().equals(question.getAnswer())){
                                option1.setBackgroundColor(Color.GREEN);
                            } else if (option4.getText().toString().equals(question.getAnswer())){
                                option4.setBackgroundColor(Color.GREEN);
                            }

                            Handler handler = new Handler();
                            handler.postDelayed(() -> {
                                option1.setBackgroundColor(Color.parseColor("#41436A"));
                                option2.setBackgroundColor(Color.parseColor("#41436A"));
                                option3.setBackgroundColor(Color.parseColor("#41436A"));
                                option4.setBackgroundColor(Color.parseColor("#41436A"));
                                updateQuestion();
                            },1500);

                        }
                    });

                    option4.setOnClickListener(view -> {
                        //If the Answer is Correct
                        if (option4.getText().toString().equals(question.getAnswer())){
                            option4.setBackgroundColor(Color.GREEN);
                            Handler handler = new Handler();
                            handler.postDelayed(() -> {
                                correct++;
                                option4.setBackgroundColor(Color.parseColor("#41436A"));
                                updateQuestion();
                            },1500);
                        } else{
                            //If the Answer is Incorrect
                            // Red for Incorrect and Show the Correct Answer in Green
                            wrong++;
                            option4.setBackgroundColor(Color.RED);
                            if (option2.getText().toString().equals(question.getAnswer())){
                                option2.setBackgroundColor(Color.GREEN);
                            } else if (option3.getText().toString().equals(question.getAnswer())){
                                option3.setBackgroundColor(Color.GREEN);
                            } else if (option1.getText().toString().equals(question.getAnswer())){
                                option1.setBackgroundColor(Color.GREEN);
                            }

                            Handler handler = new Handler();
                            handler.postDelayed(() -> {
                                option1.setBackgroundColor(Color.parseColor("#41436A"));
                                option2.setBackgroundColor(Color.parseColor("#41436A"));
                                option3.setBackgroundColor(Color.parseColor("#41436A"));
                                option4.setBackgroundColor(Color.parseColor("#41436A"));
                                updateQuestion();
                            },1500);

                        }
                    });

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    public void reverseTimer(int seconds,TextView tv){
        new CountDownTimer(seconds * 1000+1000,1000){
            public void onTick(long millisUnitFinished){
                int seconds = (int) (millisUnitFinished/1000);
                int minutes = seconds/60;
                seconds = seconds%60;
                tv.setText(String.format(String.format("%02d", minutes)
                        + ":" + String.format("%02d", seconds)));

            }

            @Override
            public void onFinish() {
                tv.setText("Complete");
                total = 5;
                Intent myIntent = new Intent(BeginQuiz.this,QuizResult.class);
                myIntent.putExtra("total",String.valueOf(total));
                myIntent.putExtra("correct",String.valueOf(correct));
                myIntent.putExtra("wrong",String.valueOf(wrong));
                startActivity(myIntent);
            }

        }.start();
    }
}