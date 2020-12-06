package student.inti.itquizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterPlayer extends AppCompatActivity implements View.OnClickListener{

    private TextView banner,registerUser;
    private EditText eUsername, eEmail, ePassword;
    private ProgressBar mProgressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_player);

        mAuth = FirebaseAuth.getInstance();

        banner = (TextView) findViewById(R.id.banner);
        banner.setOnClickListener(this);

        registerUser = (Button) findViewById(R.id.register);
        registerUser.setOnClickListener(this);

        eUsername = (EditText) findViewById(R.id.username);
        eEmail = (EditText) findViewById(R.id.email);
        ePassword = (EditText) findViewById(R.id.password);

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.banner:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.register:
                registerPlayer();
                break;
        }
    }

    private void registerPlayer() {
        String email = eEmail.getText().toString().trim();
        String password = ePassword.getText().toString().trim();
        String username = eUsername.getText().toString().trim();
        String highScore = "0";

        //If the EditText is Empty, print Error Message
        if(username.isEmpty()){
            eUsername.setError("Username is required!");
            eUsername.requestFocus();
            return;
        }

        if(email.isEmpty()){
            eEmail.setError("Email is Required");
            eEmail.requestFocus();
            return;
        }
        //Check if the email is valid (e.g. with @gmail.com at the end or not) else error message
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            eEmail.setError("Please Provide a Valid Email Address");
            eEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            ePassword.setError("Password is Required");
            ePassword.requestFocus();
            return;
        }
        if(password.length() < 6){
            ePassword.setError("Password must be at Least 6 Letters");
            ePassword.requestFocus();
            return;
        }

        mProgressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            User user = new User(username, email, highScore);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(RegisterPlayer.this, "User has been registered successfully. Check Email to verify your account", Toast.LENGTH_LONG).show();
                                        mProgressBar.setVisibility(View.GONE);

                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                        user.sendEmailVerification();

                                        //redirect to Login Page
                                        startActivity(new Intent(RegisterPlayer.this, MainActivity.class));

                                    } else {
                                        Toast.makeText(RegisterPlayer.this, "Failed to Register. Try Again!", Toast.LENGTH_LONG).show();
                                        mProgressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(RegisterPlayer.this, "Failed to Register. Try Again!", Toast.LENGTH_LONG).show();
                            mProgressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }
}