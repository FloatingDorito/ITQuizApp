package student.inti.itquizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private FirebaseUser user;
    private DatabaseReference mReference;
    private String userID;
    private TextView mUsername,pUsername,pEmail,pScore;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pUsername = findViewById(R.id.pUsername);
        pEmail = findViewById(R.id.pEmail);
        pScore = findViewById(R.id.pHighScore);

        mDrawerLayout = findViewById(R.id.pDrawer);
        mNavigationView = findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        toggle = new ActionBarDrawerToggle(this,mDrawerLayout,toolbar,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true); //Enable the icon to be able to press
        toggle.syncState(); //Keep track if the drawer is open/close

        user = FirebaseAuth.getInstance().getCurrentUser();
        mReference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        //Get TextView from NavigationBar
        View header = mNavigationView.getHeaderView(0);
        mUsername = (TextView) header.findViewById(R.id.username);

        mReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //get the username from User.java Class
                User user = snapshot.getValue(User.class);
                //if the user exist in the database
                if(user != null){
                    String username = user.username;
                    String email = user.email;
                    String highScore = user.highScore;
                    mUsername.setText(username);
                    pEmail.setText(email);
                    pUsername.setText(username);
                    pScore.setText(highScore);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        mDrawerLayout.closeDrawer(GravityCompat.START);
        if(item.getItemId() == R.id.btnHome) {

            startActivity(new Intent(Profile.this,QuizMain.class));

        }else if(item.getItemId() == R.id.btnProfile){
            startActivity(new Intent(Profile.this,Profile.class));

        }else if(item.getItemId() == R.id.btnSettings){

        }else if (item.getItemId() == R.id.btnLogOut){

            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(Profile.this,MainActivity.class));
            Toast.makeText(Profile.this,"Sign Out Successfully", Toast.LENGTH_LONG).show();

        }
        return false;
    }
}
