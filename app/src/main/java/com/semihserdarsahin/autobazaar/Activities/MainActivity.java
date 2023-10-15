package com.semihserdarsahin.autobazaar.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.semihserdarsahin.autobazaar.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);

        mAuth=FirebaseAuth.getInstance();
    }
    public void browse(View view){
        Intent intent=new Intent(MainActivity.this,BrowseActivity.class);
        startActivity(intent);
    }

    public void sell(View view){
        Intent intent=new Intent(MainActivity.this,SellingActivity.class);
        startActivity(intent);
    }

    public void settings(View view){
        Intent intent=new Intent(MainActivity.this,SettingsActivity.class);
        startActivity(intent);
    }

    public void contact(View view){
        Intent intent=new Intent(MainActivity.this,ContactActivity.class);
        startActivity(intent);
    }
    public void about(View view){
        Intent intent=new Intent(MainActivity.this,AboutActivity.class);
        startActivity(intent);
    }
    public void exit(View view){
        mAuth.signOut();
        Intent intent=new Intent(MainActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }
}