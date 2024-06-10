package riss.com.redapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Donerhome extends AppCompatActivity {
Button Dbt_camps,Dbt_logout,bt_requirement,don,donpro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donerhome);
        Dbt_camps = findViewById(R.id.Dbt_camps);
        Dbt_logout=findViewById(R.id.Dbt_logout);
        bt_requirement=findViewById(R.id.bt_requirement);
        donpro=findViewById(R.id.donpro);
        don=findViewById(R.id.don);
        donpro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Donationupdate.class));
            }
        });

        Dbt_camps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), DViewCamps.class));
            }
        });
        bt_requirement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Viewrequirement.class));
            }
        });
        don.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Viewdonations.class));
            }
        });
        Dbt_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });
    }
}