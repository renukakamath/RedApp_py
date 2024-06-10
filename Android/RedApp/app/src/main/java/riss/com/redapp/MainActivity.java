package riss.com.redapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText ed_ip;
    Button bt_ip;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        ed_ip = findViewById(R.id.ed_ip);
        bt_ip = findViewById(R.id.bt_ip);

        ed_ip.setText(sh.getString("ip", "192.168.1.100:5050"));
        bt_ip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ipVal = ed_ip.getText().toString();
                if (ipVal.equalsIgnoreCase(""))
                    ed_ip.setError("Fill the field");
                else {
                    SharedPreferences.Editor ed = sh.edit();
                    ed.putString("ip", ipVal);
                    ed.commit();
                    startActivity(new Intent(getApplicationContext(), Login.class));
                }
            }
        });
    }
}
