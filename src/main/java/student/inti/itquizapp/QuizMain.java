package student.inti.itquizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

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

public class QuizMain extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseUser user;
    private DatabaseReference mReference;
    private String userID;
    private TextView mUsername;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView mNavigationView;
    private Button begin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        begin = (Button) findViewById(R.id.btnBegin);
        begin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(QuizMain.this,BeginQuiz.class));
                Toast.makeText(QuizMain.this,"Let's Begin!", Toast.LENGTH_LONG).show();
            }
        });

        mDrawerLayout = findViewById(R.id.drawer);
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
                    mUsername.setText(username);
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

            startActivity(new Intent(QuizMain.this,QuizMain.class));

        }else if(item.getItemId() == R.id.btnProfile){
            startActivity(new Intent(QuizMain.this,Profile.class));

        }else if(item.getItemId() == R.id.btnSettings){

        }else if (item.getItemId() == R.id.btnLogOut){

            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(QuizMain.this,MainActivity.class));
            Toast.makeText(QuizMain.this,"Sign Out Successfully", Toast.LENGTH_LONG).show();

        }
        return false;
    }
    }