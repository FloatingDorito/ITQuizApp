package student.inti.itquizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity implements View.OnClickListener {

    private TextView banner;
    private EditText eEmail;
    private Button resetPassword;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        mAuth = FirebaseAuth.getInstance();

        banner = (TextView) findViewById(R.id.banner);
        banner.setOnClickListener(this);

        resetPassword = (Button) findViewById(R.id.reset_password);
        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetUserPassword();
            }
        });

        eEmail = (EditText) findViewById(R.id.email);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.banner:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }

    private void resetUserPassword() {
        String email = eEmail.getText().toString().trim();

        if(email.isEmpty()){
            eEmail.setError("Email is Required");
            eEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            eEmail.setError("Please Provide a Valid Email Address");
            eEmail.requestFocus();
            return;
        }

        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                startActivity(new Intent(ForgotPassword.this, MainActivity.class));
                Toast.makeText(ForgotPassword.this,"Check Your Email to Reset Your Password", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(ForgotPassword.this,"There's Something Wrong Try Again",Toast.LENGTH_LONG).show();
            }
        });
    }
}
