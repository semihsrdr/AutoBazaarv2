package com.semihserdarsahin.autobazaar.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.semihserdarsahin.autobazaar.R;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

    }
    public void resume(View view){

    }
    public void github(View view){
        gotoUrl("https://github.com/semihsrdr");
    }
    public void mail(View view){
        gotoUrl("mailto:smhserdarshn52@gmail.com");

    }
    public void linkedin(View view){
        gotoUrl("https://www.linkedin.com/in/semih-serdar-%C5%9Fahin-5245b6266?utm_source=share&utm_campaign=share_via&utm_content=profile&utm_medium=android_app ");
    }
    public void gotoUrl(String s){
        Uri uri= Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }
}