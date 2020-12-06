package student.inti.itquizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class QuizResult extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference mReference;
    private String userID;
    private TextView totalQuestion, correct, wrong;
    private Button endQuiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);

        totalQuestion = (TextView) findViewById(R.id.totalQuestions);
        correct = (TextView) findViewById(R.id.correctAnswers);
        wrong = (TextView) findViewById(R.id.wrongAnswers);

        user = FirebaseAuth.getInstance().getCurrentUser();
        mReference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        //Get intent value from BeginQuiz.java (Total Questions, Correct Answers and Incorrect Answers)
        Intent i = getIntent();

        String tQuestion = i.getStringExtra("total");
        String tCorrect = i.getStringExtra("correct");
        String tWrong = i.getStringExtra("wrong");

        totalQuestion.setText(tQuestion);
        correct.setText(tCorrect);
        wrong.setText(tWrong);

        mReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //get the username from User.java Class
                User user = snapshot.getValue(User.class);
                //if the user exist in the database
                if (user != null) {
                    String highScore = user.highScore;
                    int i = Integer.parseInt(highScore);
                    int j = Integer.parseInt(tCorrect);
                    if (j >= i) {
                        snapshot.getRef().child("highScore").setValue(tCorrect);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        endQuiz = (Button) findViewById(R.id.btnHome);
        endQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(QuizResult.this, QuizMain.class));
                Toast.makeText(QuizResult.this,"Thanks for Playing!", Toast.LENGTH_LONG).show();
            }
        });

    }
}