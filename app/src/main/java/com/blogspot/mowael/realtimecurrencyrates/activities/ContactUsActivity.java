package com.blogspot.mowael.realtimecurrencyrates.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.blogspot.mowael.realtimecurrencyrates.R;

public class ContactUsActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton ibGitHub, ibLinkedIn, ibTwitter;
    Intent webIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        ibGitHub = (ImageButton) findViewById(R.id.ibGitHub);
        ibLinkedIn = (ImageButton) findViewById(R.id.ibLinkedIn);
        ibTwitter = (ImageButton) findViewById(R.id.ibTwitter);
        webIntent = new Intent(ContactUsActivity.this, WebActivity.class);

        ibGitHub.setOnClickListener(this);
        ibLinkedIn.setOnClickListener(this);
        ibTwitter.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.ibTwitter:
                if (isConnectingToInternet(this)) {
                    String twitterLink = "https://mobile.twitter.com/iMohamedWael";
                    webIntent.putExtra("link", twitterLink);
                    startActivity(webIntent);
                } else {
                    Toast.makeText(this, "please! check the internet connection", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.ibLinkedIn:
                if (isConnectingToInternet(this)) {
                    String linkedInLink = "https://eg.linkedin.com/in/mohamedwael";
                    webIntent.putExtra("link", linkedInLink);
                    startActivity(webIntent);
                } else {
                    Toast.makeText(this, "please! check the internet connection", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.ibGitHub:
                if (isConnectingToInternet(this)) {
                    String gitHubLink = "https://github.com/MohamedWael";
                    webIntent.putExtra("link", gitHubLink);
                    startActivity(webIntent);
                } else {
                    Toast.makeText(this, "please! check the internet connection", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }


    public boolean isConnectingToInternet(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
